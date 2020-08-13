EAT CI (Unix Based)
===================

### How it works

First of all you have to set the parameters in your terminal

Parameters:
<table>
<tr>
<td>SERVER</td>
<td>The server to test EAT (github url)</td>
</tr>
<tr>
<td>SERVER_PR</td>
<td>Pull Request of the server to merge and test</td>
</tr>
<tr>
<td>SERVER_BRANCH</td>
<td>The branch name of the server to be used. Default: master</td>
</tr>
<tr>
<td>EAT</td>
<td>EAT (github url)</td>
</tr>
<tr>
<td>EAT_PR</td>
<td>Pull Request of the EAT to merge and test</td>
</tr>
<tr>
<td>EAT_BRANCH</td>
<td>The branch name of EAT to be used. Default: master</td>
</tr>
<tr>
<td>TEST_CATEGORY</td>
<td>Category of tests. Default: wildfly</td>
</tr>
<tr>
<td>SERVER_BUILD</td>
<td>Build the server. Values: true, false. Default: true</td>
</tr>
</table>

e.g.
```
$ export SERVER=https://github.com/wildfly/wildfly
$ export SERVER_PR=13456
$ export EAT=https://github.com/EAT-JBCOMMUNITY/EAT
$ export EAT_PR=29
$ ./run.sh
```

Extra options 
- Display parameters with values
```
$ ./run.sh -v
```

- Run all Pull Requests of EAT on the given server
```
$ ./run.sh -all
```
