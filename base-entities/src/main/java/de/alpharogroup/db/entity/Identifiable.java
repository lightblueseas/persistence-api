package de.alpharogroup.db.entity;

/**
 * The interface {@link Identifiable} can be implemented from an entity that have to be as it name
 * says identifiable.
 *
 * @param <PK>
 *            the generic type of the identifier
 */
public interface Identifiable<PK>
{

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	PK getId();

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	void setId(final PK id);
}
