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

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 * The entity class {@link KeyValueOwnerEntity} holds a key value pair with his owner.
 *
 * @param <PK> the generic type of the technical primary key
 * @param <K> the key type
 * @param <V> the value type
 * @param <O> the generic type
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Access(AccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level=AccessLevel.PRIVATE)
public abstract class KeyValueOwnerEntity<PK extends Serializable, O, K, V> extends BaseEntity<PK>
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The owner of this key-value pair. */
	@Column(name="owner", nullable=false)
	O owner;
	
	/** The key of this key-value pair. */
	@Column(name="key", nullable=false)
	K key;
	
	/** The value of this key-value pair. */
	@Column(name="value")
	V value;

}
