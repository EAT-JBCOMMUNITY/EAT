package org.jboss.additional.testsuite.jdkall.present.elytron.login;

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
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.junit.Assert;
import static org.junit.Assert.fail;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/Eap7Plus/elytron/src/main/java#7.4.8","modules/testcases/jdkAll/WildflyJakarta/elytron/src/main/java#28.0.0"})
@RunWith(Arquillian.class)
@RunAsClient
public class LoginSecurityTestCase {

    private static final String DEPLOYMENT = "loginsecurity";

    private static final Logger log = LoggerFactory.getLogger(LoginSecurityTestCase.class);

    private static final String PAGE_CONTENT = "<html>"
            + "<html>\n"
            + "  <body>\n"
            + "    <h2>Hello World protected!</h2>\n"
            + "  </body>\n"
            + "</html>";

    private static final String PAGE_CONTENT_LOGIN = "<!DOCTYPE html>\n"
            + "<html lang=\"en\">\n"
            + "    <head>\n"
            + "        <meta charset=\"UTF-8\">\n"
            + "        <title>Log in</title>\n"
            + "    </head>\n"
            + "    <body>\n"
            + "        <h2>Please log in:</h2>\n"
            + "        <br><br>\n"
            + "        <form action=\"j_security_check\" method=post>\n"
            + "            <p>\n"
            + "            <strong>Username: </strong>\n"
            + "            <input type=\"text\" name=\"j_username\" size=\"25\">\n"
            + "            <p><p>\n"
            + "            <strong>Password: </strong>\n"
            + "            <input type=\"password\" size=\"15\" name=\"j_password\">\n"
            + "            <p><p>\n"
            + "            <input type=\"submit\" value=\"Submit\">\n"
            + "            <input type=\"reset\" value=\"Reset\">\n"
            + "        </form>\n"
            + "    </body>\n"
            + "</html>";

    private static final String WEBXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "\n"
            + "<web-app version=\"2.5\" xmlns=\"http://java.sun.com/xml/ns/javaee\"\n"
            + "   xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"
            + "   xsi:schemaLocation=\"http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd\"\n"
            + "   metadata-complete=\"false\">\n"
            + "\n"
            + "   <welcome-file-list>\n"
            + "      <welcome-file>index.html</welcome-file>\n"
            + "   </welcome-file-list>\n"
            + "\n"
            + "   <security-constraint>\n"
            + "      <web-resource-collection>\n"
            + "         <web-resource-name>Example Web App</web-resource-name>\n"
            + "         <description></description>\n"
            + "         <url-pattern>/main/*</url-pattern>\n"
            + "      </web-resource-collection>\n"
            + "      <auth-constraint>\n"
            + "         <role-name>regular_user</role-name>\n"
            + "      </auth-constraint>\n"
            + "   </security-constraint>\n"
            + "\n"
            + "   <security-role>\n"
            + "      <role-name>regular_user</role-name>\n"
            + "   </security-role>\n"
            + "\n"
            + "   <login-config>\n"
            + "      <auth-method>FORM</auth-method>\n"
            + "      <form-login-config>\n"
            + "         <form-login-page>/pub/login.html</form-login-page>\n"
            + "      </form-login-config>\n"
            + "   </login-config>\n"
            + "\n"
            + "</web-app>";

    private static final String JBOSSWEBXML = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n"
            + "<!DOCTYPE jboss-web PUBLIC\n"
            + "        \"-//JBoss//DTD Web Application 5.0//EN\"\n"
            + "        \"http://www.jboss.org/j2ee/dtd/jboss-web_5_0.dtd\">\n"
            + "<jboss-web>\n"
            + "   <security-domain flushOnSessionInvalidation=\"true\">my_app_security_domain</security-domain>\n"
            + "</jboss-web>";

    private static final String UNDERTOWEXTENSION = "org.jboss.additional.testsuite.jdkall.present.elytron.login.MyServletExtension";

    @Deployment
    public static WebArchive getDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, DEPLOYMENT + ".war");
        war.addClass(MyServletExtension.class);
        war.addClass(MyDummyTokenHandler.class);
        war.add(new StringAsset(PAGE_CONTENT), "main/index.html");
        war.add(new StringAsset(PAGE_CONTENT_LOGIN), "pub/login.html");
        war.addAsWebInfResource(new StringAsset(WEBXML), "web.xml");
        war.addAsWebInfResource(new StringAsset(JBOSSWEBXML), "jboss-web.xml");
        war.addAsResource(new StringAsset(UNDERTOWEXTENSION), "META-INF/services/io.undertow.servlet.ServletExtension");
        log.info(war.toString(true));
        return war;
    }

    @Test
    public void loginTest(@ArquillianResource URL url) {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create()
                .setMaxConnPerRoute(1)
                .setMaxConnTotal(1)
                .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false));
        try (CloseableHttpClient httpClient = httpClientBuilder.build()) {
            HttpGet httpGet = new HttpGet(url.toExternalForm() + "main/?login=true");
            CloseableHttpResponse response = null;
            log.info("Performing request to: " + httpGet.toString());
            response = httpClient.execute(httpGet);
            String responseString = new BasicResponseHandler().handleResponse(response);
            //    System.out.println("======= " + responseString);
            Assert.assertTrue(response.getStatusLine().getStatusCode() == 200);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

}
