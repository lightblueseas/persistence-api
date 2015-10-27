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

import de.alpharogroup.file.create.CreateFileUtils;
import de.alpharogroup.file.read.ReadFileUtils;
import de.alpharogroup.file.search.PathFinder;
import de.alpharogroup.file.write.WriteFileUtils;
import de.alpharogroup.io.StreamUtils;
import de.alpharogroup.jdbc.ConnectionsUtils;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.engine.jdbc.internal.DDLFormatterImpl;
import org.hibernate.engine.jdbc.internal.Formatter;

public abstract class AbstractDatabaseInitialization
{

	protected static final String DELETE_PROCESS = "delete";
	protected static final String CREATE_PROCESS = "create";
	protected static final String DROP_PROCESS = "drop";
	protected final Properties databaseProperties;
	protected String host;
	protected String databaseName;
	protected String databaseUser;
	protected String databasePassword;
	protected String initializationProcess;
	protected String fileEncoding;
	protected boolean log;
	protected boolean postgresDatabase;

	public AbstractDatabaseInitialization(Properties databaseProperties)
	{
		this.databaseProperties = databaseProperties;
		host = databaseProperties.getProperty("jdbc.host");
		databaseName = databaseProperties.getProperty("jdbc.db.name");
		databaseUser = databaseProperties.getProperty("jdbc.user");
		databasePassword = databaseProperties.getProperty("jdbc.password");
		initializationProcess = databaseProperties.getProperty("jdbc.create.db.process");
		fileEncoding = databaseProperties.getProperty("jdbc.file.encoding");
		log = BooleanUtils.toBoolean(databaseProperties.getProperty("jdbc.show.sql.log"));
		String vendor = databaseProperties.getProperty("jdbc.db.vendor");
		if (vendor != null && !vendor.isEmpty())
		{
			postgresDatabase = BooleanUtils.toBoolean(vendor);
		}
		else
		{
			postgresDatabase = true;
		}
	}

	public Properties getDatabaseProperties()
	{
		return databaseProperties;
	}


