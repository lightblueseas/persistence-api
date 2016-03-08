package de.alpharogroup.db.entity;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class {@link VersionableBaseEntity}.
 *
 * @param <T> the generic type
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Access(AccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
public abstract class VersionableBaseEntity<T extends Serializable> extends BaseEntity<T> {


	/**  The serial Version UID. */
	private static final long serialVersionUID = 1L;

	/**
	 * The version property for the optimistic lock value.
	 *
	 * @see {@link javax.persistence.Version}
	 * */
	@Version
	private Integer version;

}