package org.jboss.additional.testsuite.jdkall.present.web.ee.services;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.eap.additional.testsuite.annotations.EAT;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.io.File;
import java.io.FileInputStream;
import org.apache.commons.io.IOUtils;
import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
@RunAsClient
@EAT({"modules/testcases/jdkAll/Eap7Plus/web/src/main/java#7.4.10","modules/testcases/jdkAll/WildflyJakarta/web/src/main/java#28.0.0"})
public class FactoryFinderCacheTestCase {

    @Deployment
    public static WebArchive deployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, FactoryFinderCacheTestCase.class.getSimpleName() + ".war");
        war.addAsWebInfResource(new StringAsset("# testing comment1\n" +
              "# testing comment2\n" +
              "com.class.Factory"), "classes/META-INF/services/javax.el.ExpressionFactory");
        return war;
    }

    private static final File serverLog = new File(System.getProperty("jboss.dist"), "standalone" + File.separator + "log"
            + File.separator + "server.log");

    @Test
    public void testExpressionFactory() throws Exception {
        FileInputStream inputStream = new FileInputStream(serverLog.getAbsolutePath());
        try {
            String everything = IOUtils.toString(inputStream);
            assertTrue("testExpressionFactory test fails ...", !everything.contains("# testing comment1 not found"));
        } finally {
            inputStream.close();
        }
    }

}
