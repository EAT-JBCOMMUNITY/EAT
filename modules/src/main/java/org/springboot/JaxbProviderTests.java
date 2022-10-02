package org.springboot;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jboss.eap.additional.testsuite.annotations.EAT;


@EAT({"modules/testcases/jdkAll/Wildfly/spring/src/main/java#23.0.0.Final*27.0.0.Alpha2","modules/testcases/jdkAll/WildflyRelease-24.0.0.Final/spring/src/main/java","modules/testcases/jdkAll/ServerBeta/spring/src/main/java#21.0.0","modules/testcases/jdkAll/Eap7Plus/spring/src/main/java#7.3.5"})
@RunWith(Arquillian.class)
@RunAsClient
public class JaxbProviderTests {

    private final static String WARNAME = "arquillian-managed.war";

    @Deployment(testable = false)
    public static Archive<?> deploy() {
        WebArchive war = ShrinkWrap.create(WebArchive.class,WARNAME);
        war.addClasses(JaxbProviderTests.class,HttpRequestCommands.class);
        return war;
    }

    @ArquillianResource
    private URL url;

    private String performCall(String urlPattern) throws Exception {
        return HttpRequestCommands.get(url.toString().replace("arquillian-managed","additional-testsuite-spring-buildapp2-2.4.4") + urlPattern, 10, TimeUnit.SECONDS);
    }

    @Test
    public void testJaxRs() throws Exception {
        String result = performCall("restspring/resource");
        Assert.assertEquals("resource", result);
    }

}

