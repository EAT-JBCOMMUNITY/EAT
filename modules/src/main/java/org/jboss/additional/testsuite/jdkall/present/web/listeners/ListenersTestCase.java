package org.jboss.additional.testsuite.jdkall.present.web.listeners;

import java.io.IOException;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.arquillian.container.test.api.Testable;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.http.util.EntityUtils;
import java.net.URL;
import java.io.File;
import java.io.FileInputStream;
import org.apache.commons.io.IOUtils;
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
@EAT({"modules/testcases/jdkAll/Eap7Plus/web/src/main/java#7.4.16","modules/testcases/jdkAll/WildflyJakarta/web/src/main/java#32.0.0","modules/testcases/jdkAll/EapJakarta/web/src/main/java#8.0.2"})
public class ListenersTestCase {

    private final String serverLogPath = "../../../../../servers/wildfly/build/target/jbossas/standalone/log/server.log";
    private final String serverLogPath2 = "../../../../../servers/eap7/build/target/jbossas/standalone/log/server.log";

    private static final Logger log = LoggerFactory.getLogger(ListenersTestCase.class);

    private static final String PAGE_CONTENT = "helloworld";
	
     private static final String WEB_XML = "<web-app>" +
"<listener>" +
"<listener-class>org.jboss.additional.testsuite.jdkall.present.web.listeners.CustomHttpSessionListener</listener-class>" +
"</listener>" +
"</web-app>";

    private static final String JBOSSALL_XML = "<jboss umlns=\"urn:jboss:1.0\">" +
	"<shared-session-config xmlns=\"urn:jboss:shared-session-config:1.0\">" +
		"<session-config>" +
			"<session-timeout>1</session-timeout>" +
			"<cookie-config>" +
			"	<path>/</path>" +
			"</cookie-config>" +
		"</session-config>" +
	"</shared-session-config>" +
"</jboss>";
       
    @Deployment()
    public static EnterpriseArchive getDeployment() throws IOException {
        EnterpriseArchive ear = ShrinkWrap.create(EnterpriseArchive.class, "testlisteners.ear");
        ear.addAsModule(Testable.archiveToTest(getDeployment1()));
        ear.addAsModule(getDeployment2());
        ear.addAsModule(getDeployment3());
        ear.addAsResource(new StringAsset(JBOSSALL_XML), "META-INF/jboss-all.xml");
        return ear;
    }

    public static WebArchive getDeployment1() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "app4.war");
        war.addClass(CustomHttpSessionListener.class);
        war.add(new StringAsset(PAGE_CONTENT), "hi.jsp");
        war.addAsWebInfResource(new StringAsset(WEB_XML), "web.xml");
        log.info(war.toString(true));
        return war;
    }

    public static WebArchive getDeployment2() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "app5.war");
        war.addClass(CustomHttpSessionListener.class);
        war.add(new StringAsset(PAGE_CONTENT), "hi.jsp");
        war.addAsWebInfResource(new StringAsset(WEB_XML), "web.xml");
        log.info(war.toString(true));
        return war;
    }

    public static WebArchive getDeployment3() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "app6.war");
        war.addClass(CustomHttpSessionListener.class);
        war.add(new StringAsset(PAGE_CONTENT), "hi.jsp");
        war.addAsWebInfResource(new StringAsset(WEB_XML), "web.xml");
        log.info(war.toString(true));
        return war;
    }

    @Test
    public void listenersTest(@ArquillianResource URL url) throws InterruptedException, TimeoutException {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create()
                .setMaxConnPerRoute(1)
                .setMaxConnTotal(1)
                .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false));
   System.out.println("========== " + url.toExternalForm() + "/app4/hi.jsp");
        try (CloseableHttpClient httpClient = httpClientBuilder.build()) {
            HttpGet httpGet = new HttpGet(url.toExternalForm() + "/app4/hi.jsp");
            CloseableHttpResponse response = null;
            try {
                log.info("Performing request to: " + httpGet.toString());
                response = httpClient.execute(httpGet);
                String content = EntityUtils.toString(response.getEntity());
                System.out.println("========== " + content);

                Assert.assertTrue(response.getStatusLine().getStatusCode() == 200);

                Thread.sleep(70000);

                String path = new File("").getAbsolutePath() + "/" + serverLogPath;
		File serverlogfile = new File(path);
		if(!serverlogfile.exists()) {
		    path = new File("").getAbsolutePath() + "/" + serverLogPath2;
		}

		FileInputStream inputStream = new FileInputStream(path);
		try {
		    String everything = IOUtils.toString(inputStream); 
		    System.out.println(path + "========== " + everything);
                    System.out.println("========== " + content);
		//    Assert.assertTrue(everything.contains(">>>> Destroyed Session :"));
                    everything=everything.replaceFirst("Destroyed Session :","");
                    Assert.assertFalse(everything.contains("Destroyed Session :"));         
		} finally {
		    inputStream.close();
		}
                
            } catch (HttpHostConnectException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            log.warn("The request threw an exception", e);
        }
    }


}
