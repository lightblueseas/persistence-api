package de.alpharogroup.db.dao.jpa;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import de.alpharogroup.db.dao.api.GenericDao;
import de.alpharogroup.db.entity.BaseEntity;

public interface EntityManagerDao<T extends BaseEntity<PK>, PK extends Serializable> extends GenericDao<T, PK> {

	void create(T entity);

	EntityManager getEntityManager();

	void setEntityManager(EntityManager entityManager);

	Query getQuery(String hqlQuery);

}