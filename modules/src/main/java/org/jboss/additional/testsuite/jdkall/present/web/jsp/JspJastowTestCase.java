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
import org.apache.http.util.EntityUtils;
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
import org.jboss.eap.additional.testsuite.annotations.ATTest;

@RunWith(Arquillian.class)
@RunAsClient
@EAT({"modules/testcases/jdkAll/Eap7Plus/web/src/main/java#7.4.10","modules/testcases/jdkAll/WildflyJakarta/web/src/main/java#29.0.0"})
public class JspJastowTestCase {

    private static final String JSP_DEPLOYMENT = "jastowjsp";

    private static final Logger log = LoggerFactory.getLogger(JspJastowTestCase.class);

    private static final String PAGE_CONTENT = "<%@ taglib prefix=\"c\" uri=\"http://java.sun.com/jsp/jstl/core\" %>"
	+ "<%@ page language=\"java\" contentType=\"text/html; charset=UTF-8\" pageEncoding=\"ISO-8859-1\"%>"
	+ "<%@ page import=\"com.landg.cnbs.web.common.WebConstants\" %>"
	+ "<html>"
	+ "<head>"
	+ "<title>test-jsp</title>"
	+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />"
	+ "</head>"
	+ "<body>"
	+ "<div>test-output</div>"
	+ "<div class=\"test\" id=\"123\">"
	+ "<label>conditional output</label>"
	+ "<c:out value=\"\\\"function(<%=WebConstants.REFRESH%>, ${Boolean.TRUE})\\\"\"/>"
	+ "</div>"
	+ "</body>"
	+ "</html>";
	
     private static final String PAGE_CONTENT2 = "<%@ page language=\"java\" contentType=\"text/html;charset=UTF-8\" pageEncoding=\"UTF-8\"%>"
	+ "<ul>"
        + "<li>euro=${param.euro}</li>"
        + "<li>acutes=${param.acutes}</li>"
        + "</ul>";
	
    private static final String PAGE_CONTENT3 = "<%@ page language=\"java\" contentType=\"text/html;charset=UTF-8\" pageEncoding=\"UTF-8\"%>" 
	+ "<html>"
	+ "    <body>"
	+ "	<jsp:include page=\"/include.jsp\">"
	+ "	    <jsp:param name=\"euro\" value=\"€\" />"
	+ "	    <jsp:param name=\"acutes\" value=\"áéíóú\" />"
	+ "	</jsp:include>"
	+ "    </body>"
	+ "</html>";

     private static final String WEBXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<web-app xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://java.sun.com/xml/ns/javaee\" xsi:schemaLocation=\"http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd\" id=\"WebApp_ID\" version=\"2.5\">\n"
            + "</web-app>";

    @Deployment
    public static WebArchive getDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, JSP_DEPLOYMENT + ".war");
        war.addClass(com.landg.cnbs.web.common.WebConstants.class);
        war.add(new StringAsset(PAGE_CONTENT), "jspjastow.jsp");
        war.add(new StringAsset(PAGE_CONTENT2), "include.jsp");
        war.add(new StringAsset(PAGE_CONTENT3), "include_parent.jsp");
        war.addAsWebInfResource(new StringAsset(WEBXML), "web.xml");
        log.info(war.toString(true));
        return war;
    }

    @ATTest({"modules/testcases/jdkAll/Eap7Plus/web/src/main/java#7.4.10"})
    public void jspJastowTest(@ArquillianResource URL url) throws InterruptedException, TimeoutException {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create()
                .setMaxConnPerRoute(1)
                .setMaxConnTotal(1)
                .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false));
        try (CloseableHttpClient httpClient = httpClientBuilder.build()) {
            HttpGet httpGet = new HttpGet(url.toExternalForm() + "jspjastow.jsp");
            CloseableHttpResponse response = null;
            try {
                log.info("Performing request to: " + httpGet.toString());
                response = httpClient.execute(httpGet);

                Assert.assertTrue(response.getStatusLine().getStatusCode() == 200);
            } catch (HttpHostConnectException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            log.warn("The request threw an exception", e);
        }
    }
    
    @Test
    public void jspJastowUTF8Test(@ArquillianResource URL url) throws InterruptedException, TimeoutException {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create()
                .setMaxConnPerRoute(1)
                .setMaxConnTotal(1)
                .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false));
        try (CloseableHttpClient httpClient = httpClientBuilder.build()) {
            HttpGet httpGet = new HttpGet(url.toExternalForm() + "include_parent.jsp");
            CloseableHttpResponse response = null;
            try {
                log.info("Performing request to: " + httpGet.toString());
                response = httpClient.execute(httpGet);
                String content = EntityUtils.toString(response.getEntity());
             //   System.out.println("========== " + content);

                Assert.assertTrue(response.getStatusLine().getStatusCode() == 200);
                Assert.assertTrue(content.contains("euro=€"));
                Assert.assertTrue(content.contains("acutes=áéíóú"));
            } catch (HttpHostConnectException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            log.warn("The request threw an exception", e);
        }
    }


}
