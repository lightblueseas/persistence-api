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
package de.alpharogroup.db.entity.validation;

/**
 * The interface {@link Validatable}.
 *
 * @param <T>
 *            the generic type of time measurement
 */
public interface Validatable<T>
{

	/**
	 * Gets the valid from.
	 *
	 * @return the valid from
	 */
	T getValidFrom();

	/**
	 * Gets the valid till.
	 *
	 * @return the valid till
	 */
	T getValidTill();

	/**
	 * Sets the valid from.
	 *
	 * @param validFrom
	 *            the new valid from
	 */
	void setValidFrom(T validFrom);

	/**
	 * Sets the valid till.
	 *
	 * @param validTill
	 *            the new valid till
	 */
	void setValidTill(T validTill);
}