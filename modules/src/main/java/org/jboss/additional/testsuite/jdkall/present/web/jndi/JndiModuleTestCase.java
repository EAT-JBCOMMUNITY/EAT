package org.jboss.additional.testsuite.jdkall.present.web.jndi;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import java.net.HttpURLConnection;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.URL;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/Eap73x/web/src/main/java","modules/testcases/jdkAll/Eap7Plus/web/src/main/java#7.4.3", "modules/testcases/jdkAll/WildflyJakarta/web/src/main/java#29.0.0"})
@RunAsClient
@RunWith(Arquillian.class)
public class JndiModuleTestCase {

    private static final String SERVLET_DEPLOYMENT = "jndi-deployment";

    @Deployment(name = SERVLET_DEPLOYMENT)
    public static Archive<?> getDeployment() {
        JavaArchive testJar = ShrinkWrap.create(JavaArchive.class, "common.jar")
                .addClasses(ApplicationConfig.class, ConfigInterface.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");

        JavaArchive testJar2 = ShrinkWrap.create(JavaArchive.class, "service.jar")
                .addClasses(AsyncModuleServlet.class, Processing.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");

        WebArchive war = ShrinkWrap.create(WebArchive.class, SERVLET_DEPLOYMENT + ".war");
        war.addClasses(JndiModuleTestCase.class);
        war.addAsLibraries(testJar);
        war.addAsLibraries(testJar2);
        war.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
        return war;
    }

    @Test
    public void testModuleJndi(@ArquillianResource URL url) throws Exception {
        String responseContext = getResponseContent(new URL(url.toExternalForm() + AsyncModuleServlet.URL_PATTERN));
        System.out.println("====== " + responseContext);
        Assert.assertEquals("Expected context does not match the actual one ...", responseContext, "lookup [java:module/ApplicationConfig!org.jboss.additional.testsuite.jdkall.present.web.jndi.ConfigInterface] : getConfigValue...");
    }
    
    private String getResponseContent(URL url) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        String encoding = conn.getContentEncoding();
        if (encoding == null) {
            encoding = "UTF-8";
        }
        int responseCode = conn.getResponseCode();
        InputStream stream;
        if (responseCode != HttpURLConnection.HTTP_OK) {
            stream = conn.getErrorStream();
        } else {
            stream = conn.getInputStream();
        }
        try {
            return IOUtils.toString(stream, encoding);
        } finally {
            IOUtils.closeQuietly(stream);
            conn.disconnect();
        }
    }
}
