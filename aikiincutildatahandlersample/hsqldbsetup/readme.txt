o Base database directory is:
    G:/hsql/dbunit/db02/
o Create the server.properties in base directory
    server.database.0=file:G:/hsql/dbunit/db02/testdb;user=sa;password=
    server.dbname.0=testdb
o Run server.cmd to create standalone server
o Run ide.cmd to connect to db. Use:
    File->connect
    Type->HSQL Database Engine Server
    Driver-> org.hsqldb.jdbcDirver
    Url->jdbc:hsqldb:hsql://localhost/testdb
    User-> SA
    Password->


On Eclipse JDBC Project Using Hibernate
---------------------------------------------
JDBC Property
    jdbc.driverClassName=org.hsqldb.jdbcDriver
    jdbc.url=jdbc:hsqldb:hsql://localhost/testdb
    jdbc.username=sa
    jdbc.password=

Hibernate property to init Database
    <prop key="hibernate.hbm2ddl.auto">create</prop>


Do Some type of update on Database
---------------------------------------------
o Select a table to see if posting was done