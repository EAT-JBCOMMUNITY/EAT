# EAP ADDITIONAL TESTSUITE
==========================
## THE JBOSS TESTSUITE TO DEVELOP TESTS AGAINST INFINITE NUMBER OF JBOSS SERVERS
================================================================================

An additional JBOSS testsuite in order to facilitate QE.

Write your tests once and run them against any version of EAP and WILDFLY application server.



In order to synchronize the dependecny versions of eap-additional-testsuite with the component versions of the equivalent server you can run the additional-testsuite using -Dstandalone or -Dserver-integration property.

Standalone Mode
---------------
If you enable standalone mode (-Dstandalone), please make sure that the server parent pom is available locally or remotely as an artifact.

Server-Integration Mode
---------------
If you enable server-integration mode (-Dserver-integration), please make sure that the server BOM is available locally or remotely as an artifact.



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


ADVANTAGES 
----------
1. Having all the tests at one place.
2. Comparison of the servers based on the testsuite.
3. Guarding against regression.

 
MOTIVATION
----------
If a test is developed for server X then it can be automatically tested against all the other servers.

 
HOW TO DEBUG
------------
1. Start the server (that you have defined in JBOSS_FOLDER) with the --debug 'port' option.
2. Connect the Debugger to the Remote VM inside the server using some IDE.
3. Activate the equivalent debug profile at your IDE (eg eap7.testsuite)
4. Set your breakpoints.
5. And then start the debugging executing the specific test which is errorous.


HOW TO BUILD EAP ADDITIONAL TESTSUITE INSIDE AN IDE (NETBEANS)
--------------------------------------------------------------
1. Before starting the IDE, go to your IDE directory and export JBOSS_FOLDER and JBOSS_VERSION environment variables at file /etc/environment and execute the command "source /etc/environment".
2. Start your IDE from command line and load the project.
3. Go to Modules directory and activate the desired server profile (eg wildfly.testsuite). Then activate the standalone profile at the server dir that has appeared.
4. Make sure that the server parent pom is available locally or remotely as an artifact.
5. Build your project.


EAP-ADDITIONAL-TESTSUITE INTEGRATED INSIDE THE SERVERS (HOW TO USE)  - AVAILABLE SOON
-------------------------------------------------------------------------------------
1. Send your PR containing the test to eap-additional-testsuite.
2. Before sending the server PR inform the server that you would like to include a specific PR of eap-additional-testsuite, adding the following lines in git-sub-modules/git-sub-module.sh : 
    - git submodule update --init --remote
    - cd ../testsuite/addtional-testsuite/eap-additional-testsuite/
    - git fetch origin refs/pull/8/head && git checkout FETCH_HEAD (declare that you would like to include PR 8 of eap-additional-testsuite)
3. Commit the changes on the server and send the server PR.
4. The specific PR of the test will be tested along with the server PR.


EXAMPLE OF USAGE 
-----------------
Supposing that we would like to add Mtom TestCase for Wildfly (master) and Eap 7 (7.x) Servers in Eap Additional Testsuite, we would follow the steps bellow :

1. Add the source code of the test in [modules/src/main directory](https://github.com/jboss-set/eap-additional-testsuite/tree/master/modules/src/main/java/org/jboss/additional/testsuite/jdkall/web/mtom) (both for Wildfly and Eap7 - write your code once) and annotate all the classes used in the test with the @EapAdditionalTestsuite annotation, specifing the locations of the Servers that the classes will be distributed to, as a String Array attribute (e.g. [@EapAdditionalTestsuite Example](https://github.com/jboss-set/eap-additional-testsuite/blob/master/modules/src/main/java/org/jboss/additional/testsuite/jdkall/web/mtom/MtomTestCase.java#L48)).

2. Add the resources in [Wildfy Mtom recource directory](https://github.com/jboss-set/eap-additional-testsuite/tree/master/modules/testcases/jdkAll/Wildfly/web/test-configurations/src/test/resources) (for Wildfly) and  [Eap7 Mtom recource directory](https://github.com/jboss-set/eap-additional-testsuite/tree/master/modules/testcases/jdkAll/Eap7/web/test-configurations/src/test/resources) (for Eap7)

3. Add any additional dependencies in [Wildfy pom](https://github.com/jboss-set/eap-additional-testsuite/blob/master/modules/testcases/jdkAll/Wildfly/pom.xml)  (for Wildfly) and [Eap7 pom](https://github.com/jboss-set/eap-additional-testsuite/blob/master/modules/testcases/jdkAll/Eap7/pom.xml) (for Eap7)

4. Enable the Mtom TestCase in [Wildfy Web Configuration pom](https://github.com/jboss-set/eap-additional-testsuite/blob/master/modules/testcases/jdkAll/Wildfly/web/test-configurations/pom.xml) (for Wildfly) and [Eap7 Web Configuration pom](https://github.com/jboss-set/eap-additional-testsuite/blob/master/modules/testcases/jdkAll/Eap7/web/test-configurations/pom.xml) (for Eap7)

5. Build the Eap Additional Testsuite for Wildfy and Eap7 (having set the equivalent JBOSS_FOLDER and JBOSS_VERSION environment variables) :
    - mvn clean install -Dwildfly -Dstandalone (for Wildfly)
    - mvn clean install -Deap7 -Dstandalone (for Eap7)
    
 
OTHER FEATURES
--------------
1. Infinite number of servers could be added in Eap-Additional-Testsuite, while the tests included could be tested against all these servers.
2. Eap-Additional-Testsuite could be a very helpful tool in combination with the documentation. In parallel with the documentation, examples of use cases could be provided, which could be tested against all the servers.
3. Eap-Additional-Testsuite is the first component to change the relation of Component -> Server to Component <-> Server, as this component can be considered as a part of a Server, but also a Server can be considered as a part of Eap-Additional-Testsuite.


MOJO DOCUMENTATION
------------------
[Eap Additional Testsuite Mojo Document](https://mojo.redhat.com/docs/DOC-1063426)
 

EAP-ADDITIONAL-TESTSUITE as a COLLABORATION PROJECT
---------------------------------------------------
Many thanks to Carlo de Wolf for his ideas and help.


#License 
* [GNU Lesser General Public License Version 2.1](http://www.gnu.org/licenses/lgpl-2.1-standalone.html)

