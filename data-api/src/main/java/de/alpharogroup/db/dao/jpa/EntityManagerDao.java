package de.alpharogroup.db.dao.jpa;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import de.alpharogroup.db.dao.api.GenericDao;
import de.alpharogroup.db.entity.BaseEntity;

/**
 * The Interface {@link EntityManagerDao}.
 *
 * @param <T>
 *            the generic type of the entity object
 * @param <PK>
 *            the generic type of the primary key
 */
public interface EntityManagerDao<T extends BaseEntity<PK>, PK extends Serializable> extends GenericDao<T, PK> {

	/**
	 * Persists the given entity.
	 *
	 * @param entity
	 *            the entity
	 */
	void create(T entity);

	/**
	 * Gets the entity manager.
	 *
	 * @return the entity manager
	 */
	EntityManager getEntityManager();

	/**
	 * Gets a {@link Query} from the given hql query.
	 *
	 * @param hqlQuery
	 *            the hql query
	 * @return the {@link Query}
	 */
	Query getQuery(String hqlQuery);

	/**
	 * Sets the entity manager.
	 *
	 * @param entityManager the new entity manager
	 */
	void setEntityManager(EntityManager entityManager);

}