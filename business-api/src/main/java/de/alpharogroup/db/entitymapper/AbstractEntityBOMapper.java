package de.alpharogroup.db.entitymapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.MappingException;

import de.alpharogroup.db.domain.BusinessObject;
import de.alpharogroup.db.entity.BaseEntity;
import de.alpharogroup.lang.TypeArgumentsUtils;
import lombok.Getter;

/**
 * The abstract class {@link AbstractEntityBOMapper} provides an base
 * implementation for mapping entities to business objects and back.
 *
 * @param <E>
 *            the element type
 * @param <BO>
 *            the generic type
 */
public abstract class AbstractEntityBOMapper<E extends BaseEntity<?>, BO extends BusinessObject<?>>
		implements EntityBOMapper<E, BO> {

	/**
	 * The mapper instance.
	 */
	@Getter
	private final Mapper mapper;

	/** The entity class. */
	@SuppressWarnings("unchecked")
	@Getter
	private final Class<E> entityClass = (Class<E>) TypeArgumentsUtils.getTypeArgument(AbstractEntityBOMapper.class,
			getClass(), 0);

	/** The business object class. */
	@SuppressWarnings("unchecked")
	@Getter
	private final Class<BO> businessObjectClass = (Class<BO>) TypeArgumentsUtils
			.getTypeArgument(AbstractEntityBOMapper.class, getClass(), 1);

	/**
	 * Instantiates a new {@link AbstractEntityBOMapper}.
	 */
	public AbstractEntityBOMapper() {
		this(Collections.<String> emptyList());
	}

	/**
	 * Instantiates a new {@link AbstractEntityBOMapper}.
	 *
	 * @param mappingFiles
	 *            the mapping files
	 */
	public AbstractEntityBOMapper(final List<String> mappingFiles) {
		mapper = newMapper(mappingFiles);
	}

	/**
	 * Factory method for creating the new {@link Mapper} for the mapping
	 * process with the given mapping files list. This method is invoked in the
	 * constructor and can be overridden so users can provide their own mapping
	 * process.
	 * 
	 * @param mappingFiles
	 *            the mapping files
	 *
	 * @return the new {@link Mapper} for the mapping process.
	 */
	public Mapper newMapper(final List<String> mappingFiles) {
		return new DozerBeanMapper(mappingFiles);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BO toBusinessObject(final E entity) {
		if (entity != null) {
			return this.mapper.map(entity, getBusinessObjectClass());
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<BO> toBusinessObjects(final List<E> entities) {
		final List<BO> businessObjects = new ArrayList<>();
		if ((entities != null) && !entities.isEmpty()) {
			for (final E entity : entities) {
				businessObjects.add(toBusinessObject(entity));
			}
		}
		return businessObjects;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<E> toEntities(final List<BO> businessObjects) {
		final List<E> entities = new ArrayList<>();
		if ((businessObjects != null) && !businessObjects.isEmpty()) {
			for (final BO businessObject : businessObjects) {
				entities.add(toEntity(businessObject));
			}
		}
		return entities;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public E toEntity(final BO businessObject) {
		if(businessObject != null) {
			return this.mapper.map(businessObject, getEntityClass());
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T, S> T map(S source, Class<T> destinationClass) throws MappingException {		
		return getMapper().map(source, destinationClass);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T, S> List<T> map(List<S> sources, Class<T> destinationClass) throws MappingException {
		final List<T> destination = new ArrayList<>();
		if ((sources != null) && !sources.isEmpty()) {
			for(final S source : sources) {
				destination.add(map(source, destinationClass));
			}
		}
		return destination;
	}

}