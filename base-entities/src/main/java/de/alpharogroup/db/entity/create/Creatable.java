package de.alpharogroup.db.entity.create;

/**
 * The interface {@link Creatable} can be implemented from an entity that needs the data of the
 * point of time from its creation
 *
 * @param <T>
 *            the generic type of time measurement
 * @param <U>
 *            the generic type of the user or account
 */
public interface Creatable<T, U>
{

	/**
	 * Gets the point of time from creation
	 *
	 * @return the point of time from creation
	 */
	T getCreated();

	/**
	 * Sets the point of time from creation
	 *
	 * @param created
	 *            the point of time from creation
	 */
	void setCreated(T created);

	/**
	 * Gets the user or account that created this entity
	 *
	 * @return the user or account that created this entity
	 */
	U getCreatedBy();

	/**
	 * Sets the user or account that created this entity
	 *
	 * @param user
	 *            the user or account that created this entity
	 */
	void setCreatedBy(U user);
}
