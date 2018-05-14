package de.alpharogroup.db.entity.modify;

/**
 * The interface {@link LastModified} can be implemented from an entity that needs the data of the
 * point of time from its last modification
 *
 * @param <T>
 *            the generic type of time measurement
 * @param <U>
 *            the generic type of the user or account
 */
public interface LastModified<T, U>
{

	/**
	 * Gets the point of time from the last modification
	 *
	 * @return the point of time from the last modification
	 */
	T getLastModified();

	/**
	 * Sets the point of time from the last modification
	 *
	 * @param created
	 *            the new point of time from the last modification
	 */
	void setLastModified(T created);

	/**
	 * Gets the user or account that last modified this entity
	 *
	 * @return the user or account that last modified this entity
	 */
	U getLastModifiedBy();

	/**
	 * Sets the user or account that last modified this entity
	 *
	 * @param user
	 *            the user or account that last modified this entity
	 */
	void setLastModifiedBy(U user);
}
