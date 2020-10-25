AT CI (Unix Based)
===================

### How it works
*The CI scripts could be used from the users in order to test their commits with EAT or inside containers on servers in order form an EAT - CI architecture*

First of all you have to set the parameters in your terminal

Parameters:
<table>
<tr>
<td>PROGRAM</td>
<td>The program to test AT (github url)</td>
</tr>
<tr>
<td>PROGRAM_PR</td>
<td>Pull Request of the program to merge and test</td>
</tr>
<tr>
<td>PROGRAM_BRANCH</td>
<td>The branch name of the program to be used. Default: master</td>
</tr>
<tr>
<td>AT</td>
<td>AT (github url)</td>
</tr>
<tr>
<td>AT_PR</td>
<td>Pull Request of the AT to merge and test</td>
</tr>
<tr>
<td>AT_BRANCH</td>
<td>The branch name of AT to be used. Default: master</td>
</tr>
<tr>
<td>TEST_CATEGORY</td>
<td>Category of tests. Default: wildfly</td>
</tr>
<tr>
<td>PROGRAM_BUILD</td>
<td>Build the program. Values: true, false. Default: true</td>
</tr>
<tr>
<td>GITHUB_TOKEN</td>
<td>Token used in order to send the result messages, as a comment, to the pr tested.</td>
</tr>
</table>

e.g.
```
$ export PROGRAM=https://github.com/wildfly/wildfly
$ export PROGRAM_PR=13456
$ export AT=https://github.com/EAT-JBCOMMUNITY/EAT
$ export AT_PR=29
$ ./run.sh
```

Extra options 
- Display parameters with values
```
$ ./run.sh -v
```

- Run all Pull Requests of AT on the given program  
If you run **-all** for first time you have to fill **to_check_PRs.txt**
```
$ ./run.sh -all reset
```
CI will test all PRs inside **to_check_PRs.txt**  
You can also remove or add PRs manually
```
$ ./run.sh -all
```

- Comment results on Pull Request (Github)  
  
*CI needs access to your Github account to comment*  
Settings -> Developer Settings -> Personal access tokens -> Generate new token
```
$ export GITHUB_TOKEN=generated token from Github
```
Run CI with comment option
```
$ ./run.sh -all comment
or
$ ./run.sh -all reset comment
```
Run CI using menu options
```
Test Wildfly
$ ./run.sh -wildfly

Test ActiveMQ
$ ./run.sh -activemq

Test Artemis Activemq
$ ./run.sh -activemq-artemis

Test JBoss Threads
$ ./run.sh -jboss-threads

Test JBoss Modules
$ ./run.sh -jboss-modules

Test Open Liberty
$ ./run.sh -openliberty

Test Spring Boot
(Please make sure that the dependencies needed are available in the local repository)
$ ./run.sh -springboot
```
Customizing the at scripts
```
In case some AT needs specific ci script customization the at.sh script could be used/customized

e.g.
$ export PROGRAM=https://github.com/jbossas/jboss-threads.git
$ export AT=export AT=https://github.com/panossot/JBTAT.git
$ ./run.sh -at
```

Executing AT CI in a container
```
$ sudo docker build -t docker.io/atci --ulimit nofile=5000:5000 .
$ ./program_build_on_change.sh
$ sudo docker run --name atci -e TEST_PROGRAM=(e.g. wildfly,openliberty,etc) (-e TEST_CATEGORY=the category of the AT that will be used to test the program) (-e AT_PR=75) (-e AT=the AT github url to be used) (-e PROGRAM_PR=100) (-e GITHUB_TOKEN=the AT github token, used to send the result message to the PR tested) (-e PROGRAM=the program github url to be tested) (-e PROGRAM_BRANCH=the branch of the program to be tested) (-v $HOME/.m2/repository:/home/user/.m2/repository --privileged=true) --ulimit nofile=5000:5000 docker.io/atci
```

The AT CI Servers (daemons)
```
This daemon is implemented for checking the jboss servers (CI). Similar daemons can be used for any program.
Also, multiple daemons can be used in parallel (e.g. using pr filtering) from different servers.

Commands to execute:
$ sudo docker build -t docker.io/atci --ulimit nofile=5000:5000 .
$ export GITHUB_TOKEN=the AT github token
$ ./program_build_on_change.sh
$ sudo ./daemon.sh
```
