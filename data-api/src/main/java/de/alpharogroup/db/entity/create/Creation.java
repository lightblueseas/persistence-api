package de.alpharogroup.db.entity.create;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Table;

import de.alpharogroup.db.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * The entity class {@link Creation} is keeping the information for the creation of an entity. This
 * entity can be attached to the parent entity for keep information when the parent entity was
 * created.
 *
 * @param <PK>
 *            the generic type of the id
 * @param <U>
 *            the generic type of the user or account
 */
@Entity
@Table(name = "creation")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder=true)
public class Creation<PK extends Serializable, U> extends BaseEntity<PK>
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The date and time when the entity that owns this entity was created. */
	private LocalDateTime created;

	/** The user or account that created the entity that owns this entity. */
	private U createdBy;

}
