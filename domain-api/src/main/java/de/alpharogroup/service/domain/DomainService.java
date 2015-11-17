package de.alpharogroup.service.domain;

import java.io.Serializable;

import de.alpharogroup.domain.DomainObject;

/**
 * The Interface {@link DomainService} provide methods for crud processes.
 *
 * @param <PK>
 *            the generic type of the primary key
 * @param <BO>
 *            the generic type of the domain object
 */
public interface DomainService<PK extends Serializable, BO extends DomainObject<PK>> {

	/**
	 * Creates an entity from the given domain object and persist it to the underlying database.
	 *
	 * @param domainObject
	 *            the domain object
	 * @return the domain object with the id from the entity.
	 */
	BO create(BO domainObject);

	/**
	 * Deletes an entity with the given id.
	 *
	 * @param id
	 *            the id
	 */
	void delete(PK id);

	/**
	 * Read an entity with the given id and maps it to a domain object that will be returned.
	 *
	 * @param id
	 *            the id
	 * @return the domain object
	 */
	BO read(PK id);

	/**
	 * Updates the given domain object to the underlying database.
	 *
	 * @param domainObject
	 *            the domain object
	 */
	void update(BO domainObject);

}