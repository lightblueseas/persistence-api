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
 * The interface {@link DeleteStrategy} can interact if entities are deleted.
 *
 * @param <T>
 *            the type of the entity object
 * @param <PK>
 *            the type of the primary key from the entity object
 */
public interface DeleteStrategy<T extends BaseEntity<PK>, PK extends Serializable>
	extends
		Serializable
{
	/**
	 * Delete all persistent entities in the given list.
	 *
	 * @param entities
	 *            the list with the persistent entities to delete
	 */
	void delete(final List<T> entities);

	/**
	 * Deletes an entity of a given Id. Will load the entity internally so consider using delete (T
	 * entity) directly
	 *
	 * @param id
	 *            the id
	 */
	void delete(PK id);

	/**
	 * Deletes the given entity from persistent storage in the database.
	 *
	 * @param entity
	 *            the persistent entity
	 */
	void delete(T entity);
}
