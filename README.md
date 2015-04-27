# JBOSS ADDITIONAL TESTSUITE
An additional JBOSS testsuite in order to facilitate QE.

Write your tests once and run them on both EAP and WILDFLY application server.


HOW TO USE:

TESTING AGAINST EAP APPLICATION SERVER

1. Make sure that JBOSS_HOME is set with the path to your JBOSS EAP directory.
2. Build the additional testsuite activating the EAP profile (-Deap).
3. Run the additional testsuite.

TESTING AGAINST WILDFLY APPLICATION SERVER

1. Make sure that JBOSS_HOME is set with the path to your WILDFLY directory.
2. Build the additional testsuite activating the WILDFLY profile (-Dwildfly).
3. Run the additional testsuite.


#License 
* [GNU Lesser General Public License Version 2.1](http://www.gnu.org/licenses/lgpl-2.1-standalone.html)
