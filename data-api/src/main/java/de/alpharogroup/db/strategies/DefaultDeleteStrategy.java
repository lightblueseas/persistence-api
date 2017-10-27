package de.alpharogroup.db.strategies;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import de.alpharogroup.db.entity.BaseEntity;
import de.alpharogroup.lang.TypeArgumentsExtensions;
import lombok.Getter;
import lombok.NonNull;

/**
 * The class {@link DefaultDeleteStrategy} is a default implementation of the {@link DeleteStrategy}.
 *
 * @param <T>
 *            the type of the entity object
 * @param <PK>
 *            the type of the primary key from the entity object
 */
public class DefaultDeleteStrategy<T extends BaseEntity<PK>, PK extends Serializable> implements DeleteStrategy<T, PK>
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The class type of the entity. */
	@Getter
	@SuppressWarnings("unchecked")
	private final Class<T> type = (Class<T>)TypeArgumentsExtensions
		.getFirstTypeArgument(DefaultDeleteStrategy.class, this.getClass());

	/**
	 * Gets the entity manager.
	 *
	 * @return the entity manager
	 */
	@Getter @NonNull
	private final EntityManager entityManager;

	/**
	 * Instantiates a new {@link DefaultDeleteStrategy}.
	 *
	 * @param entityManager the entity manager
	 */
	public DefaultDeleteStrategy(EntityManager entityManager)
	{
		this.entityManager = entityManager;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(List<T> objects)
	{
		for (final T entity : objects)
		{
			if(getEntityManager().contains(entity)) {
				getEntityManager().remove(entity);
			} else {
				getEntityManager().remove(getEntityManager().merge(entity));
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(PK id)
	{
		final T entity = getEntityManager().find(type, id);
		delete(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(T entity)
	{
		if(getEntityManager().contains(entity)) {
			getEntityManager().remove(entity);
		} else {
			getEntityManager().remove(getEntityManager().merge(entity));
		}
	}

}
