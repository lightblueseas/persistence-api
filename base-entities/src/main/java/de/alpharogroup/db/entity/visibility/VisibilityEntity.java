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
package de.alpharogroup.db.entity.visibility;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import de.alpharogroup.db.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The class {@link VisibilityEntity} is a base entity with a flag 'visible' that indicates if the
 * entity is visible or not.
 *
 * @param <PK>
 *            the generic type of the id
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Access(AccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class VisibilityEntity<PK extends Serializable> extends BaseEntity<PK>
	implements
		IdentifiableVisibility<PK>
{

	/** The serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** The attribute visible, if true this entity is visible. */
	@Column(name = "visible")
	private boolean visible;
}
