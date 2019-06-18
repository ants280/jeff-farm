package com.github.ants280.jeff.farm.ws.resources;

import com.github.ants280.jeff.farm.ws.JeffFarmWsException;
import com.github.ants280.jeff.farm.ws.dao.HiveDao;
import com.github.ants280.jeff.farm.ws.model.Hive;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/hive")
public class HiveResource
{
	private final HiveDao hiveDao;

	@Inject
	public HiveResource(HiveDao hiveDao)
	{
		this.hiveDao = hiveDao;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createHive(Hive hive)
	{
		int id = hiveDao.create(hive);

		return Response.ok(id).build();
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getHive(@PathParam("id") int id)
	{
		Hive hive = hiveDao.read(id);

		return Response.ok(hive).build();
	}

	@GET
	@Path("list")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getHivesList(@QueryParam("farmId") int farmId)
	{
		List<Hive> hives = hiveDao.readList(farmId);

		return Response.ok(hives).build();
	}

	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateHive(@PathParam("id") int id, Hive hive)
	{
		if (id != hive.getId())
		{
			throw new JeffFarmWsException("Id must be same as CrudItem id.");
		}

		hiveDao.update(hive);

		return Response.ok().build();
	}

	@DELETE
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteHive(@PathParam("id") int id)
	{
		hiveDao.delete(id);

		return Response.ok().build();
	}

	@GET
	@Path("{id}/canDelete")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response canDeleteHive(@PathParam("id") int id)
	{
		boolean canDelete = hiveDao.canDelete(id);

		return Response.ok(canDelete).build();
	}
}
