package de.alpharogroup.db.service.entitymapper;

import java.io.Serializable;

import de.alpharogroup.db.domain.BusinessObject;

/**
 * The Interface {@link BusinessMapperService} provide methods for crud processes.
 *
 * @param <PK>
 *            the generic type of the primary key
 * @param <BO>
 *            the generic type of the business object
 */
public interface BusinessMapperService<PK extends Serializable, BO extends BusinessObject<PK>> {

	/**
	 * Creates an entity from the given business object and persist it to the underlying database.
	 *
	 * @param businessObject
	 *            the business object
	 * @return the business object with the id from the entity.
	 */
	BO create(BO businessObject);

	/**
	 * Deletes an entity with the given id.
	 *
	 * @param id
	 *            the id
	 */
	void delete(PK id);

	/**
	 * Read an entity with the given id and maps it to a business object that will be returned.
	 *
	 * @param id
	 *            the id
	 * @return the business object
	 */
	BO read(PK id);

	/**
	 * Updates the given business object to the underlying database.
	 *
	 * @param businessObject
	 *            the business object
	 */
	void update(BO businessObject);

}