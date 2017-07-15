package de.alpharogroup.springconfig;

import static org.testng.AssertJUnit.assertEquals;

import org.testng.annotations.Test;

/**
 * The test class for {@link JdbcUrlBean}.
 */
public class JdbcUrlBeanTest
{

	@Test
	public void testNewPostgresJdbcUrl()
	{
		String expected;
		String actual;
		expected = "jdbc:postgresql://localhost:5432/foo";
		actual = JdbcUrlBean.newDefaultPostgresJdbcUrl("foo");
		assertEquals(expected, actual);

		final JdbcUrlBean bean = JdbcUrlBean.builder().protocol("jdbc:postgresql://")
			.host("10.0.101.23").port(5555).database("foo").build();

		expected = "jdbc:postgresql://10.0.101.23:5555/foo";
		actual = JdbcUrlBean.newPostgresJdbcUrl(bean);
		assertEquals(expected, actual);


	}

	@Test
	public void testNewMysqlJdbcUrl()
	{
		String expected;
		String actual;
		expected = "jdbc:mysql://localhost:3306/foo";
		actual = JdbcUrlBean.newDefaultMysqlJdbcUrl("foo");
		assertEquals(expected, actual);

		final JdbcUrlBean bean = JdbcUrlBean.builder().protocol("jdbc:mysql://").host("10.0.101.23")
			.port(5555).database("foo").build();

		expected = "jdbc:mysql://10.0.101.23:5555/foo";
		actual = JdbcUrlBean.newPostgresJdbcUrl(bean);
		assertEquals(expected, actual);
	}

	@Test
	public void testNewH2JdbcUrl()
	{
		String expected;
		String actual;

		expected = "jdbc:h2:file:~/bundlemanagement;MODE=PostgreSQL;DB_CLOSE_ON_EXIT=FALSE;DB_CLOSE_DELAY=-1";
		final JdbcUrlBean bean = JdbcUrlBean.builder().protocol("jdbc:h2:")
			.database("file:~/bundlemanagement").parameter("MODE=PostgreSQL")
			.parameter("DB_CLOSE_ON_EXIT=FALSE").parameter("DB_CLOSE_DELAY=-1").build();
		actual = JdbcUrlBean.newH2JdbcUrl(bean);
		assertEquals(expected, actual);
	}

}
