package org.jboss.additional.testsuite.jdkall.present.jaxrs.gzip;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.eap.additional.testsuite.annotations.EAT;
import org.jboss.logging.Logger;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.io.File;
import java.io.FileInputStream;
import org.apache.commons.io.IOUtils;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.Locale;
import java.util.stream.Collectors;


@RunWith(Arquillian.class)
@RunAsClient
@EAT({"modules/testcases/jdkAll/Eap7Plus/jaxrs/src/main/java","modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java#27.0.0.Alpha4","modules/testcases/jdkAll/EapJakarta/jaxrs/src/main/java"})
public class GzipTestCase {

    private static Logger log = Logger.getLogger(GzipTestCase.class.getName());

    @Deployment
    public static Archive<?> deploy() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "gzip.war");
        war.addPackage(GzipTestCase.class.getPackage());
        return war;
    }

    @ArquillianResource
    private URL url;

    @Test
    public void testCache() throws Exception {
        String uri = url.getPath() + "gzip/gzipservice/articles";
        System.out.println("uri: " + uri);

        try (Socket client = new Socket("127.0.0.1", 8080)) {
            try (PrintWriter out = new PrintWriter(client.getOutputStream(), true)) {
                out.printf(Locale.US, "GET %s HTTP/1.1\nHost: \n\n", uri);
                String response = new BufferedReader(new InputStreamReader(client.getInputStream())).lines().collect(Collectors.joining("\n"));
                System.out.println("response: " + response);
                Assert.assertNotNull(response);
                Assert.assertTrue(response.contains("HTTP/1.1 200 OK"));
                Assert.assertTrue(response.contains("Multiversion"));
            }catch(Exception e) {
                Assert.fail("Printwriter could not be created");
            }
        }catch(Exception ex) {
            Assert.fail("Socket could not be created");
        }
        
    }

}
