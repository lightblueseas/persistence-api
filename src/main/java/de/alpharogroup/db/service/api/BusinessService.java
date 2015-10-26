package de.alpharogroup.db.service.api;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.type.Type;

/**
 * The Interface BusinessService.
 * 
 * @param <T>
 *            the type of the domain object
 * @param <PK>
 *            the type of the primary key from the domain object
 */
public interface BusinessService<T, PK extends Serializable> extends Serializable
{

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
	 * Returns a list of objects.
	 * 
	 * @return list of objects
	 */
	List<T> findAll();

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
	 * Retrieve a persisted object with a given id from the database.
	 * 
	 * @param id
	 *            the id
	 * @return An object of type T
	 */
	T get(PK id);

	/**
	 * Gets the hibernate session.
	 * 
	 * @return the hibernate session
	 */
	Session getSession();


	/**
	 * Sets the hibernate session.
	 * 
	 * @param session
	 *            The hibernate session to set.
	 */
	void setSession(Session session);

	/**
	 * Retrieve a persisted object with a given id from the database.
	 * 
	 * @param id
	 *            the id
	 * @return An object of type T
	 */
	T load(PK id);

	/**
	 * Merges the given object. @see Hibernate documentation.
	 * 
	 * @param object
	 *            the object
	 * @return the object
	 */
	T merge(final T object);

	/**
	 * Merge and flush.
	 * 
	 * @param object
	 *            the object
	 * @return the object
	 */
	T mergeAndFlush(final T object);

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

}
