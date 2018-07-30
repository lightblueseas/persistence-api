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
package de.alpharogroup.db.init;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.engine.jdbc.internal.DDLFormatterImpl;
import org.hibernate.engine.jdbc.internal.Formatter;

import de.alpharogroup.file.create.CreateFileQuietlyExtensions;
import de.alpharogroup.file.read.ReadFileExtensions;
import de.alpharogroup.file.search.PathFinder;
import de.alpharogroup.file.write.WriteFileQuietlyExtensions;
import de.alpharogroup.io.StreamExtensions;
import de.alpharogroup.jdbc.ConnectionsExtensions;

/**
 * The abstract class {@link AbstractDatabaseInitialization} for initialize, drop and create a
 * database.
 */
public abstract class AbstractDatabaseInitialization
{

	/** The Constant CREATE_PROCESS. */
	public static final String CREATE_EMPTY_PROCESS = "create-empty";

	/** The Constant CREATE_PROCESS. */
	public static final String CREATE_PROCESS = "create";

	/** The Constant DELETE_PROCESS. */
	public static final String DELETE_PROCESS = "delete";

	/** The Constant DROP_PROCESS. */
	public static final String DROP_PROCESS = "drop";

	/** The Constant JDBC_CREATE_DB_PROCESS_KEY. */
	public static final String JDBC_CREATE_DB_PROCESS_KEY = "jdbc.create.db.process";

	/** The Constant JDBC_DB_NAME_KEY. */
	public static final String JDBC_DB_NAME_KEY = "jdbc.db.name";

	/** The Constant JDBC_DB_VENDOR_KEY for the properties key. */
	public static final String JDBC_DB_VENDOR_KEY = "jdbc.db.vendor";

	/** The Constant JDBC_FILE_ENCODING_KEY. */
	public static final String JDBC_FILE_ENCODING_KEY = "jdbc.file.encoding";

	/** The Constant JDBC_HOST_KEY. */
	public static final String JDBC_HOST_KEY = "jdbc.host";

	/** The Constant JDBC_PASSWORD_KEY. */
	public static final String JDBC_PASSWORD_KEY = "jdbc.password";

	/** The Constant JDBC_SHOW_SQL_LOG_KEY. */
	public static final String JDBC_SHOW_SQL_LOG_KEY = "jdbc.show.sql.log";

	/** The Constant JDBC_USER_KEY. */
	public static final String JDBC_USER_KEY = "jdbc.user";

	/** The logger constant. */
	protected static final Logger LOG = Logger
		.getLogger(AbstractDatabaseInitialization.class.getName());

	/** The database name. */
	protected String databaseName;

	/** The database password. */
	protected String databasePassword;

	/** The database properties. */
	protected final Properties databaseProperties;

	/** The database user. */
	protected String databaseUser;

	/** The file encoding. */
	protected String fileEncoding;

	/** The host. */
	protected String host;

	/** The initialization process. */
	protected String initializationProcess;

	/** The log. */
	protected boolean log;

	/** The postgres database. */
	protected boolean postgresDatabase;

	/**
	 * Instantiates a new {@link AbstractDatabaseInitialization}.
	 *
	 * @param databaseProperties
	 *            the database properties
	 */
	public AbstractDatabaseInitialization(final Properties databaseProperties)
	{
		this.databaseProperties = databaseProperties;
		host = databaseProperties.getProperty(JDBC_HOST_KEY);
		databaseName = databaseProperties.getProperty(JDBC_DB_NAME_KEY);
		databaseUser = databaseProperties.getProperty(JDBC_USER_KEY);
		databasePassword = databaseProperties.getProperty(JDBC_PASSWORD_KEY);
		initializationProcess = databaseProperties.getProperty(JDBC_CREATE_DB_PROCESS_KEY);
		fileEncoding = databaseProperties.getProperty(JDBC_FILE_ENCODING_KEY, "UTF-8");
		log = BooleanUtils.toBoolean(databaseProperties.getProperty(JDBC_SHOW_SQL_LOG_KEY));
		final String vendor = databaseProperties.getProperty(JDBC_DB_VENDOR_KEY);
		if ((vendor != null) && !vendor.isEmpty())
		{
			postgresDatabase = BooleanUtils.toBoolean(vendor);
		}
		else
		{
			postgresDatabase = true;
		}
	}

