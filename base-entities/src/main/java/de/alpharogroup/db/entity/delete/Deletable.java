package de.alpharogroup.db.entity.delete;

/**
 * The interface {@link Deletable} can be implemented from an entity that needs the data of the
 * point of time from its deletion
 *
 * @param <T>
 *            the generic type of time measurement
 * @param <U>
 *            the generic type of the user or account
 */
public interface Deletable<T, U>
{

	/**
	 * Gets the point of time from deletion
	 *
	 * @return the point of time from deletion
	 */
	T getDeleted();

	/**
	 * Sets the point of time from deletion
	 *
	 * @param created
	 *            the point of time from deletion
	 */
	void setDeleted(T created);

	/**
	 * Gets the user or account that deleted this entity
	 *
	 * @return the user or account that deleted this entity
	 */
	U getDeletedBy();

	/**
	 * Sets the user or account that deleted this entity
	 *
	 * @param user
	 *            the user or account that deleted this entity
	 */
	void setDeletedBy(U user);
}
