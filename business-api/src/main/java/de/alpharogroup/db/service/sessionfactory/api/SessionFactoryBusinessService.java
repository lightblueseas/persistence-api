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
package de.alpharogroup.db.service.sessionfactory.api;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.type.Type;

import de.alpharogroup.db.entity.BaseEntity;
import de.alpharogroup.db.service.api.BusinessService;

/**
 * The Interface BusinessService.
 * 
 * @param <T>
 *            the type of the domain object
 * @param <PK>
 *            the type of the primary key from the domain object
 */
public interface SessionFactoryBusinessService<T extends BaseEntity<PK>, PK extends Serializable>
		extends BusinessService<T, PK> {

	/**
	 * Delete all persistent objects in the given list and flush.
	 * 
	 * @param objects
	 *            the objects to delete
	 */
	void deleteAndFlush(final List<T> objects);

	/**
	 * Deletes an object of a given Id and flush after.
	 * 
	 * @param id
	 *            the id to delete
	 */
	void deleteAndFlush(final PK id);

	/**
	 * Delete and flush.
	 * 
	 * @param object
	 *            the object
	 */
	void deleteAndFlush(final T object);

	/**
	 * Returns a list from the result from the given hqlquery.
	 * 
	 * @param hqlQuery
	 *            the hql query.
	 * @param params
	 *            Array from the parameter for the query.
	 * @param paramValues
	 *            Array from the values from the parameters for the query.
	 * @param paramTypes
	 *            Array which defines what kind of type the the parameter is.
	 * @param start
	 *            Defines from where to start the result.
	 * @param count
	 *            Defines how much rows to get from the query.
	 * @return the result list.
	 */
	List<T> find(final String hqlQuery, final String[] params, final Object[] paramValues, final Type[] paramTypes,
			final Integer start, final Integer count);

	/**
	 * Hibernate wrapper.
	 * 
	 * @param criterion
	 *            the criterion
	 * @return list of objects
	 */
	List<T> findByCriteria(Criterion... criterion);

	/**
	 * Find by example.
	 * 
	 * @param exampleInstance
	 *            the example instance
	 * @param excludeProperty
	 *            the exclude property
	 * @return the list
	 */
	List<T> findByExample(T exampleInstance, String... excludeProperty);

	/**
	 * Flush.
	 */
	void flush();

	/**
	 * Gets the hibernate session.
	 * 
	 * @return the hibernate session
	 */
	Session getSession();

	/**
	 * Merge and flush.
	 * 
	 * @param object
	 *            the object
	 * @return the object
	 */
	T mergeAndFlush(final T object);

	/**
	 * Save all given objects into database and flush.
	 * 
	 * @param objects
	 *            the objects to save.
	 * @return the list with the ids of the saved objects.
	 */
	List<PK> saveAndFlush(final List<T> objects);

	/**
	 * Save the given object into database and flush.
	 * 
	 * @param object
	 *            the object to save.
	 * @return the id of the saved object
	 */
	PK saveAndFlush(final T object);

	/**
	 * Save or update all given objects into database and flush.
	 * 
	 * @param objects
	 *            the objects to save or update.
	 */
	void saveOrUpdateAndFlush(final List<T> objects);

	/**
	 * Save or update the given object into database and flush.
	 * 
	 * @param object
	 *            the object to save or update.
	 */
	void saveOrUpdateAndFlush(T object);

	/**
	 * Sets the hibernate session.
	 * 
	 * @param session
	 *            The hibernate session to set.
	 */
	void setSession(Session session);

}
