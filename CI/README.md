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
```


Customizing the at scripts
```
In case some AT needs specific ci script customization the at.sh script could be used/customized

e.g.
$ export PROGRAM=https://github.com/jbossas/jboss-threads.git
$ export AT=export AT=https://github.com/panossot/JBTAT.git
$ ./run.sh -at
```

