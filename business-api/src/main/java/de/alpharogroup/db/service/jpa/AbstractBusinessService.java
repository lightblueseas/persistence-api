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
package de.alpharogroup.db.service.jpa;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import de.alpharogroup.db.dao.jpa.EntityManagerDao;
import de.alpharogroup.db.entity.BaseEntity;
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
 * @param <DAO>
 *            the type of the data access object.
 */
public abstract class AbstractBusinessService<T extends BaseEntity<PK>, PK extends Serializable, DAO extends EntityManagerDao<T, PK>>
		implements BusinessService<T, PK> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	/** The dao reference. */
	@Getter
	@Setter
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
	 * Gets the {@link Query} from the given string.
	 *
	 * @param s
	 *            the query as string
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
	public List<T> merge(List<T> objects) {
		return getDao().merge(objects);
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

}
