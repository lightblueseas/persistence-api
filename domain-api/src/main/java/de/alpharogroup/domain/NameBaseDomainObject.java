package de.alpharogroup.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * The abstract class {@link NameBaseDomainObject} is the base class for all domain objects that have a name attribute.
 *
 * @param <K> the primary key type of the corresponding entity
 */
@Getter
@Setter
@ToString(callSuper=true)
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
public class NameBaseDomainObject<K> extends VersionableBaseDomainObject<K> {
	
	/**
	 * The serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/** The name. */
	private String name;

}
