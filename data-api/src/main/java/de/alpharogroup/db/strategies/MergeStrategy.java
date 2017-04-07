package de.alpharogroup.db.strategies;

import java.io.Serializable;
import java.util.List;

/**
 * The interface {@link MergeStrategy} can interact if entities are merged.
 *
 * @param <T>
 *            the type of the entity object
 * @param <PK>
 *            the type of the primary key from the entity object
 */
public interface MergeStrategy<T, PK extends Serializable> extends Serializable {

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
	
}
