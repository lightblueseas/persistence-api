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
package de.alpharogroup.db.entity.auditable;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import de.alpharogroup.db.entity.create.Creation;
import de.alpharogroup.db.entity.modify.LastModification;
import lombok.Getter;
import lombok.Setter;

/**
 * The entity class {@link AuditingEntity} can be used for entities that have to
 * be audited. <br>
 *
 * @param <T> the generic type of time measurement
 * @param <U> the generic type of the user or account
 * @see Creation
 * @see LastModification
 */
@Getter
@Setter
@Entity
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditingEntity<T, U> implements Auditable<T, U> {

	/** The date and time when this entity was created */
	@CreatedDate
	private T created;

	/** The user or account that created this entity */
	@CreatedBy
	private U createdBy;

	/** The date and time when this entity was last modified */
	@LastModifiedDate
	private T lastModified;

	/** The user or account that last modified this entity */
	@LastModifiedBy
	private U lastModifiedBy;
}
