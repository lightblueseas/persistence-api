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
package de.alpharogroup.db.strategies;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import de.alpharogroup.db.dao.jpa.EntityManagerDao;
import de.alpharogroup.db.entity.BaseEntity;
import de.alpharogroup.db.strategies.api.MergeStrategy;
import de.alpharogroup.lang.TypeArgumentsExtensions;
import lombok.Getter;
import lombok.NonNull;

/**
 * The class {@link DefaultDaoMergeStrategy} is a default implementation of the {@link MergeStrategy}.
 *
 * @param <T>
 *            the type of the entity object
 * @param <PK>
 *            the type of the primary key from the entity object
 */
public class DefaultDaoMergeStrategy<T extends BaseEntity<PK>, PK extends Serializable>
	implements
		MergeStrategy<T, PK>
{


	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The class type of the entity. */
	@Getter
	@SuppressWarnings("unchecked")
	private final Class<T> type = (Class<T>)TypeArgumentsExtensions
		.getFirstTypeArgument(DefaultDaoMergeStrategy.class, this.getClass());

	/** The dao. */
	@NonNull
	private final EntityManagerDao<T, PK> dao;

	/**
	 * Instantiates a new {@link DefaultDaoMergeStrategy}.
	 *
	 * @param dao
	 *            the dao
	 */
	public DefaultDaoMergeStrategy(EntityManagerDao<T, PK> dao)
	{
		this.dao = dao;
	}

	/**
	 * Gets the entity manager.
	 *
	 * @return the entity manager
	 */
	private EntityManager getEntityManager()
	{
		return this.dao.getEntityManager();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<T> merge(List<T> objects)
	{
		final List<T> mergedEntities = new ArrayList<>();
		for (final T object : objects)
		{
			mergedEntities.add(merge(object));
		}
		return mergedEntities;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T merge(T entity)
	{
		return getEntityManager().merge(entity);
	}

}
