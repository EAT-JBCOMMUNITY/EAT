package org.springboot;

import java.net.URL;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;
import java.io.InputStreamReader;

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

    private final static String WARNAME = "testspring2.war";

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
        HTMLEditorKit htmlKit = new HTMLEditorKit();
        HTMLDocument htmlDoc = (HTMLDocument) htmlKit.createDefaultDocument();
        HTMLEditorKit.Parser parser = new ParserDelegator();
        parser.parse(new InputStreamReader(new URL("http://"+url.getHost()+":"+url.getPort()+url.getPath().substring(0, url.getPath().length()-2)).openStream()), htmlDoc.getReader(0), true);

        Assert.assertEquals("Spring Boot Wildfly", htmlDoc.getProperty("title"));

    }

}
