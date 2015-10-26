package de.alpharogroup.db.sessionfactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import de.alpharogroup.db.entity.BaseEntity;
import de.alpharogroup.lang.TypeArgumentsUtils;

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

/**
 * The Class GenericHibernateDao.
 * 
 * @param <T>
 *            the generic type of the dao object.
 * @param <PK>
 *            the generic type of the primary key from the dao object.
 */
public class GenericHibernateDao<T extends BaseEntity<PK>, PK extends Serializable> implements
		SessionFactoryDao<T, PK> {

	/**
	 * The serialVersionUID.
	 */
	private static final long serialVersionUID = 6551795469182243398L;
	
	/** The data source. */
	@Autowired
	private DataSource dataSource;

	/** The jdbc template. */
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/** The session factory. */
	@Autowired
	private SessionFactory sessionFactory;	

	/** Placeholder for the current session. */
	private static ThreadLocal<Session> currentSession = new ThreadLocal<Session>();

	/** Class type. */
	@SuppressWarnings("unchecked")
	private final Class<T> type = (Class<T>) TypeArgumentsUtils.getFirstTypeArgument(GenericHibernateDao.class, this.getClass());

	/**
	 * {@inheritDoc}
	 */
	public Class<T> getType() {
		return type;
	}

	/**
	 * Gets the data source.
	 *
	 * @return the data source
	 */
	public DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * Sets the data source.
	 *
	 * @param dataSource the new data source
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * Gets the jdbc template.
	 *
	 * @return the jdbc template
	 */
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	/**
	 * Sets the jdbc template.
	 *
	 * @param jdbcTemplate the new jdbc template
	 */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * Gets the session factory.
	 * 
	 * @return SessionFactory Object
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * Sets the session factory.
	 * 
	 * @param sessionFactory
	 *            the new session factory
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * {@inheritDoc}
	 */
	public Session getSession() {
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			if (currentSession.get() == null) {
				session = sessionFactory.openSession();
				session.setFlushMode(FlushMode.ALWAYS);
				setSession(session);
			} else {
				session = currentSession.get();
			}
		}
		return session;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setSession(Session session) {
		currentSession.set(session);
	}

	/**
	 * {@inheritDoc}
	 */
	public void delete(final PK id) {
		getSession().delete(load(id));
	}

	/**
	 * {@inheritDoc}
	 */
	public void delete(final PK id, final Session session) {
		session.delete(load(id));
	}

	/**
	 * {@inheritDoc}
	 */
	public void delete(final T object) {
		getSession().delete(object);
	}

	/**
	 * {@inheritDoc}
	 */
	public void delete(final T object, final Session session) {
		session.delete(object);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean exists(final PK id) {
		if (id != null) {
			return null != get(id);
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<T> findAll() {
		return findByCriteria();
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByCriteria(final Criterion... criterion) {
		Criteria crit = getSession().createCriteria(getPersistentClass());

		for (Criterion c : criterion) {
			crit.add(c);
		}
		return crit.list();
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByExample(final T exampleInstance,
			final String... excludeProperty) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		Example example = Example.create(exampleInstance);
		for (String exclude : excludeProperty) {
			example.excludeProperty(exclude);
		}
		crit.add(example);
		return crit.list();
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public T get(final PK id) {
		return (T) getSession().get(this.type, id);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public T get(final PK id, final Session session) {
		return (T) session.get(this.type, id);
	}

	/**
	 * Gets the persitent class.
	 * @return the persitent class.
	 */
	public Class<T> getPersistentClass() {
		return this.type;
	}

	/**
	 * {@inheritDoc}
	 */
	public Query getQuery(final String hqlQuery) {
		return getSession().createQuery(hqlQuery);
	}

	/**
	 * {@inheritDoc}
	 */
	public Query getQuery(final String queryString, final Session session) {
		return session.createQuery(queryString);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public T load(final PK id) {
		return (T) getSession().load(this.type, id);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public T load(final PK id, final Session session) {
		return (T) session.load(this.type, id);
	}

	/**
	 * {@inheritDoc}
	 */
	public void refresh(final T object) {
		getSession().refresh(object);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public PK save(final T object) {
		return (PK) getSession().save(object);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public PK save(final T object, final Session session) {
		return (PK) session.save(object);
	}

	/**
	 * {@inheritDoc}
	 */
	public void saveOrUpdate(final T object) {
		getSession().saveOrUpdate(object);
	}

	/**
	 * {@inheritDoc}
	 */
	public void saveOrUpdate(final T object, final Session session) {
		session.saveOrUpdate(object);
	}

	/**
	 * {@inheritDoc}
	 */
	public void update(final T object) {
		getSession().update(object);
	}

	/**
	 * {@inheritDoc}
	 */
	public void update(final T object, final Session session) {
		session.update(object);
	}

	/**
	 * {@inheritDoc}
	 */
	public void evict(T object) {
		getSession().evict(object);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<T> find(String hqlQuery, String[] params, Object[] paramValues,
			Type[] paramTypes, Integer start, Integer count) {

		final Query query = getQuery(hqlQuery);
		final int paramsLength = (params == null ? 0 : params.length);
		if (params != null) {
			if (paramValues == null || paramValues.length != params.length) {
				throw new IllegalArgumentException(
						"ParamValues not completely specified for all params");
			}
			if (paramValues == null || paramTypes.length != params.length) {
				throw new IllegalArgumentException(
						"ParamTypes not completely specified for all params");
			}
		}
		for (int i = 0; i < paramsLength; i++) {
			query.setParameter(params[i], paramValues[i], paramTypes[i]);
		}
		if (start == null) {
			query.setFirstResult(0);
		} else {
			query.setFirstResult(start.intValue());
		}
		if (count == null) {
			query.setMaxResults(0);
		} else {
			query.setMaxResults(count.intValue());
		}
		return query.list();
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public T merge(T object) {
		return (T) getSession().merge(object);
	}

	/**
	 * {@inheritDoc}
	 */
	public void delete(List<T> objects) {
		for (T object : objects) {
			delete(object);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public List<PK> save(List<T> objects) {
		List<PK> primaryKeys = new ArrayList<PK>();
		for (T object : objects) {
			primaryKeys.add(save(object));
		}
		return primaryKeys;
	}

	/**
	 * {@inheritDoc}
	 */
	public void saveOrUpdate(List<T> objects) {
		for (T object : objects) {
			saveOrUpdate(object);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void update(List<T> objects) {
		for (T object : objects) {
			saveOrUpdate(object);
		}
	}

	@Override
	public List<T> merge(List<T> objects) {
		List<T> mergedEntities = new ArrayList<T>();
		for (T object : objects) {
			mergedEntities.add(merge(object));
		}
		return mergedEntities;
	}

}