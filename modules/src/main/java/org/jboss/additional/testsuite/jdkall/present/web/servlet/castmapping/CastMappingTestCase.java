package org.jboss.additional.testsuite.jdkall.present.web.servlet.castmapping;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.eap.additional.testsuite.annotations.EAT;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

@RunAsClient
@RunWith(Arquillian.class)
@EAT({"modules/testcases/jdkAll/Eap7Plus/web/src/main/java#7.4.14","modules/testcases/jdkAll/EapJakarta/web/src/main/java","modules/testcases/jdkAll/WildflyJakarta/web/src/main/java#31.0.0"})
public class CastMappingTestCase {

    public static final String DEPLOYMENT = "castmapping.war";

    @Deployment(name = DEPLOYMENT)
    public static Archive<?> getDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, DEPLOYMENT);
        war.addClass(CastMappingServlet.class);
        return war;
    }

    @Test
    @OperateOnDeployment(DEPLOYMENT)
    public void catMappingTest(@ArquillianResource URL url) throws Exception {
        URL testURL = new URL(url.toString() + "CastMappingServlet");

        final HttpGet request = new HttpGet(testURL.toString());
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response = null;

        try {
            response = httpClient.execute(request);
            Assert.assertEquals("Failed to access " + testURL, HttpURLConnection.HTTP_OK, response.getStatusLine().getStatusCode());        
        } finally {
            IOUtils.closeQuietly(response);
            httpClient.close();
        }
    }
}
