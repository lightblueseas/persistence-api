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
package de.alpharogroup.service.api;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import de.alpharogroup.collections.list.ListExtensions;
import de.alpharogroup.db.entity.name.BasicNameEntity;
import de.alpharogroup.db.service.api.BusinessService;
import de.alpharogroup.lang.TypeArgumentsExtensions;

public interface NameEntityService<T extends BasicNameEntity<PK>, PK extends Serializable>
	extends
		BusinessService<T, PK>
{

	/**
	 * Find the entity object from the given name value.
	 *
	 * @param nameValue
	 *            the name value
	 * @return the found entity object or null if not.
	 * @deprecated use instead the method findFirst in this interface
	 */
	@Deprecated
	default T find(final String nameValue)
	{
		return findFirst(nameValue);
	}

	/**
	 * Find the entities objects from the given name value.
	 *
	 * @param nameValue
	 *            the name value
	 * @return the list with the entities
	 */
	default List<T> findEntities(final String nameValue)
	{
		@SuppressWarnings("unchecked")
		Class<T> type = (Class<T>)TypeArgumentsExtensions.getFirstTypeArgument(this.getClass());
		final String hqlString = JpqlStringFactory.forNameEntity(type, nameValue);
		final Query query = getQuery(hqlString);
		if (nameValue != null && !nameValue.isEmpty())
		{
			query.setParameter("name", nameValue);
		}
		@SuppressWarnings("unchecked")
		final List<T> nameEntities = query.getResultList();
		return nameEntities;
	}

	/**
	 * Find the entity object from the given name value.
	 *
	 * @param nameValue
	 *            the name value
	 * @return the found entity object or null if not.
	 */
	default T findFirst(final String nameValue)
	{
		final List<T> nameEntities = findEntities(nameValue);
		return ListExtensions.getFirst(nameEntities);
	}

	/**
	 * Gets the or creates a new entity object
	 *
	 * @param value
	 *            the value
	 * @return the entity object
	 */
	@Transactional
	default T getOrCreateNewNameEntity(final String value)
	{
		T nameEntity = findFirst(value);
		if (nameEntity == null)
		{
			nameEntity = newNameEntity(value);
			nameEntity = merge(nameEntity);
		}
		return nameEntity;
	}

	/**
	 * Factory method for create a new name entity.
	 *
	 * @param value
	 *            the value
	 * @return the new name entity
	 */
	T newNameEntity(final String value);
}