package org.jboss.additional.testsuite.jdkall.present.clustering.concurrentexception;

import java.io.FileInputStream;
import java.io.File;
import org.apache.commons.io.IOUtils;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.commons.io.IOUtils;
import org.jboss.arquillian.container.test.api.ContainerController;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.arquillian.container.test.api.TargetsContainer;

import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jboss.arquillian.container.test.api.Deployer;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.asset.UrlAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Before;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/clustering/src/main/java#32.0.0","modules/testcases/jdkAll/Eap7Plus/clustering/src/main/java#7.4.15"})
@RunWith(Arquillian.class)
@RunAsClient
public class WebClusterTestCase {

    private static final Logger log = LoggerFactory.getLogger(WebClusterTestCase.class);

    private static final String DEPLOYMENT = "test";

    private final String serverLogPath = "../../../../../servers/wildfly/build/target/wildfly-SYNC-tcp-0/standalone/log/server.log";
    private final String serverLogPath2 = "../../../../../servers/eap7/build/target/eap7-SYNC-tcp-0/standalone/log/server.log";
    private static final String EXCEPTION_PATTERN = "java.util.ConcurrentModificationException";

    @ArquillianResource
    private static ContainerController container;

    @ArquillianResource
    private static ContainerController container2;

    private static final String WEB_XML
            = "<web-app><distributable/></web-app>";

     private static final String PAGE_CONTENT1 = "<%@page contentType=\"text/html; charset=UTF-8\" import=\"org.jboss.logging.Logger, java.util.*, java.util.concurrent.*\" %>" +
"<%" +
    "Logger logger = Logger.getLogger(\"test.jsp\");" +
    "logger.info(\"### test.jsp\");" +
    
    "response.sendRedirect(\"/test/index.html\");" +
    "try {" +
        "long duration = 60000L;" +
        "long now = System.currentTimeMillis();" +
        "long finishTime = now + duration;" +
        "logger.info(\"start loop during \" + duration + \" milliseconds\");" +
        "long count = 0;" +
        "do {" +
            "session.setAttribute(\"testAttr\" + count, count + \"Test-jsp.\");" +
            "count++;" +
            "if (count % 100 == 0) {" +
                "session.removeAttribute(\"testAttr\" + (count - 100));" +
            "}" +
            "Thread.sleep(10);" +
        "} while (System.currentTimeMillis() < finishTime);" +
        "logger.info(\"loop finished. loop count = \" + count);" +
    "} catch (Exception e) {" +
        "e.printStackTrace();" +
    "}" +
"%>";

    @ArquillianResource
    public Deployer deployer;

    @TargetsContainer("container-2")
    @Deployment(name = "container2", testable = false, managed = false)
    public static Archive<?> getDeployment1() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, DEPLOYMENT + ".war");
        war.add(new StringAsset(PAGE_CONTENT1), "test.jsp");
        war.addAsWebInfResource(new StringAsset(WEB_XML), "web.xml");
        war.add(new StringAsset("<html><body><p>test.war - index.html<p></body></html>"), "index.html");
        return war;
    }

    @TargetsContainer("container-3")
    @Deployment(name = "container3", testable = false, managed = false)
    public static Archive<?> getDeployment2() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, DEPLOYMENT + ".war");
        war.add(new StringAsset(PAGE_CONTENT1), "test.jsp");
        war.addAsWebInfResource(new StringAsset(WEB_XML), "web.xml");
        war.add(new StringAsset("<html><body><p>test.war - index.html<p></body></html>"), "index.html");
        return war;
    }


    @Test
    public void testConcurrentModificationExceptionTest() throws Exception {
        try {
            container.start("container-2");

            container2.start("container-3");

            deployer.deploy("container2");
            deployer.deploy("container3");

            container.stop("container-2");

            container2.stop("container-3");

            Thread.sleep(30000);

            container.start("container-2");

            new Thread() {
                CloseableHttpClient httpClient = HttpClientBuilder.create()
                        .build();
                HttpGet httpGet = new HttpGet(new URL("http://localhost:8180/test/test.jsp").toURI());
                HttpResponse response = httpClient.execute(httpGet);
            }.run();

            container2.start("container-3");
            Thread.sleep(60000);
            container.stop("container-2");
            container2.stop("container-3");

        } catch(Exception e) {
            e.printStackTrace();
        }

        String path = new File("").getAbsolutePath() + "/" + serverLogPath;
        File serverlogfile = new File(path);
        if(!serverlogfile.exists()) {
            path = new File("").getAbsolutePath() + "/" + serverLogPath2;
        }

        FileInputStream inputStream = new FileInputStream(path);
        try {
            String everything = IOUtils.toString(inputStream);
            Assert.assertFalse(EXCEPTION_PATTERN + " should not exist in server log...", everything.contains(EXCEPTION_PATTERN));
        } finally {
            inputStream.close();
        }
    }

}
