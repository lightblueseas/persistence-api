package de.alpharogroup.service.rs;

import java.io.Serializable;

import de.alpharogroup.db.domain.BusinessObject;
import de.alpharogroup.db.service.entitymapper.BusinessMapperService;
import lombok.Getter;
import lombok.Setter;

/**
 * The Class {@link AbstractRestfulResource}.
 *
 * @param <PK>
 *            the generic type of the primary key
 * @param <BO>
 *            the generic type of the business object
 * @param <BMS> the generic type of the business mapper service interface 
 */
public class AbstractRestfulResource<PK extends Serializable, BO extends BusinessObject<PK>, BMS extends BusinessMapperService<PK, BO>> implements RestfulResource<PK, BO> {
	
	/**
	 * Gets the business mapper service.
	 *
	 * @return the business mapper service
	 */
	@Getter
	
	/**
	 * Sets the business mapper service.
	 *
	 * @param businessMapperService the new business mapper service
	 */
	@Setter
	private BMS businessMapperService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BO create(final BO businessObject) {
		final BO newBusinessObject = businessMapperService.create(businessObject);
		return newBusinessObject;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(final PK id) {
		businessMapperService.delete(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BO read(final PK id) {
		final BO businessObject = businessMapperService.read(id);
		return businessObject;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(final BO businessObject) {
		businessMapperService.update(businessObject);
	}

}
