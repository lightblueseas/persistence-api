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
package de.alpharogroup.db.entity.name.versionable.unique;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import de.alpharogroup.db.entity.name.versionable.VersionableNameEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The class {@link VersionableExtraSmallUniqueNameEntity} is a base entity for a table with a
 * single unique value of length of 64.
 *
 * @param <PK>
 *            the generic type of the id
 */
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class VersionableExtraSmallUniqueNameEntity<PK extends Serializable>
	extends
		VersionableNameEntity<PK>
{

	/** The serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** The name. */
	@Column(unique = true, name = "name", length = 64)
	private String name;
}
