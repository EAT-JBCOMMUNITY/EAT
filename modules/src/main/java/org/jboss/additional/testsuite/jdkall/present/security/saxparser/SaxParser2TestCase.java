package org.jboss.additional.testsuite.jdkall.present.security.saxparser;

import org.jboss.eap.additional.testsuite.annotations.EAT;


import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.dom4j.io.SAXReader;
import org.dom4j.Document;
import java.io.StringReader;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import  org.hibernate.internal.util.xml.XMLHelper;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
@RunAsClient
@EAT({"modules/testcases/jdkAll/Wildfly/security/src/main/java#20.0.0.Beta1*20.0.0.Final","modules/testcases/jdkAll/Eap72x/security/src/main/java#7.2.9","modules/testcases/jdkAll/Eap72x-Proposed/security/src/main/java#7.2.9","modules/testcases/jdkAll/Eap73x/security/src/main/java","modules/testcases/jdkAll/Eap7Plus/security/src/main/java#7.3.2*7.3.2"})
public class SaxParser2TestCase {

    private static final String ARCHIVE_NAME = "SaxParser2TestCase";
    private final String txt = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n" +
                "<!DOCTYPE hello [\n" +
                "<!ELEMENT hello ANY >\n" +
                "<!ENTITY external SYSTEM \"http://localhost:8111\" >]><hello>&external;</hello>";

    @Deployment
    public static Archive<?> deploy() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class, ARCHIVE_NAME + ".jar");
        jar.addClass(SaxParser2TestCase.class);
        return jar;
    }

    @Test
    public void testSaxParserHHH() throws Exception {
        XMLHelper xh = new XMLHelper();
        SAXReader reader = xh.createSAXReader(null, null);
        reader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        reader.setFeature("http://xml.org/sax/features/external-general-entities", false);
        reader.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        try {
            Document d = reader.read(new StringReader(txt));
        } catch (Exception e) {
            fail("external entity");
        }
    }
}
