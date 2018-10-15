package de.alpharogroup.db.entity.uniqueable;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;

/**
 * The class {@link UniqueableEntity} holds an UUID as primary key.
 *
 * @param <PK>
 *            the generic type of the UUID technical primary key.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class UniqueableEntity<PK extends Serializable> implements Serializable, Uniqueable<PK>
{

	/** The serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The technical primary key. */
	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
			name = "UUID",
			strategy = "org.hibernate.id.UUIDGenerator"
	)
	@Column(name = "uuid", updatable = false, nullable = false)
	PK uuid;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString()
	{
		return getClass().getSimpleName() + ": uuid=" + uuid;
	}

}
