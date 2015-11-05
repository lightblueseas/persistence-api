package de.alpharogroup.db.domain;

import java.io.Serializable;

/**
 * Marker interface for business objects.
 *
 * @param <K> the primary key type of the corresponding entity
 */
public interface BusinessObject<K> extends Serializable {

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
