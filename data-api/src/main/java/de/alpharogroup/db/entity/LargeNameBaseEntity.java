package de.alpharogroup.db.entity;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The class {@link NameBaseEntity} is a base entity for a table with a single value.
 *
 * @param <T> the generic type of the id
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Access(AccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
public abstract class LargeNameBaseEntity<T extends Serializable> extends VersionableBaseEntity<T>
{

	/**  The serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** The name. */
	@Column( unique=true, name = "name", length = 512  )
	private String name;
}
