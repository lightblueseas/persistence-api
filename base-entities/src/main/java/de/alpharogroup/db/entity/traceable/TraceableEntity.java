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
import java.time.LocalDateTime;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import de.alpharogroup.db.entity.BaseEntity;
import de.alpharogroup.db.entity.create.Creation;
import de.alpharogroup.db.entity.delete.Deletion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The entity class {@link TraceableEntity} is keeping the information for the deletion and for the
 * creation of an entity. <br>
 *
 * @param <PK>
 *            the generic type of the id
 * @param <U>
 *            the generic type of the user or account
 * @see Creation
 * @see Deletion
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Access(AccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class TraceableEntity<PK extends Serializable, U> extends BaseEntity<PK>
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The date and time when the entity that owns this entity was deleted. */
	private LocalDateTime deleted;

	/** The user or account that deleted the entity that owns this entity. */
	private U deletedBy;


	/** The date and time when the entity that owns this entity was created. */
	private LocalDateTime created;

	/** The user or account that created the entity that owns this entity. */
	private U createdBy;

}
