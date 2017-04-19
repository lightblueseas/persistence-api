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
package de.alpharogroup.db.strategies;

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
	 * Save all new objects in the given list.
	 * 
	 * @param objects
	 *            the list to save
	 * @return the list with the ids of the saved objects
	 */
	List<PK> save(List<T> objects);

	/**
	 * Persist the given object into database.
	 * 
	 * @param object
	 *            the new instance to save.
	 * @return the id of the saved object
	 */
	PK save(T object);

	/**
	 * Save or update all transient objects in the given list.
	 * 
	 * @param objects
	 *            the transient objects
	 */
	void saveOrUpdate(List<T> objects);

	/**
	 * Save or update the given persistent object.
	 * 
	 * @param object
	 *            the transient object to save or update.
	 */
	void saveOrUpdate(T object);

	/**
	 * Update all transient objects in the given list.
	 * 
	 * @param objects
	 *            the transient objects to update.
	 */
	void update(List<T> objects);

	/**
	 * Update changes made to the given object.
	 * 
	 * @param object
	 *            the transient object to update.
	 */
	void update(T object);
}
