package de.alpharogroup.db.strategies;

import java.io.Serializable;
import java.util.List;

/**
 * The interface {@link DeleteStrategy} can interact if entities are deleted.
 *
 * @param <T>
 *            the type of the entity object
 * @param <PK>
 *            the type of the primary key from the entity object
 */
public interface DeleteStrategy<T, PK extends Serializable> extends Serializable {
	/**
	 * Deletes the given object from persistent storage in the database.
	 * 
	 * @param object
	 *            the persistent object
	 */
	void delete(T object);

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
}
