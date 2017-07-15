package de.alpharogroup.springconfig;

import java.util.List;

import de.alpharogroup.collections.ListExtensions;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;

/**
 * The class {@link JdbcUrlBean} hold data to build a jdbc url and factory methods for create a jdbc
 * url as an {@link String} object.
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class JdbcUrlBean
{

	/** The protocol. */
	private String protocol;

	/** The host. */
	private String host;

	/** The port. */
	private int port;

	/** The database. */
	private String database;

	/** The parameters. */
	@Singular
	private List<String> parameters;

	/** The default builder for the postgresql jdbc url. */
	public static final JdbcUrlBean DEFAULT_POSTGRESQL_URL = JdbcUrlBean.builder()
		.protocol("jdbc:postgresql://").host("localhost").port(5432).build();

	/** The default builder for the mysql jdbc url. */
	public static final JdbcUrlBean DEFAULT_MYSQL_URL = JdbcUrlBean.builder()
		.protocol("jdbc:mysql://").host("localhost").port(3306).build();

	/**
	 * Builds a default postgres jdbc url with the given database.
	 *
	 * @param database
	 *            the database
	 * @return the jdbc url
	 */
	public static String newDefaultPostgresJdbcUrl(final String database)
	{
		return newPostgresJdbcUrl(JdbcUrlBean.DEFAULT_POSTGRESQL_URL.toBuilder().database(database).build());
	}

	/**
	 * Builds a postgres jdbc url with the given {@link JdbcUrlBean} object.
	 *
	 * @param bean
	 *            the bean
	 * @return the string
	 */
	public static String newPostgresJdbcUrl(final JdbcUrlBean bean)
	{
		final StringBuilder sb = new StringBuilder();
		sb.append(bean.getProtocol()).append(bean.getHost()).append(":").append(bean.getPort())
			.append("/").append(bean.getDatabase());
		return sb.toString();
	}

	/**
	 * Builds a H2 jdbc url with the given {@link JdbcUrlBean} object.
	 *
	 * @param bean
	 *            the bean
	 * @return the string
	 */
	public static String newH2JdbcUrl(final JdbcUrlBean bean)
	{
		final StringBuilder sb = new StringBuilder();
		sb.append(bean.getProtocol()).append(bean.getDatabase());
		if (ListExtensions.isNotEmpty(bean.getParameters()))
		{
			for (final String parameter : bean.getParameters())
			{
				sb.append(";").append(parameter);
			}
		}
		return sb.toString();
	}

	/**
	 * Builds a default mysql jdbc url with the given database.
	 *
	 * @param database
	 *            the database
	 * @return the jdbc url
	 */
	public static String newDefaultMysqlJdbcUrl(final String database)
	{
		return newMysqlJdbcUrl(JdbcUrlBean.DEFAULT_MYSQL_URL.toBuilder().database(database).build());
	}

	/**
	 * Builds a mysql jdbc url with the given {@link JdbcUrlBean} object.
	 *
	 * @param bean
	 *            the bean
	 * @return the string
	 */
	public static String newMysqlJdbcUrl(final JdbcUrlBean bean)
	{
		final StringBuilder sb = new StringBuilder();
		sb.append(bean.getProtocol()).append(bean.getHost()).append(":").append(bean.getPort())
			.append("/").append(bean.getDatabase());
		return sb.toString();
	}


}
