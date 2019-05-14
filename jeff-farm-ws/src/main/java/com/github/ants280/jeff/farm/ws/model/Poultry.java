package com.github.ants280.jeff.farm.ws.model;

public class Poultry extends CrudItem<Poultry>
{
	public static final String NAME_COLUMN = "name";
	private String name;

	@Override
	protected Poultry getThis()
	{
		return this;
	}

	public String getName()
	{
		return name;
	}

	public Poultry setName(String name)
	{
		this.name = name;
		return this;
	}
}
