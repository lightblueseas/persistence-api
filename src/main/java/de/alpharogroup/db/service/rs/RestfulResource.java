package de.alpharogroup.db.service.rs;

import java.io.Serializable;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import de.alpharogroup.db.domain.BusinessObject;

public interface RestfulResource<PK extends Serializable, BO extends BusinessObject<PK>> {
	@GET
	@Path("/{id}/")
	BO read(@PathParam("id") PK id);

	@POST
	@Path("/")
	BO create(BO businessObject);

	@PUT
	@Path("/")
	void update(BO businessObject);

	@DELETE
	@Path("/{id}/")
	void delete(PK id);

}
