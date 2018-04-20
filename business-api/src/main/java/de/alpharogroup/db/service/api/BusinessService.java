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
package de.alpharogroup.db.service.api;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import de.alpharogroup.db.entity.BaseEntity;

/**
 * The Interface BusinessService.
 * 
 * @param <T>
 *            the type of the domain object
 * @param <PK>
 *            the type of the primary key from the domain object
 */
public interface BusinessService<T extends BaseEntity<PK>, PK extends Serializable>
	extends
		Serializable
{

	/**
	 * Factory method for create a new instance of {@link Query} from the given sql string and
	 * execute a native sql statement for update or delete.
	 * 
	 * Note: this method delegates to the underlying entity manager.
	 * 
	 * @param sqlString
	 *            a native SQL query string
	 * @return the new {@link Query} instance
	 */
	Query createNativeQuery(String sqlString);

	/**
	 * Factory method for create a new instance of {@link Query} from the given sql string and
	 * execute a native sql statement.
	 * 
	 * Note: this method delegates to the underlying entity manager.
	 * 
	 * @param sqlString
	 *            a native SQL query string
	 * @param resultClass
	 *            the class of the resulting instance(s)
	 * 
	 * @return the new {@link Query} instance
	 */
	Query createNativeQuery(String sqlString, @SuppressWarnings("rawtypes") Class resultClass);

	/**
	 * Factory method for create a new instance of {@link Query} from the given sql string and
	 * execute a native sql statement.
	 * 
	 * Note: this method delegates to the underlying entity manager.
	 * 
	 * @param sqlString
	 *            a native SQL query string
	 * @param resultSetMapping
	 *            the name of the result set mapping
	 * @return the new {@link Query} instance
	 */
	Query createNativeQuery(String sqlString, String resultSetMapping);

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
	 * Returns a list of objects.
	 * 
	 * @return list of objects
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
	 *            the object to refresh.
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
	 * Save or update all given objects into database.
	 * 
	 * @param objects
	 *            the objects to save or update.
	 */
	void saveOrUpdate(List<T> objects);

	/**
	 * Save or update the given object into database.
	 * 
	 * @param object
	 *            the object to save or update.
	 */
	void saveOrUpdate(T object);

}
