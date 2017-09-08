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
package de.alpharogroup.db.dao.jpa;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
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
public interface EntityManagerDao<T extends BaseEntity<PK>, PK extends Serializable>
	extends
		GenericDao<T, PK>
{

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
	 * @param entityManager
	 *            the new entity manager
	 */
	void setEntityManager(EntityManager entityManager);
	
	
	/**
	 * Factory method for create a new {@link TypedQuery} from the given name
	 * and the given type.
	 *
	 * @param name
	 *            the name
	 * @param resultClass
	 *            the result class
	 * @return the new {@link TypedQuery}
	 */
	TypedQuery<T> createNamedQuery(String name, Class<T> resultClass);

	/**
	 * Factory method for create a new {@link TypedQuery} from the given name
	 * and the type of the generic entity.
	 *
	 * @param name
	 *            the name
	 * @return the new {@link TypedQuery}
	 */
	TypedQuery<T> createNamedTypedQuery(String name);
	
}
