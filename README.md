# JAVA-T (JBOSS ALL VERSION ADDITIONAL - TESTSUITE)
An additional JBOSS testsuite in order to facilitate QE.

Write your tests once and run them on both EAP and WILDFLY application server.


HOW TO USE:

TESTING AGAINST EAP APPLICATION SERVER

1. Make sure that JBOSS_HOME is set with the path to your JBOSS EAP zip file,
             that WORKSPACE is set with the path to the JAVA-T (Jboss All Version Additional - Testsuite) directory,
             that EAP_VERSION is set to the version of the eap build.
2. Build the additional testsuite activating the EAP profile (-Deap).
3. Run the additional testsuite.

TESTING AGAINST WILDFLY APPLICATION SERVER

1. Make sure that JBOSS_HOME is set with the path to your WILDFLY zip file,
             that WORKSPACE is set with the path to the JAVA-T (Jboss All Version Additional - Testsuite) directory,
             that WILDFLY_VERSION is set to the version of the wildfly build.
2. Build the additional testsuite activating the WILDFLY profile (-Dwildfly).
3. Run the additional testsuite.


#License 
* [GNU Lesser General Public License Version 2.1](http://www.gnu.org/licenses/lgpl-2.1-standalone.html)
