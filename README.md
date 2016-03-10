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

