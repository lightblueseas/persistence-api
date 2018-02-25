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

import de.alpharogroup.db.entity.BaseEntity;
import de.alpharogroup.db.repository.AbstractRepository;
import de.alpharogroup.db.strategies.api.SaveOrUpdateStrategy;
import de.alpharogroup.lang.TypeArgumentsExtensions;
import lombok.Getter;
import lombok.NonNull;

/**
 * The class {@link DefaultSaveOrUpdateStrategy} is a default implementation of the
 * {@link SaveOrUpdateStrategy}.
 *
 * @param <T>
 *            the type of the entity object
 * @param <PK>
 *            the type of the primary key from the entity object
 */
public class DefaultSaveOrUpdateStrategy<T extends BaseEntity<PK>, PK extends Serializable>
	implements
		SaveOrUpdateStrategy<T, PK>
{


	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The repository. */
	@NonNull
	private final AbstractRepository<T, PK> repository;

	/** The class type of the entity. */
	@Getter
	@SuppressWarnings("unchecked")
	private final Class<T> type = (Class<T>)TypeArgumentsExtensions
		.getFirstTypeArgument(DefaultSaveOrUpdateStrategy.class, this.getClass());

	/**
	 * Instantiates a new {@link DefaultSaveOrUpdateStrategy}.
	 *
	 * @param repository
	 *            the repository
	 */
	public DefaultSaveOrUpdateStrategy(AbstractRepository<T, PK> repository)
	{
		this.repository = repository;
	}

	/**
	 * Gets the entity manager.
	 *
	 * @return the entity manager
	 */
	private EntityManager getEntityManager()
	{
		return this.repository.getEntityManager();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<PK> save(List<T> entities)
	{
		final List<PK> primaryKeys = new ArrayList<>();
		for (final T entity : entities)
		{
			primaryKeys.add(save(entity));
		}
		return primaryKeys;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PK save(T entity)
	{
		getEntityManager().merge(entity);
		return entity.getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveOrUpdate(List<T> entities)
	{
		for (final T entity : entities)
		{
			saveOrUpdate(entity);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveOrUpdate(T entity)
	{
		if (entity.getId() == null)
		{
			save(entity);
		}
		else
		{
			update(entity);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(List<T> entities)
	{
		for (final T entity : entities)
		{
			update(entity);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(T entity)
	{
		getEntityManager().merge(entity);
	}

}
