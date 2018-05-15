package de.alpharogroup.db.entity.name;

import lombok.experimental.UtilityClass;

/**
 * A factory for creating JpqlString objects.
 */
@UtilityClass
public class JpqlStringFactory
{

	/**
	 * Creates jpql query for the given name entity class.
	 *
	 * @param name
	 *            the name
	 *
	 * @return the jpql string
	 */
	public static<T extends NameEntity<?>> String forNameEntity(final Class<T> entityClass, final String name)
	{
		final StringBuilder sb = new StringBuilder();
		sb.append("select ne from " + entityClass.getSimpleName() + " ne");
		final boolean nameIsNotNull = name != null && !name.isEmpty();
		if (nameIsNotNull)
		{
			sb.append(" ");
			sb.append("where ne.name=:name");
		}
		return sb.toString();
	}
}
