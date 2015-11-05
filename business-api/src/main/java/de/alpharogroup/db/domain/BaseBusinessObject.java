package de.alpharogroup.db.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * The abstract class BaseBusinessObject is the base class for all business objects.
 *
 * @param <K> the primary key type of the corresponding entity
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
public abstract class BaseBusinessObject<K> implements BusinessObject<K> {
	/**
	 * The serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/** The id. */
	@Getter
	@Setter
	K id;
}
