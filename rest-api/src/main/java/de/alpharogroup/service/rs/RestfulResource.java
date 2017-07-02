/**
 * Copyright (C) 2015 Asterios Raptis
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.alpharogroup.service.rs;

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
public interface RestfulResource<PK, DO extends DomainObject<PK>>
{

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
	void delete(@PathParam("id") PK id);

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
