Pictochatnt
===========

This example relies on the DataNucleus Maven plugin. Check the database configuration in the *datanucleus.properties* file and the JDBC driver dependency specified in the *pom.xml* file. In addition, the project contains the server and client example codes.

Run the following command to build everything and enhance the DB classes:

    mvn clean compile

Make sure that the database was correctly configured. Use the contents of the file *create-message.sql* to create the database and grant privileges. For example,

    mysql –u root -p < sql/create-pds.sql

Run the following command to create database schema for this sample.

    mvn datanucleus:schema-create

To launch the server run the command

    mvn jetty:run

El proyecto requiere de dos ventanas, una para todos los comandos , y otra para el cliente

Now, the client sample code can be executed in a new command window with

    mvn exec:java -Pclient

To launch the Unit Tests
      
    mvn test

To see the Documentation

    https://vammdk1.github.io/Proceso-de-software/html/
    
