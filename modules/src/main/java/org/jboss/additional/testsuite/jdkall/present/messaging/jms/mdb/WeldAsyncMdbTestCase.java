package org.jboss.additional.testsuite.jdkall.present.messaging.jms.mdb;

import java.io.File;
import java.io.FileInputStream;
import org.apache.commons.io.IOUtils;
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
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jboss.as.arquillian.container.ManagementClient;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.junit.Assert;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@RunWith(Arquillian.class)
@RunAsClient
@EAT({"modules/testcases/jdkAll/Eap73x/messaging/src/main/java","modules/testcases/jdkAll/Eap7Plus/messaging/src/main/java#7.4.8","modules/testcases/jdkAll/WildflyJakarta/messaging/src/main/java#27.0.0.Final"})
public class WeldAsyncMdbTestCase {

    private static final String SERVLET_DEPLOYMENT = "weldasyncmdb";
    private final String serverLogPath = "../../../../../servers/wildfly/build/target/jbossas/standalone/log/server.log";
    private final String serverLogPath2 = "../../../../../servers/eap7/build/target/jbossas/standalone/log/server.log";

    private static final Logger log = LoggerFactory.getLogger(WeldAsyncMdbTestCase.class);

    private static final String PERSISTENCEXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<persistence version=\"2.1\"\n"
            + "    xmlns=\"http://xmlns.jcp.org/xml/ns/persistence\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"
            + "    xsi:schemaLocation=\"http://xmlns.jcp.org/xml/ns/persistence http://xmlns.jcp.org/xml/ns/persistence/persistence_2_1.xsd\">\n"
            + "    \n"
            + "    <persistence-unit name=\"booking-corePU\" transaction-type=\"JTA\">\n"
            + "         <!-- CHANGE THIS DATASOURCE ACCORDINGLY TO THE ONE YOU DEFINED -->\n"
            + "         <jta-data-source>java:jboss/datasources/ExampleDS</jta-data-source>\n"
            + "         <properties>            \n"
            + "                 <!-- PROPERTIES FOR SQL LOGGING ARE (UNLUCKILY) VENDOR-SPECIFIC -->\n"
            + "                 <property name=\"hibernate.hbm2ddl.auto\" value=\"create-drop\" />\n"
            + "                 <property name=\"hibernate.show_sql\" value=\"false\" />\n"
            + "                 <property name=\"hibernate.format_sql\" value=\"false\"/>\n"
            + "                 <!-- CHANGE THIS DATASOURCE ACCORDINGLY TO THE DB YOU'RE USING -->\n"
            + "                 <property name=\"hibernate.dialect\" value=\"org.hibernate.dialect.H2Dialect\"/>\n"
            + "          </properties>\n"
            + "    </persistence-unit>\n"
            + "    \n"
            + "</persistence>";

    private static final String JBOSSWEBXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<jboss-web>\n"
            + "    <context-root>/</context-root>\n"
            + "</jboss-web>";

    @Deployment
    public static WebArchive getDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, SERVLET_DEPLOYMENT + ".war");
        war.addClass(Booking.class);
        war.addClass(BookingEvent.class);
        war.addClass(BookingProcess.class);
        war.addClass(MessageGenerator.class);
        war.addClass(MessageProducer.class);
        war.addClass(MessageReader.class);
        war.addClass(ServletTester.class);
        war.addAsWebInfResource(new StringAsset(JBOSSWEBXML), "jboss-web.xml");
        war.addAsResource(new StringAsset(PERSISTENCEXML), "META-INF/persistence.xml");
        log.info(war.toString(true));
        return war;
    }

    @Test
    public void weldAsyncMdbTest(@ArquillianResource URL url) throws InterruptedException, TimeoutException, IOException {

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create()
                .setMaxConnPerRoute(1)
                .setMaxConnTotal(1)
                .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false));
        try (CloseableHttpClient httpClient = httpClientBuilder.build()) {
            HttpGet httpGet = new HttpGet("http://localhost:8080/test");
            CloseableHttpResponse response = null;
            response = httpClient.execute(httpGet);
            String responseString = new BasicResponseHandler().handleResponse(response);
            //   System.out.println("======= " + responseString);
            String path = new File("").getAbsolutePath() + "/" + serverLogPath;
            File serverlogfile = new File(path);
            if(!serverlogfile.exists()) {
                path = new File("").getAbsolutePath() + "/" + serverLogPath2;
            }

            FileInputStream inputStream = new FileInputStream(path);
            try {
                String everything = IOUtils.toString(inputStream);
                Assert.assertFalse(everything.contains("Caused by: javax.naming.NameNotFoundException: java:comp/TransactionSynchronizationRegistry"));
            } finally {
                inputStream.close();
            }
            
        } catch (IOException ex) {
            log.warn("The request threw an exception", ex);
        }

    }

}
