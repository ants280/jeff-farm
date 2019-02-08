-- $ sudo mysql
CREATE DATABASE JEFF_FARM_DB;
CREATE USER 'fred'@'localhost' IDENTIFIED BY 'flintstone';
GRANT ALL PRIVILEGES ON JEFF_FARM_DB.* TO 'fred'@'localhost' WITH GRANT OPTION;
-- exit

-- !!! Connector-J !!! --
-- wget https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-java_8.0.15-1ubuntu18.04_all.deb
-- sudo dpkg -i mysql-connector-java_8.0.15-1ubuntu18.04_all.deb || sudo -k
-- ./asadmin add-library /usr/share/java/mysql-connector-java-8.0.15.jar


-- !!! connection Pool: !!! --
-- (resourceType=javax.sql.DataSourec, className=com.mysql.cj.jdbc.MysqlDataSource)
-- add user, password, databaseName, serverName to pool
-- create JNDI Resource for connection pool, named jeff-farm.
-- @Resource(lookup = "jdbc/jeff-farm") DataSource ds;