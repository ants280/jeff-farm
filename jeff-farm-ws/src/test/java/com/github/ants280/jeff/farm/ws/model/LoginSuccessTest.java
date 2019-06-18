package com.github.ants280.jeff.farm.ws.model;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public class LoginSuccessTest
{
	private Jsonb jsonb;

	@Before
	public void setUp()
	{
		jsonb = JsonbBuilder.create();
	}

	@Test
	public void testSerialize()
	{
		LoginSuccess loginSuccess = new LoginSuccess(true,61);
		String expectedValue = "{\"adminUser\":true,\"userId\":61}";

		String actualValue = jsonb.toJson(loginSuccess);

		assertThat("Pojo must have public getters to serialize properly.",
			actualValue, is(expectedValue));
	}
}
