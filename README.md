[![EO principles respected here](https://www.elegantobjects.org/badge.svg)](https://www.elegantobjects.org)
[![DevOps By Rultor.com](https://www.rultor.com/b/baudoliver7/lightweight-db)](https://www.rultor.com/p/baudoliver7/lightweight-db)
[![We recommend IntelliJ IDEA](https://www.elegantobjects.org/intellij-idea.svg)](https://www.jetbrains.com/idea/)

[![Javadoc](http://www.javadoc.io/badge/com.baudoliver7/lightweight-db.svg)](http://www.javadoc.io/doc/com.baudoliver7/lightweight-db)
[![Maven Central](https://img.shields.io/maven-central/v/com.baudoliver7/lightweight-db.svg)](https://maven-badges.herokuapp.com/maven-central/com.baudoliver7/lightweight-db)
[![PDD status](http://www.0pdd.com/svg?name=baudoliver7/lightweight-db)](http://www.0pdd.com/p?name=baudoliver7/lightweight-db)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](https://github.com/baudoliver7/lightweight-db/blob/main/LICENSE.txt)

[![codecov](https://codecov.io/gh/baudoliver7/lightweight-db/branch/master/graph/badge.svg?token=AlLqLoNAeF)](https://codecov.io/gh/baudoliver7/lightweight-db)
[![Hits-of-Code](https://hitsofcode.com/github/baudoliver7/lightweight-db)](https://hitsofcode.com/github/baudoliver7/lightweight-db/view)

## What does it do ?
Lightweight DB allows you to run PostgreSQL, Oracle, MySQL, MS SQL Server... databases for fast use purposes. It's very suitable for unit and integration Testing.

All currently supported databases are :

* PostgreSQL
* Oracle
* MySQL
* MS SQL Server
* DB2
* Derby
* HSQLDB
* Ignite
* H2

## How does it work ?
This is how you can create an embedded PostgreSQL database and do common SQL tasks on it.

```java
final DataSource source = new EmbeddedPostgreSQLDataSource();
try (
    Connection connection = source.getConnection();
    Statement s = connection.createStatement()
) {
    s.execute(
        String.join(
            " ",
            "CREATE TABLE accounting_chart (",
            "   id BIGSERIAL NOT NULL,",
            "   type VARCHAR(25) NOT NULL,",
            "   state VARCHAR(10) NOT NULL,",
            "   version VARCHAR(10) NOT NULL,",
            "   CONSTRAINT accounting_chart_pkey PRIMARY KEY (id)",
            ")"
        )
    );
    s.execute(
        String.join(
            " ",
            "INSERT INTO accounting_chart (type, state, version)",
            "VALUES ('SYSCOHADA', 'ACTIVE', '2018');"
        )
    );
}
``` 
You can use [Liquibase](https://www.liquibase.org/) to execute theses operations like this (provided that <code>liquibase</code> folder is at the root of folder <code>resources</code>) :

```java
final DataSource source =  
    new LiquibaseDataSource(
        new EmbeddedPostgreSQLDataSource(), 
        "liquibase/db.changelog-master-test.xml"
    );
``` 

## Using with Maven
If you're using Maven, you should add this to your <code>pom.xml</code> dependencies:

```xml
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <version><!-- 1.4.200 or higher --></version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>com.baudoliver7</groupId>
    <artifactId>lightweight-db</artifactId>
    <version><!-- latest version --></version>
    <scope>test</scope>
</dependency>
``` 

## Using with Gradle
If you're using Gradle, you should add this to your <code>build.gradle</code> file:

```gradle
repositories {
    mavenCentral()
}

dependencies {
    testImplementation 'com.h2database:h2:/* 1.4.200 or higher */'
    testImplementation 'com.minlessika.incubator:lightweight-db:/* latest version */'
}
```

## How to contribute
Fork repository, make changes, send us a pull request. We will review
your changes and apply them to the `main` branch shortly, provided
they don't violate our quality standards. To avoid frustration, before
sending us your pull request please run full Maven build:

> mvn clean install -Pqulice

Keep in mind that JDK 8 and Maven 3.1.0 are the lowest versions you may use.

## Got questions ?

If you have questions or general suggestions, don't hesitate to submit
a new [Github issue](https://github.com/baudoliver7/lightweight-db/issues/new).