	public void initializeDatabase() throws ClassNotFoundException, SQLException, IOException
	{
		String processtype = getProcessType();

		if (processtype.equals(DELETE_PROCESS))
		{
			deleteAndCreateEmptyDatabaseWithoutTables();
		}
		try ( // Get jdbc connection...
		Connection jdbcConnection = ConnectionsUtils.getPostgreSQLConnection(host, databaseName,
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
			String arg = initializationProcess;
			if (arg.equals(DELETE_PROCESS) || arg.equals(DROP_PROCESS))
			{
				processtype = arg;
			}
		}
		return processtype;
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
		ConnectionsUtils.dropPostgreSQLDatabase(host, databaseName, databaseUser, databasePassword);
		newEmptyDatabaseWithoutTables();
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
		ConnectionsUtils.newPostgreSQLDatabase(host, databaseName, databaseUser, databasePassword,
			"", "");
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
		File projectDir = PathFinder.getProjectDirectory();
		File dropSchemaSqlFile = PathFinder.getRelativePathTo(projectDir, "/",
			"src/main/resources/dll", "dropSchema.sql");
		if (!dropSchemaSqlFile.exists())
		{
			CreateFileUtils.newFileQuietly(dropSchemaSqlFile);
		}
		ConnectionsUtils.executeSqlScript(
			(BufferedReader)StreamUtils.getReader(dropSchemaSqlFile, fileEncoding, false),
			jdbcConnection, log);
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
	protected boolean createInitializationScript(String processtype) throws IOException
	{
		// Get the sql dir...
		final File sqlDir = getSqlDir();
		final File insertsDir = getInsertDir();
		StringBuilder sb = new StringBuilder();
		Formatter formatter = new DDLFormatterImpl();
		File schema = new File(sqlDir, "schema.sql");
		if (postgresDatabase)
		{
			String result = replaceMediumblobToBytea(schema);
			sb.append(formatter.format(result));
		}
		else
		{
			String result = ReadFileUtils.readFromFile(schema);
			sb.append(formatter.format(result));
		}
		sb.append(System.getProperty("line.separator"));
		sb.append(System.getProperty("line.separator"));
		if (processtype.equals(DELETE_PROCESS))
		{
			File createEnums = new File(sqlDir, "createEnumTypes.sql");
			if (createEnums.exists())
			{
				String result = ReadFileUtils.readFromFile(createEnums);
				sb.append(result);
				sb.append(System.getProperty("line.separator"));
			}
			File updateEnums = new File(sqlDir, "updateEnumFields.sql");
			if (updateEnums.exists())
			{
				String result = ReadFileUtils.readFromFile(updateEnums);
				sb.append(result);
				sb.append(System.getProperty("line.separator"));
			}
		}
		File createIndexesAndForeignKeys = new File(sqlDir, "createIndexesAndForeignKeys.sql");
		if (createIndexesAndForeignKeys.exists())
		{
			String result = ReadFileUtils.readFromFile(createIndexesAndForeignKeys);
			sb.append(result);
		}
		File initializeSchemaDdl = new File(insertsDir, "initializeSchema.sql");
		boolean writen = WriteFileUtils.writeStringToFile(initializeSchemaDdl, sb.toString(),
			fileEncoding);
		return writen;
	}

	protected String replaceMediumblobToBytea(File schema) throws IOException
	{
		String contentSchema = ReadFileUtils.readFromFile(schema);
		String result = StringUtils.replace(contentSchema, "MEDIUMBLOB", "BYTEA");
		WriteFileUtils.writeStringToFile(schema, result, "UTF-8");
		return result;
	}

	protected void createSchema(Connection jdbcConnection, String processtype)
		throws FileNotFoundException, IOException, SQLException
	{
		initializeScriptFiles();
		boolean writen = createInitializationScript(processtype);

		if (writen)
		{
			System.err.println(writen);
		}
		createSchemaFromScript(jdbcConnection);
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
		File projectDir = PathFinder.getProjectDirectory();
		File schemaDllDir = PathFinder.getRelativePathTo(projectDir, "/", "target/hibernate3/sql",
			"schema.ddl");
		if (schemaDllDir.exists())
		{
			File schemaSqlDir = PathFinder.getRelativePathTo(projectDir, "/",
				"src/main/resources/dll", "schema.sql");
			File dropSchemaSqlDir = PathFinder.getRelativePathTo(projectDir, "/",
				"src/main/resources/dll", "dropSchema.sql");
			File createIndexesAndForeignKeys = PathFinder.getRelativePathTo(projectDir, "/",
				"src/main/resources/dll", "createIndexesAndForeignKeys.sql");
			List<String> lines = ReadFileUtils.readLinesInList(schemaDllDir);
			List<String> dropTables = new ArrayList<>();
			List<String> createTables = new ArrayList<>();
			List<String> createIndexesAndAlterTable = new ArrayList<>();
			for (String currentLine : lines)
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
			WriteFileUtils.writeLinesToFile(dropTables, dropSchemaSqlDir, "UTF-8");
			WriteFileUtils.writeLinesToFile(createTables, schemaSqlDir, "UTF-8");
			WriteFileUtils.writeLinesToFile(createIndexesAndAlterTable,
				createIndexesAndForeignKeys, "UTF-8");
		}
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
		File initializeSchemaDdl = new File(insertsDir, "initializeSchema.sql");
		String result = ReadFileUtils.readFromFile(initializeSchemaDdl);
		ConnectionsUtils.executeSqlScript(jdbcConnection, result, log);
	}


	protected abstract List<File> getScriptFiles();


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
		List<File> scriptFiles = getScriptFiles();
		int size = scriptFiles.size();
		for (int i = 0; i < size; i++)
		{
			ConnectionsUtils.executeSqlScript(
				(BufferedReader)StreamUtils.getReader(scriptFiles.get(i), fileEncoding, false),
				jdbcConnection, log);
		}
	}

}