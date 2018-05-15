package de.alpharogroup.service.api;

import java.io.Serializable;

import de.alpharogroup.db.entity.BaseEntity;
import de.alpharogroup.db.service.api.BusinessService;

public interface BaseNameEntityService<T extends BaseEntity<PK>, PK extends Serializable> extends BusinessService<T, PK>
{

	/**
	 * Find the entity object from the given name value.
	 * 
	 * @param nameValue
	 *            the name value
	 * @return the found entity object or null if not.
	 */
	T find(final String nameValue);

	/**
	 * Gets the or creates a new entity object
	 *
	 * @param value
	 *            the value
	 * @return the entity object
	 */
	T getOrCreateNewNameEntity(final String value);
}