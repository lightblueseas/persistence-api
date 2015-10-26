package de.alpharogroup.db.domain;

import java.io.Serializable;

/**
 * Marker interface for business objects.
 *
 * @param <K> the primary key type of the corresponding entity
 */
public interface BusinessObject<K> extends Serializable {
	K getId();
	void setId(K id);
}
