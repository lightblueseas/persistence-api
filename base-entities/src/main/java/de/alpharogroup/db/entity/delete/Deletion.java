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
package de.alpharogroup.db.entity.delete;

import java.io.Serializable;

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
 * The entity class {@link Deletion} acts like an deletion flag that keeps the information for the
 * deletion of another entity that have this entity as an instance variable. This information are
 * the date when it was deleted and from whom it was deleted. So this entity can be attached to
 * another entity for keeping information if it is deleted and when it was deleted. <br>
 * <br>
 * In specific situation entities have to be kept for instance of historical purposes. So the intent
 * of this entity is that a parent entity will not really be deleted. The initial value of the
 * parent entity that keeps this entity as an instance variable as a null object that signals that
 * it is not deleted, and when it get a value with the deletion data it signals that it is now
 * deleted and is not visible to selections. <br>
 * <br>
 * Note: So this entity will only be created if the parent entity references to this entity and is
 * intended to be deleted. This can be done for instance in a deletion strategy.
 *
 * Note: There is no need of a delete flag in this entity because this entity is the flag. So if the
 * parent entity is not any more 'deleted' the reference of this entity will be set to null back.
 *
 * @param <PK>
 *            the generic type of the id
 * @param <T>
 *            the generic type of time measurement
 * @param <U>
 *            the generic type of the user or account
 */
@Entity
@Table(name = "deletion")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Deletion<PK extends Serializable, T, U> extends BaseEntity<PK>
	implements
		IdentifiableDeletable<PK, T, U>
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The date and time when the entity that owns this entity was deleted. */
	private T deleted;

	/** The user or account that deleted the entity that owns this entity. */
	private U deletedBy;

}
