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
package de.alpharogroup.db.entitymapper;

import java.util.Collections;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.TypeMappingOptions;

import de.alpharogroup.db.entity.BaseEntity;
import de.alpharogroup.domain.DomainObject;
import de.alpharogroup.lang.TypeArgumentsExtensions;
import lombok.Getter;

/**
 * The abstract class {@link AbstractEntityDOMapper} provides an base
 * implementation for mapping entities to domain objects and back.
 *
 * @param <E>
 *            the element type
 * @param <DO>
 *            the generic type
 */
public abstract class AbstractEntityDOMapper<E extends BaseEntity<?>, DO extends DomainObject<?>>
		implements EntityDOMapper<E, DO> {

	/**
	 * The mapper instance.
	 */
	@Getter
	private final Mapper mapper;

	/** The entity class. */
	@SuppressWarnings("unchecked")
	@Getter
	private final Class<E> entityClass = (Class<E>) TypeArgumentsExtensions
			.getTypeArgument(AbstractEntityDOMapper.class, this.getClass(), 0);

	/** The domain object class. */
	@SuppressWarnings("unchecked")
	@Getter
	private final Class<DO> domainObjectClass = (Class<DO>) TypeArgumentsExtensions
			.getTypeArgument(AbstractEntityDOMapper.class, this.getClass(), 1);

	/**
	 * Instantiates a new {@link AbstractEntityDOMapper}.
	 */
	public AbstractEntityDOMapper() {
		this(Collections.<String>emptyList());
	}

	/**
	 * Instantiates a new {@link AbstractEntityDOMapper}.
	 *
	 * @param mappingFiles
	 *            the mapping files
	 */
	public AbstractEntityDOMapper(final List<String> mappingFiles) {
		mapper = newMapper(mappingFiles);
	}

	private BeanMappingBuilder beanMappingBuilder() {
		return new BeanMappingBuilder() {
			@Override
			protected void configure() {
				mapping(getEntityClass(), getDomainObjectClass(), TypeMappingOptions.mapNull(false),
						TypeMappingOptions.mapEmptyString(false));
			}

		};
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
		DozerBeanMapper mapper = new DozerBeanMapper(mappingFiles);
		mapper.addMapping(beanMappingBuilder());
		mapper.setCustomFieldMapper(
				(source, destination, sourceFieldValue, classMap, fieldMapping) -> sourceFieldValue == null);
		return mapper;
	}

}