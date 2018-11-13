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
package de.alpharogroup.db.entitymapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.dozer.Mapper;
import org.dozer.MappingException;

import de.alpharogroup.db.entity.BaseEntity;
import de.alpharogroup.domain.DomainObject;

/**
 * The Interface {@link EntityDOMapper} provides the methods for mapping
 * entities to domain objects and back.
 *
 * @param <E> the element type of the entity object
 * @param <DO> the generic type of the domain object
 */
public interface EntityDOMapper<E extends BaseEntity<?>, DO extends DomainObject<?>> {

	/**
	 * Gets the domain object class.
	 *
	 * @return the domain object class
	 */
	Class<DO> getDomainObjectClass();

	/**
	 * Gets the entity class.
	 *
	 * @return the entity class
	 */
	Class<E> getEntityClass();

	/**
	 * Gets the mapper.
	 *
	 * @return the mapper
	 */
	Mapper getMapper();

	/**
	 * Constructs new instances of destinationClass and performs mapping between
	 * from source.
	 *
	 * @param                  <T> the generic type of the destinationClass
	 * @param                  <S> the generic type of the source
	 * @param sources          the collection of source objects
	 * @param destinationClass the destination class
	 * @return the new instance of destinationClass mapped to source object.
	 * @throws MappingException is thrown if something goes wrong with the mapping
	 *                          process.
	 */
	default <T, S> List<T> map(final Collection<S> sources, final Class<T> destinationClass) throws MappingException {
		return MapperExtensions.map(getMapper(), sources, destinationClass);
	};

	/**
	 * Constructs new instance of destinationClass and performs mapping between from
	 * source.
	 *
	 * @param                  <T> the generic type of the destinationClass
	 * @param                  <S> the generic type of the source
	 * @param source           the source
	 * @param destinationClass the destination class
	 * @return the new instance of destinationClass mapped to source object.
	 * @throws MappingException is thrown if something goes wrong with the mapping
	 *                          process.
	 */
	default <T, S> T map(final S source, final Class<T> destinationClass) throws MappingException {
		return MapperExtensions.map(getMapper(), source, destinationClass);
	};

	/**
	 * Maps the given entity object to a domain object.
	 *
	 * @param entity the entity
	 * @return the domain object
	 */
	default DO toDomainObject(final E entity) {
		if (entity != null) {
			return getMapper().map(entity, getDomainObjectClass());
		}
		return null;
	};

	/**
	 * Maps the given list of entity objects to a list of domain objects.
	 *
	 * @param entities the entities
	 * @return the list of domain objects.
	 */
	default List<DO> toDomainObjects(final Collection<E> entities) {
		final List<DO> domainObjects = new ArrayList<>();
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
	 * @param domainObjects the list of domain objects
	 * @return the list of entity objects.
	 */
	default List<E> toEntities(final Collection<DO> domainObjects) {
		final List<E> entities = new ArrayList<>();
		if ((domainObjects != null) && !domainObjects.isEmpty()) {
			for (final DO domainObject : domainObjects) {
				entities.add(toEntity(domainObject));
			}
		}
		return entities;
	};

	/**
	 * Maps the given domain object to a entity object.
	 *
	 * @param domainObject the domain object
	 * @return the entity object
	 */
	default E toEntity(final DO domainObject) {
		if (domainObject != null) {
			return getMapper().map(domainObject, getEntityClass());
		}
		return null;
	};

}