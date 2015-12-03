package de.alpharogroup.db.entitymapper;

import java.util.Collections;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import de.alpharogroup.db.entity.BaseEntity;
import de.alpharogroup.domain.DomainObject;
import de.alpharogroup.lang.TypeArgumentsExtensions;
import lombok.Getter;

/**
 * The abstract class {@link AbstractEntityBOMapper} provides an base
 * implementation for mapping entities to domain objects and back.
 *
 * @param <E>
 *            the element type
 * @param <BO>
 *            the generic type
 */
public abstract class AbstractEntityBOMapper<E extends BaseEntity<?>, BO extends DomainObject<?>>
		implements EntityBOMapper<E, BO> {

	/**
	 * The mapper instance.
	 */
	@Getter
	private final Mapper mapper;

	/** The entity class. */
	@SuppressWarnings("unchecked")
	@Getter
	private final Class<E> entityClass = (Class<E>) TypeArgumentsExtensions.getTypeArgument(AbstractEntityBOMapper.class,
			this.getClass(), 0);

	/** The domain object class. */
	@SuppressWarnings("unchecked")
	@Getter
	private final Class<BO> domainObjectClass = (Class<BO>) TypeArgumentsExtensions
			.getTypeArgument(AbstractEntityBOMapper.class, this.getClass(), 1);

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

}