	/**
	 * Creates the initialization script.
	 *
	 * @param processtype
	 *            the processtype
	 * @return true, if successful
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	protected boolean createInitializationScript(final String processtype) throws IOException
	{
		// Get the sql dir...
		final File sqlDir = getSqlDir();
		final File insertsDir = getInsertDir();
		final StringBuilder sb = new StringBuilder();
		final Formatter formatter = new DDLFormatterImpl();
		final File schema = new File(sqlDir, "schema.sql");
		final String schemaString;
		LOG.debug("start process of creation of initializeSchema.sql file.");
		LOG.debug("read from file schema.sql");
		if (postgresDatabase)
		{
			schemaString = replaceMediumblobToBytea(schema);
		}
		else
		{
			schemaString = ReadFileExtensions.readFromFile(schema);
		}
		sb.append(formatter.format(schemaString));
		sb.append(System.getProperty("line.separator"));
		sb.append(System.getProperty("line.separator"));
		if (processtype.equals(DELETE_PROCESS))
		{
			final File createEnums = new File(sqlDir, "createEnumTypes.sql");
			if (createEnums.exists())
			{
				LOG.debug("read from file createEnumTypes.sql");
				final String result = ReadFileExtensions.readFromFile(createEnums);
				sb.append(result);
				sb.append(System.getProperty("line.separator"));
			}
			final File updateEnums = new File(sqlDir, "updateEnumFields.sql");
			if (updateEnums.exists())
			{
				LOG.debug("read from file updateEnumFields.sql");
				final String result = ReadFileExtensions.readFromFile(updateEnums);
				sb.append(result);
				sb.append(System.getProperty("line.separator"));
			}
		}
		final File createIndexesAndForeignKeys = new File(sqlDir,
			"createIndexesAndForeignKeys.sql");
		if (createIndexesAndForeignKeys.exists())
		{
			LOG.debug("read from file createIndexesAndForeignKeys.sql");
			final String result = ReadFileExtensions.readFromFile(createIndexesAndForeignKeys);
			sb.append(result);
		}
		final File initializeSchemaDdl = new File(insertsDir, "initializeSchema.sql");
		LOG.debug("write result to file initializeSchema.sql");
		final boolean writen = WriteFileQuietlyExtensions.writeStringToFile(initializeSchemaDdl,
			sb.toString(), fileEncoding);
		LOG.debug("end process of creation of initializeSchema.sql file.");
		return writen;
	}

	/**
	 * Creates the schema.
	 *
	 * @param jdbcConnection
	 *            the jdbc connection
	 * @param processtype
	 *            the processtype
	 * @throws FileNotFoundException
	 *             the file not found exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws SQLException
	 *             the SQL exception
	 */
	protected void createSchema(final Connection jdbcConnection, final String processtype)
		throws FileNotFoundException, IOException, SQLException
	{
		initializeScriptFiles();
		final boolean writen = createInitializationScript(processtype);

		if (writen)
		{
			LOG.debug("write process of file initializeSchema.sql is finished");
		}
		createSchemaFromScript(jdbcConnection);
	}

	/**
	 * Creates the initialization script for the schema.
	 *
	 * @param jdbcConnection
	 *            the jdbc connection
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws SQLException
	 *             Signals that an sql error has occurred.
	 */
	protected void createSchemaFromScript(final Connection jdbcConnection)
		throws IOException, SQLException
	{
		LOG.debug("creating database schema from script");
		final File insertsDir = getInsertDir();
		final File initializeSchemaDdl = new File(insertsDir, "initializeSchema.sql");
		final String result = ReadFileExtensions.readFromFile(initializeSchemaDdl);
		ConnectionsExtensions.executeSqlScript(jdbcConnection, result, log);
	}

