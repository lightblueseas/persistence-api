package de.alpharogroup.db.entitymapper;

import java.util.List;

import de.alpharogroup.db.domain.BusinessObject;
import de.alpharogroup.db.entity.BaseEntity;

/**
 * The Interface {@link EntityBOMapper} provides the methods for mapping entities to business
 * objects and back.
 *
 * @param <E>
 *            the element type
 * @param <BO>
 *            the generic type
 */
public interface EntityBOMapper<E extends BaseEntity<?>, BO extends BusinessObject<?>> {

	/**
	 * Maps the given entity object to a business object.
	 *
	 * @param entity
	 *            the entity
	 * @return the business object
	 */
	BO toBusinessObject(E entity);

	/**
	 * Maps the given list of entity objects to a list of business objects.
	 *
	 * @param entities
	 *            the entities
	 * @return the list of business objects.
	 */
	List<BO> toBusinessObjects(List<E> entities);

	/**
	 * Maps the given list of business objects to a list of entity objects.
	 *
	 * @param businessObjects
	 *            the list of business objects
	 * @return the list of entity objects.
	 */
	List<E> toEntities(List<BO> businessObjects);

	/**
	 * Maps the given business object to a entity object.
	 *
	 * @param businessObject
	 *            the business object
	 * @return the entity object
	 */
	E toEntity(BO businessObject);

}