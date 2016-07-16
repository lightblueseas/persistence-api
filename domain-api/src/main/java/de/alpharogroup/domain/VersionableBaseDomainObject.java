package de.alpharogroup.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * The abstract class {@link VersionableBaseDomainObject} is the base class for all domain objects that needs to be versionable.
 *
 * @param <K> the primary key type of the corresponding entity
 */
@Getter
@Setter
@ToString(callSuper=true)
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
public class VersionableBaseDomainObject<K> extends BaseDomainObject<K> {
	
	/**
	 * The serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The version property for the optimistic lock value.
	 **/
	private Integer version;

}
