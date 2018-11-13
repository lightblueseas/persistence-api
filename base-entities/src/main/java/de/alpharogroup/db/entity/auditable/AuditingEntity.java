package de.alpharogroup.db.entity.auditable;

import de.alpharogroup.db.entity.create.Creation;
import de.alpharogroup.db.entity.delete.Deletion;
import de.alpharogroup.db.entity.modify.LastModification;
import de.alpharogroup.db.entity.traceable.TraceableEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

/**
 * The entity class {@link AuditingEntity} can be used for entities that have to be audited. <br>
 *
 * @param <T>
 *            the generic type of time measurement
 * @param <U>
 *            the generic type of the user or account
 * @see Creation
 * @see LastModification
 */
@Getter
@Setter
@Entity
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditingEntity<T, U> implements Auditable<T, U> {

    /** The date and time when this entity was created */
    @CreatedDate
    private T created;

    /** The user or account that created this entity */
    @CreatedBy
    private U createdBy;

    /** The date and time when this entity was last modified */
    @LastModifiedDate
    private T lastModified;

    /** The user or account that last modified this entity */
    @LastModifiedBy
    private U lastModifiedBy;
}
