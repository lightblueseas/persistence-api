package de.alpharogroup.db.entitymapper;

import java.util.List;

import de.alpharogroup.db.domain.BusinessObject;
import de.alpharogroup.db.entity.BaseEntity;


public interface EntityBOMapper<E extends BaseEntity<?>, BO extends BusinessObject<?>> {

	E toEntity(BO businessObject);
	
	E toEntity(BO businessObject, List<String> mappingFiles);

	BO toBusinessObject(E entity);
	
	BO toBusinessObject(E entity, List<String> mappingFiles);

}