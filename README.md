[![EO principles respected here](https://www.elegantobjects.org/badge.svg)](https://www.elegantobjects.org)

[![License](https://img.shields.io/badge/license-MIT-green.svg)](https://github.com/Minlessika/lightweight-db/blob/main/LICENSE.txt)

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
This is how we create an embedded PostgreSQL database and do common SQL tasks on it.

```java
public static void main(String... args) throws Exception {
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
}
``` 

## Using with Maven
If you're using Maven, you should add this to your <code>pom.xml</code> dependencies:

```xml
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <version>1.4.200</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>com.minlessika.incubator</groupId>
    <artifactId>lightweight-db</artifactId>
    <version>0.1.0</version>
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
    test group: 'com.h2database', name: 'h2', version: '1.4.200'
    test group: 'org.minlessika.incubator', name: 'lightweight-db', version: '0.1.0'
}
```

## How to contribute
Fork repository, make changes, send us a pull request. We will review
your changes and apply them to the `main` branch shortly, provided
they don't violate our quality standards. To avoid frustration, before
sending us your pull request please run full Maven build:

> mvn clean install -Pqulice

Keep in mind that JDK7 and Maven 3.1.0 are the lowest versions you may use.

## Got questions ?

If you have questions or general suggestions, don't hesitate to submit
a new [Github issue](https://github.com/Minlessika/lightweight-db/issues/new).
