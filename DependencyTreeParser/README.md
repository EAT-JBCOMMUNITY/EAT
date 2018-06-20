# EAP ADDITIONAL TESTSUITE
--------------------------
## THE JBOSS TESTSUITE TO DEVELOP TESTS AGAINST INFINITE NUMBER OF JBOSS SERVERS
--------------------------------------------------------------------------------
## A PROJECT UNDER THE ΙΔΕΑ STATEMENT
--------------------------------------

Starting the development of a first implementation of the dependency schema of AT Structures for EAT.

This subdirectory is used for finding the packages that are used / loaded by the JBoss Servers and the packages that are used via the test cases.


1. Go to the server parent directory and execute : mvn dependency:tree > output.txt
2. export DependencyTreeFilePath=path to the output.txt file
3. export MavenRepoPath=path to the local maven repository
4. export BaseDir=the path to the eap-additional-testsuite dir
5. export SourcePath=the path to the dir of sources
6. export Server=the server name of the test subset that is aimed to be used
7. export Version=the version of the server that will be tested
8. export VersionOrderDir=versionOrder
9. Then go to this current DependencyTreeParser directory and execute : mvn clean install (This command will display all the packages being used/loaded in the maven repo)
