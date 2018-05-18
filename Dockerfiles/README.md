# EAP ADDITIONAL TESTSUITE
--------------------------
## RUNNING EAT USING DOCKER IN ORDER TO AVOID PORT CONFLICTS
------------------------------------------------------------

### WILDFLY
-----------
1. Go to dir Dockerfiles/wildfly
2. Run : docker build -t docker.io/eat . (in order too create the eat image)
3. Run : docker run --name=eat -e JBOSS_FOLDER=/wildfly/master/dist/target/wildfly-13.0.0.Beta2-SNAPSHOT -e JBOSS_VERSION=13.0.0.Beta2-SNAPSHOT  docker.io/eat (in order to execute eat, the version should be replaced with the current wildfly master version)

