persistence-api
====================


The persistence-api is the parent project that provides api module projects for implement jpa projects. It tends to be easy to understand and uses different standards like JPA and or-mapper framework hibernate and spring for dependency injection.

Here is a simple description of the important module projects:


# business-api

The business-api module project provides business intefaces and abstract classes for execute crud processes.


# data-api

The data-api module project provides generic dao intefaces and base entity classes like versionable and validatable.
It contains attribute converter classes for the new java 8 java.time package.


# data-initialization

The data-initialization module project provides only one abstract class AbstractDatabaseInitialization that provides callback methods for initialization of a database. The callback methods can be overwritten to provide custom behavior if needed.


# domain-api

The domain-api module project provides generic mapper classes that use the dozzer framework for map entity classes to domain specific classes. It also contains a base domain class.

# rest-api

The domain-api module project provides rest intefaces and abstract classes for execute crud processes.

# rest-client

The rest-client module project provides an abstract rest client for test rest services. It uses the cxf framework from apache.


## License

The source code comes under the liberal Apache License V2.0, making persistence-api great for all types of back end applications.


# Build status
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/be62e9b55fb444818a70be678a5a1fb8)](https://www.codacy.com/app/tatjana19/persistence-api?utm_source=github.com&utm_medium=referral&utm_content=lightblueseas/persistence-api&utm_campaign=badger)
[![Build Status](https://travis-ci.org/lightblueseas/persistence-api.svg?branch=master)](https://travis-ci.org/lightblueseas/persistence-api)

## Maven Central

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/de.alpharogroup/persistence-api/badge.svg)](https://maven-badges.herokuapp.com/maven-central/de.alpharogroup/persistence-api)

## Maven dependency

Maven dependency is now on sonatype.
Check out [sonatype repository](https://oss.sonatype.org/index.html#nexus-search;gav~de.alpharogroup~persistence-api~~~) for latest snapshots and releases.

Add the following maven dependencies to your project `pom.xml` if you want to import the core functionality:

You can first define the version properties:

	<properties>
			...
		<!-- persistence-api version -->
		<persistence-api.version>3.33.0</persistence-api.version>
		<business-api.version>${persistence-api.version}</business-api.version>
		<data-api.version>${persistence-api.version}</data-api.version>
		<data-initialization.version>${persistence-api.version}</data-initialization.version>
		<data-usertype.version>${persistence-api.version}</data-usertype.version>
		<domain-api.version>${persistence-api.version}</domain-api.version>
		<rest-api.version>${persistence-api.version}</rest-api.version>
		<rest-client.version>${persistence-api.version}</rest-client.version>
			...
	</properties>

Add the following maven dependency to your project `pom.xml` if you want to import the functionality of business-api:

		<dependencies>
			...
			<dependency>
				<groupId>de.alpharogroup</groupId>
				<artifactId>business-api</artifactId>
				<version>${business-api.version}</version>
			</dependency>
			...
		</dependencies>

Add the following maven dependency to your project `pom.xml` if you want to import the functionality of data-api:

		<dependencies>
			...
			<dependency>
				<groupId>de.alpharogroup</groupId>
				<artifactId>data-api</artifactId>
				<version>${data-api.version}</version>
			</dependency>
			...
		</dependencies>

Add the following maven dependency to your project `pom.xml` if you want to import the functionality of data-initialization:

		<dependencies>
			...
			<dependency>
				<groupId>de.alpharogroup</groupId>
				<artifactId>data-initialization</artifactId>
				<version>${data-initialization.version}</version>
			</dependency>
			...
		</dependencies>

Add the following maven dependency to your project `pom.xml` if you want to import the functionality of data-usertype:

		<dependencies>
			...
			<dependency>
				<groupId>de.alpharogroup</groupId>
				<artifactId>data-usertype</artifactId>
				<version>${data-usertype.version}</version>
			</dependency>
			...
		</dependencies>

Add the following maven dependency to your project `pom.xml` if you want to import the functionality of domain-api:

		<dependencies>
			...
			<dependency>
				<groupId>de.alpharogroup</groupId>
				<artifactId>domain-api</artifactId>
				<version>${domain-api.version}</version>
			</dependency>
			...
		</dependencies>

Add the following maven dependency to your project `pom.xml` if you want to import the functionality of rest-api:

		<dependencies>
			...
			<dependency>
				<groupId>de.alpharogroup</groupId>
				<artifactId>rest-api</artifactId>
				<version>${rest-api.version}</version>
			</dependency>
			...
		</dependencies>

Add the following maven dependency to your project `pom.xml` if you want to import the functionality of rest-client:

		<dependencies>
			...
			<dependency>
				<groupId>de.alpharogroup</groupId>
				<artifactId>rest-client</artifactId>
				<version>${rest-client.version}</version>
			</dependency>
			...
		</dependencies>


## Want to Help and improve it? ###

The source code for persistence-api are on GitHub. Please feel free to fork and send pull requests!

Create your own fork of [lightblueseas/persistence-api/fork](https://github.com/lightblueseas/persistence-api/fork)

To share your changes, [submit a pull request](https://github.com/lightblueseas/persistence-api/pull/new/master).

Don't forget to add new units tests on your changes.

## Contacting the Developer

Do not hesitate to contact the persistence-api developers with your questions, concerns, comments, bug reports, or feature requests.
- Feature requests, questions and bug reports can be reported at the [issues page](https://github.com/lightblueseas/persistence-api/issues).

## Note

No animals were harmed in the making of this library.

# Donate

If you like this library, please consider a donation through  
<a href="https://flattr.com/submit/auto?fid=r7vp62&url=https%3A%2F%2Fgithub.com%2Flightblueseas%2Fpersistence-api" target="_blank">	
<img src="http://button.flattr.com/flattr-badge-large.png" alt="Flattr this" title="Flattr this" border="0">
</a>

# Similar projects

Here is a list of awesome projects for persistence:

* [mardao](https://github.com/sosandstrom/mardao) Mardao Architect's Java DAO generator.
 
