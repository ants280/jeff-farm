package com.github.ants280.jeff.farm.ws.dao;

import com.github.ants280.jeff.farm.ws.PasswordGenerator;
import com.github.ants280.jeff.farm.ws.dao.api.crud.CrudItemDao;
import com.github.ants280.jeff.farm.ws.dao.api.parameter.IntegerSqlFunctionParameter;
import com.github.ants280.jeff.farm.ws.dao.api.parameter.StringSqlFunctionParameter;
import com.github.ants280.jeff.farm.ws.model.User;
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
	public UserDao(DataSource dataSource, PasswordGenerator passwordGenerator)
	{
		super(dataSource);
		
		this.passwordGenerator = passwordGenerator;
	}

	@Override
	public int create(User user)
	{
		String password
				= passwordGenerator.getHashedPassword(user.getPassword());

		return this.executeCreate(
				"create_user",
				Arrays.asList(
						new StringSqlFunctionParameter(User.USER_NAME_COLUMN, user.getUserName()),
						new StringSqlFunctionParameter(User.PASSWORD_COLUMN, password),
						new StringSqlFunctionParameter(User.FIRST_NAME_COLUMN, user.getFirstName()),
						new StringSqlFunctionParameter(User.LAST_NAME_COLUMN, user.getLastName())),
			-1); // (registered users cannot create users)
	}

	@Override
	public User read(int id)
	{
		return this.executeRead(
				"read_user",
				Collections.singletonList(
						new IntegerSqlFunctionParameter(User.ID_COLUMN, id)));
	}

	public User read(String userName)
	{
		return this.executeRead(
				"read_user_from_user_name",
				Collections.singletonList(
						new StringSqlFunctionParameter(User.USER_NAME_COLUMN, userName)));
	}

	@Override
	public List<User> readList(int parentId)
	{
		throw new IllegalArgumentException("Not allowed");
	}

	@Override
	public void update(int id, User user)
	{
		String password
				= passwordGenerator.getHashedPassword(user.getPassword());
		
		this.executeUpdate(
				"update_user",
				Arrays.asList(
						new IntegerSqlFunctionParameter(User.ID_COLUMN, id),
						new StringSqlFunctionParameter(User.PASSWORD_COLUMN, password),
						new StringSqlFunctionParameter(User.FIRST_NAME_COLUMN, user.getFirstName()),
						new StringSqlFunctionParameter(User.LAST_NAME_COLUMN, user.getLastName())),
				user.getId());
	}

	@Override
	public void delete(int id)
	{
		this.executeUpdate(
				"delete_user",
				Collections.singletonList(
						new IntegerSqlFunctionParameter(User.ID_COLUMN, id)),
				id);
	}

	@Override
	public boolean canDelete(int id)
	{
		return true;
	}

	@Override
	public User mapRow(ResultSet rs) throws SQLException
	{
		return new User()
				.setId(rs.getInt(User.ID_COLUMN))
				.setUserName(rs.getString(User.USER_NAME_COLUMN))
				.setFirstName(rs.getString(User.FIRST_NAME_COLUMN))
				.setLastName(rs.getString(User.LAST_NAME_COLUMN))
				.setCreatedTimestamp(rs.getTimestamp(User.CREATED_DATE_COLUMN))
				.setModifiedTimestamp(rs.getTimestamp(User.MODIFIED_DATE_COLUMN));
	}
}
