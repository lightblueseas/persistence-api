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
package de.alpharogroup.db.dao.sessionfactory;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.type.Type;

import de.alpharogroup.db.dao.api.GenericDao;
import de.alpharogroup.db.entity.BaseEntity;

/**
 * The Interface {@link SessionFactoryDao}.
 *
 * @param <T>
 *            the generic type of the entity object
 * @param <PK>
 *            the generic type of the primary key
 */
public interface SessionFactoryDao<T extends BaseEntity<PK>, PK extends Serializable>
	extends
		GenericDao<T, PK>
{

	/**
	 * Deletes an object of a given Id with the given session object. Note: Will load the object
	 * internally so consider using delete(T persistentObject) directly
	 *
	 * @param id
	 *            the id
	 * @param session
	 *            the session to use for the operation.
	 */
	void delete(PK id, Session session);

	/**
	 * Deletes the given object from persistent storage in the database with the given session
	 * object.
	 *
	 * @param object
	 *            the persistent object
	 * @param session
	 *            the session to use for the operation.
	 */
	void delete(T object, Session session);

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
	List<T> find(final String hqlQuery, final String[] params, final Object[] paramValues,
		final Type[] paramTypes, final Integer start, final Integer count);

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
	 * @return A list of objects
	 */
	List<T> findByExample(T exampleInstance, String... excludeProperty);

	/**
	 * Loads the given Object with the given session object.
	 *
	 * @param id
	 *            Id to load
	 * @param session
	 *            the session to use for the operation.
	 * @return An object of type T
	 */
	T get(PK id, Session session);

	/**
	 * Gets a query object from the given String.
	 *
	 * @param queryString
	 *            the hql query as String
	 * @return A query object
	 */
	Query getQuery(String queryString);

	/**
	 * Gets a query object from the given String with the given session object.
	 *
	 * @param queryString
	 *            the hql query as String
	 * @param session
	 *            the session to use for the operation.
	 * @return Query object
	 */
	Query getQuery(String queryString, Session session);

	/**
	 * Gets the hibernate session.
	 *
	 * @return the hibernate session
	 */
	Session getSession();

	/**
	 * Retrieve a persisted object with a given id from the database with the given session object.
	 *
	 * @param id
	 *            the id
	 * @param session
	 *            the session to use for the operation.
	 * @return an object of type T
	 */
	T load(PK id, Session session);

	/**
	 * Save the given object.
	 *
	 * @param object
	 *            the new instance to save.
	 * @param session
	 *            the session to use for the operation.
	 * @return the id of the saved object
	 */
	PK save(T object, Session session);

	/**
	 * Save or update the given transient object.
	 *
	 * @param object
	 *            the transient object to save or update.
	 * @param session
	 *            the session to use for the operation.
	 */
	void saveOrUpdate(T object, Session session);

	/**
	 * Sets the hibernate session.
	 *
	 * @param session
	 *            The hibernate session to set.
	 */
	void setSession(Session session);

	/**
	 * Updates the given object with the given session object.
	 *
	 * @param object
	 *            the transient object to save or update.
	 * @param session
	 *            the session to use for the operation.
	 */
	void update(T object, Session session);

}
