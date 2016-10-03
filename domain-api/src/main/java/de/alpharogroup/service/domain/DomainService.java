package de.alpharogroup.service.domain;

import java.io.Serializable;
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
public interface DomainService<PK extends Serializable, DO extends DomainObject<PK>> {

	/**
	 * Creates an entity from the given domain object and persist it to the underlying database.
	 *
	 * @param domainObject
	 *            the domain object
	 * @return the domain object with the id from the entity.
	 */
	DO create(DO domainObject);

	/**
	 * Deletes an entity with the given id.
	 *
	 * @param id
	 *            the id
	 * @return the deleted entity as a domain object
	 */
	DO delete(PK id);

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
	

	/**
	 * Returns a list of all domain objects.
	 * 
	 * @return list of all domain objects
	 */
	List<DO> findAll();

}