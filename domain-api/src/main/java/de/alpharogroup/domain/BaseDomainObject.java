package de.alpharogroup.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * The abstract class {@link BaseDomainObject} is the base class for all domain objects.
 *
 * @param <K> the primary key type of the corresponding entity
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
public abstract class BaseDomainObject<K> implements DomainObject<K> {
	/**
	 * The serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/** The id. */
	@Getter
	@Setter
	K id;
}
