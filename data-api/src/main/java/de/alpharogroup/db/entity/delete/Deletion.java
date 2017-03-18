package de.alpharogroup.db.entity.delete;

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
 * The entity class {@link Deletion} is keeping the information for the deletion
 * of an parent entity. The intent of this entity is that a parent entity will
 * not really deleted. The initial value of the parent entity the reference to
 * this entity will be null that signals that it is not deleted. So this entity
 * will only be created if the parent entity references to this entity and is
 * intended to be deleted. This can be done for instance in a deletion strategy.
 * 
 * Note: There is no need of a delete flag in this entity because this entity is
 * the flag. So if the parent entity is not any more 'deleted' the reference of
 * this entity will be set to null back.
 *
 * @param <PK>
 *            the generic type of the id
 * @param <U>
 *            the generic type of the user or account
 */
@Entity
@Table(name = "deletion")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Deletion<PK extends Serializable, U> extends BaseEntity<PK> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The date and time when the entity that owns this entity was deleted. */
	private LocalDateTime deleted;

	/** The user or account that deleted the entity that owns this entity. */
	private U deletedBy;

}
