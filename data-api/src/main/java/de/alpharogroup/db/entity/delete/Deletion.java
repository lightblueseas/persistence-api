package de.alpharogroup.db.entity.delete;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Table;

import de.alpharogroup.db.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The entity class {@link Deletion} is keeping the information for the deletion of an
 * entity. The intent of this entity is that a parent entity will not really deleted. So this entity
 * will only be created if the parent entity references to this entity.
 *
 * @param <PK>
 *            the generic type of the id
 * @param <D>
 *            the generic type of the user or account
 */
@Entity
@Table(name = "deletion")
@Getter
@Setter
@NoArgsConstructor
public class Deletion<PK extends Serializable, D> extends BaseEntity<PK>
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The date and time when the entity that owns this entity was deleted. */
	private LocalDateTime deleted;

	/** The user or account that deleted the entity that owns this entity. */
	private D deletedBy;

}
