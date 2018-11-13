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
package de.alpharogroup.db.entity.create;

/**
 * The interface {@link Creatable} can be implemented from an entity that needs
 * the data of the point of time from its creation
 *
 * @param <T> the generic type of time measurement
 * @param <U> the generic type of the user or account
 */
public interface Creatable<T, U> {

	/**
	 * Gets the point of time from creation
	 *
	 * @return the point of time from creation
	 */
	T getCreated();

	/**
	 * Gets the user or account that created this entity
	 *
	 * @return the user or account that created this entity
	 */
	U getCreatedBy();

	/**
	 * Sets the point of time from creation
	 *
	 * @param created the point of time from creation
	 */
	void setCreated(T created);

	/**
	 * Sets the user or account that created this entity
	 *
	 * @param user the user or account that created this entity
	 */
	void setCreatedBy(U user);
}
