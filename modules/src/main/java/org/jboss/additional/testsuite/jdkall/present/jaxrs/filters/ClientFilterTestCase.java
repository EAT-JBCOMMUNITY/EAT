package org.jboss.additional.testsuite.jdkall.present.jaxrs.filters;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.eap.additional.testsuite.annotations.EAT;
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

import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import static org.junit.Assert.fail;

/**
 *  Documentation added : https://play.google.com/store/apps/details?id=edu.eatproposals.eatapp ( RESTEASY DOC / ClientSideFilters.pdf )
 **/
@RunWith(Arquillian.class)
@RunAsClient
@EAT({"modules/testcases/jdkAll/Eap7Plus/jaxrs/src/main/java"})
public class ClientFilterTestCase {

    private final String serverLogPath = "target/surefire-reports/org.jboss.additional.testsuite.jdkall.present.jaxrs.filters.ClientFilterTestCase-output.txt";

    @Deployment
    public static Archive<?> deploy() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "jaxrsclientfilter.war");
        war.addPackage(ClientFilterTestCase.class.getPackage());
        return war;
    }

    @ArquillianResource
    private URL url;

    @Test
    public void testFilters() throws Exception {
        Client client = ClientBuilder.newClient();
	client.register(new ClientFilter());
	client.register(new WriterInterceptorImplementation());
	client.register(new ReaderInterceptorImplementation());
	String response = client.target("http://127.0.0.1:8080/jaxrsclientfilter/myjrs/filter/getUriInfo").request().get(String.class);
	Assert.assertTrue(response.contains("uriInfo: http://127.0.0.1:8080/jaxrsclientfilter/myjrs/filter/getU"));
	
	String path = new File("").getAbsolutePath() + "/" + serverLogPath;
        File serverlogfile = new File(path);
        if(!serverlogfile.exists()) {
            fail();
        }

        assertTrue("Request Client Filter was not invoked ...", System.getProperty("ClientRequestFilter").compareTo("true")==0);
        assertTrue("Response Client Filter was not invoked ...", System.getProperty("ClientResponseFilter").compareTo("true")==0);
        assertTrue("ReaderInterceptorImplementation was not invoked ...", System.getProperty("ReaderInterceptorImplementation").compareTo("true")==0);
    }

}
