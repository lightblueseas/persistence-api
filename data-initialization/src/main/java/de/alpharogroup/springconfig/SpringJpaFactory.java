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

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

/**
 * A factory class for creating jpa configuration objects.
 * @deprecated use instead same name class in project spring-tool-extensions
 */
@Deprecated
public class SpringJpaFactory
{

	/**
	 * Factory method for create the new {@link DataSource} object from the given
	 * {@link DataSourceBean} object.
	 *
	 * @param dataSourceBean
	 *            the {@link DataSourceBean} object
	 * @return the new {@link DataSource}
	 */
	public static DataSource newDataSource(final DataSourceBean dataSourceBean)
	{
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(dataSourceBean.getDriverClassName());
		dataSource.setUrl(dataSourceBean.getUrl());
		dataSource.setUsername(dataSourceBean.getUsername());
		dataSource.setPassword(dataSourceBean.getPassword());
		return dataSource;
	}

	/**
	 * Factory method for create the new {@link LocalContainerEntityManagerFactoryBean} object from
	 * the given persistence unit name as {@link String} object, the {@link DataSource} object, the
	 * {@link JpaVendorAdapter} object and the jpa {@link Properties}.
	 *
	 * @param persistenceUnitName
	 *            the persistence unit name
	 * @param dataSource
	 *            the data source
	 * @param vendorAdapter
	 *            the vendor adapter
	 * @param jpaProperties
	 *            the jpa properties
	 * @return the new {@link LocalContainerEntityManagerFactoryBean} object
	 */
	public static LocalContainerEntityManagerFactoryBean newEntityManagerFactoryBean(
		String persistenceUnitName, DataSource dataSource, JpaVendorAdapter vendorAdapter,
		Properties jpaProperties)
	{
		final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

		entityManagerFactoryBean.setPersistenceUnitName(persistenceUnitName);
		entityManagerFactoryBean.setDataSource(dataSource);

		entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
		entityManagerFactoryBean.setJpaProperties(jpaProperties);
		entityManagerFactoryBean.afterPropertiesSet();
		return entityManagerFactoryBean;
	}

	/**
	 * Factory method for create the new {@link JdbcTemplate} object from the given
	 * {@link DataSource} object.
	 *
	 * @param dataSource
	 *            the {@link DataSource} object
	 * @return the new {@link JdbcTemplate}
	 */
	public static JdbcTemplate newJdbcTemplate(DataSource dataSource)
	{
		JdbcTemplate jdbcTemplate;
		jdbcTemplate = new JdbcTemplate(dataSource);
		return jdbcTemplate;
	}

	/**
	 * Factory method for create the new {@link JpaVendorAdapter} object from the given
	 * {@link Database} object.
	 *
	 * @param db
	 *            the {@link Database} object
	 * @return the new {@link JpaVendorAdapter}
	 */
	public static JpaVendorAdapter newJpaVendorAdapter(Database db)
	{
		final HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setShowSql(false);
		hibernateJpaVendorAdapter.setGenerateDdl(true);
		hibernateJpaVendorAdapter.setDatabase(db);
		return hibernateJpaVendorAdapter;
	}

	/**
	 * Factory method for create the new {@link JpaTransactionManager} object from the given
	 * {@link EntityManagerFactory} object.
	 *
	 * @param entityManagerFactory
	 *            {@link EntityManagerFactory} object
	 * @return the new {@link JpaTransactionManager}
	 */
	public static JpaTransactionManager newTransactionManager(
		EntityManagerFactory entityManagerFactory)
	{
		final JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	}

}
