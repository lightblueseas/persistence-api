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

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.engine.jdbc.internal.DDLFormatterImpl;
import org.hibernate.engine.jdbc.internal.Formatter;

import de.alpharogroup.file.create.CreateFileExtensions;
import de.alpharogroup.file.read.ReadFileExtensions;
import de.alpharogroup.file.search.PathFinder;
import de.alpharogroup.file.write.WriteFileExtensions;
import de.alpharogroup.io.StreamExtensions;
import de.alpharogroup.jdbc.ConnectionsExtensions;

/**
 * The abstract class {@link AbstractDatabaseInitialization} for initialize a database.
 */
public abstract class AbstractDatabaseInitialization
{

	/** The Constant DELETE_PROCESS. */
	protected static final String DELETE_PROCESS = "delete";

	/** The Constant CREATE_PROCESS. */
	protected static final String CREATE_PROCESS = "create";

	/** The Constant CREATE_PROCESS. */
	protected static final String CREATE_EMPTY_PROCESS = "create-empty";

	/** The Constant DROP_PROCESS. */
	protected static final String DROP_PROCESS = "drop";

	/** The database properties. */
	protected final Properties databaseProperties;

	/** The host. */
	protected String host;

	/** The database name. */
	protected String databaseName;

	/** The database user. */
	protected String databaseUser;

	/** The database password. */
	protected String databasePassword;

	/** The initialization process. */
	protected String initializationProcess;

	/** The file encoding. */
	protected String fileEncoding;

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
		host = databaseProperties.getProperty("jdbc.host");
		databaseName = databaseProperties.getProperty("jdbc.db.name");
		databaseUser = databaseProperties.getProperty("jdbc.user");
		databasePassword = databaseProperties.getProperty("jdbc.password");
		initializationProcess = databaseProperties.getProperty("jdbc.create.db.process");
		fileEncoding = databaseProperties.getProperty("jdbc.file.encoding", "UTF-8");
		log = BooleanUtils.toBoolean(databaseProperties.getProperty("jdbc.show.sql.log"));
		final String vendor = databaseProperties.getProperty("jdbc.db.vendor");
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
		if (postgresDatabase)
		{
			final String result = replaceMediumblobToBytea(schema);
			sb.append(formatter.format(result));
		}
		else
		{
			final String result = ReadFileExtensions.readFromFile(schema);
			sb.append(formatter.format(result));
		}
		sb.append(System.getProperty("line.separator"));
		sb.append(System.getProperty("line.separator"));
		if (processtype.equals(DELETE_PROCESS))
		{
			final File createEnums = new File(sqlDir, "createEnumTypes.sql");
			if (createEnums.exists())
			{
				final String result = ReadFileExtensions.readFromFile(createEnums);
				sb.append(result);
				sb.append(System.getProperty("line.separator"));
			}
			final File updateEnums = new File(sqlDir, "updateEnumFields.sql");
			if (updateEnums.exists())
			{
				final String result = ReadFileExtensions.readFromFile(updateEnums);
				sb.append(result);
				sb.append(System.getProperty("line.separator"));
			}
		}
		final File createIndexesAndForeignKeys = new File(sqlDir, "createIndexesAndForeignKeys.sql");
		if (createIndexesAndForeignKeys.exists())
		{
			final String result = ReadFileExtensions.readFromFile(createIndexesAndForeignKeys);
			sb.append(result);
		}
		final File initializeSchemaDdl = new File(insertsDir, "initializeSchema.sql");
		final boolean writen = WriteFileExtensions.writeStringToFile(initializeSchemaDdl, sb.toString(),
			fileEncoding);
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
			System.err.println(writen);
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
	protected void createSchemaFromScript(final Connection jdbcConnection) throws IOException,
		SQLException
	{
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
	protected void deleteAndCreateEmptyDatabaseWithoutTables() throws ClassNotFoundException,
		SQLException
	{
		ConnectionsExtensions.dropPostgreSQLDatabase(host, databaseName, databaseUser, databasePassword);
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
	protected void dropTablesAndSequences(final Connection jdbcConnection) throws IOException,
		SQLException
	{
		final File projectDir = PathFinder.getProjectDirectory();
		final File dropSchemaSqlFile = PathFinder.getRelativePathTo(projectDir, "/",
			"src/main/resources/dll", "dropSchema.sql");
		if (!dropSchemaSqlFile.exists())
		{
			CreateFileExtensions.newFileQuietly(dropSchemaSqlFile);
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
			if (arg.equals(DELETE_PROCESS)
				|| arg.equals(DROP_PROCESS)
				|| arg.equals(CREATE_PROCESS)
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
		final String processtype = getProcessType();

		// check if database exist...
		final boolean dbExists = ConnectionsExtensions.existsPostgreSQLDatabase(host, databaseName,
			databaseUser, databasePassword);
		if(!dbExists) {
			newEmptyDatabaseWithoutTables();
		}

		if(!processtype.equals(CREATE_EMPTY_PROCESS)) {

			if (processtype.equals(DELETE_PROCESS))
			{
				deleteAndCreateEmptyDatabaseWithoutTables();
			}
			try ( // Get jdbc connection...
			Connection jdbcConnection = ConnectionsExtensions.getPostgreSQLConnection(host, databaseName,
				databaseUser, databasePassword))
			{
				if (processtype.equals(DROP_PROCESS))
				{
					// drop database schema...
					dropTablesAndSequences(jdbcConnection);
				}
				// create database schema...
				createSchema(jdbcConnection, processtype);
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
	protected void initializeDatabase(final Connection jdbcConnection) throws IOException,
		SQLException
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
		final File schemaDllDir = PathFinder.getRelativePathTo(projectDir, "/", "target/hibernate3/sql",
			"schema.ddl");
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
			WriteFileExtensions.writeLinesToFile(dropTables, dropSchemaSqlDir, "UTF-8");
			WriteFileExtensions.writeLinesToFile(createTables, schemaSqlDir, "UTF-8");
			WriteFileExtensions.writeLinesToFile(createIndexesAndAlterTable,
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
		ConnectionsExtensions.newPostgreSQLDatabase(host, databaseName, databaseUser, databasePassword,
			null, null);
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
		WriteFileExtensions.writeStringToFile(schema, result, "UTF-8");
		return result;
	}

}