package org.jboss.additional.testsuite.jdkall.present.web.security.manualh2;

import java.io.File;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.io.IOUtils;
import java.io.InputStream;
import java.io.IOException;
import java.net.HttpURLConnection;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.jboss.eap.additional.testsuite.annotations.EAT;


@RunWith(Arquillian.class)
@RunAsClient
@EAT({"modules/testcases/jdkAll/Eap73x/web/src/main/java","modules/testcases/jdkAll/Eap7Plus/web/src/main/java#7.4.11", "modules/testcases/jdkAll/WildflyJakarta/web/src/main/java#30.0.0"})
public class H2AccessTestCase {

    private static final Logger log = LoggerFactory.getLogger(H2AccessTestCase.class);
    public static final int RESPONSE_CODE = 200;

    private static final String DEPLOYMENT = "H2AccessTestCase.war";

    @Deployment(name = DEPLOYMENT, testable = false)
    public static Archive<?> getDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, DEPLOYMENT)
                .addClasses(H2Servlet.class);
        File jbossDepStruct = new File(H2AccessTestCase.class.getResource("h2/jboss-deployment-structure.xml").getFile());
        war.addAsWebInfResource(jbossDepStruct);
        return war;
    }

    @Test
    @OperateOnDeployment(DEPLOYMENT)
    public void h2ServletTest(@ArquillianResource URL url) throws IOException {
        Assert.assertTrue(getResponseContent((new URL(url.toExternalForm() + "h2"))).contains("Only java scheme is supported for JNDI lookups"));
    }
    
    private String getResponseContent(URL url) throws IOException {
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
