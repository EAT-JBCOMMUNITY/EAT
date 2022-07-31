package org.jboss.additional.testsuite.jdkall.present.ejb.sfsbconcurrency2;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertTrue;
import jakarta.inject.Inject;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@RunWith(Arquillian.class)
@EAT({"modules/testcases/jdkAll/WildflyJakarta/ejb/src/main/java#27.0.0.Alpha4"})
public class SfsbTestCase {

	private static final String ARCHIVE_NAME = "SfsbTestCase";
        private static final String DEPLOYMENT = ARCHIVE_NAME + ".jar";

        @Inject
	private SFBean3 bean3;

        @Deployment(name = DEPLOYMENT)
        public static Archive<?> deploy() {
            JavaArchive jar = ShrinkWrap.create(JavaArchive.class, ARCHIVE_NAME + ".jar");
            jar.addPackage("org.jboss.additional.testsuite.jdkall.present.ejb.sfsbconcurrency2");
            return jar;
        }

    @Test
    public void ejbTest() {
        try {
            bean3.test();
        }catch(Exception e) {
            assertTrue("There should be an ejb concurrency exception ...", !e.getMessage().contains("Could not instantiate a proxy for a session bean:  Session bean [class org.jboss.additional.testsuite.jdkall.present.ejb.sfsbconcurrency2.SFBean2 with qualifiers [@Any @Default]"));
        }
    }

}
