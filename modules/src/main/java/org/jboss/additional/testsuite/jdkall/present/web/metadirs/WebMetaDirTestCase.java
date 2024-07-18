package org.jboss.additional.testsuite.jdkall.present.web.metadirs;


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
import org.jboss.eap.additional.testsuite.annotations.ATTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.HttpURLConnection;
import java.net.URL;

@RunAsClient
@RunWith(Arquillian.class)
@EAT({"modules/testcases/jdkAll/WildflyJakarta/web/src/main/java#28.0.0","modules/testcases/jdkAll/Eap7Plus/web/src/main/java#7.4.9","modules/testcases/jdkAll/EapJakarta/web/src/main/java#9.0.0"})
public class WebMetaDirTestCase {

    private static final String SERVLET_DEPLOYMENT = "webmetadir";

    @Deployment(name = SERVLET_DEPLOYMENT)
    public static Archive<?> getServletDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, SERVLET_DEPLOYMENT + ".war");
        war.addClass(WebMetaDirTestCase.class);
        war.addAsResource("META-INF/text");
        war.addAsResource("WEB-INF/text");
        war.addAsResource("meta-inf/text");
        war.addAsResource("web-inf/text");
        war.addAsResource("::/::/META-INF/text");
        war.addAsResource("::/::/WEB-INF/text");
        war.addAsResource("::/::/meta-inf/text");
        war.addAsResource("::/::/web-inf/text");
        return war;
    }

    @Test
    public void metainfTest(@ArquillianResource URL url) throws IOException, URISyntaxException {
        URL testUrl = new URL(url.toExternalForm() + "META-INF/text");
        final HttpGet request = new HttpGet(testUrl.toString());
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response = null;

        try {
            response = httpClient.execute(request);
            Assert.assertEquals("Failed to access " + testUrl, HttpURLConnection.HTTP_NOT_FOUND, response.getStatusLine().getStatusCode());
        } finally {
            IOUtils.closeQuietly(response);
            httpClient.close();
        }
    }

    @Test
    public void webinfTest(@ArquillianResource URL url) throws IOException, URISyntaxException {
        URL testUrl = new URL(url.toExternalForm() + "WEB-INF/text");
        final HttpGet request = new HttpGet(testUrl.toString());
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response = null;

        try {
            response = httpClient.execute(request);
            Assert.assertEquals("Failed to access " + testUrl, HttpURLConnection.HTTP_NOT_FOUND, response.getStatusLine().getStatusCode());
        } finally {
            IOUtils.closeQuietly(response);
            httpClient.close();
        }
    }

    @Test
    public void metaINFTest(@ArquillianResource URL url) throws IOException, URISyntaxException {
        URL testUrl = new URL(url.toExternalForm() + "meta-inf/text");
        final HttpGet request = new HttpGet(testUrl.toString());
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response = null;

        try {
            response = httpClient.execute(request);
            Assert.assertEquals("Failed to access " + testUrl, HttpURLConnection.HTTP_NOT_FOUND, response.getStatusLine().getStatusCode());
        } finally {
            IOUtils.closeQuietly(response);
            httpClient.close();
        }
    }

    @Test
    public void webINFTest(@ArquillianResource URL url) throws IOException, URISyntaxException {
        URL testUrl = new URL(url.toExternalForm() + "web-inf/text");
        final HttpGet request = new HttpGet(testUrl.toString());
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response = null;

        try {
            response = httpClient.execute(request);
            Assert.assertEquals("Failed to access " + testUrl, HttpURLConnection.HTTP_NOT_FOUND, response.getStatusLine().getStatusCode());
        } finally {
            IOUtils.closeQuietly(response);
            httpClient.close();
        }
    }

    @ATTest({"modules/testcases/jdkAll/WildflyJakarta/web/src/main/java#35.0.0","modules/testcases/jdkAll/Eap7Plus/web/src/main/java#7.4.19","modules/testcases/jdkAll/EapJakarta/web/src/main/java#9.0.0"})
    public void metainfWithDotsTest(@ArquillianResource URL url) throws IOException, URISyntaxException {
        URL testUrl = new URL(url.toExternalForm() + "::/::/META-INF/text");
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

    @ATTest({"modules/testcases/jdkAll/WildflyJakarta/web/src/main/java#35.0.0","modules/testcases/jdkAll/Eap7Plus/web/src/main/java#7.4.19","modules/testcases/jdkAll/EapJakarta/web/src/main/java#9.0.0"})
    public void webinfWithDotsTest(@ArquillianResource URL url) throws IOException, URISyntaxException {
        URL testUrl = new URL(url.toExternalForm() + "::/::/WEB-INF/text");
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

    @Test
    public void metaINFWithDotsTest(@ArquillianResource URL url) throws IOException, URISyntaxException {
        URL testUrl = new URL(url.toExternalForm() + "::/::/meta-inf/text");
        final HttpGet request = new HttpGet(testUrl.toString());
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response = null;

        try {
            response = httpClient.execute(request);
            Assert.assertEquals("Failed to access " + testUrl, HttpURLConnection.HTTP_NOT_FOUND, response.getStatusLine().getStatusCode());
        } finally {
            IOUtils.closeQuietly(response);
            httpClient.close();
        }
    }

    @Test
    public void webINFWithDotsTest(@ArquillianResource URL url) throws IOException, URISyntaxException {
        URL testUrl = new URL(url.toExternalForm() + "::/::/web-inf/text");
        final HttpGet request = new HttpGet(testUrl.toString());
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response = null;

        try {
            response = httpClient.execute(request);
            Assert.assertEquals("Failed to access " + testUrl, HttpURLConnection.HTTP_NOT_FOUND, response.getStatusLine().getStatusCode());
        } finally {
            IOUtils.closeQuietly(response);
            httpClient.close();
        }
    }
}
