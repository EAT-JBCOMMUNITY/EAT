package org.jboss.additional.testsuite.jdkall.present.ejb.timerservice;

import org.jboss.eap.additional.testsuite.annotations.EAT;

import java.io.FileInputStream;
import java.io.File;
import org.apache.commons.io.IOUtils;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

@RunWith(Arquillian.class)
@RunAsClient
@EAT({"modules/testcases/jdkAll/WildflyJakarta/ejb/src/main/java#27.0.0", "modules/testcases/jdkAll/Eap7Plus/ejb/src/main/java#7.4.6"})
public class TimerServiceTestCase {

    private final String serverLogPath = "target/surefire-reports/org.jboss.additional.testsuite.jdkall.present.ejb.timerservice.TimerServiceTestCase-output.txt";

    @Deployment
    public static WebArchive deployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "timerservice.war");
        return war;
    }

    @Test
    public void keystoreTest() throws Exception {
        String path = new File("").getAbsolutePath() + "/" + serverLogPath;
        File serverlogfile = new File(path);
        if(!serverlogfile.exists()) {
            fail();
        }

        String everything = "";
        FileInputStream inputStream = new FileInputStream(path);
        try {
            everything = IOUtils.toString(inputStream);            
        } finally {
            inputStream.close();
            assertFalse("StartException ERROR should not be logs", everything.contains("ERROR"));
        }
    }

}
