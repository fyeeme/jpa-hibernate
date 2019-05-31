# jpa-hibernate
use hibernate stand alone connected based on jpa 

## how to start from sketch

1. init project using maven

```shell
mvn archetype:generate -DgroupId=com.flyme.app -DartifactId=jpa-hibernate -DarchetypeArtifactId=maven-archetype-webapp  -DinteractiveMode=false -DarchetypeCatalog=internal -X
```

2. add dependencies

```shell
<properties>
    <h2.version>1.3.176</h2.version>
    <hibernate.version>5.4.2.Final</hibernate.version>
    <mysql.version>8.0.16</mysql.version>
    <slf4j.version>1.7.26</slf4j.version>
    <junit.version>3.8.1</junit.version>
</properties>
<dependencies>
    <dependency>
        <groupId>org.hibernate</groupId>
        <artifactId>hibernate-entitymanager</artifactId>
        <version>${hibernate.version}</version>
    </dependency>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>${mysql.version}</version>
    </dependency>
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
        <version>${slf4j.version}</version>
    </dependency>
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>${junit.version}</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

3. set compile version  

```shell
<build>
    <pluginManagement>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.1</version>
                <configuration>
                    <source>1.8</source>
                    <target>1.8</target>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-site-plugin</artifactId>
                <version>3.6</version>
                <configuration>
                    <outputEncoding>UTF-8</outputEncoding>
                </configuration>
            </plugin>
        </plugins>
    </pluginManagement>
</build>
```