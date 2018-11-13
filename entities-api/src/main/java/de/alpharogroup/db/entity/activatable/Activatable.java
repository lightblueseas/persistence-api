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
package de.alpharogroup.db.entity.activatable;

/**
 * The interface {@link Activatable} can be implemented from an entity that
 * needs a trigger to set if the entity is active or not.
 */
public interface Activatable {

	/**
	 * Checks if the entity is active
	 *
	 * @return true, if the entity is active otherwise false
	 */
	boolean isActive();

	/**
	 * Sets the active flag
	 *
	 * @param active the new active
	 */
	void setActive(boolean active);
}
