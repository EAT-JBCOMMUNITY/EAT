# EAP ADDITIONAL TESTSUITE SINGLE TO MULTIPLE VERSION TESTS
-----------------------------------------------

Someone can develop a single test anywhere, not just in Single Version Test Inclusion. If he/she adds it in the SingleVersionTestInlcusionFolder he/she can follow the method described below, or mention the branch were the test is added as single version and someone will help with the multiple version inclusion.



They just have to add some metadata about the versions and servers they want to distribute the code to : 

e.g.

```

package org.server.test;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertFalse;

/** 
@author AuthorName
@EATSERVERS : Wildfly,Eap72x,Eap72x-Proposed
@EATSERVERMODULE : basic
@EATLOWERLIMITVERSIONS : 18.0.0, 7.2.3, 7.2.3
@EATUPPERLIMITVERSIONS : 18.0.0, 7.2.4, 7.2.4
*/
@RunWith(Arquillian.class)
public class BasicTest {

@Deployment
public static Archive<?> getDeployment() {
JavaArchive archive = ShrinkWrap.create(JavaArchive.class);
archive.addClass(BasicTest.class);
return archive;
}

@Test
public void testServerStart() {
assertFalse("Running a basic arquillian test ... ", false);
}
}

```

And then

1. export MultipleVersionsFilePath="the path to the file you would like to distribute" e.g.  ./eap-additional-testsuite/SingleVersionTestInclusion/wildfly/src/test/java/org/server/test/BasicTest.java
2. export MultipleVersionsDestination="the path to the modules/src dir of the eat you use" e.g. ./eap-additional-testsuite/modules/src/main/java
3. execute the code in eap-additional-testsuite/SingleVersionTestInclusion/OneToManyVersions


# License 
* [GNU Lesser General Public License Version 2.1](http://www.gnu.org/licenses/lgpl-2.1-standalone.html) ([GPL Cooperation Commitment](https://github.com/gplcc/gplcc/blob/master/Project/COMMITMENT))

