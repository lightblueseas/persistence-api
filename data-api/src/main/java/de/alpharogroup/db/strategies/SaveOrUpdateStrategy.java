package de.alpharogroup.db.strategies;

import java.io.Serializable;
import java.util.List;

/**
 * The interface {@link SaveOrUpdateStrategy} can interact if entities are saved
 * or updated.
 *
 * @param <T>
 *            the type of the entity object
 * @param <PK>
 *            the type of the primary key from the entity object
 */
public interface SaveOrUpdateStrategy<T, PK extends Serializable> extends Serializable {

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
