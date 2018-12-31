/**
 * Copyright (C) 2015 Asterios Raptis
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.alpharogroup.db.strategies.api;

import java.io.Serializable;
import java.util.List;

import de.alpharogroup.db.entity.BaseEntity;

/**
 * The interface {@link SaveOrUpdateStrategy} can interact if entities are saved or updated.
 *
 * @param <T>
 *            the type of the entity object
 * @param <PK>
 *            the type of the primary key from the entity object
 */
public interface SaveOrUpdateStrategy<T extends BaseEntity<PK>, PK extends Serializable>
	extends
		Serializable
{

	/**
	 * Save all new entities in the given list.
	 *
	 * @param entities
	 *            the list to save
	 * @return the list with the primary keys of the saved entities
	 */
	List<PK> save(List<T> entities);

	/**
	 * Persist the given entity into database.
	 *
	 * @param entity
	 *            the new instance to save.
	 * @return the id of the saved entity
	 */
	PK save(T entity);

	/**
	 * Save or update all transient entities in the given list.
	 *
	 * @param entities
	 *            the transient entities
	 */
	void saveOrUpdate(List<T> entities);

	/**
	 * Save or update the given persistent entity.
	 *
	 * @param entity
	 *            the transient entity to save or update.
	 */
	void saveOrUpdate(T entity);

	/**
	 * Update all transient entities in the given list.
	 *
	 * @param entities
	 *            the transient entities to update.
	 */
	void update(List<T> entities);

	/**
	 * Update changes made to the given entity.
	 *
	 * @param entity
	 *            the transient entity to update.
	 */
	void update(T entity);
}
