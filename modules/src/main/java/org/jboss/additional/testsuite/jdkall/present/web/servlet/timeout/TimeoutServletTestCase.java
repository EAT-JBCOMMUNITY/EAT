package org.jboss.additional.testsuite.jdkall.present.web.servlet.timeout;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.net.HttpURLConnection;
import java.net.URL;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@RunAsClient
@RunWith(Arquillian.class)
@EAT({"modules/testcases/jdkAll/Eap7Plus/web/src/main/java#7.4.18","modules/testcases/jdkAll/WildflyJakarta/web/src/main/java#36.0.0"})
public class TimeoutServletTestCase {
    
    public static final int RESPONSE_CODE = 200;

    private static final String WEB_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<web-app xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://java.sun.com/xml/ns/javaee\"\n"
            + "xsi:schemaLocation=\"http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd\"\n"
            + "version=\"3.0\"\n"
            + ">\n"
            + "<display-name>TimeoutServlet</display-name>\n"
            + "<servlet>\n"
            + "<display-name>TimeoutServlet</display-name>\n"
            + "<servlet-name>TimeoutServlet</servlet-name>\n"
            + "<servlet-class>org.jboss.additional.testsuite.jdkall.present.web.servlet.timeout.TimeoutServlet</servlet-class>\n"
            + "<init-param>\n"
            + "<param-name>jboss.tx.timeout.override</param-name>\n"
            + "<param-value>5</param-value>\n"
            + "</init-param>\n"
            + "</servlet>\n"
            + "<servlet-mapping>\n"
            + "<servlet-name>TimeoutServlet</servlet-name>\n"
            + "<url-pattern>/*</url-pattern>\n"
            + "</servlet-mapping>\n"
            + "</web-app>";


    private static final String DEPLOYMENT_WEB_XML = "TimeoutServlet.war";

    @Deployment(name = DEPLOYMENT_WEB_XML, testable = false)
    public static Archive<?> getDeploymentJBossWebXml() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, DEPLOYMENT_WEB_XML)
                .addClasses(TimeoutServlet.class)
                .addAsWebInfResource(new StringAsset(WEB_XML), "web.xml");
        return war;
    }

    @Test

    @OperateOnDeployment(DEPLOYMENT_WEB_XML)
    public void timeoutServletTest(@ArquillianResource URL url) throws IOException {
        Assert.assertEquals(RESPONSE_CODE,
                getResponseCode(new URL(url.toExternalForm() + "TimeoutServlet")));
        Assert.assertEquals(RESPONSE_CODE,
                getResponseCode(new URL(url.toExternalForm() + "TimeoutServlet")));
    }
    
    private int getResponseCode(URL url) throws IOException {
        HttpURLConnection huc = (HttpURLConnection) url.openConnection();
        return huc.getResponseCode();
    }
}
