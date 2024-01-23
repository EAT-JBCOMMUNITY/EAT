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
import org.jboss.eap.additional.testsuite.annotations.ATTest;


@EAT({"modules/testcases/jdkAll/WildflyJakarta/spring/src/main/java#31.0.0.Final","modules/testcases/jdkAll/Eap7Plus/spring/src/main/java#7.4.15"})
@RunWith(Arquillian.class)
@RunAsClient
public class SpringResteasyTests {

    private final static String WARNAME = "sample-app-wildfly.war";

    @Deployment(testable = false)
    public static Archive<?> deploy() {
        WebArchive war = ShrinkWrap.create(WebArchive.class,WARNAME);
        war.addClasses(SpringResteasyTests.class,HttpRequestCommands.class);
        return war;
    }

    @ArquillianResource
    private URL url;

    private String performCall(String urlPattern) throws Exception {
        return HttpRequestCommands.get(url.toString().replace("sample-app-wildfly", "sample-app") + urlPattern, 10, TimeUnit.SECONDS);
    }

    @ATTest({"modules/testcases/jdkAll/WildflyJakarta/spring/src/main/java#31.0.0.Final"})
    public void testSpringResteasyWildfly() throws Exception {
	int iend = System.getProperty("java.version").indexOf(".");
        if ( Integer.valueOf(System.getProperty("java.version").substring(0,iend)) >= 17 ) {
            String result = performCall("rest/hello");
            Assert.assertEquals("Hello, world!", result);
	}
    }

    @ATTest({"modules/testcases/jdkAll/Eap7Plus/spring/src/main/java#7.4.15"})
    public void testSpringResteasy() throws Exception {
        String result = performCall("rest/hello");
        Assert.assertEquals("Hello, world!", result);
    }
}

