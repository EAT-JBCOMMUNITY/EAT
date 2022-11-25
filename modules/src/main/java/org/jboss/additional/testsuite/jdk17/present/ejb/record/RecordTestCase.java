package org.jboss.additional.testsuite.jdk17.present.ejb.record;

import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdk17/WildflyJakarta/ejb/src/main/java#28.0.0.Final"}) //for 7.4.x the initial issue was resolved. Can throw "java.lang.UnsupportedOperationException: can't get field offset on a record class". Needs a custom serializer ...
@RunWith(Arquillian.class)
public class RecordTestCase {
    private static final String ARCHIVE_NAME = "RecordTestCase";

    @Deployment(name = "recordtest")
    public static Archive<?> deploy() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class, ARCHIVE_NAME + ".jar");
        jar.addPackage(RecordTestCase.class.getPackage());
        return jar;
    }

    @Test
    public void testRecord() throws Exception {
        System.out.println("Start Sigleton ...");
    }
}
