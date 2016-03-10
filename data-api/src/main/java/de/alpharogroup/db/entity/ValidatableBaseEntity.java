package de.alpharogroup.db.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The class {@link ValidatableBaseEntity} is a base entity and has a validFrom property and a validTill property for restricting an entity in a range in which it is valid.
 *
 * @param <T> the generic type of the id
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Access(AccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
public class ValidatableBaseEntity<T extends Serializable> extends VersionableBaseEntity<T>
{

	/**  The serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** The valid from date for validation. */
	private LocalDateTime validFrom;

    /** The valid till date for validation. */
    private LocalDateTime validTill;
}
