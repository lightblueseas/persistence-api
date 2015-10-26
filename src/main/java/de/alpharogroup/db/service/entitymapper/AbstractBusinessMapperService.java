package de.alpharogroup.db.service.entitymapper;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import de.alpharogroup.db.dao.jpa.EntityManagerDao;
import de.alpharogroup.db.domain.BusinessObject;
import de.alpharogroup.db.entity.BaseEntity;
import de.alpharogroup.db.entitymapper.EntityBOMapper;
import de.alpharogroup.lang.ObjectExtensions;
import de.alpharogroup.lang.TypeArgumentsUtils;

public abstract class AbstractBusinessMapperService<
PK extends Serializable, 
BO extends BusinessObject<PK>, 
E extends BaseEntity<PK>, 
DAO extends EntityManagerDao<E, PK>,
M extends EntityBOMapper<E, BO>>
 implements
		BusinessMapperService<PK, BO> {
	/** The dao reference. */
	@Setter
	@Getter
	private DAO dao;
	@Setter
	@Getter
	private M mapper;
    /** The entity class. */
    @SuppressWarnings("unchecked")
    @Getter
    private final Class<E> entityClass = (Class<E>) TypeArgumentsUtils.getTypeArgument(AbstractBusinessMapperService.class, getClass(), 2);
    
    /** The bo class. */
    @SuppressWarnings("unchecked")
    @Getter
    private final Class<BO> businessObjectClass = (Class<BO>) TypeArgumentsUtils.getTypeArgument(AbstractBusinessMapperService.class, getClass(), 1);
    
	@Override
	public BO read(PK id) {
		E entity = dao.get(id);
		return getMapper().toBusinessObject(entity);
	}

	@Override
	public BO create(BO businessObject) {
		E entity = getMapper().toEntity(businessObject);
		businessObject.setId(dao.save(entity));
		return businessObject;
	}

	@Override
	public void update(BO businessObject) {
		E entity = dao.get(businessObject.getId());
		ObjectExtensions.copyQuietly(entity, businessObject);
		dao.merge(entity);
	}

	@Override
	public void delete(PK id) {
		dao.delete(id);
	}
	

}
