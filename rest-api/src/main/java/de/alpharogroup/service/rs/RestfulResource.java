package de.alpharogroup.service.rs;

import java.io.Serializable;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import de.alpharogroup.domain.DomainObject;

/**
 * The Interface {@link RestfulResource}.
 *
 * @param <PK>
 *            the generic type of the primary key
 * @param <DO>
 *            the generic type of the domain object
 */
public interface RestfulResource<PK extends Serializable, DO extends DomainObject<PK>> {

	/**
	 * Creates an entity from the given domain object and persist it to the underlying database.
	 *
	 * @param domainObject
	 *            the domain object
	 * @return the domain object with the id from the entity.
	 */
	@POST
	@Path("/")
	DO create(DO domainObject);

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
	 * Read an entity with the given id and maps it to a domain object that will be returned.
	 *
	 * @param id
	 *            the id
	 * @return the domain object
	 */
	@GET
	@Path("/{id}/")
	DO read(@PathParam("id") PK id);

	/**
	 * Updates the given domain object to the underlying database.
	 *
	 * @param domainObject
	 *            the domain object
	 */
	@PUT
	@Path("/")
	void update(DO domainObject);

}
