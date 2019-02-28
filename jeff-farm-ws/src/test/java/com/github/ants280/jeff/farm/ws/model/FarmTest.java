package com.github.ants280.jeff.farm.ws.model;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import static com.github.ants280.jeff.farm.ws.JsonProvider.OBJECT_MAPPER;

public class FarmTest
{
	@Test
	public void testGetDisplayValue()
	{
		String name = "cool farm";
		Farm farm = new Farm(-1, name, null, null, null);

		String displayValue = farm.getDisplayValue();

		assertEquals(name, displayValue);
	}

	@Test
	public void testGetName()
	{
		String name = "boring farm";
		Farm farm = new Farm(-1, name, null, null, null);

		String name1 = farm.getName();

		assertEquals(name, name1);
	}

	@Test
	public void testGetLocation()
	{
		String location = "somewhere";
		Farm farm = new Farm(-1, null, location, null, null);

		String location1 = farm.getLocation();

		assertEquals(location, location1);
	}

	@Test
	public void testSerialize() throws IOException
	{
		int id = 1;
		String name = "name1";
		String location = "location1";
		Timestamp createdDate = Timestamp.valueOf(LocalDateTime.now());
		Timestamp modifiedDate = createdDate;
		Farm farm1 = new Farm(id, name, location, createdDate, modifiedDate);
//		Farm farm1 = new Farm(id, name, location, createdDate.toString(), modifiedDate.toString());
		
		String serializedFarm = OBJECT_MAPPER.writeValueAsString(farm1);

		assertEquals(true, serializedFarm.contains("id"));
		assertEquals(true, serializedFarm.contains("name"));
		assertEquals(true, serializedFarm.contains("location"));
		assertEquals(true, serializedFarm.contains("createdDate"));
		assertEquals(true, serializedFarm.contains("modifiedDate"));
		assertEquals(true, serializedFarm.contains("displayValue"));
	}

	@Test
	public void testDeserialize_minimal() throws IOException
	{
		String serializedFarm = "{\"id\":2,\"name\":\"name2\",\"location\":\"location2\"}";

		Farm farm2 = OBJECT_MAPPER.readValue(serializedFarm, Farm.class);

		assertEquals(2, farm2.getId());
		assertEquals("name2", farm2.getName());
		assertEquals("location2", farm2.getLocation());
		assertEquals(null, farm2.getCreatedDate());
		assertEquals(null, farm2.getModifiedDate());
		assertEquals("name2", farm2.getDisplayValue());
	}

	@Test
	public void testDeserialize_full() throws IOException
	{
		String serializedFarm = "{\"id\":3,\"createdDate\":\"2019-02-27T14:52:27-0800\",\"modifiedDate\":\"2019-02-27T14:52:27-0800\",\"name\":\"name3\",\"location\":\"location3\",\"displayValue\":\"name3\"}";

		Farm farm2 = OBJECT_MAPPER.readValue(serializedFarm, Farm.class);

		assertEquals(3, farm2.getId());
		assertEquals("name3", farm2.getName());
		assertEquals("location3", farm2.getLocation());
		assertEquals(null, farm2.getCreatedDate());
		assertEquals(null, farm2.getModifiedDate());
		assertEquals("name3", farm2.getDisplayValue());
	}
}
