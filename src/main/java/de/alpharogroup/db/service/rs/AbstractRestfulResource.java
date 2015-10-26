package de.alpharogroup.db.service.rs;

import java.io.Serializable;

import de.alpharogroup.db.domain.BusinessObject;
import de.alpharogroup.db.service.entitymapper.BusinessMapperService;
import lombok.Getter;
import lombok.Setter;

public class AbstractRestfulResource<PK extends Serializable, BO extends BusinessObject<PK>> implements RestfulResource<PK, BO> {
	@Getter
	@Setter
	private BusinessMapperService<PK, BO> businessMapperService;
	
	@Override
	public BO read(PK id) {
		BO businessObject = businessMapperService.read(id);
		return businessObject;
	}

	@Override
	public BO create(BO businessObject) {
		BO newBusinessObject = businessMapperService.create(businessObject);
		return newBusinessObject;
	}

	@Override
	public void update(BO businessObject) {
		businessMapperService.update(businessObject);
	}

	@Override
	public void delete(PK id) {
		businessMapperService.delete(id);
	}

}
