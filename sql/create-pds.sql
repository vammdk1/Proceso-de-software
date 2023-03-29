/* DELETE 'pdsDB' database*/
DROP SCHEMA IF EXISTS pdsDB;
/* DELETE USER 'procesoDeSoftware' AT LOCAL SERVER*/
DROP USER IF EXISTS 'procesoDeSoftware'@'localhost';

/* CREATE 'pdsDB' DATABASE */
CREATE SCHEMA pdsDB;
/* CREATE THE USER 'procesoDeSoftware' AT LOCAL SERVER WITH PASSWORD 'PDSsecurePassword' */
CREATE USER IF NOT EXISTS 'procesoDeSoftware'@'localhost' IDENTIFIED BY 'PDSsecurePassword';

GRANT ALL ON pdsDB.* TO 'procesoDeSoftware'@'localhost';