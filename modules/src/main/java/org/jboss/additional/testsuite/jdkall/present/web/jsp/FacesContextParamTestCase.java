package org.jboss.additional.testsuite.jdkall.present.web.jsp;

import java.io.IOException;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.concurrent.TimeoutException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.junit.Assert;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.apache.http.util.EntityUtils;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@RunWith(Arquillian.class)
@RunAsClient
@EAT({"modules/testcases/jdkAll/Eap7Plus/web/src/main/java#7.4.15","modules/testcases/jdkAll/WildflyJakarta/web/src/main/java#31.0.0.Final"})
public class FacesContextParamTestCase {

    private static final String JSP_DEPLOYMENT = "facesContextParam";

    private static final Logger log = LoggerFactory.getLogger(FacesContextParamTestCase.class);

    private static final String WEBXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<web-app xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://java.sun.com/xml/ns/javaee\" xsi:schemaLocation=\"http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd\" id=\"WebApp_ID\" version=\"2.5\">\n"
            + "\n"
            + "<distributable/>\n"
            + "  <context-param>\n"
            + "    <param-name>com.sun.faces.enableDistributable</param-name>\n"
            + "    <param-value>false</param-value>\n"
            + "  </context-param>"
            + "\n"
            + "</web-app>";

    private static final String WEBXML2 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<web-app xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://java.sun.com/xml/ns/javaee\" xsi:schemaLocation=\"http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd\" id=\"WebApp_ID\" version=\"2.5\">\n"
            + "\n"
            + "<distributable/>\n"
            + "  <context-param>\n"
            + "    <param-name>com.sun.faces.enableDistributable</param-name>\n"
            + "    <param-value>true</param-value>\n"
            + "  </context-param>"
            + "\n"
            + "</web-app>";

    @Deployment(name="falseparam")
    public static WebArchive getDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, JSP_DEPLOYMENT + ".war");
        war.addClass(NewServlet.class);
        war.addAsWebInfResource(new StringAsset(WEBXML), "web.xml");
        log.info(war.toString(true));
        return war;
    }

    @Deployment(name="trueparam")
    public static WebArchive getDeployment2() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, JSP_DEPLOYMENT + "2.war");
        war.addClass(NewServlet.class);
        war.addAsWebInfResource(new StringAsset(WEBXML2), "web.xml");
        log.info(war.toString(true));
        return war;
    }

    @Deployment(name="noparam")
    public static WebArchive getDeployment3() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, JSP_DEPLOYMENT + "3.war");
        war.addClass(NewServlet.class);
        log.info(war.toString(true));
        return war;
    }

    @Test @OperateOnDeployment("falseparam")
    public void contextFalseParamTest(@ArquillianResource URL url) throws InterruptedException, TimeoutException {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create()
                .setMaxConnPerRoute(1)
                .setMaxConnTotal(1)
                .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false));
        try (CloseableHttpClient httpClient = httpClientBuilder.build()) {
            HttpGet httpGet = new HttpGet(url.toExternalForm() + "NewServlet");
            CloseableHttpResponse response = null;
            try {
                log.info("Performing request to: " + httpGet.toString());
                response = httpClient.execute(httpGet);

                Assert.assertTrue(response.getStatusLine().getStatusCode() == 200);
		Assert.assertTrue(EntityUtils.toString(response.getEntity()).contains("false"));
            } catch (HttpHostConnectException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            log.warn("The request threw an exception", e);
        }
    }

    @Test @OperateOnDeployment("trueparam")
    public void contextTrueParamTest(@ArquillianResource URL url) throws InterruptedException, TimeoutException {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create()
                .setMaxConnPerRoute(1)
                .setMaxConnTotal(1)
                .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false));
        try (CloseableHttpClient httpClient = httpClientBuilder.build()) {
            HttpGet httpGet = new HttpGet(url.toExternalForm() + "NewServlet");
            CloseableHttpResponse response = null;
            try {
                log.info("Performing request to: " + httpGet.toString());
                response = httpClient.execute(httpGet);
System.out.println("===== " + EntityUtils.toString(response.getEntity()));
                Assert.assertTrue(response.getStatusLine().getStatusCode() == 200);
		Assert.assertTrue(EntityUtils.toString(response.getEntity()).contains("true"));
            } catch (HttpHostConnectException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            log.warn("The request threw an exception", e);
        }
    }

    @Test @OperateOnDeployment("noparam")
    public void contextNoParamTest(@ArquillianResource URL url) throws InterruptedException, TimeoutException {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create()
                .setMaxConnPerRoute(1)
                .setMaxConnTotal(1)
                .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false));
        try (CloseableHttpClient httpClient = httpClientBuilder.build()) {
            HttpGet httpGet = new HttpGet(url.toExternalForm() + "NewServlet");
            CloseableHttpResponse response = null;
            try {
                log.info("Performing request to: " + httpGet.toString());
                response = httpClient.execute(httpGet);
System.out.println("===== " + EntityUtils.toString(response.getEntity()));

                Assert.assertTrue(response.getStatusLine().getStatusCode() == 200);
		Assert.assertTrue(EntityUtils.toString(response.getEntity()).contains("null"));
            } catch (HttpHostConnectException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            log.warn("The request threw an exception", e);
        }
    }

}
