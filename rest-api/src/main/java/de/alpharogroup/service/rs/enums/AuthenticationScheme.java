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
package de.alpharogroup.service.rs.enums;

import de.alpharogroup.service.rs.api.ImmutableValue;
import lombok.Getter;
import lombok.NonNull;

/**
 * The enum {@link AuthenticationScheme} provides values for
 */
public enum AuthenticationScheme implements ImmutableValue<String> {

	/** The basic. */
	BASIC("Basic"),

	/** The bearer. */
	BEARER("Bearer");

	/** The value. */
	@Getter
	private final String value;

	/**
	 * private constructor
	 *
	 * @param value the value
	 */
	private AuthenticationScheme(final @NonNull String value) {
		this.value = value;
	}
}
