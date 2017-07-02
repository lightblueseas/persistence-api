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
package de.alpharogroup.db.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import de.alpharogroup.db.entity.BaseEntity;
import de.alpharogroup.lang.TypeArgumentsExtensions;
import lombok.Getter;

/**
 * The class {@link AbstractRepository} provides methods for database operations like insert,
 * delete, update and selections.
 *
 * @param <T>
 *            the type of the entity object
 * @param <PK>
 *            the type of the primary key from the entity object
 * @author Asterios Raptis
 */
public abstract class AbstractRepository<T extends BaseEntity<PK>, PK extends Serializable>
	implements
		GenericRepository<T, PK>
{

	private static final long serialVersionUID = 1L;

	@Getter
	@SuppressWarnings("unchecked")
	private final Class<T> type = (Class<T>)TypeArgumentsExtensions
		.getFirstTypeArgument(AbstractRepository.class, this.getClass());

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void create(final T entity)
	{
		getEntityManager().persist(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(final List<T> objects)
	{
		for (final T entity : objects)
		{
			getEntityManager().remove(entity);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(final PK id)
	{
		final T entity = get(id);
		delete(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(final T entity)
	{
		getEntityManager().remove(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void evict(final T object)
	{
		getEntityManager().detach(object);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean exists(final PK id)
	{
		return get(id) != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<T> findAll()
	{
		final CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<T> cq = builder.createQuery(getType());
		final Root<T> root = cq.from(getType());
		cq.select(root);
		return getEntityManager().createQuery(cq).getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T get(final PK id)
	{
		if (id != null)
		{
			return getEntityManager().find(type, id);
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Query getQuery(final String hqlQuery)
	{
		return getEntityManager().createQuery(hqlQuery);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T load(final PK id)
	{
		return get(id);
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<T> merge(final List<T> objects)
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
	public T merge(final T object)
	{
		return getEntityManager().merge(object);
	}

	@Override
	public void refresh(final T object)
	{
		getEntityManager().refresh(object);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<PK> save(final List<T> objects)
	{
		final List<PK> primaryKeys = new ArrayList<>();
		for (final T object : objects)
		{
			primaryKeys.add(save(object));
		}
		return primaryKeys;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PK save(final T object)
	{
		getEntityManager().persist(object);
		return object.getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveOrUpdate(final List<T> objects)
	{
		save(objects);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveOrUpdate(final T object)
	{
		save(object);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(final List<T> objects)
	{
		for (final T t : objects)
		{
			update(t);
		}
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(final T entity)
	{
		getEntityManager().merge(entity);
	}

}