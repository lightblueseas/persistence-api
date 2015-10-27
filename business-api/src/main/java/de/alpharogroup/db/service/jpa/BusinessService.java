package de.alpharogroup.db.service.jpa;

import java.io.Serializable;
import java.util.List;

import de.alpharogroup.db.entity.BaseEntity;

/**
 * The Interface BusinessService.
 * 
 * @param <T>
 *            the type of the domain object
 * @param <PK>
 *            the type of the primary key from the domain object
 */
public interface BusinessService<T extends BaseEntity<PK>, PK extends Serializable> extends
		Serializable {

	/**
	 * Delete all persistent objects in the given list.
	 * 
	 * @param objects
	 *            the list with the persistent objects to delete
	 */
	void delete(final List<T> objects);

	/**
	 * Deletes an object of a given Id. Will load the object internally so
	 * consider using delete (T obj) directly
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
	 * Merges all new objects in the given list.
	 * 
	 * @param objects
	 *            the list to save
	 * @return the list with the ids of the merged objects
	 */
	List<T> merge(List<T> objects);

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
