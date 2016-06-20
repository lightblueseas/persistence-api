package de.alpharogroup.service.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.alpharogroup.db.dao.jpa.EntityManagerDao;
import de.alpharogroup.db.entity.BaseEntity;
import de.alpharogroup.db.entitymapper.EntityDOMapper;
import de.alpharogroup.domain.DomainObject;
import de.alpharogroup.lang.ObjectExtensions;
import de.alpharogroup.lang.TypeArgumentsExtensions;
import lombok.Getter;
import lombok.Setter;

/**
 * The Class {@link AbstractDomainService}.
 *
 * @param <PK> the generic type of the primary key
 * @param <DO> the generic type of the domain object
 * @param <E> the element type of the entity
 * @param <DAO> the generic type of the data transfer object
 * @param <M> the generic type of the entity mapper
 */
public abstract class AbstractDomainService<
PK extends Serializable, 
DO extends DomainObject<PK>, 
E extends BaseEntity<PK>, 
DAO extends EntityManagerDao<E, PK>,
M extends EntityDOMapper<E, DO>>
 implements
		DomainService<PK, DO> {


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
    private final Class<E> entityClass = (Class<E>) TypeArgumentsExtensions.getTypeArgument(AbstractDomainService.class, getClass(), 2);
    
    /** The domain object class. */
    @SuppressWarnings("unchecked")
    @Getter
    private final Class<DO> domainObjectClass = (Class<DO>) TypeArgumentsExtensions.getTypeArgument(AbstractDomainService.class, getClass(), 1);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DO read(PK id) {
		E entity = dao.get(id);
		return getMapper().toDomainObject(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DO create(DO domainObject) {
		E entity = getMapper().toEntity(domainObject);
		domainObject.setId(dao.save(entity));
		return domainObject;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(DO domainObject) {
		E entity = dao.get(domainObject.getId());
		ObjectExtensions.copyQuietly(entity, domainObject);
		dao.merge(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(PK id) {
		dao.delete(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<DO> findAll() {
		Collection<E> all = dao.findAll();
		List<DO> domainObjects = new ArrayList<>();
		for(E entity : all) {
			domainObjects.add(getMapper().toDomainObject(entity));
		}
		return domainObjects;
	}
}