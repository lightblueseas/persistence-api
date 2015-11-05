package de.alpharogroup.db.service.rs;

import java.io.Serializable;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import de.alpharogroup.db.domain.BusinessObject;

/**
 * The Interface {@link RestfulResource}.
 *
 * @param <PK>
 *            the generic type of the primary key
 * @param <BO>
 *            the generic type of the business object
 */
public interface RestfulResource<PK extends Serializable, BO extends BusinessObject<PK>> {

	/**
	 * Creates an entity from the given business object and persist it to the underlying database.
	 *
	 * @param businessObject
	 *            the business object
	 * @return the business object with the id from the entity.
	 */
	@POST
	@Path("/")
	BO create(BO businessObject);

	/**
	 * Deletes an entity with the given id.
	 *
	 * @param id
	 *            the id
	 */
	@DELETE
	@Path("/{id}/")
	void delete(PK id);

	/**
	 * Read an entity with the given id and maps it to a business object that will be returned.
	 *
	 * @param id
	 *            the id
	 * @return the business object
	 */
	@GET
	@Path("/{id}/")
	BO read(@PathParam("id") PK id);

	/**
	 * Updates the given business object to the underlying database.
	 *
	 * @param businessObject
	 *            the business object
	 */
	@PUT
	@Path("/")
	void update(BO businessObject);

}
