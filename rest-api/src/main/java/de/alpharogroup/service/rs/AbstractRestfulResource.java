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
