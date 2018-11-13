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
package de.alpharogroup.db.entity.modify;

/**
 * The interface {@link LastModified} can be implemented from an entity that
 * needs the data of the point of time from its last modification
 *
 * @param <T> the generic type of time measurement
 * @param <U> the generic type of the user or account
 */
public interface LastModified<T, U> {

	/**
	 * Gets the point of time from the last modification
	 *
	 * @return the point of time from the last modification
	 */
	T getLastModified();

	/**
	 * Gets the user or account that last modified this entity
	 *
	 * @return the user or account that last modified this entity
	 */
	U getLastModifiedBy();

	/**
	 * Sets the point of time from the last modification
	 *
	 * @param created the new point of time from the last modification
	 */
	void setLastModified(T created);

	/**
	 * Sets the user or account that last modified this entity
	 *
	 * @param user the user or account that last modified this entity
	 */
	void setLastModifiedBy(U user);
}
