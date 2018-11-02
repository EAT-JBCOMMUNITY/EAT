# MINI LAB
--------------------------
## WHEN ALL CIs ARE DOWN ...
-----------------------------

In order for the EAP jobs to run first change $GITHUB_USERNAME:$GITHUB_PASSWORD to some existent values ...

Docker jobs to run testsuites when all CIs are down ...

1. Wildfly build including Testsuite and EAT : sudo ./wildfly.sh
2. Eap 7.2.x build including Testsuite and EAT : sudo ./72x.sh
3. Eap 7.1.x build including Testsuite and EAT : sudo ./71x.sh

