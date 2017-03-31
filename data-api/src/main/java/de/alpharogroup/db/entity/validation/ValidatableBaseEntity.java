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
import java.time.LocalDateTime;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import de.alpharogroup.db.entity.version.VersionableBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The class {@link ValidatableBaseEntity} is a base entity and has a validFrom
 * property and a validTill property for restricting an entity in a range in
 * which it is valid.
 *
 * @param <T>
 *            the generic type of the id
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Access(AccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValidatableBaseEntity<T extends Serializable> extends VersionableBaseEntity<T> {

	/** The serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** The valid from date for validation. */
	private LocalDateTime validFrom;

	/** The valid till date for validation. */
	private LocalDateTime validTill;
}
