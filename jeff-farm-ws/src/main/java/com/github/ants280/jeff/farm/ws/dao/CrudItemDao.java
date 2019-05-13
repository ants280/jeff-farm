package com.github.ants280.jeff.farm.ws.dao;

import com.github.ants280.jeff.farm.ws.model.CrudItem;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;

public abstract class CrudItemDao<T extends CrudItem> extends SqlFunctionDao
{
	public CrudItemDao(DataSource dataSource)
	{
		super(dataSource);
	}

	public abstract int create(T entity);

	public abstract T read(int id);

	public abstract List<T> readList(int parentId);

	public abstract void update(int id, T entity);

	public abstract void delete(int id);

	public abstract boolean canDelete(int id);

	protected abstract T mapRow(ResultSet rs) throws SQLException;

	protected int executeCreate(
		String functionName,
		List<SqlFunctionParameter> inParameters,
		int userId)
	{
		SqlFunctionCall<Integer> functionCall = new SingleCommandSqlFunctionCall<>(
			functionName,
			inParameters,
			new SimpleResultSetTransformer<>(
				true,
				resultSet -> resultSet.getInt(CrudItem.ID_COLUMN)));
		return this.execute(functionCall, userId).get(0);
	}

	protected T executeRead(
		String functionName,
		List<SqlFunctionParameter> inParameters,
		RowMapper<T> rowMapper)
	{
		SqlFunctionCall<T> functionCall = new SingleCommandSqlFunctionCall<>(
			functionName,
			inParameters,
			new SimpleResultSetTransformer<>(false, rowMapper));
		return this.execute(functionCall, null).get(0);
	}

	protected List<T> executeReadList(
		String functionName,
		List<SqlFunctionParameter> inParameters,
		RowMapper<T> rowMapper)
	{
		SqlFunctionCall<T> functionCall = new SingleCommandSqlFunctionCall<>(
			functionName,
			inParameters,
			new SimpleResultSetTransformer<>(false, rowMapper));
		return this.execute(functionCall, null);
	}

	protected void executeUpdate( // and delete
		String functionName,
		List<SqlFunctionParameter> inParameters,
		int userId)
	{
		SqlFunctionCall<T> functionCall = new SingleCommandSqlFunctionCall<>(
			functionName,
			inParameters,
			new SimpleResultSetTransformer<>(false, null)); // TODO: should expectSingleRecord be false here?
		this.execute(functionCall, userId); // TODO: should this be checked? (also in crudItemGroupDao update & delete
	}

	protected boolean canDelete(
		String functionName,
		List<SqlFunctionParameter> inParameters,
		String outParameterName)
	{
		SqlFunctionCall<Boolean> functionCall = new SingleCommandSqlFunctionCall<>(
			functionName,
			inParameters,
			new SimpleResultSetTransformer<>(
				true,
				resultSet -> resultSet.getBoolean(outParameterName)));
		return this.execute(functionCall, null).get(0);
	}
}
