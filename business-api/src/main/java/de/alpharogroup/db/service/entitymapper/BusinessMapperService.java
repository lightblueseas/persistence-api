package de.alpharogroup.db.service.entitymapper;

import java.io.Serializable;

import de.alpharogroup.db.domain.BusinessObject;

public interface BusinessMapperService<PK extends Serializable, BO extends BusinessObject<PK>> {
	
	BO read(PK id);	

	BO create(BO businessObject);
	
	void update(BO businessObject);
	
	void delete(PK id);

}