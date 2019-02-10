package com.github.ants280.jeff.farm.ws.resources;

import com.github.ants280.jeff.farm.ws.dao.FarmDao;
import com.github.ants280.jeff.farm.ws.entity.Farm;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

// http://localhost:8080/jeff-farm-ws/resources/farm
@Resource
@Path("farm")
public class FarmResource
{
	private final FarmDao farmDao;

	@Inject
	public FarmResource(FarmDao farmDao)
	{
		this.farmDao = farmDao;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createFarm(Farm farm)
	{
		farmDao.create(farm);

		return Response.ok().build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFarms()
	{
		List<Farm> farms = farmDao.read();
		
		Farm farm1 = new Farm();
		farm1.setId(1);
		farm1.setName("farm1");
		farm1.setLocation("location1");
		Farm farm2 = new Farm();
		farm2.setId(2);
		farm2.setName("farm2");
		farm2.setLocation("location2");
		farms = Arrays.asList(farm1, farm2);

		return Response.ok(farms).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateFarm(Farm farm)
	{
		farmDao.update(farm);

		return Response.ok().build();
	}

	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteFarm(Farm farm)
	{
		farmDao.delete(farm);

		return Response.ok().build();
	}
}
