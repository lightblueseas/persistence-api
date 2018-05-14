package de.alpharogroup.db.entity.traceable;

import de.alpharogroup.db.entity.create.Creatable;
import de.alpharogroup.db.entity.delete.Deletable;
import de.alpharogroup.db.entity.modify.LastModified;

public interface Traceable<T, U> extends Creatable<T, U>, LastModified<T, U>, Deletable<T, U>
{
}
