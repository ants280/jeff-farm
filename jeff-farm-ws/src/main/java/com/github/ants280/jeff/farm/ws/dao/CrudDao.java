package com.github.ants280.jeff.farm.ws.dao;

import com.github.ants280.jeff.farm.ws.model.CrudItem;
import java.util.List;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface CrudDao<T extends CrudItem>
{
	public int create(T entity);

	public T read(int id);
	
	public List<T> readList(int parentId);

	public void update(T entity);

	public void delete(int id);
	
	public boolean canDelete(int id);
}
