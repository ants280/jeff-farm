package com.github.ants280.jeff.farm.ws.dao;

import java.util.List;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface BaseDao<T>
{
	public int create(T entity);
	
	public List<T> read();
	
	public void update(T entity);
	
	public void delete(T entity);
}
