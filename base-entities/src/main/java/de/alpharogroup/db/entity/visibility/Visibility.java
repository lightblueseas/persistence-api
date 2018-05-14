package de.alpharogroup.db.entity.visibility;

/**
 * The interface {@link Visibility} can be implemented from an entity that needs a trigger for the
 * visibility.
 */
public interface Visibility
{

	/**
	 * Checks if the entity is visible
	 *
	 * @return true, if the entity is visible otherwise false
	 */
	boolean isVisible();

	/**
	 * Sets the visible flag
	 *
	 * @param visible
	 *            the new visible
	 */
	void setVisible(boolean visible);
}
