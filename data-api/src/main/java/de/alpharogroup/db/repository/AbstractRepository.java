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
package de.alpharogroup.db.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import de.alpharogroup.db.entity.BaseEntity;
import de.alpharogroup.db.repository.api.GenericRepository;
import de.alpharogroup.db.strategies.DefaultDeleteStrategy;
import de.alpharogroup.db.strategies.DefaultMergeStrategy;
import de.alpharogroup.db.strategies.DefaultSaveOrUpdateStrategy;
import de.alpharogroup.db.strategies.api.DeleteStrategy;
import de.alpharogroup.db.strategies.api.MergeStrategy;
import de.alpharogroup.db.strategies.api.SaveOrUpdateStrategy;
import de.alpharogroup.lang.TypeArgumentsExtensions;
import lombok.Getter;
import lombok.Setter;

/**
 * The abstract class {@link AbstractRepository} provides methods for database
 * operations like insert, delete, update and selections. The create, update and
 * delete processes can be overwritten by providing strategies for them. By
 * default the strategies are null and the default behavior of the process will
 * be taken.
 *
 * @param <T> the generic type of the domain entity
 * @param <PK> the generic type of the primary key from the domain entity
 * @author Asterios Raptis
 */
public abstract class AbstractRepository<T extends BaseEntity<PK>, PK extends Serializable>
		implements GenericRepository<T, PK> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The data source. */
	@Setter
	@Getter
	@Autowired
	private DataSource dataSource;

	/** The delete strategy for interact on deletion process. */
	@Getter
	private DeleteStrategy<T, PK> deleteStrategy;

	/** The entity manager. */
	@PersistenceContext
	@Getter
	@Setter
	private EntityManager entityManager;

	/** The jdbc template. */
	@Setter
	@Getter
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/** The merge strategy for interact on merge process. */
	@Getter
	private MergeStrategy<T, PK> mergeStrategy;

	/** The save or update strategy for interact on save or update process. */
	@Getter
	private SaveOrUpdateStrategy<T, PK> saveOrUpdateStrategy;

	/** The class type of the entity. */
	@Getter
	@SuppressWarnings("unchecked")
	private final Class<T> type = (Class<T>) TypeArgumentsExtensions.getFirstTypeArgument(AbstractRepository.class,
			this.getClass());

	/**
	 * initialization block for the strategies.
	 */
	{
		newDeleteStrategy();
		newMergeStrategy();
		newSaveOrUpdateStrategy();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void create(final T entity) {
		getEntityManager().persist(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TypedQuery<T> createNamedQuery(String name, Class<T> resultClass) {
		return getEntityManager().createNamedQuery(name, resultClass);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TypedQuery<T> createNamedTypedQuery(String name) {
		return createNamedQuery(name, getType());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Query createNativeQuery(String sqlString) {
		return getEntityManager().createNativeQuery(sqlString);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Query createNativeQuery(String sqlString, Class resultClass) {
		return getEntityManager().createNativeQuery(sqlString, resultClass);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Query createNativeQuery(String sqlString, String resultSetMapping) {
		return getEntityManager().createNativeQuery(sqlString, resultSetMapping);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(final List<T> entities) {
		if (getDeleteStrategy() != null) {
			getDeleteStrategy().delete(entities);
		} else {
			for (final T entity : entities) {
				if (getEntityManager().contains(entity)) {
					getEntityManager().remove(entity);
				} else {
					getEntityManager().remove(getEntityManager().merge(entity));
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(final PK id) {
		if (getDeleteStrategy() != null) {
			getDeleteStrategy().delete(id);
		} else {
			final T entity = get(id);
			delete(entity);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void delete(final T entity) {
		if (getDeleteStrategy() != null) {
			getDeleteStrategy().delete(entity);
		} else {
			if (getEntityManager().contains(entity)) {
				getEntityManager().remove(entity);
			} else {
				getEntityManager().remove(getEntityManager().merge(entity));
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void evict(final T entity) {
		getEntityManager().detach(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean exists(final PK id) {
		return get(id) != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<T> findAll() {
		final CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<T> cq = builder.createQuery(getType());
		final Root<T> root = cq.from(getType());
		cq.select(root);
		return getEntityManager().createQuery(cq).getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T get(final PK id) {
		if (id != null) {
			return getEntityManager().find(type, id);
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Query getQuery(final String hqlQuery) {
		return getEntityManager().createQuery(hqlQuery);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T load(final PK id) {
		return get(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<T> merge(final List<T> entities) {
		final List<T> mergedEntities = new ArrayList<>();
		if (getMergeStrategy() != null) {
			mergedEntities.addAll(getMergeStrategy().merge(entities));
		} else {
			for (final T entity : entities) {
				mergedEntities.add(merge(entity));
			}
		}
		return mergedEntities;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public T merge(final T entity) {
		if (getMergeStrategy() != null) {
			return getMergeStrategy().merge(entity);
		} else {
			return getEntityManager().merge(entity);
		}
	}

	/**
	 * Factory method for creating a new {@link DeleteStrategy} for interact on
	 * deletion process. This method can be overridden so users can provide their
	 * own version of a new {@link DeleteStrategy} for the deletion process.
	 *
	 * @return the new {@link DeleteStrategy} for the deletion process.
	 */
	public DeleteStrategy<T, PK> newDeleteStrategy() {
		deleteStrategy = new DefaultDeleteStrategy<>(this);
		return deleteStrategy;
	}

	/**
	 * Factory method for creating a new {@link MergeStrategy} for interact on merge
	 * process. This method can be overridden so users can provide their own version
	 * of a new {@link MergeStrategy} for the merge process.
	 *
	 * @return the new {@link MergeStrategy} for the merge process.
	 */
	public MergeStrategy<T, PK> newMergeStrategy() {
		mergeStrategy = new DefaultMergeStrategy<>(this);
		return mergeStrategy;
	}

	/**
	 * Factory method for creating a new {@link SaveOrUpdateStrategy} for interact
	 * on save or update process. This method can be overridden so users can provide
	 * their own version of a new {@link SaveOrUpdateStrategy} for the save or
	 * update process.
	 *
	 * @return the new {@link SaveOrUpdateStrategy} for the save or update process.
	 */
	public SaveOrUpdateStrategy<T, PK> newSaveOrUpdateStrategy() {
		saveOrUpdateStrategy = new DefaultSaveOrUpdateStrategy<>(this);
		return saveOrUpdateStrategy;
	}

	@Override
	public void refresh(final T entity) {
		getEntityManager().refresh(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<PK> save(final List<T> entities) {
		final List<PK> primaryKeys = new ArrayList<>();
		if (getSaveOrUpdateStrategy() != null) {
			primaryKeys.addAll(getSaveOrUpdateStrategy().save(entities));
		} else {
			for (final T entity : entities) {
				primaryKeys.add(save(entity));
			}
		}
		return primaryKeys;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public PK save(final T entity) {
		if (getSaveOrUpdateStrategy() != null) {
			return getSaveOrUpdateStrategy().save(entity);
		} else {
			getEntityManager().merge(entity);
			return entity.getId();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveOrUpdate(final List<T> entities) {
		if (getSaveOrUpdateStrategy() != null) {
			getSaveOrUpdateStrategy().saveOrUpdate(entities);
		} else {
			for (final T entity : entities) {
				saveOrUpdate(entity);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void saveOrUpdate(final T entity) {
		if (getSaveOrUpdateStrategy() != null) {
			getSaveOrUpdateStrategy().saveOrUpdate(entity);
		} else {
			if (entity.getId() == null) {
				save(entity);
			} else {
				update(entity);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(final List<T> entities) {
		if (getSaveOrUpdateStrategy() != null) {
			getSaveOrUpdateStrategy().update(entities);
		} else {
			for (final T entity : entities) {
				update(entity);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void update(final T entity) {
		if (getSaveOrUpdateStrategy() != null) {
			getSaveOrUpdateStrategy().update(entity);
		} else {
			getEntityManager().merge(entity);
		}
	}

}
