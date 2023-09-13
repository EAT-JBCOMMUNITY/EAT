package org.jboss.additional.testsuite.jdkall.present.ejb.transactional;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import java.net.HttpURLConnection;
import java.net.URL;
import org.junit.Assert;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/ejb/src/main/java#29.0.0","modules/testcases/jdkAll/Eap7Plus/ejb/src/main/java#7.4.14","modules/testcases/jdkAll/EapJakarta/ejb/src/main/java#8.0.0"})
@RunWith(Arquillian.class)
@RunAsClient
public class TransactionalTestCase {

    private static final String DEPLOYMENT = "TransactionalTestCase.war";

    @Deployment(name = DEPLOYMENT)
    public static Archive<?> getWelcomeContextDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, DEPLOYMENT).addClasses(TransactionalServlet.class, TransactionalEjb.class);
        return war;
    }

    @Test
    public void transactionalTest(@ArquillianResource URL url) throws Exception {
        URL testURL = new URL(url.toExternalForm() + "transactional");

        final HttpGet request = new HttpGet(testURL.toString());
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response = null;

        try {
            response = httpClient.execute(request);
            Assert.assertEquals("Failed to access " + testURL, HttpURLConnection.HTTP_OK, response.getStatusLine().getStatusCode());
        }finally {
            IOUtils.closeQuietly(response);
            httpClient.close();
        }
    }
}
