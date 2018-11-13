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
package de.alpharogroup.db.entity.visibility;

/**
 * The interface {@link Visibility} can be implemented from an entity that needs
 * a trigger for the visibility.
 */
public interface Visibility {

	/**
	 * Checks if the entity is visible
	 *
	 * @return true, if the entity is visible otherwise false
	 */
	boolean isVisible();

	/**
	 * Sets the visible flag
	 *
	 * @param visible the new visible
	 */
	void setVisible(boolean visible);
}
