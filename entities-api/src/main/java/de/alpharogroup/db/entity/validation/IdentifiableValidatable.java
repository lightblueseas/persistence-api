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

import java.io.Serializable;

import de.alpharogroup.db.entity.Identifiable;

/**
 * The interface {@link IdentifiableValidatable} is a combination of the
 * interfaces {@link Identifiable} and {@link Validatable}.
 *
 * @param <PK> the generic type of the identifier
 * @param <T> the generic type of time measurement
 */
public interface IdentifiableValidatable<PK extends Serializable, T> extends Identifiable<PK>, Validatable<T> {
}
