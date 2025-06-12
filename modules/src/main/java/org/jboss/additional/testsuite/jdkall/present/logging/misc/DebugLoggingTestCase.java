package org.jboss.additional.testsuite.jdkall.present.logging.misc;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletResponse;

import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.additional.testsuite.jdkall.present.logging.util.LoggingServlet;
import org.jboss.logging.Logger;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import java.io.File;
import java.io.FileInputStream;
import org.apache.commons.io.IOUtils;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.eap.additional.testsuite.annotations.EAT;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunAsClient
@RunWith(Arquillian.class)
@EAT({"modules/testcases/jdkAll/Eap7Plus/logging/src/main/java#7.4.5*7.4.12"}) //The test has regressed closing the set. Issue is reported.
//@AT({"modules/testcases/jdkAll/Wildfly/logging/src/main/java#?"})
public class DebugLoggingTestCase {

    private final String serverLogPath = "${jboss.server.log.dir}/logging-properties-test.log";
    private static Logger log = Logger
            .getLogger(DebugLoggingTestCase.class);

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive archive = ShrinkWrap.create(WebArchive.class, "debuglogging.war");
        archive.addClasses(LoggingServlet.class);

        return archive;
    }

    @ArquillianResource(LoggingServlet.class)
    URL url;

    @Test
    public void debugLogTest() throws IOException {
        HttpURLConnection http = (HttpURLConnection) new URL(url, "Logger")
                .openConnection();
        int statusCode = http.getResponseCode();
        assertTrue("Invalid response statusCode: " + statusCode,
                statusCode == HttpServletResponse.SC_OK);
        // check logs
        String path = new File("").getAbsolutePath() + "/" + serverLogPath;
        File serverlogfile = new File(path);

        FileInputStream inputStream = new FileInputStream(path);
        try {
            String everything = IOUtils.toString(inputStream);
            assertTrue("Xnio debug message missing ...", everything.contains("Creating worker:") && everything.contains("pool size:") && everything.contains("max pool size:") && everything.contains("keep alive:") && everything.contains("io threads:") && everything.contains("stack size:"));
        } finally {
            inputStream.close();
        }

    }

}
