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
import org.jboss.eap.additional.testsuite.annotations.EAT;

@RunWith(Arquillian.class)
@RunAsClient
@EAT({"modules/testcases/jdkAll/Eap7Plus/web/src/main/java#7.4.1","modules/testcases/jdkAll/Wildfly/web/src/main/java#24.0.0","modules/testcases/jdkAll/WildflyJakarta/web/src/main/java#27.0.0.Alpha4","modules/testcases/jdkAll/WildflyRelease-27.0.0.Final/web/src/main/java","modules/testcases/jdkAll/EapJakarta/web/src/main/java"})
public class JspForwardTestCase {

    private static final String JSP_DEPLOYMENT = "forwardjsp";

    private static final Logger log = LoggerFactory.getLogger(JspForwardTestCase.class);

    private static final String PAGE_CONTENT = "<html>"
            + "<head>"
            + "<title>JSP</title>"
            + "</head>"
            + "<body>"
            + "<% request.getRequestDispatcher(\"../../../../WEB-INF/web.xml\").forward(request, response);%>"
            + "</body>"
            + "</html>";

    private static final String WEBXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<web-app xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://java.sun.com/xml/ns/javaee\" xsi:schemaLocation=\"http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd\" id=\"WebApp_ID\" version=\"2.5\">\n"
            + "\n"
            + "<display-name>IssueTest</display-name>\n"
            + "  <welcome-file-list>\n"
            + "    <welcome-file>index.html</welcome-file>\n"
            + "    <welcome-file>index.htm</welcome-file>\n"
            + "    <welcome-file>index.jsp</welcome-file>\n"
            + "    <welcome-file>default.html</welcome-file>\n"
            + "    <welcome-file>default.htm</welcome-file>\n"
            + "    <welcome-file>default.jsp</welcome-file>\n"
            + "  </welcome-file-list>"
            + "\n"
            + "</web-app>";

    @Deployment
    public static WebArchive getDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, JSP_DEPLOYMENT + ".war");
        war.add(new StringAsset(PAGE_CONTENT), "forward.jsp");
        war.addAsWebInfResource(new StringAsset(WEBXML), "web.xml");
        log.info(war.toString(true));
        return war;
    }

    @Test
    public void jspForwardTest(@ArquillianResource URL url) throws InterruptedException, TimeoutException {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create()
                .setMaxConnPerRoute(1)
                .setMaxConnTotal(1)
                .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false));
        try (CloseableHttpClient httpClient = httpClientBuilder.build()) {
            HttpGet httpGet = new HttpGet(url.toExternalForm() + "forward.jsp");
            CloseableHttpResponse response = null;
            try {
                log.info("Performing request to: " + httpGet.toString());
                response = httpClient.execute(httpGet);

                Assert.assertTrue(response.getStatusLine().getStatusCode() != 200);
            } catch (HttpHostConnectException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            log.warn("The request threw an exception", e);
        }
    }

}
