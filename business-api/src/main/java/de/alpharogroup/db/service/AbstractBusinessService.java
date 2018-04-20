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
package de.alpharogroup.db.service;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import de.alpharogroup.db.entity.BaseEntity;
import de.alpharogroup.db.repository.api.GenericRepository;
import de.alpharogroup.db.service.api.BusinessService;
import lombok.Getter;
import lombok.Setter;

/**
 * The abstract class {@link AbstractBusinessService}.
 *
 * @param <T>
 *            the type of the domain object
 * @param <PK>
 *            the type of the primary key from the domain object
 * @param <REPOSITORY>
 *            the type of the data access object.
 */
public abstract class AbstractBusinessService<T extends BaseEntity<PK>, PK extends Serializable, REPOSITORY extends GenericRepository<T, PK>>
	implements
		BusinessService<T, PK>
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The repository reference. */
	@Getter
	@Setter
	private REPOSITORY repository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Query createNativeQuery(String sqlString)
	{
		return getRepository().createNativeQuery(sqlString);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Query createNativeQuery(String sqlString, Class resultClass)
	{
		return getRepository().createNativeQuery(sqlString, resultClass);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Query createNativeQuery(String sqlString, String resultSetMapping)
	{
		return getRepository().createNativeQuery(sqlString, resultSetMapping);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void delete(List<T> objects)
	{
		for (final T t : objects)
		{
			delete(t.getId());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void delete(PK id)
	{
		getRepository().delete(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void delete(T object)
	{
		getRepository().delete(object);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void evict(T object)
	{
		getRepository().evict(object);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean exists(PK id)
	{
		return getRepository().exists(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<T> findAll()
	{
		return getRepository().findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T get(PK id)
	{
		return getRepository().get(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EntityManager getEntityManager()
	{
		return getRepository().getEntityManager();
	}

	/**
	 * Gets the {@link Query} from the given string.
	 *
	 * @param s
	 *            the query as string
	 * @return the query
	 */
	public Query getQuery(String s)
	{
		return getRepository().getQuery(s);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T load(PK id)
	{
		return getRepository().load(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public List<T> merge(List<T> objects)
	{
		return getRepository().merge(objects);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public T merge(T object)
	{
		return getRepository().merge(object);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void refresh(T object)
	{
		getRepository().refresh(object);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public List<PK> save(List<T> objects)
	{
		return getRepository().save(objects);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public PK save(T newInstance)
	{
		return getRepository().save(newInstance);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void saveOrUpdate(List<T> objects)
	{
		getRepository().saveOrUpdate(objects);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void saveOrUpdate(T object)
	{
		getRepository().saveOrUpdate(object);
	}
}
