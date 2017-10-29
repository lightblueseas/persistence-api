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
package de.alpharogroup.db.dao.sessionfactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import de.alpharogroup.db.entity.BaseEntity;
import de.alpharogroup.lang.TypeArgumentsExtensions;

/**
 * The Class GenericHibernateDao.
 *
 * @param <T>
 *            the generic type of the dao entity.
 * @param <PK>
 *            the generic type of the primary key from the dao entity.
 */
public class GenericHibernateDao<T extends BaseEntity<PK>, PK extends Serializable>
	implements
		SessionFactoryDao<T, PK>
{

	/**
	 * The serialVersionUID.
	 */
	private static final long serialVersionUID = 6551795469182243398L;

	/** Placeholder for the current session. */
	private static ThreadLocal<Session> currentSession = new ThreadLocal<>();

	/** The data source. */
	@Autowired
	private DataSource dataSource;

	/** The jdbc template. */
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/** The session factory. */
	@Autowired
	private SessionFactory sessionFactory;

	/** Class type. */
	@SuppressWarnings("unchecked")
	private final Class<T> type = (Class<T>)TypeArgumentsExtensions
		.getFirstTypeArgument(GenericHibernateDao.class, this.getClass());

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(List<T> entities)
	{
		for (final T entity : entities)
		{
			delete(entity);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(final PK id)
	{
		getSession().delete(load(id));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(final PK id, final Session session)
	{
		session.delete(load(id));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(final T entity)
	{
		getSession().delete(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(final T entity, final Session session)
	{
		session.delete(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void evict(T entity)
	{
		getSession().evict(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean exists(final PK id)
	{
		if (id != null)
		{
			return null != get(id);
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<T> find(String hqlQuery, String[] params, Object[] paramValues, Type[] paramTypes,
		Integer start, Integer count)
	{

		final Query query = getQuery(hqlQuery);
		final int paramsLength = (params == null ? 0 : params.length);
		if (params != null)
		{
			if (paramValues == null || paramValues.length != params.length)
			{
				throw new IllegalArgumentException(
					"ParamValues not completely specified for all params");
			}
			if (paramValues == null || paramTypes.length != params.length)
			{
				throw new IllegalArgumentException(
					"ParamTypes not completely specified for all params");
			}
		}
		for (int i = 0; i < paramsLength; i++)
		{
			query.setParameter(params[i], paramValues[i], paramTypes[i]);
		}
		if (start == null)
		{
			query.setFirstResult(0);
		}
		else
		{
			query.setFirstResult(start.intValue());
		}
		if (count == null)
		{
			query.setMaxResults(0);
		}
		else
		{
			query.setMaxResults(count.intValue());
		}
		return query.list();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<T> findAll()
	{
		return findByCriteria();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<T> findByCriteria(final Criterion... criterion)
	{
		final Criteria crit = getSession().createCriteria(getPersistentClass());

		for (final Criterion c : criterion)
		{
			crit.add(c);
		}
		return crit.list();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<T> findByExample(final T exampleInstance, final String... excludeProperty)
	{
		final Criteria crit = getSession().createCriteria(getPersistentClass());
		final Example example = Example.create(exampleInstance);
		for (final String exclude : excludeProperty)
		{
			example.excludeProperty(exclude);
		}
		crit.add(example);
		return crit.list();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public T get(final PK id)
	{
		return (T)getSession().get(this.type, id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public T get(final PK id, final Session session)
	{
		return (T)session.get(this.type, id);
	}

	/**
	 * Gets the data source.
	 *
	 * @return the data source
	 */
	public DataSource getDataSource()
	{
		return dataSource;
	}

	/**
	 * Gets the jdbc template.
	 *
	 * @return the jdbc template
	 */
	public JdbcTemplate getJdbcTemplate()
	{
		return jdbcTemplate;
	}

	/**
	 * Gets the persitent class.
	 *
	 * @return the persitent class.
	 */
	public Class<T> getPersistentClass()
	{
		return this.type;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Query getQuery(final String hqlQuery)
	{
		return getSession().createQuery(hqlQuery);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Query getQuery(final String queryString, final Session session)
	{
		return session.createQuery(queryString);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Session getSession()
	{
		Session session = null;
		try
		{
			session = sessionFactory.getCurrentSession();
		}
		catch (final HibernateException e)
		{
			if (currentSession.get() == null)
			{
				session = sessionFactory.openSession();
				session.setFlushMode(FlushMode.ALWAYS);
				setSession(session);
			}
			else
			{
				session = currentSession.get();
			}
		}
		return session;
	}

	/**
	 * Gets the session factory.
	 *
	 * @return SessionFactory Object
	 */
	public SessionFactory getSessionFactory()
	{
		return sessionFactory;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class<T> getType()
	{
		return type;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public T load(final PK id)
	{
		return (T)getSession().load(this.type, id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public T load(final PK id, final Session session)
	{
		return (T)session.load(this.type, id);
	}

	@Override
	public List<T> merge(List<T> entities)
	{
		final List<T> mergedEntities = new ArrayList<>();
		for (final T entity : entities)
		{
			mergedEntities.add(merge(entity));
		}
		return mergedEntities;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public T merge(T entity)
	{
		return (T)getSession().merge(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void refresh(final T entity)
	{
		getSession().refresh(entity);
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
	@SuppressWarnings("unchecked")
	public PK save(final T entity)
	{
		return (PK)getSession().save(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public PK save(final T entity, final Session session)
	{
		return (PK)session.save(entity);
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
	public void saveOrUpdate(final T entity)
	{
		getSession().saveOrUpdate(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveOrUpdate(final T entity, final Session session)
	{
		session.saveOrUpdate(entity);
	}

	/**
	 * Sets the data source.
	 *
	 * @param dataSource
	 *            the new data source
	 */
	public void setDataSource(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}

	/**
	 * Sets the jdbc template.
	 *
	 * @param jdbcTemplate
	 *            the new jdbc template
	 */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate)
	{
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setSession(Session session)
	{
		currentSession.set(session);
	}

	/**
	 * Sets the session factory.
	 *
	 * @param sessionFactory
	 *            the new session factory
	 */
	public void setSessionFactory(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(List<T> entities)
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
	public void update(final T entity)
	{
		getSession().update(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(final T entity, final Session session)
	{
		session.update(entity);
	}

}