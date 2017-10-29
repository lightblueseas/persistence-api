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
package de.alpharogroup.service.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import de.alpharogroup.db.dao.jpa.EntityManagerDao;
import de.alpharogroup.db.entity.BaseEntity;
import de.alpharogroup.db.entitymapper.EntityDOMapper;
import de.alpharogroup.domain.DomainObject;
import de.alpharogroup.lang.TypeArgumentsExtensions;
import de.alpharogroup.merge.object.MergeObjectExtensions;
import lombok.Getter;
import lombok.Setter;

/**
 * The Class {@link AbstractDomainService}.
 *
 * @param <PK>
 *            the generic type of the primary key
 * @param <DO>
 *            the generic type of the domain object
 * @param <E>
 *            the element type of the entity
 * @param <DAO>
 *            the generic type of the data transfer object
 * @param <M>
 *            the generic type of the entity mapper
 */
@Transactional
public abstract class AbstractDomainService<PK extends Serializable, DO extends DomainObject<PK>, E extends BaseEntity<PK>, DAO extends EntityManagerDao<E, PK>, M extends EntityDOMapper<E, DO>>
	implements
		DomainService<PK, DO>
{

	/** The dao reference. */
	@Setter
	@Getter
	private DAO dao;

	/**
	 * The mapper.
	 */
	@Setter
	@Getter
	private M mapper;
	/** The entity class. */
	@SuppressWarnings("unchecked")
	@Getter
	private final Class<E> entityClass = (Class<E>)TypeArgumentsExtensions
		.getTypeArgument(AbstractDomainService.class, getClass(), 2);

	/** The domain object class. */
	@SuppressWarnings("unchecked")
	@Getter
	private final Class<DO> domainObjectClass = (Class<DO>)TypeArgumentsExtensions
		.getTypeArgument(AbstractDomainService.class, getClass(), 1);

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	@Override
	public DO create(final DO domainObject)
	{
		E entity = getMapper().toEntity(domainObject);
		entity = dao.merge(entity);
		domainObject.setId(entity.getId());
		return domainObject;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	@Override
	public DO delete(final PK id)
	{
		final E entity = dao.get(id);
		final DO domainObject = getMapper().toDomainObject(entity);
		dao.delete(id);
		return domainObject;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean exists(final PK id)
	{
		return dao.exists(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<DO> findAll()
	{
		final Collection<E> all = dao.findAll();
		final List<DO> domainObjects = new ArrayList<>();
		for (final E entity : all)
		{
			domainObjects.add(getMapper().toDomainObject(entity));
		}
		return domainObjects;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	@Override
	public Collection<PK> persist(final Collection<DO> domainObjects)
	{
		final Collection<PK> primaryKeys = new ArrayList<>();
		for (final DO domainObject : domainObjects)
		{
			primaryKeys.add(create(domainObject).getId());
		}
		return primaryKeys;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DO read(final PK id)
	{
		final E entity = dao.get(id);
		final DO domainObject = getMapper().toDomainObject(entity);
		return domainObject;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	@Override
	public DO update(final DO domainObject)
	{
		E entity = dao.get(domainObject.getId());
		MergeObjectExtensions.mergeOrCopyQuietly(entity, domainObject);
		entity = dao.merge(entity);
		return domainObject;
	}
}