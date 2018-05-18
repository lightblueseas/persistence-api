package de.alpharogroup.service.api;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import de.alpharogroup.collections.list.ListExtensions;
import de.alpharogroup.db.entity.name.JpqlStringFactory;
import de.alpharogroup.db.entity.name.NameEntity;
import de.alpharogroup.db.service.api.BusinessService;
import de.alpharogroup.lang.TypeArgumentsExtensions;

public interface NameEntityService<T extends NameEntity<PK>, PK extends Serializable>
	extends
		BusinessService<T, PK>
{

	/**
	 * Find the entity object from the given name value.
	 * 
	 * @param nameValue
	 *            the name value
	 * @return the found entity object or null if not.
	 */
	default T find(final String nameValue)
	{
		@SuppressWarnings("unchecked")
		Class<T> type = (Class<T>)TypeArgumentsExtensions
			.getFirstTypeArgument(this.getClass());
		final String hqlString = JpqlStringFactory.forNameEntity(type, nameValue);
		final Query query = getQuery(hqlString);
		if (nameValue != null && !nameValue.isEmpty())
		{
			query.setParameter("name", nameValue);
		}
		@SuppressWarnings("unchecked")
		final List<T> nameEntities = query.getResultList();
		return ListExtensions.getFirst(nameEntities);
	}

	/**
	 * Gets the or creates a new entity object
	 *
	 * @param value
	 *            the value
	 * @return the entity object
	 */
	@Transactional
	default T getOrCreateNewNameEntity(final String value)
	{
		T nameEntity = find(value);
		if (nameEntity == null)
		{
			nameEntity = newNameEntity(value);
			nameEntity = merge(nameEntity);
		}
		return nameEntity;
	}
	
	/**
	 * Factory method for create a new name entity.
	 *
	 * @param value the value
	 * @return the new name entity
	 */
	T newNameEntity(final String value);
}