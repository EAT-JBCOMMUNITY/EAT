package org.jboss.additional.testsuite.jdkall.present.web.servlet;


import java.io.IOException;
import java.net.URISyntaxException;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.additional.testsuite.jdkall.present.web.servlet.buffersize.ResponseBufferSizeServlet;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jboss.arquillian.container.test.api.Deployment;
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

@RunAsClient
@RunWith(Arquillian.class)
@EAT({"modules/testcases/jdkAll/WildflyJakarta/web/src/main/java#29.0.0","modules/testcases/jdkAll/Eap7Plus/web/src/main/java#7.4.9","modules/testcases/jdkAll/EapJakarta/web/src/main/java#9.0.0"})
public class ServletWebConfigTestCase {

    private static final String SERVLET_DEPLOYMENT = "servletwebconfig";

    private static String getWebXml() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE web-app PUBLIC \"-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN\"\n" +
            "        \"http://java.sun.com/dtd/web-app_2_3.dtd\">\n" +
            "<web-app>\n" +
            "	<security-constraint>\n" +
            "		<web-resource-collection>\n" +
            "			<url-pattern>/ResponseBufferSizeServlet</url-pattern>\n" +
            "		</web-resource-collection>\n" +
            "	</security-constraint>\n" +
            "\n" +
            "	<deny-uncovered-http-methods />\n" +
            "</web-app>";
    }

    @Deployment(name = SERVLET_DEPLOYMENT)
    public static Archive<?> getServletDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, SERVLET_DEPLOYMENT + ".war");
        war.addClass(ResponseBufferSizeServlet.class);
        war.setWebXML(new StringAsset(getWebXml()));
        return war;
    }

    @Test
    public void servletWebConfigTest(@ArquillianResource URL url) throws IOException, URISyntaxException {
        URL testUrl = new URL(url.toExternalForm() + "ResponseBufferSizeServlet");
        final HttpGet request = new HttpGet(testUrl.toString());
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response = null;

        try {
            response = httpClient.execute(request);
            Assert.assertEquals("Failed to access " + testUrl, HttpURLConnection.HTTP_OK, response.getStatusLine().getStatusCode());
        } finally {
            IOUtils.closeQuietly(response);
            httpClient.close();
        }
    }
}
