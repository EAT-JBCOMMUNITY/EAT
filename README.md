# EAP ADDITIONAL TESTSUITE
============================
An additional JBOSS testsuite in order to facilitate QE.

Write your tests once and run them on both EAP and WILDFLY application server.



In order to synchronize the dependecny versions of eap-additional-testsuite with the component versions of the equivalent server you can run the additional-testsuite using -Dstandalone or -Dserver-integration property.

Standalone Mode
---------------
If you enable standalone mode please make sure that the server parent pom is available locally as an artifact.

Server-Integration Mode
---------------
If you enable server-integration mode please make sure that the server BOM is available locally as an artifact.



Testing EAP
-----------
1. Make sure that JBOSS_FOLDER environment variable is set with the path to your JBOSS EAP directory.
2. Make sure that JBOSS_VERSION environment variable is set with the version of JBOSS EAP Server.
3. Build and run the additional testsuite activating the EAP profile (-Deap).


Testing Wildfly
---------------
1. Make sure that JBOSS_FOLDER environment variable is set with the path to your WILDFLY directory.
2. Make sure that JBOSS_VERSION environment variable is set with the version of WILDFLY Server.
3. Build and run the additional testsuite activating the WILDFLY profile (-Dwildfly).


Testing EAP or Wildfly with specific JDK version
------------------------------------------------
1. Make sure that JBOSS_FOLDER is set with the path to your EAP OR WILDFLY directory.
2. Make sure that JBOSS_VERSION environment variable is set with the version of JBOSS EAP OR WILDFLY Server.
3. Make sure that JAVA_HOME is pointing to the jdk of desired version.
4. Build and run the additional testsuite activating the EAP or WILDFLY specific jdk version profile (-Deap-jdk8, -Dwildfly-jdk8).


#License 
* [GNU Lesser General Public License Version 2.1](http://www.gnu.org/licenses/lgpl-2.1-standalone.html)

