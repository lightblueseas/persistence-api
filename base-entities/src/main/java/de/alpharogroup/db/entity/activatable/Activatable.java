package de.alpharogroup.db.entity.activatable;

/**
 * The interface {@link Activatable} can be implemented from an entity that needs a trigger to set
 * if the entity is active or not.
 */
public interface Activatable
{

	/**
	 * Checks if the entity is active
	 *
	 * @return true, if the entity is active otherwise false
	 */
	boolean isActive();

	/**
	 * Sets the active flag
	 *
	 * @param active
	 *            the new active
	 */
	void setActive(boolean active);
}
