package org.springboot;

import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

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


@EAT({"modules/testcases/jdkAll/Wildfly/spring/src/main/java"})
@RunWith(Arquillian.class)
@RunAsClient
public class SpringTests {

    private final static String WARNAME = "arquillian-managed.war";

    @Deployment(testable = false)
    public static Archive<?> deploy() {
        WebArchive war = ShrinkWrap.create(WebArchive.class,WARNAME);
        war.addClasses(SpringTests.class);
        return war;
    }

    @ArquillianResource
    private URL url;

    @Test
    public void testSpring() throws Exception {
       
    }

}
