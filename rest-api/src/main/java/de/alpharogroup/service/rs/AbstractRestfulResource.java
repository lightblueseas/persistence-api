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
package de.alpharogroup.service.rs;

import java.io.Serializable;

import de.alpharogroup.domain.DomainObject;
import de.alpharogroup.service.domain.DomainService;
import lombok.Getter;
import lombok.Setter;

/**
 * The Class {@link AbstractRestfulResource}.
 *
 * @param <PK>
 *            the generic type of the primary key
 * @param <DO>
 *            the generic type of the domain object
 * @param <BMS> the generic type of the domain service interface 
 */
public class AbstractRestfulResource<PK extends Serializable, DO extends DomainObject<PK>, BMS extends DomainService<PK, DO>> implements RestfulResource<PK, DO> {
	
	/**
	 * The domain service.
	 */
	@Getter
	@Setter
	private BMS domainService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DO create(final DO domainObject) {
		final DO newDomainObject = domainService.create(domainObject);
		return newDomainObject;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(final PK id) {
		domainService.delete(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DO read(final PK id) {
		final DO domainObject = domainService.read(id);
		return domainObject;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(final DO domainObject) {
		domainService.update(domainObject);
	}

}
