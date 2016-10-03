package de.alpharogroup.db.service.sessionfactory;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.type.Type;

import de.alpharogroup.db.entity.BaseEntity;
import de.alpharogroup.db.service.sessionfactory.api.SessionFactoryBusinessService;
import lombok.Getter;
import lombok.Setter;
import de.alpharogroup.db.dao.sessionfactory.SessionFactoryDao;

/**
 * The Class AbstractBusinessService.
 *
 * @param <T>
 *            the type of the domain object
 * @param <PK>
 *            the type of the primary key from the domain object
 * @param <DAO>
 *            the type of the data access object.
 */
public abstract class AbstractBusinessService<T extends BaseEntity<PK>, PK extends Serializable, DAO extends SessionFactoryDao<T, PK>>
		implements SessionFactoryBusinessService<T, PK> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	/** The dao reference. */
	@Getter @Setter
	private DAO dao;

	/**
	 * {@inheritDoc}
	 */
	public void delete(final List<T> objects) {
		for (final T t : objects) {
			delete(t);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void delete(final PK id) {
		getDao().delete(id);
	}

	/**
	 * {@inheritDoc}
	 */
	public void delete(final T id) {
		getDao().delete(id);
	}

	/**
	 * Delete and flush.
	 *
	 * @param objects the objects
	 */
	public void deleteAndFlush(final List<T> objects) {
		delete(objects);
		flush();
	}

	/**
	 * Delete and flush.
	 *
	 * @param id the id
	 */
	public void deleteAndFlush(final PK id) {
		delete(id);
		flush();
	}

	/**
	 * Delete and flush.
	 *
	 * @param t the t
	 */
	public void deleteAndFlush(final T t) {
		delete(t);
		flush();
	}

	/**
	 * {@inheritDoc}
	 */
	public void evict(final T object) {
		getDao().evict(object);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean exists(final PK id) {
		return getDao().exists(id);
	}

	/**
	 * Find.
	 *
	 * @param hqlQuery the hql query
	 * @param params the params
	 * @param paramValues the param values
	 * @param paramTypes the param types
	 * @param start the start
	 * @param count the count
	 * @return the list
	 */
	public List<T> find(final String hqlQuery, final String[] params, final Object[] paramValues,
			final Type[] paramTypes, final Integer start, final Integer count) {
		return getDao().find(hqlQuery, params, paramValues, paramTypes, start,
				count);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<T> findAll() {
		return getDao().findAll();
	}

	/**
	 * Find by criteria.
	 *
	 * @param criterion the criterion
	 * @return the list
	 */
	public List<T> findByCriteria(final Criterion... criterion) {
		return getDao().findByCriteria(criterion);
	}

	/**
	 * Find by example.
	 *
	 * @param exampleInstance the example instance
	 * @param excludeProperty the exclude property
	 * @return the list
	 */
	public List<T> findByExample(final T exampleInstance, final String... excludeProperty) {
		return getDao().findByExample(exampleInstance, excludeProperty);
	}

	/**
	 * Flush.
	 */
	public void flush() {
		getSession().flush();
	}

	/**
	 * {@inheritDoc}
	 */
	public T get(final PK id) {
		return getDao().get(id);
	}

	/**
	 * Gets the query.
	 *
	 * @param s the s
	 * @return the query
	 */
	public Query getQuery(final String s) {
		return getDao().getQuery(s);
	}

	/**
	 * Gets the session.
	 *
	 * @return the session
	 */
	public Session getSession() {
		return getDao().getSession();
	}

	/**
	 * {@inheritDoc}
	 */
	public T load(final PK id) {
		return getDao().load(id);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<T> merge(final List<T> objects) {
		return getDao().merge(objects);
	}

	/**
	 * {@inheritDoc}
	 */
	public T merge(final T object) {
		return getDao().merge(object);
	}

	/**
	 * Merge and flush.
	 *
	 * @param object the object
	 * @return the t
	 */
	public T mergeAndFlush(final T object) {
		try {
			return merge(object);
		} finally {
			flush();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void refresh(final T object) {
		getDao().refresh(object);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<PK> save(final List<T> objects) {
		return getDao().save(objects);
	}

	/**
	 * {@inheritDoc}
	 */
	public PK save(final T newInstance) {
		return getDao().save(newInstance);
	}

	/**
	 * Save and flush.
	 *
	 * @param objects the objects
	 * @return the list
	 */
	public List<PK> saveAndFlush(final List<T> objects) {
		try {
			return save(objects);
		} finally {
			flush();
		}
	}

	/**
	 * Save and flush.
	 *
	 * @param object the object
	 * @return the pk
	 */
	public PK saveAndFlush(final T object) {
		try {
			return save(object);
		} finally {
			flush();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void saveOrUpdate(final List<T> objects) {
		getDao().saveOrUpdate(objects);
	}

	/**
	 * {@inheritDoc}
	 */
	public void saveOrUpdate(final T object) {
		getDao().saveOrUpdate(object);
	}

	/**
	 * Save or update and flush.
	 *
	 * @param objects the objects
	 */
	public void saveOrUpdateAndFlush(final List<T> objects) {
		getDao().saveOrUpdate(objects);
		flush();
	}

	/**
	 * Save or update and flush.
	 *
	 * @param object the object
	 */
	public void saveOrUpdateAndFlush(final T object) {
		getDao().saveOrUpdate(object);
		flush();
	}

	/**
	 * Sets the session.
	 *
	 * @param session the new session
	 */
	public void setSession(final Session session){
		getDao().setSession(session);
	}
}