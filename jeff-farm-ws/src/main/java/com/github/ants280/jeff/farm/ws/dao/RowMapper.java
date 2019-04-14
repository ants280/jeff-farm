package com.github.ants280.jeff.farm.ws.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper<T>
{
	T getValue (ResultSet rs) throws SQLException;
}
