# REST service endpoint for HydraDB

This REST service endpoint is for use with regressionserver

## Building a new hydradbREST distribution

hydradbREST uses gradle to build. To compile, run './gradlew build'

## Generating HydraDB java classes

Note that @Entity, @Table, @Id, @Colummn, @GeneratedValue type annotations
are not generated and will need to be added manually as needed

Follow install instructions for the code generators

  spring-data-jdbc-codegen
  
  spring-data-jdbc-codegen-maven-plugin
  
The codegenerator.properties file has been tailored for local Postgresql HydraDB server and the HydradbREST pom.xml has the required dependencies and plugin entries.

From the root project directory (i.e. hydradbREST) type:

  mvn clean install
  
The generated java classes and helper classes will be placed into:

src/main/java/io.pivotal.gemfire.toolsmiths.data.gen