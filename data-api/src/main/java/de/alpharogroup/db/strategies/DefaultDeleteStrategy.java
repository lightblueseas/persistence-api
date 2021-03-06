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
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.transaction.annotation.Transactional;

import de.alpharogroup.db.entity.BaseEntity;
import de.alpharogroup.db.repository.AbstractRepository;
import de.alpharogroup.db.strategies.api.DeleteStrategy;
import de.alpharogroup.lang.TypeArgumentsExtensions;
import lombok.Getter;
import lombok.NonNull;

/**
 * The class {@link DefaultDeleteStrategy} is a default implementation of the
 * {@link DeleteStrategy}.
 *
 * @param <T>
 *            the type of the entity object
 * @param <PK>
 *            the type of the primary key from the entity object
 */
public class DefaultDeleteStrategy<T extends BaseEntity<PK>, PK extends Serializable>
	implements
		DeleteStrategy<T, PK>
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
		.getFirstTypeArgument(DefaultDeleteStrategy.class, this.getClass());

	/**
	 * Instantiates a new {@link DefaultDeleteStrategy}.
	 *
	 * @param repository
	 *            the repository
	 */
	public DefaultDeleteStrategy(AbstractRepository<T, PK> repository)
	{
		this.repository = repository;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(List<T> objects)
	{
		for (final T entity : objects)
		{
			delete(entity);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(PK id)
	{
		final T entity = getEntityManager().find(type, id);
		delete(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void delete(T entity)
	{
		if (getEntityManager().contains(entity))
		{
			getEntityManager().remove(entity);
		}
		else
		{
			getEntityManager().remove(getEntityManager().merge(entity));
		}
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

}
