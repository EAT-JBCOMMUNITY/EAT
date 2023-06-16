package org.jboss.additional.testsuite.jdkall.present.web.curl;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.File;
import io.undertow.server.handlers.HttpContinueAcceptingHandler;
import io.undertow.server.HttpHandler;
import io.undertow.util.Headers;
import io.undertow.server.HttpServerExchange;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.runner.RunWith;
import java.io.File;
import java.io.FileInputStream;
import org.apache.commons.io.IOUtils;
import org.jboss.eap.additional.testsuite.annotations.EAT;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java#29.0.0","modules/testcases/jdkAll/Eap7Plus/jaxrs/src/main/java#7.4.11"})
@RunWith(Arquillian.class)
@RunAsClient
public class MultipartCurlTestCase {

    private final String serverLogPath = "../../../../../servers/wildfly/build/target/jbossas/standalone/log/server.log";
    private final String serverLogPath2 = "../../../../../servers/eap7/build/target/jbossas/standalone/log/server.log";

    @Deployment
    public static Archive<?> deploy() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "multipartcutlTestCase.war");
        return war;
    }

    @Test
    public void testMultipartCurl1() throws Exception {
        
        ProcessBuilder pb = new ProcessBuilder("./curlmultipart.sh").redirectErrorStream(true);
        pb.redirectError(new File("output1.txt"));
        pb.start();
        
        Thread.sleep(5000);
        
        String content = new String(Files.readAllBytes(Paths.get("output1.txt")), StandardCharsets.UTF_8);
        assertTrue(!content.contains("iVBORw0KGgoAAAANSUhEUgAAABQA"));
        
        String path = new File("").getAbsolutePath() + "/" + serverLogPath;
        File serverlogfile = new File(path);
            if(!serverlogfile.exists()) {
                path = new File("").getAbsolutePath() + "/" + serverLogPath2;
            }

            FileInputStream inputStream = new FileInputStream(path);
            try {
                String everything = IOUtils.toString(inputStream);
                assertTrue(!everything.contains("iVBORw0KGgoAAAANSUhEUgAAABQA"));
            } finally {
                inputStream.close();
            }
        
    }

    @Test
    public void testMultipartCurl2() throws Exception {
        
        ProcessBuilder pb = new ProcessBuilder("./curlmultipart2.sh").redirectErrorStream(true);
        pb.redirectError(new File("output2.txt"));
        pb.start();
        
        Thread.sleep(5000);
        
        String content = new String(Files.readAllBytes(Paths.get("output2.txt")), StandardCharsets.UTF_8);
        assertTrue(!content.contains("iVBORw0KGgoAAAANSUhEUgAAABQA"));

        String path = new File("").getAbsolutePath() + "/" + serverLogPath;
        File serverlogfile = new File(path);
            if(!serverlogfile.exists()) {
                path = new File("").getAbsolutePath() + "/" + serverLogPath2;
            }

            FileInputStream inputStream = new FileInputStream(path);
            try {
                String everything = IOUtils.toString(inputStream);
                assertTrue(!everything.contains("iVBORw0KGgoAAAANSUhEUgAAABQA"));
            } finally {
                inputStream.close();
            }
    }
}
