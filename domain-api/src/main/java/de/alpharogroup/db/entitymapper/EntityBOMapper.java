package de.alpharogroup.db.entitymapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.dozer.Mapper;
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
	 * Gets the mapper.
	 *
	 * @return the mapper
	 */
	Mapper getMapper();
	
	/**
	 * Gets the domain object class.
	 *
	 * @return the domain object class
	 */
	Class<BO> getDomainObjectClass();
	
	/**
	 * Gets the entity class.
	 *
	 * @return the entity class
	 */
	Class<E> getEntityClass();
	
	/**
	 * Maps the given entity object to a domain object.
	 *
	 * @param entity
	 *            the entity
	 * @return the domain object
	 */
	default BO toDomainObject(E entity) {
		if (entity != null) {
			return getMapper().map(entity, getDomainObjectClass());
		}
		return null;
	};

	/**
	 * Maps the given list of entity objects to a list of domain objects.
	 *
	 * @param entities
	 *            the entities
	 * @return the list of domain objects.
	 */
	default List<BO> toDomainObjects(Collection<E> entities) {
		final List<BO> domainObjects = new ArrayList<>();
		if ((entities != null) && !entities.isEmpty()) {
			for (final E entity : entities) {
				domainObjects.add(toDomainObject(entity));
			}
		}
		return domainObjects;
	};

	/**
	 * Maps the given list of domain objects to a list of entity objects.
	 *
	 * @param domainObjects
	 *            the list of domain objects
	 * @return the list of entity objects.
	 */
	default List<E> toEntities(Collection<BO> domainObjects) {
		final List<E> entities = new ArrayList<>();
		if ((domainObjects != null) && !domainObjects.isEmpty()) {
			for (final BO domainObject : domainObjects) {
				entities.add(toEntity(domainObject));
			}
		}
		return entities;
	};

	/**
	 * Maps the given domain object to a entity object.
	 *
	 * @param domainObject
	 *            the domain object
	 * @return the entity object
	 */
	default E toEntity(BO domainObject) {
		if(domainObject != null) {
			return getMapper().map(domainObject, getEntityClass());
		}
		return null;
	};

	/**
	 * Constructs new instance of destinationClass and performs mapping between
	 * from source.
	 *
	 * @param <T>            the generic type of the destinationClass
	 * @param <S>            the generic type of the source
	 * @param source            the source
	 * @param destinationClass            the destination class
	 * @return the new instance of destinationClass mapped to source object.
	 * @throws MappingException             is thrown if something goes wrong with the mapping process.
	 */
	default <T, S> T map(S source, Class<T> destinationClass) throws MappingException {		
		return getMapper().map(source, destinationClass);
	};
	
	/**
	 * Constructs new instances of destinationClass and performs mapping between
	 * from source.
	 *
	 * @param <T>            the generic type of the destinationClass
	 * @param <S>            the generic type of the source
	 * @param sources            the collection of source objects
	 * @param destinationClass            the destination class
	 * @return the new instance of destinationClass mapped to source object.
	 * @throws MappingException             is thrown if something goes wrong with the mapping process.
	 */
	default <T, S> List<T> map(Collection<S> sources, Class<T> destinationClass) throws MappingException {
		final List<T> destination = new ArrayList<>();
		if ((sources != null) && !sources.isEmpty()) {
			for(final S source : sources) {
				destination.add(map(source, destinationClass));
			}
		}
		return destination;
	};	

}