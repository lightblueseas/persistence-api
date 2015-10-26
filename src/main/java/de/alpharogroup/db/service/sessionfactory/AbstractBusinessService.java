package de.alpharogroup.db.service.sessionfactory;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.type.Type;

import de.alpharogroup.db.entity.BaseEntity;
import de.alpharogroup.db.service.jpa.BusinessService;
import de.alpharogroup.db.sessionfactory.SessionFactoryDao;

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
		implements BusinessService<T, PK> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	/** The dao reference. */
	private DAO dao;

	/**
	 * {@inheritDoc}
	 */
	public void delete(List<T> objects) {
		for (T t : objects) {
			delete(t);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void delete(PK id) {
		getDao().delete(id);
	}

	/**
	 * {@inheritDoc}
	 */
	public void delete(T id) {
		getDao().delete(id);
	}

	/**
	 * Delete and flush.
	 *
	 * @param objects the objects
	 */
	public void deleteAndFlush(List<T> objects) {
		delete(objects);
		flush();
	}

	/**
	 * Delete and flush.
	 *
	 * @param t the t
	 */
	public void deleteAndFlush(T t) {
		delete(t);
		flush();
	}

	/**
	 * Delete and flush.
	 *
	 * @param id the id
	 */
	public void deleteAndFlush(PK id) {
		delete(id);
		flush();		
	}

	/**
	 * {@inheritDoc}
	 */
	public void evict(T object) {
		getDao().evict(object);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean exists(PK id) {
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
	public List<T> find(String hqlQuery, String[] params, Object[] paramValues,
			Type[] paramTypes, Integer start, Integer count) {
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
	public List<T> findByCriteria(Criterion... criterion) {
		return getDao().findByCriteria(criterion);
	}

	/**
	 * Find by example.
	 *
	 * @param exampleInstance the example instance
	 * @param excludeProperty the exclude property
	 * @return the list
	 */
	public List<T> findByExample(T exampleInstance, String... excludeProperty) {
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
	public T get(PK id) {
		return getDao().get(id);
	}

	/**
	 * Gets the dao reference.
	 * 
	 * @return the dao reference.
	 */
	public DAO getDao() {
		return dao;
	}

	/**
	 * Gets the query.
	 *
	 * @param s the s
	 * @return the query
	 */
	public Query getQuery(String s) {
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
	 * Sets the session.
	 *
	 * @param session the new session
	 */
	public void setSession(Session session){
		getDao().setSession(session);
	}

	/**
	 * {@inheritDoc}
	 */
	public T load(PK id) {
		return getDao().load(id);
	}

	/**
	 * {@inheritDoc}
	 */
	public T merge(T object) {
		return (T) getDao().merge(object);
	}

	/**
	 * Merge and flush.
	 *
	 * @param object the object
	 * @return the t
	 */
	public T mergeAndFlush(T object) {
		try {
			return merge(object);
		} finally {
			flush();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void refresh(T object) {
		getDao().refresh(object);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<PK> save(List<T> objects) {
		return getDao().save(objects);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<T> merge(List<T> objects) {
		return getDao().merge(objects);
	}

	/**
	 * {@inheritDoc}
	 */
	public PK save(T newInstance) {
		return getDao().save(newInstance);
	}

	/**
	 * Save and flush.
	 *
	 * @param objects the objects
	 * @return the list
	 */
	public List<PK> saveAndFlush(List<T> objects) {
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
	public PK saveAndFlush(T object) {
		try {
			return save(object);
		} finally {
			flush();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void saveOrUpdate(List<T> objects) {
		getDao().saveOrUpdate(objects);
	}

	/**
	 * {@inheritDoc}
	 */
	public void saveOrUpdate(T object) {
		getDao().saveOrUpdate(object);
	}

	/**
	 * Save or update and flush.
	 *
	 * @param objects the objects
	 */
	public void saveOrUpdateAndFlush(List<T> objects) {
		getDao().saveOrUpdate(objects);
		flush();
	}

	/**
	 * Save or update and flush.
	 *
	 * @param object the object
	 */
	public void saveOrUpdateAndFlush(T object) {
		getDao().saveOrUpdate(object);
		flush();
	}

	/**
	 * Sets the dao reference.
	 * 
	 * @param dao
	 *            the new dao dao reference.
	 */
	public void setDao(DAO dao) {
		this.dao = dao;
	}
}