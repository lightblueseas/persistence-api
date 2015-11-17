package de.alpharogroup.domain;

import java.io.Serializable;

/**
 * Marker interface for domain objects.
 *
 * @param <K> the primary key type of the corresponding entity
 */
public interface DomainObject<K> extends Serializable {

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	K getId();

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	void setId(K id);
}
