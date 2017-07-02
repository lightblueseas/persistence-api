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
package de.alpharogroup.db.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import de.alpharogroup.db.entity.BaseEntity;

/**
 * The interface {@link GenericRepository} provide an API for database operations like insert,
 * delete, update and selections.
 *
 * @param <T>
 *            the type of the entity object
 * @param <PK>
 *            the type of the primary key from the entity object
 * @author Asterios Raptis
 */
public interface GenericRepository<T extends BaseEntity<PK>, PK extends Serializable>
	extends
		Serializable
{

	/**
	 * Persists the given entity.
	 *
	 * @param entity
	 *            the entity
	 */
	void create(T entity);

	/**
	 * Delete all persistent objects in the given list.
	 *
	 * @param objects
	 *            the list with the persistent objects to delete
	 */
	void delete(final List<T> objects);

	/**
	 * Deletes an object of a given Id. Will load the object internally so consider using delete (T
	 * obj) directly
	 *
	 * @param id
	 *            the id
	 */
	void delete(PK id);

	/**
	 * Deletes the given object from persistent storage in the database.
	 *
	 * @param object
	 *            the persistent object
	 */
	void delete(T object);

	/**
	 * Remove this instance from the session cache.
	 *
	 * @param object
	 *            the object to evict.
	 */
	void evict(T object);

	/**
	 * Checks if an entry exists with the given id.
	 *
	 * @param id
	 *            the id to check
	 * @return true, if an entry exists with the given id, otherwise false.
	 */
	boolean exists(PK id);

	/**
	 * Returns a list of all persistent objects.
	 *
	 * @return list of all persistent objects
	 */
	List<T> findAll();

	/**
	 * Retrieve a persisted object with a given id from the database.
	 *
	 * @param id
	 *            the id
	 * @return An object of type T
	 */
	T get(PK id);

	/**
	 * Gets the entity manager.
	 *
	 * @return the entity manager
	 */
	EntityManager getEntityManager();

	/**
	 * Gets a {@link Query} from the given hql query.
	 *
	 * @param hqlQuery
	 *            the hql query
	 * @return the {@link Query}
	 */
	Query getQuery(String hqlQuery);

	/**
	 * Gets the class type.
	 *
	 * @return the class type
	 */
	Class<T> getType();

	/**
	 * Retrieve a persisted object with a given id from the database.
	 *
	 * @param id
	 *            the id
	 * @return An object of type T
	 */
	T load(PK id);

	/**
	 * Merges all new objects in the given list.
	 *
	 * @param objects
	 *            the list to save
	 * @return the list with the ids of the merged objects
	 */
	List<T> merge(List<T> objects);

	/**
	 * Merges the given object. @see Hibernate documentation.
	 *
	 * @param object
	 *            the object
	 * @return the object
	 */
	T merge(final T object);

	/**
	 * Re-read the state of the given instance from the underlying database.
	 *
	 * @param object
	 *            the object to re-read.
	 */
	void refresh(final T object);

	/**
	 * Save all new objects in the given list.
	 *
	 * @param objects
	 *            the list to save
	 * @return the list with the ids of the saved objects
	 */
	List<PK> save(List<T> objects);

	/**
	 * Persist the given object into database.
	 *
	 * @param object
	 *            the new instance to save.
	 * @return the id of the saved object
	 */
	PK save(T object);

	/**
	 * Save or update all transient objects in the given list.
	 *
	 * @param objects
	 *            the transient objects
	 */
	void saveOrUpdate(List<T> objects);

	/**
	 * Save or update the given persistent object.
	 *
	 * @param object
	 *            the transient object to save or update.
	 */
	void saveOrUpdate(T object);

	/**
	 * Sets the entity manager.
	 *
	 * @param entityManager
	 *            the new entity manager
	 */
	void setEntityManager(EntityManager entityManager);

	/**
	 * Update all transient objects in the given list.
	 *
	 * @param objects
	 *            the transient objects to update.
	 */
	void update(List<T> objects);

	/**
	 * Update changes made to the given object.
	 *
	 * @param object
	 *            the transient object to update.
	 */
	void update(T object);

}
