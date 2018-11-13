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

import java.io.Serializable;

import de.alpharogroup.db.entity.Identifiable;

/**
 * The interface {@link IdentifiableKeyValueOwner} is a combination of the
 * interfaces {@link Identifiable} and {@link KeyValueOwner}.
 *
 * @param <PK> the generic type of the identifier
 * @param <O> the generic type of the owner
 * @param <K> the generic type of the key
 * @param <V> the generic type of the value
 */
public interface IdentifiableKeyValueOwner<PK extends Serializable, O, K, V>
		extends Identifiable<PK>, KeyValueOwner<O, K, V> {
}
