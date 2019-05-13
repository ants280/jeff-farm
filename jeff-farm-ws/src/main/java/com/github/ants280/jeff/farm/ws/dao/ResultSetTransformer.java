package com.github.ants280.jeff.farm.ws.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface ResultSetTransformer<T>
{
	void accept(ResultSet resultSet) throws SQLException;

	List<T> getResults();
}
