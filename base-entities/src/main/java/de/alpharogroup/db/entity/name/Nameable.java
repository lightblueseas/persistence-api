package de.alpharogroup.db.entity.name;

/**
 * The interface {@link Nameable} can be implemented from an entity for a table with a single string value.
 *
 * @param <T>
 *            the generic type of the id
 */
public interface Nameable
{
	String getName();
	
	void setName(String name);
}