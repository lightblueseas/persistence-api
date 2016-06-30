package de.alpharogroup.db.service.jpa;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import de.alpharogroup.db.dao.jpa.EntityManagerDao;
import de.alpharogroup.db.entity.BaseEntity;

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
public abstract class AbstractBusinessService<T extends BaseEntity<PK>, PK extends Serializable, DAO extends EntityManagerDao<T, PK>>
		implements BusinessService<T, PK> {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** The dao reference. TODO lombokify */
	private DAO dao;

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	public void delete(List<T> objects) {
		for (T t : objects) {
			delete(t.getId());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	public void delete(PK id) {
		getDao().delete(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	public void delete(T id) {
		getDao().delete(id);
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
	 * {@inheritDoc}
	 */
	public List<T> findAll() {
		return getDao().findAll();
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
	 * {@inheritDoc}
	 */
	public T load(PK id) {
		return getDao().load(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	public T merge(T object) {
		return (T) getDao().merge(object);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	public void refresh(T object) {
		getDao().refresh(object);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	public List<PK> save(List<T> objects) {
		 return getDao().save(objects);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	public List<T> merge(List<T> objects) {
		return getDao().merge(objects);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	public PK save(T newInstance) {
		return getDao().save(newInstance);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	public void saveOrUpdate(List<T> objects) {
		getDao().saveOrUpdate(objects);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	public void saveOrUpdate(T object) {
		getDao().saveOrUpdate(object);
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
