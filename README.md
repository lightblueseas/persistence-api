persistence-api
====================

<div align="center">

[![Build Status](https://travis-ci.org/lightblueseas/persistence-api.svg?branch=master)](https://travis-ci.org/lightblueseas/persistence-api)
[![license apache2](https://img.shields.io/badge/license-apache2-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/be62e9b55fb444818a70be678a5a1fb8)](https://www.codacy.com/app/tatjana19/persistence-api?utm_source=github.com&utm_medium=referral&utm_content=lightblueseas/persistence-api&utm_campaign=badger)
[![Open Issues](https://img.shields.io/github/issues/lightblueseas/persistence-api.svg?style=flat)](https://github.com/lightblueseas/persistence-api/issues) 
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/de.alpharogroup/persistence-api/badge.svg)](https://maven-badges.herokuapp.com/maven-central/de.alpharogroup/persistence-api)

</div>

The persistence-api is the parent project that provides api module projects for implement jpa projects. It tends to be easy to understand and uses different standards like JPA and or-mapper framework hibernate and spring for dependency injection.

Here is a simple description of the important module projects:

# entities-api

The entities-api module project provides several interfaces like Versionable, Activatable, Validatable that can be implemented from entity or domain classes

# base-entities

The base-entities module project provides abstract entity classes like versionable, activatable, validatable and other useful base entity classes. 

# business-api

The business-api module project provides business intefaces and abstract classes for execute crud processes.

# data-api

The data-api module project provides generic repository intefaces.

- contains attribute converter classes for the new java 8 java.time package.

# data-initialization

The data-initialization module project provides only one abstract class AbstractDatabaseInitialization that provides callback methods for initialization of a database. The callback methods can be overwritten to provide custom behavior if needed.

# data-usertype

The data-usertype module project provides specific usertype classes.

# domain-api

The domain-api module project provides generic base domain classes and service intefaces.

# domain-mapper

The domain-mapper module project provides generic mapper classes that use the dozzer framework for map entity classes to domain specific classes.

# rest-api

The domain-api module project provides rest intefaces and abstract classes for execute crud processes.

# rest-client

The rest-client module project provides an abstract rest client for test rest services. It uses the cxf framework from apache.

## License

The source code comes under the liberal Apache License V2.0, making persistence-api great for all types of back end applications.

## Maven dependency

Maven dependency is now on sonatype.
Check out [sonatype repository](https://oss.sonatype.org/index.html#nexus-search;gav~de.alpharogroup~persistence-api~~~) for latest snapshots and releases.

Add the following maven dependencies to your project `pom.xml` if you want to import the core functionality:

You can first define the version properties:

	<properties>
			...
		<!-- PERSISTENCE-API version -->
		<persistence-api.version>6.3</persistence-api.version>
		<base-entities.version>${persistence-api.version}</base-entities.version>
		<business-api.version>${persistence-api.version}</business-api.version>
		<data-api.version>${persistence-api.version}</data-api.version>
		<data-initialization.version>${persistence-api.version}</data-initialization.version>
		<data-usertype.version>${persistence-api.version}</data-usertype.version>
		<domain-api.version>${persistence-api.version}</domain-api.version>
		<domain-mapper.version>${persistence-api.version}</domain-mapper.version>
		<rest-api.version>${persistence-api.version}</rest-api.version>
		<rest-client.version>${persistence-api.version}</rest-client.version>
			...
	</properties>

Add the following maven dependency to your project `pom.xml` if you want to import the functionality of base-entities:

		<dependencies>
			...
            <!-- BUSINESS-API DEPENDENCY -->
			<dependency>
				<groupId>de.alpharogroup</groupId>
				<artifactId>base-entities</artifactId>
				<version>${base-entities.version}</version>
			</dependency>
			...
		</dependencies>

Add the following maven dependency to your project `pom.xml` if you want to import the functionality of business-api:

		<dependencies>
			...
            <!-- BUSINESS-API DEPENDENCY -->
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
            <!-- DATA-API DEPENDENCY -->
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
            <!-- DATA-INITIALIZATION DEPENDENCY -->
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
            <!-- DATA-USERTYPE DEPENDENCY -->
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
            <!-- DOMAIN-API DEPENDENCY -->
			<dependency>
				<groupId>de.alpharogroup</groupId>
				<artifactId>domain-api</artifactId>
				<version>${domain-api.version}</version>
			</dependency>
			...
		</dependencies>

Add the following maven dependency to your project `pom.xml` if you want to import the functionality of domain-mapper:

		<dependencies>
			...
            <!-- DOMAIN-API DEPENDENCY -->
			<dependency>
				<groupId>de.alpharogroup</groupId>
				<artifactId>domain-mapper</artifactId>
				<version>${domain-mapper.version}</version>
			</dependency>
			...
		</dependencies>

Add the following maven dependency to your project `pom.xml` if you want to import the functionality of rest-api:

		<dependencies>
			...
            <!-- REST-API DEPENDENCY -->
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
            <!-- REST-CLIENT DEPENDENCY -->
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

# Donations

If you like this library, please consider a donation through paypal: <a href="https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=B37J9DZF6G9ZC" target="_blank">
<img src="https://www.paypalobjects.com/en_US/GB/i/btn/btn_donateCC_LG.gif" alt="PayPal this" title="PayPal – The safer, easier way to pay online!" border="0" />
</a>

or over bitcoin or bitcoin-cash with:

1Jzso5h7U82QCNmgxxSCya1yUK7UVcSXsW

or over ether with:

0xaB6EaE10F352268B0CA672Dd6e999C86344D49D8

or over flattr: <a href="https://flattr.com/submit/auto?fid=r7vp62&url=https%3A%2F%2Fgithub.com%2Flightblueseas%2Fpersistence-api" target="_blank">	
<img src="http://button.flattr.com/flattr-badge-large.png" alt="Flattr this" title="Flattr this" border="0">
</a>

## Credits

|Travis CI|
|:-:|
|![Travis CI](https://travis-ci.com/images/logos/TravisCI-Full-Color.png)|
|Many thanks to [Travis CI](https://travis-ci.org) for providing a free continuous integration service for open source projects.|

# Similar projects

Here is a list of awesome projects for persistence:

* [mardao](https://github.com/sosandstrom/mardao) Mardao Architect's Java DAO generator.
* [spring-data](https://github.com/spring-projects/spring-data-jpa) Simplifies the development of creating a JPA-based data access layer. 
 
