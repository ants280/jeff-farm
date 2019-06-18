package com.github.ants280.jeff.farm.ws.resources;

import com.github.ants280.jeff.farm.ws.JeffFarmWsException;
import com.github.ants280.jeff.farm.ws.dao.PoultryDao;
import com.github.ants280.jeff.farm.ws.model.Poultry;
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

@Path("/poultry")
public class PoultryResource
{
	private final PoultryDao poultryDao;

	@Inject
	public PoultryResource(PoultryDao poultryDao)
	{
		this.poultryDao = poultryDao;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createPoultry(Poultry poultry)
	{
		int id = poultryDao.create(poultry);

		return Response.ok(id).build();
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPoultry(@PathParam("id") int id)
	{
		Poultry poultry = poultryDao.read(id);

		return Response.ok(poultry).build();
	}

	@GET
	@Path("list")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPoultryList(@QueryParam("farmId") int farmId)
	{
		List<Poultry> poultryList = poultryDao.readList(farmId);

		return Response.ok(poultryList).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updatePoultry(Poultry poultry)
	{
		poultryDao.update(poultry);

		return Response.ok().build();
	}

	@DELETE
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deletePoultry(@PathParam("id") int id)
	{
		poultryDao.delete(id);

		return Response.ok().build();
	}

	@GET
	@Path("{id}/canDelete")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response canDeletePoultry(@PathParam("id") int id)
	{
		boolean canDelete = poultryDao.canDelete(id);

		return Response.ok(canDelete).build();
	}
}
