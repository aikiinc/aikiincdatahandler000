AikiInc DbUnit Basic Data Handler Parent Project For JDK 1.6
---------------------------------------------------------------------
The parent project is wrapper project for:
aikiincutiltestbase - Aikiinc basic test which provide configuration for SpringFramework, Hibernate and DbUnit.
aikiincutildatahandler - Aikiinc basic data handler which seeds an application data from POJO's (Plain Old Java Object) property file or DbUnit XML file.
                         What's so special about my approach? The data handler uses the Command and Command Chain Pattern.
aikiincutildatahandlersample - Demo an application using the AikiInc data-handler where data is populated to a HyperSQL or MySQL database.


Building in Eclipse
-----------------------------------------------
The only issue I had was making sure using JDK instead of JRE 1.6


Building in Command Line Maven
-----------------------------------------------
mvn clean install -DskipTests