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
package de.alpharogroup.db.entity.traceable;

import java.io.Serializable;

import de.alpharogroup.db.entity.Identifiable;
import de.alpharogroup.db.entity.create.Creatable;
import de.alpharogroup.db.entity.delete.Deletable;
import de.alpharogroup.db.entity.modify.LastModified;

/**
 * The interface {@link IdentifiableTraceable} is a combination of the
 * interfaces {@link Identifiable},{@link Creatable}, {@link LastModified} and
 * {@link Deletable}.
 *
 * @param <T> the generic type of time measurement
 * @param <U> the generic type of the user or account
 */
public interface IdentifiableTraceable<PK extends Serializable, T, U>
		extends Identifiable<PK>, Creatable<T, U>, LastModified<T, U>, Deletable<T, U> {
}
