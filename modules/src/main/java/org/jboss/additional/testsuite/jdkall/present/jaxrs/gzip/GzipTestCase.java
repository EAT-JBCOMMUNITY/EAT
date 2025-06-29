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
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.io.File;
import java.io.FileInputStream;
import org.apache.commons.io.IOUtils;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.client.Invocation;
import static org.junit.Assert.assertTrue;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.Locale;
import java.util.stream.Collectors;
import org.jboss.resteasy.plugins.interceptors.encoding.GZIPEncodingInterceptor;
import org.jboss.resteasy.plugins.interceptors.encoding.GZIPDecodingInterceptor;
import org.jboss.resteasy.plugins.interceptors.encoding.AcceptEncodingGZIPFilter;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;


/**
 *  Documentation added : https://play.google.com/store/apps/details?id=edu.eatproposals.eatapp ( RESTEASY DOC / ResteasyGzip.pdf )
 **/
@RunWith(Arquillian.class)
@RunAsClient
@EAT({"modules/testcases/jdkAll/Eap73x/jaxrs/src/main/java","modules/testcases/jdkAll/Eap7Plus/jaxrs/src/main/java"})
public class GzipTestCase {

    private static Logger log = Logger.getLogger(GzipTestCase.class.getName());
    private static final String gzipproviders = "org.jboss.resteasy.plugins.interceptors.encoding.GZIPDecodingInterceptor\norg.jboss.resteasy.plugins.interceptors.encoding.GZIPEncodingInterceptor\norg.jboss.resteasy.plugins.interceptors.encoding.AcceptEncodingGZIPFilter";

    @Deployment
    public static Archive<?> deploy() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "gzip.war");
        war.addPackage(GzipTestCase.class.getPackage());
        war.addPackage(org.jboss.resteasy.plugins.interceptors.encoding.AcceptEncodingGZIPFilter.class.getPackage());
         war.addAsManifestResource(new StringAsset(gzipproviders), "services/javax.ws.rs.ext.Providers");
        return war;
    }

    @ArquillianResource
    private URL url;

    @Test
    public void testgzip() throws Exception {
        String uri = url.getPath() + "/gzip/gzipservice/articles";
        System.out.println("uri: " + uri);

        Client client = new ResteasyClientBuilder() // Activate gzip compression on client:
                    .register(AcceptEncodingGZIPFilter.class)
                    .register(GZIPDecodingInterceptor.class)
                    .register(GZIPEncodingInterceptor.class)
                    .build();
        Invocation.Builder request = client.target("http://localhost:8080" + uri).request();
        Response response = request.get();
        String content = response.readEntity(String.class);
        System.out.println(response.getStringHeaders() + "========== " + content);
        System.out.println("content-encoding: "+ response.getHeaderString("Content-Encoding"));
        Assert.assertTrue(response.getStatus() == 200);
        Assert.assertTrue(content.contains("Multiversion"));
        Assert.assertTrue(response.getHeaderString("Content-Encoding").contains("gzip"));
        client.close();
    }

}
