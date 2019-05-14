package com.github.ants280.jeff.farm.ws.dao;

import com.github.ants280.jeff.farm.ws.dao.api.CrudItemDao;
import com.github.ants280.jeff.farm.ws.dao.api.IntegerSqlFunctionParameter;
import com.github.ants280.jeff.farm.ws.dao.api.StringSqlFunctionParameter;
import com.github.ants280.jeff.farm.ws.model.Poultry;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sql.DataSource;

@Singleton
public class PoultryDao extends CrudItemDao<Poultry>
{
	private final LoginDao loginDao;

	@Inject
	public PoultryDao(DataSource dataSource, LoginDao loginDao)
	{
		super(dataSource);
		this.loginDao = loginDao;
	}

	@Override
	public int create(Poultry poultry)
	{
		return this.executeCreate(
			"create_poultry",
			Collections.singletonList(
				new StringSqlFunctionParameter(Poultry.NAME_COLUMN, poultry.getName())),
			loginDao.getUserId());
	}

	@Override
	public Poultry read(int id)
	{
		return this.executeRead(
			"read_poultry",
			Collections.singletonList(
				new IntegerSqlFunctionParameter(Poultry.ID_COLUMN, id)));
	}

	@Override
	public List<Poultry> readList(int parentId)
	{
		return this.executeReadList(
			"read_poultrys",
			Collections.emptyList());
	}

	@Override
	public void update(int id, Poultry poultry)
	{
		this.executeUpdate(
			"update_poultry",
			Arrays.asList(
				new IntegerSqlFunctionParameter(Poultry.ID_COLUMN, id),
				new StringSqlFunctionParameter(Poultry.NAME_COLUMN, poultry.getName())),
			loginDao.getUserId());
	}

	@Override
	public void delete(int id)
	{
		this.executeUpdate(
			"delete_poultry",
			Collections.singletonList(
				new IntegerSqlFunctionParameter(Poultry.ID_COLUMN, id)),
			loginDao.getUserId());
	}

	@Override
	public boolean canDelete(int id)
	{
		return this.canDelete("can_delete_poultry",
			Collections.singletonList(
				new IntegerSqlFunctionParameter(Poultry.ID_COLUMN, id)),
			Poultry.CAN_DELETE_ITEM);
	}

	@Override
	public Poultry mapRow(ResultSet rs) throws SQLException
	{
		return new Poultry()
			.setId(rs.getInt(Poultry.ID_COLUMN))
			.setName(rs.getString(Poultry.NAME_COLUMN))
			.setCreatedTimestamp(rs.getTimestamp(Poultry.CREATED_DATE_COLUMN))
			.setModifiedTimestamp(rs.getTimestamp(Poultry.MODIFIED_DATE_COLUMN));
	}
}