	/**
	 * Deletes and creates an empty database without tables so without creating the database schema.
	 *
	 * @throws ClassNotFoundException
	 *             occurs if a class has not been found
	 * @throws SQLException
	 *             Signals that an sql error has occurred.
	 */
	protected void deleteAndCreateEmptyDatabaseWithoutTables()
		throws ClassNotFoundException, SQLException
	{
		ConnectionsExtensions.dropPostgreSQLDatabase(host, databaseName, databaseUser,
			databasePassword);
		newEmptyDatabaseWithoutTables();
	}

	/**
	 * Drops all tables and sequences.
	 *
	 * @param jdbcConnection
	 *            the jdbc connection
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws SQLException
	 *             Signals that an sql error has occurred.
	 */
	protected void dropTablesAndSequences(final Connection jdbcConnection)
		throws IOException, SQLException
	{
		final File projectDir = PathFinder.getProjectDirectory();
		final File dropSchemaSqlFile = PathFinder.getRelativePathTo(projectDir, "/",
			"src/main/resources/dll", "dropSchema.sql");
		if (!dropSchemaSqlFile.exists())
		{
			CreateFileQuietlyExtensions.newFileQuietly(dropSchemaSqlFile);
		}
		ConnectionsExtensions.executeSqlScript(
			(BufferedReader)StreamExtensions.getReader(dropSchemaSqlFile, fileEncoding, false),
			jdbcConnection, log);
	}

	/**
	 * Gets the database properties.
	 *
	 * @return the database properties
	 */
	public Properties getDatabaseProperties()
	{
		return databaseProperties;
	}

	/**
	 * Gets the insert dir.
	 *
	 * @return the insert dir
	 */
	protected File getInsertDir()
	{
		final File insertsDir = new File(getSqlDir(), "inserts");
		return insertsDir;
	}

	/**
	 * Gets the process type to execute. Default is the 'drop' process type.
	 *
	 * @return the process type
	 */
	protected String getProcessType()
	{
		String processtype = DROP_PROCESS;
		if (initializationProcess != null)
		{
			final String arg = initializationProcess;
			if (arg.equals(DELETE_PROCESS) || arg.equals(DROP_PROCESS) || arg.equals(CREATE_PROCESS)
				|| arg.equals(CREATE_EMPTY_PROCESS))
			{
				processtype = arg;
			}
		}
		return processtype;
	}

	/**
	 * Gets the script files.
	 *
	 * @return the script files
	 */
	protected abstract List<File> getScriptFiles();

	/**
	 * Gets the sql dir.
	 *
	 * @return the sql dir
	 */
	protected File getSqlDir()
	{
		// The resources destination dir
		final File resDestDir = PathFinder.getSrcMainResourcesDir();
		// Get the sql dir...
		final File sqlDir = new File(resDestDir, "dll");
		return sqlDir;
	}

	/**
	 * Initialize database.
	 *
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 * @throws SQLException
	 *             the SQL exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void initializeDatabase() throws ClassNotFoundException, SQLException, IOException
	{
		LOG.debug("Initialize database started...");
		final String processtype = getProcessType();
		LOG.debug("with processtype " + processtype);

		// check if database exist...
		final boolean dbExists = ConnectionsExtensions.existsPostgreSQLDatabase(host, databaseName,
			databaseUser, databasePassword);
		if (!dbExists)
		{
			LOG.debug("database does not exists");
			newEmptyDatabaseWithoutTables();
		}

		if (!processtype.equals(CREATE_EMPTY_PROCESS))
		{

			if (processtype.equals(DELETE_PROCESS))
			{
				LOG.debug("delete and create empty database without tables");
				deleteAndCreateEmptyDatabaseWithoutTables();
			}

			LOG.debug("Get jdbc connection to database");
			try ( // Get jdbc connection...
				Connection jdbcConnection = ConnectionsExtensions.getPostgreSQLConnection(host,
					databaseName, databaseUser, databasePassword))
			{
				if (processtype.equals(DROP_PROCESS))
				{
					LOG.debug("drop database schema");
					// drop database schema...
					dropTablesAndSequences(jdbcConnection);
				}
				LOG.debug("create database schema");
				// create database schema...
				createSchema(jdbcConnection, processtype);
				LOG.debug("initialize database with some data");
				// initialize database with some data...
				initializeDatabase(jdbcConnection);
				// close connection...
			}

		}
	}

	/**
	 * Initialize database.
	 *
	 * @param jdbcConnection
	 *            the jdbc connection
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws SQLException
	 *             Signals that an sql error has occurred.
	 */
	protected void initializeDatabase(final Connection jdbcConnection)
		throws IOException, SQLException
	{
		final List<File> scriptFiles = getScriptFiles();
		final int size = scriptFiles.size();
		for (int i = 0; i < size; i++)
		{
			ConnectionsExtensions.executeSqlScript(
				(BufferedReader)StreamExtensions.getReader(scriptFiles.get(i), fileEncoding, false),
				jdbcConnection, log);
		}
	}

