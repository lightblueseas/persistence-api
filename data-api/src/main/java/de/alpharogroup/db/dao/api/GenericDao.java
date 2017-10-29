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
package de.alpharogroup.db.dao.api;

import java.io.Serializable;
import java.util.List;

import de.alpharogroup.db.entity.BaseEntity;

/**
 * The interface {@link GenericDao} provide an API for database operations like insert, delete,
 * update and selections.
 *
 * @param <T>
 *            the type of the domain entity
 * @param <PK>
 *            the type of the primary key from the domain entity
 * @author Asterios Raptis
 */
public interface GenericDao<T extends BaseEntity<PK>, PK extends Serializable> extends Serializable
{

	/**
	 * Delete all persistent entities in the given list.
	 *
	 * @param entities
	 *            the list with the persistent entities to delete
	 */
	void delete(final List<T> entities);

	/**
	 * Deletes an entity of a given Id. Will load the entity internally so consider using delete (T
	 * obj) directly
	 *
	 * @param id
	 *            the id
	 */
	void delete(PK id);

	/**
	 * Deletes the given entity from persistent storage in the database.
	 *
	 * @param entity
	 *            the persistent entity
	 */
	void delete(T entity);

	/**
	 * Remove this instance from the session cache.
	 *
	 * @param entity
	 *            the entity to evict.
	 */
	void evict(T entity);

	/**
	 * Checks if an entry exists with the given id.
	 *
	 * @param id
	 *            the id to check
	 * @return true, if an entry exists with the given id, otherwise false.
	 */
	boolean exists(PK id);

	/**
	 * Returns a list of all persistent entities.
	 *
	 * @return list of all persistent entities
	 */
	List<T> findAll();

	/**
	 * Retrieve a persisted entity with a given id from the database.
	 *
	 * @param id
	 *            the id
	 * @return An entity of type T
	 */
	T get(PK id);

	/**
	 * Gets the class type.
	 *
	 * @return the class type
	 */
	Class<T> getType();

	/**
	 * Retrieve a persisted entity with a given id from the database.
	 *
	 * @param id
	 *            the id
	 * @return An entity of type T
	 */
	T load(PK id);

	/**
	 * Merges all new entities in the given list.
	 *
	 * @param entities
	 *            the list to save
	 * @return the list with the ids of the merged entities
	 */
	List<T> merge(List<T> entities);

	/**
	 * Merges the given entity. @see Hibernate documentation.
	 *
	 * @param entity
	 *            the entity
	 * @return the entity
	 */
	T merge(final T entity);

	/**
	 * Re-read the state of the given instance from the underlying database.
	 *
	 * @param entity
	 *            the entity to re-read.
	 */
	void refresh(final T entity);

	/**
	 * Save all new entities in the given list.
	 *
	 * @param entities
	 *            the list to save
	 * @return the list with the ids of the saved entities
	 */
	List<PK> save(List<T> entities);

	/**
	 * Persist the given entity into database.
	 *
	 * @param entity
	 *            the new instance to save.
	 * @return the id of the saved entity
	 */
	PK save(T entity);

	/**
	 * Save or update all transient entities in the given list.
	 *
	 * @param entities
	 *            the transient entities
	 */
	void saveOrUpdate(List<T> entities);

	/**
	 * Save or update the given persistent entity.
	 *
	 * @param entity
	 *            the transient entity to save or update.
	 */
	void saveOrUpdate(T entity);

	/**
	 * Update all transient entities in the given list.
	 *
	 * @param entities
	 *            the transient entities to update.
	 */
	void update(List<T> entities);

	/**
	 * Update changes made to the given entity.
	 *
	 * @param entity
	 *            the transient entity to update.
	 */
	void update(T entity);

}
