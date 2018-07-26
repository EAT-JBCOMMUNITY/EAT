# EAP ADDITIONAL TESTSUITE
--------------------------
## THE JBOSS TESTSUITE TO DEVELOP TESTS AGAINST INFINITE NUMBER OF JBOSS SERVERS
--------------------------------------------------------------------------------
## A PROJECT UNDER THE ΙΔΕΑ STATEMENT
--------------------------------------

Starting the development of a first implementation of the dependency schema of AT Structures for EAT.

This subdirectory is used for finding the packages that are used / loaded by the JBoss Servers and the packages that are used via the test cases.


1. ** Go to the server parent directory and execute : mvn dependency:tree > output.txt **
2. export DependencyTreeFilePath=path to the output.txt file
3. export ExternalDependencyPath=path to the another file listing the external dependencies (line format : new.artifact:new.id:jar:new.version)
4. export MavenRepoPath=path to the local maven repository
5. export BaseDir=the path to the eap-additional-testsuite dir
6. export SourcePath=the path to the dir of sources
7. export Server=the server name of the test subset that is aimed to be used
8. export Version=the version of the server that will be tested
9. export VersionOrderDir=versionOrder
10. Then go to this current DependencyTreeParser directory and execute : mvn clean install (This command will display all the packages being used/loaded in the maven repo)

** During step 1, in case the server bom-pom.xml, produced in component-matrix-builder/target path of the server, should be used, it should be firstly modified to a pom.xml file with all dependencies outside the <dependencymanagement> block (excluding the dependencies that are not available at the remote repos). Then, using this pom, we produce the output.txt file using the command : mvn dependency:tree > output.txt
