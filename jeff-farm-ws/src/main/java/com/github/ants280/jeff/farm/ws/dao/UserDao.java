package com.github.ants280.jeff.farm.ws.dao;

import com.github.ants280.jeff.farm.ws.JeffFarmWsException;
import com.github.ants280.jeff.farm.ws.PasswordGenerator;
import com.github.ants280.jeff.farm.ws.dao.api.call.SimpleCommandSqlFunctionCall;
import com.github.ants280.jeff.farm.ws.dao.api.call.SqlFunctionCall;
import com.github.ants280.jeff.farm.ws.dao.api.crud.CrudItemDao;
import com.github.ants280.jeff.farm.ws.dao.api.parameter.IntegerSqlFunctionParameter;
import com.github.ants280.jeff.farm.ws.dao.api.parameter.StringSqlFunctionParameter;
import com.github.ants280.jeff.farm.ws.dao.api.transformer.SimpleResultSetTransformer;
import com.github.ants280.jeff.farm.ws.model.CrudItem;
import com.github.ants280.jeff.farm.ws.model.User;
import com.github.ants280.jeff.farm.ws.model.UserPasswordReplacement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sql.DataSource;

@Singleton
public class UserDao extends CrudItemDao<User>
{
	private final PasswordGenerator passwordGenerator;

	@Inject
	public UserDao(
		DataSource dataSource,
		PasswordGenerator passwordGenerator,
		UserIdDao userIdDao)
	{
		super(dataSource, userIdDao);

		this.passwordGenerator = passwordGenerator;
	}

	@Override
	public int create(User user)
	{
		return this.execute(new SimpleCommandSqlFunctionCall<>("create_user",
			Arrays.asList(new StringSqlFunctionParameter(User.USER_NAME_COLUMN,
					user.getUserName()),
				new StringSqlFunctionParameter(User.PASSWORD_COLUMN,
					passwordGenerator.getHashedPassword(user.getPassword())),
				new StringSqlFunctionParameter(User.FIRST_NAME_COLUMN,
					user.getFirstName()),
				new StringSqlFunctionParameter(User.LAST_NAME_COLUMN,
					user.getLastName())),
			new SimpleResultSetTransformer<>(resultSet -> resultSet.getInt(
				CrudItem.ID_COLUMN)),
			null));
	}

	@Override
	public User read(int id)
	{
		return this.executeRead("read_user",
			Collections.singletonList(new IntegerSqlFunctionParameter(User.ID_COLUMN,
				id)));
	}

	public User read(String userName)
	{
		return this.execute(new SimpleCommandSqlFunctionCall<>(
			"read_user_from_user_name",
			Collections.singletonList(new StringSqlFunctionParameter(User.USER_NAME_COLUMN,
				userName)),
			new SimpleResultSetTransformer<>(this::mapRow),
			null));
	}

	@Override
	public List<User> readList(int parentId) // (user_id provided by SqlFunctionCall)
	{
		return this.executeReadList("read_users", Collections.emptyList());
	}

	@Override
	public void update(User user)
	{
		this.executeUpdate("update_user", Arrays.asList(
			new IntegerSqlFunctionParameter(User.ID_COLUMN, user.getId()),
			new StringSqlFunctionParameter(User.FIRST_NAME_COLUMN,
				user.getFirstName()),
			new StringSqlFunctionParameter(User.LAST_NAME_COLUMN,
				user.getLastName())));
	}

	public void updatePassword(UserPasswordReplacement userPasswordReplacement)
	{
		SqlFunctionCall passwordCheckingFunctionCall = new PasswordCheckingSqlFunctionCall(
			userPasswordReplacement.getId(),
			userPasswordReplacement.getOldPassword(),
			passwordGenerator,
			userIdDao);
		SqlFunctionCall updatePasswordFunctionCall = new SimpleCommandSqlFunctionCall<>(
			"update_user_password",
			Arrays.asList(
				new IntegerSqlFunctionParameter(
					UserPasswordReplacement.ID_COLUMN,
					userPasswordReplacement.getId()),
				new StringSqlFunctionParameter(
					User.PASSWORD_COLUMN,
					passwordGenerator.getHashedPassword(userPasswordReplacement.getNewPassword()))),
			null,
			userIdDao);
		this.execute(passwordCheckingFunctionCall, updatePasswordFunctionCall);
	}

	@Override
	public void delete(int id)
	{
		this.executeUpdate("delete_user",
			Collections.singletonList(new IntegerSqlFunctionParameter(User.ID_COLUMN,
				id)));
	}

	@Override
	public boolean canDelete(int id)
	{
		return this.canDelete("can_delete_user",
			Collections.singletonList(new IntegerSqlFunctionParameter(
				User.ID_COLUMN,
				id)),
			User.CAN_DELETE_ITEM);
	}

	@Override
	public User mapRow(ResultSet rs) throws SQLException
	{
		return new User().setId(rs.getInt(User.ID_COLUMN))
			.setUserName(rs.getString(User.USER_NAME_COLUMN))
			.setFirstName(rs.getString(User.FIRST_NAME_COLUMN))
			.setLastName(rs.getString(User.LAST_NAME_COLUMN))
			.setCreatedTimestamp(rs.getTimestamp(User.CREATED_DATE_COLUMN))
			.setModifiedTimestamp(rs.getTimestamp(User.MODIFIED_DATE_COLUMN));
	}

	private static class PasswordCheckingSqlFunctionCall // TODO: investigate combining with CrudItemInspectionGroupDao.ParentIdSettingSqlFunctionCall
		// [continued]: maybe call it "SideEffectSqlFunctionCall"
		//              --> User it to ensure a username is for a real user before calling
		//                  create_farm_permission (if not, throw a JeffFarmWsException)
		extends SimpleCommandSqlFunctionCall<String>
	{
		private static final String FUNCTION_NAME = "read_user_encrypted_password";
		private final String password;
		private final PasswordGenerator passwordGenerator;
		private final UserIdDao userIdDao;

		public PasswordCheckingSqlFunctionCall(
			int userId,
			String password,
			PasswordGenerator passwordGenerator,
			UserIdDao userIdDao)
		{
			super(
				FUNCTION_NAME,
				Collections.singletonList(new IntegerSqlFunctionParameter(
					User.ID_COLUMN,
					userId)),
				new SimpleResultSetTransformer<>(rs -> rs.getString(
					FUNCTION_NAME)),
				userIdDao);
			this.password = password;
			this.passwordGenerator = passwordGenerator;
			this.userIdDao = userIdDao;
		}

		@Override
		public void execute(PreparedStatement preparedStatement)
			throws SQLException
		{
			super.execute(preparedStatement);
			String encryptedUserPassword = this.getResult();

			if (!userIdDao.hasAdimnRole()
				// Note that this may take some time while the connection is open:
				&& !passwordGenerator.isStoredPassword(password, encryptedUserPassword))
			{
				throw new JeffFarmWsException("Incorrect old password.");
			}
		}
	}
}
