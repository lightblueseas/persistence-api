package de.alpharogroup.db.entity.uniqueable;

import java.io.Serializable;

/**
 * The interface {@link Uniqueable} can be implemented from an entity that have to be uniqueable.
 * An good example would be entities that are needed in rest services
 *
 * @param <PK>
 *            the generic type of the unique identifier
 */
public interface Uniqueable<PK extends Serializable> {

    /**
     * Gets the uuid.
     *
     * @return the uuid
     */
    PK getUuid();

    /**
     * Sets the uuid.
     *
     * @param uuid
     *            the new uuid
     */
    void setUuid(final PK uuid);
}
