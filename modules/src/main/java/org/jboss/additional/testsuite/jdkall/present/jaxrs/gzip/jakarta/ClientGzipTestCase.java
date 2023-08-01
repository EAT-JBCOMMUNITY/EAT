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
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.client.Invocation;
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
@EAT({"modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java#27.0.0.Alpha4","modules/testcases/jdkAll/EapJakarta/jaxrs/src/main/java"})
public class ClientGzipTestCase {

    private static Logger log = Logger.getLogger(ClientGzipTestCase.class.getName());

    @Deployment
    public static Archive<?> deploy() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "clientgzip.war");
        war.addPackage(ClientGzipTestCase.class.getPackage());
        return war;
    }

    @ArquillianResource
    private URL url;

    @Test
    public void testCompress() throws Exception
    {
        String uri = url.getPath() + "gzip/gzipservice/a";
        System.out.println("uri: " + uri);
        Client client = ClientBuilder.newClient();
        Invocation.Builder request = client.target("http://localhost:8080" + uri).request();
        request.acceptEncoding("gzip,compress");
        Response response = request.get();
        System.out.println("content-encoding: "+ response.getHeaderString("Content-Encoding"));
        Assert.assertTrue(response.getHeaderString("Content-Encoding").contains("compress"));
        client.close();
    }

}
