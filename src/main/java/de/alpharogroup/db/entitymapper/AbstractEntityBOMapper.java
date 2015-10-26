package de.alpharogroup.db.entitymapper;

import java.util.Collections;
import java.util.List;

import lombok.Getter;
import de.alpharogroup.db.domain.BusinessObject;
import de.alpharogroup.db.entity.BaseEntity;
import de.alpharogroup.lang.TypeArgumentsUtils;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

public abstract class AbstractEntityBOMapper<E extends BaseEntity<?>, BO extends BusinessObject<?>>
	implements
		EntityBOMapper<E, BO>
{

	/** The entity class. */
	@SuppressWarnings("unchecked")
	@Getter
	private final Class<E> entityClass = (Class<E>)TypeArgumentsUtils.getTypeArgument(
		AbstractEntityBOMapper.class, getClass(), 0);

	/** The bo class. */
	@SuppressWarnings("unchecked")
	@Getter
	private final Class<BO> businessObjectClass = (Class<BO>)TypeArgumentsUtils.getTypeArgument(
		AbstractEntityBOMapper.class, getClass(), 1);

	@Override
	public E toEntity(BO businessObject)
	{
		return toEntity(businessObject, Collections.<String> emptyList());
	}

	@Override
	public E toEntity(BO businessObject, List<String> mappingFiles)
	{
		return newMapper(mappingFiles).map(businessObject, getEntityClass());
	}

	@Override
	public BO toBusinessObject(E entity)
	{
		return toBusinessObject(entity, Collections.<String> emptyList());
	}

	@Override
	public BO toBusinessObject(E entity, List<String> mappingFiles)
	{
		return newMapper(mappingFiles).map(entity, getBusinessObjectClass());
	}

	public Mapper newMapper()
	{
		return newMapper(Collections.<String> emptyList());
	}

	public Mapper newMapper(List<String> mappingFiles)
	{
		return new DozerBeanMapper(mappingFiles);
	}
}