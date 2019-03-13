# EAP ADDITIONAL TESTSUITE
--------------------------
## THE JBOSS TESTSUITE TO DEVELOP TESTS AGAINST INFINITE NUMBER OF JBOSS SERVERS
--------------------------------------------------------------------------------
## A PROJECT UNDER THE ΙΔΕΑ STATEMENT
--------------------------------------
## EAT with QUARKUS and PROTEAN
--------------------------------

EAT can, now, be used to distribute the tests not only to the appropriate version of the server to be tested with,
but it can test the microprofile applications. It can also be used with Protean/Quarkus in both native and no-native mode, while the code (or part of the code of the tests) can be distributed for both server and Protean app testing.

 

How to test EAT with Protean/Quarkus 
-------------------------------------
 
1. Build Protean/Quarkus in order to produce the products of the built in the maven repo.
2. export GRAALVM_HOME=.../graalvm-ce-1.0.0-rc11-linux-amd64/graalvm-ce-1.0.0-rc11 (path to GraalVM)
3. ./Maven/apache-maven-3.5.3/bin/mvn clean install -Dprotean -Dmodule=basic -Dno-native -DJBOSS_VERSION=branch-version (for the non-native mode) or ./Maven/apache-maven-3.5.3/bin/mvn clean install -Dprotean -Dmodule=basic -Dnative -DJBOSS_VERSION=branch-version (for the native mode)

 

Testing with the master version :
---------------------------------
If the master version is not in the format x.y.z-SNAPSHOT (e.g. 999-SNAPSHOT), please, first modify to the appropriate format (e.g. 999.0.0-SNAPSHOT using the command find ./ -type f -exec sed -i 's/999/999\.0\.0/g' {} \; while being at the parent dir of protean/quarkus) before building the project. The run EAT using the new version format :

./Maven/apache-maven-3.5.3/bin/mvn clean install -Dprotean -Dmodule=basic -Dnative -DJBOSS_VERSION=999.0.0-SNAPSHOT

 

Testing with 0.11.0 version :
-----------------------------
./Maven/apache-maven-3.5.3/bin/mvn clean install -Dprotean -Dmodule=basic -Dnative -DJBOSS_VERSION=0.11.0

 

Testing with 0.10.0 version :
-----------------------------
./Maven/apache-maven-3.5.3/bin/mvn clean install -Dprotean -Dmodule=basic -Dnative -DJBOSS_VERSION=0.10.0

 

Testing with 0.9.1 version :
----------------------------
./Maven/apache-maven-3.5.3/bin/mvn clean install -Dprotean -Dmodule=basic -Dnative -DJBOSS_VERSION=0.9.1


