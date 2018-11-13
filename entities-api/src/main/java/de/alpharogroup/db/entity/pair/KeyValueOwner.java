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
package de.alpharogroup.db.entity.pair;

/**
 * The interface {@link KeyValueOwner} can be implemented from an entity that
 * needs the data of a generic key value pair with his owner which can be also
 * the parent if it is from the same type.
 *
 * @param <O> the generic type of the owner
 * @param <K> the generic type of the key
 * @param <V> the generic type of the value
 */
public interface KeyValueOwner<O, K, V> {

	/**
	 * Gets the key.
	 *
	 * @return the key
	 */
	K getKey();

	/**
	 * Gets the owner.
	 *
	 * @return the owner
	 */
	O getOwner();

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	V getValue();

	/**
	 * Sets the key.
	 *
	 * @param key the new key
	 */
	void setKey(final K key);

	/**
	 * Sets the owner.
	 *
	 * @param owner the new owner
	 */
	void setOwner(final O owner);

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	void setValue(final V value);
}
