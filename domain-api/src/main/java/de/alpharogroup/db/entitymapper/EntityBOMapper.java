package de.alpharogroup.db.entitymapper;

import java.util.List;

import org.dozer.MappingException;

import de.alpharogroup.db.entity.BaseEntity;
import de.alpharogroup.domain.DomainObject;

/**
 * The Interface {@link EntityBOMapper} provides the methods for mapping
 * entities to domain objects and back.
 *
 * @param <E>
 *            the element type
 * @param <BO>
 *            the generic type
 */
public interface EntityBOMapper<E extends BaseEntity<?>, BO extends DomainObject<?>> {

	/**
	 * Maps the given entity object to a domain object.
	 *
	 * @param entity
	 *            the entity
	 * @return the domain object
	 */
	BO toDomainObject(E entity);

	/**
	 * Maps the given list of entity objects to a list of domain objects.
	 *
	 * @param entities
	 *            the entities
	 * @return the list of domain objects.
	 */
	List<BO> toDomainObjects(List<E> entities);

	/**
	 * Maps the given list of domain objects to a list of entity objects.
	 *
	 * @param domainObjects
	 *            the list of domain objects
	 * @return the list of entity objects.
	 */
	List<E> toEntities(List<BO> domainObjects);

	/**
	 * Maps the given domain object to a entity object.
	 *
	 * @param domainObject
	 *            the domain object
	 * @return the entity object
	 */
	E toEntity(BO domainObject);

	/**
	 * Constructs new instance of destinationClass and performs mapping between
	 * from source
	 *
	 * @param <T>
	 *            the generic type
	 * @param source
	 *            the source
	 * @param destinationClass
	 *            the destination class
	 * @return the new instance of destinationClass mapped to source object.
	 * @throws MappingException
	 *             is thrown if something goes wrong with the mapping process.
	 */
	<T, S> T map(S source, Class<T> destinationClass) throws MappingException;
	
	/**
	 * Constructs new instances of destinationClass and performs mapping between
	 * from source
	 *
	 * @param <T>
	 *            the generic type
	 * @param source
	 *            the list of source objects
	 * @param destinationClass
	 *            the destination class
	 * @return the new instance of destinationClass mapped to source object.
	 * @throws MappingException
	 *             is thrown if something goes wrong with the mapping process.
	 */
	<T, S> List<T> map(List<S> sources, Class<T> destinationClass) throws MappingException;
	
	

}