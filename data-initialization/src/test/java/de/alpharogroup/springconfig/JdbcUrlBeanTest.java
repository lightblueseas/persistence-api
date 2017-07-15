/**
 * Copyright (C) 2015 Asterios Raptis
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