	/**
	 * Initialize the script files from the generated schema file from the hibernate3-maven-plugin.
	 *
	 * @throws FileNotFoundException
	 *             occurs if a class has not been found
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	protected void initializeScriptFiles() throws FileNotFoundException, IOException
	{
		final File projectDir = PathFinder.getProjectDirectory();
		final File schemaDllDir = PathFinder.getRelativePathTo(projectDir, "/",
			"target/hibernate3/sql", "schema.ddl");
		if (schemaDllDir.exists())
		{
			final File schemaSqlDir = PathFinder.getRelativePathTo(projectDir, "/",
				"src/main/resources/dll", "schema.sql");
			final File dropSchemaSqlDir = PathFinder.getRelativePathTo(projectDir, "/",
				"src/main/resources/dll", "dropSchema.sql");
			final File createIndexesAndForeignKeys = PathFinder.getRelativePathTo(projectDir, "/",
				"src/main/resources/dll", "createIndexesAndForeignKeys.sql");
			final List<String> lines = ReadFileExtensions.readLinesInList(schemaDllDir);
			final List<String> dropTables = new ArrayList<>();
			final List<String> createTables = new ArrayList<>();
			final List<String> createIndexesAndAlterTable = new ArrayList<>();
			for (final String currentLine : lines)
			{
				if (currentLine.startsWith("alter table")
					&& currentLine.contains("drop constraint"))
				{
					dropTables.add(currentLine);
					continue;
				}
				if (currentLine.startsWith("drop "))
				{
					dropTables.add(currentLine);
					continue;
				}
				if (currentLine.startsWith("create table"))
				{
					createTables.add(currentLine);
				}
				if (currentLine.startsWith("create index") || currentLine.startsWith("alter table")
					|| currentLine.startsWith("create sequence"))
				{
					createIndexesAndAlterTable.add(currentLine);
				}
			}
			WriteFileQuietlyExtensions.writeLinesToFile(dropTables, dropSchemaSqlDir, "UTF-8");
			WriteFileQuietlyExtensions.writeLinesToFile(createTables, schemaSqlDir, "UTF-8");
			WriteFileQuietlyExtensions.writeLinesToFile(createIndexesAndAlterTable,
				createIndexesAndForeignKeys, "UTF-8");
		}
	}

	/**
	 * Creates an empty database without tables and no database schema.
	 *
	 * @throws ClassNotFoundException
	 *             occurs if a class has not been found
	 * @throws SQLException
	 *             Signals that an sql error has occurred.
	 */
	protected void newEmptyDatabaseWithoutTables() throws ClassNotFoundException, SQLException
	{
		ConnectionsExtensions.newPostgreSQLDatabase(host, databaseName, databaseUser,
			databasePassword, null, null);
	}

	/**
	 * Replace mediumblob to bytea.
	 *
	 * @param schema
	 *            the schema
	 * @return the string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	protected String replaceMediumblobToBytea(final File schema) throws IOException
	{
		final String contentSchema = ReadFileExtensions.readFromFile(schema);
		final String result = StringUtils.replace(contentSchema, "MEDIUMBLOB", "BYTEA");
		WriteFileQuietlyExtensions.writeStringToFile(schema, result, "UTF-8");
		return result;
	}

}