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
package de.alpharogroup.db.entity.create;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Table;

import de.alpharogroup.db.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * The entity class {@link Creation} is keeping the information for the creation of an entity. This
 * entity can be extended or attached to another entity for keep information when it was created.
 *
 * @param <PK>
 *            the generic type of the id
 * @param <U>
 *            the generic type of the user or account
 */
@Entity
@Table(name = "creation")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Creation<PK extends Serializable, U> extends BaseEntity<PK>
	implements
		Creatable<LocalDateTime, U>
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The date and time when the entity that owns this entity was created. */
	private LocalDateTime created;

	/** The user or account that created the entity that owns this entity. */
	private U createdBy;

}
