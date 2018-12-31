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
package de.alpharogroup.service.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import de.alpharogroup.domain.DomainObject;

/**
 * The Interface {@link DomainService} provide methods for crud processes.
 *
 * @param <PK>
 *            the generic type of the primary key
 * @param <DO>
 *            the generic type of the domain object
 */
public interface DomainService<PK extends Serializable, DO extends DomainObject<PK>>
{

	/**
	 * Creates an entity from the given domain object and persist it to the underlying database.
	 *
	 * @param domainObject
	 *            the domain object
	 * @return the domain object with the id from the entity.
	 */
	DO create(DO domainObject);

	/**
	 * Deletes an entity in the underlying database from the given domain object
	 *
	 * @param domainObject
	 *            the domain object to be deleted
	 */
	void delete(DO domainObject);

	/**
	 * Deletes an entity with the given id.
	 *
	 * @param id
	 *            the id
	 * @return the deleted entity as a domain object
	 */
	DO delete(PK id);

	/**
	 * Checks if an entry exists with the given id.
	 * 
	 * @param id
	 *            the id to check
	 * @return true, if an entry exists with the given id, otherwise false.
	 */
	boolean exists(PK id);

	/**
	 * Returns a list of all domain objects.
	 * 
	 * @return list of all domain objects
	 */
	List<DO> findAll();

	/**
	 * Persist all new domain objects in the given {@link Collection}.
	 * 
	 * @param domainObjects
	 *            the {@link Collection} of domain objects to persist
	 * @return the {@link Collection} with the id's of the persisted objects
	 */
	Collection<PK> persist(Collection<DO> domainObjects);

	/**
	 * Read an entity with the given id and maps it to a domain object that will be returned.
	 *
	 * @param id
	 *            the id
	 * @return the domain object
	 */
	DO read(PK id);

	/**
	 * Updates the given domain object to the underlying database.
	 *
	 * @param domainObject
	 *            the domain object
	 * @return the updated domain object
	 */
	DO update(DO domainObject);

}