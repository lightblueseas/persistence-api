package de.alpharogroup.db.entity.uniqueable;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

/**
 * The abstract class {@link UUIDEntity} is a concrete class of {@link UniqueableEntity} and holds an {@link UUID} as primary key.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@NoArgsConstructor
public abstract class UUIDEntity extends UniqueableEntity<UUID>
{
	/** The serialVersionUID. */
	private static final long serialVersionUID = 1L;
}
