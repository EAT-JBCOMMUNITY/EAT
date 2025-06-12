package org.jboss.additional.testsuite.jdkall.present.security.other;

import org.junit.Assert;
import org.junit.Test;
import org.picketlink.common.util.DocumentUtil;
import org.w3c.dom.Document;
import org.picketlink.common.util.StaxParserUtil;
import org.picketlink.common.util.TransformerUtil;
import org.w3c.dom.Element;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.Transformer;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stax.StAXSource;
import java.io.ByteArrayInputStream;
import org.jboss.eap.additional.testsuite.annotations.EAT;
import org.jboss.eap.additional.testsuite.annotations.ATTest;

/**
 *
 * @author rmartinc
 */
@EAT({"modules/testcases/jdkAll/Eap7Plus/security/src/main/java#7.4.10"})
public class DocumentUtilTestCase {

    @Test
    public void testMultiThread() throws InterruptedException {
        Helper[] threads = new Helper[1000];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Helper();
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
            Assert.assertNull("Exception thrown in thread " + i, threads[i].getException());
            Assert.assertNotNull("Document was not created in thread " + i, threads[i].getDocument());
        }
    }
    
   @ATTest({"modules/testcases/jdkAll/Eap73x/security/src/main/java","modules/testcases/jdkAll/Eap7Plus/security/src/main/java#7.4.12"})
   public void testMultiThread2() throws InterruptedException {
        Helper2[] threads = new Helper2[1000];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Helper2();
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
            Assert.assertNull("Exception thrown in thread " + i, threads[i].getException());
            Assert.assertNotNull("Document was not created in thread " + i, threads[i].getDocument());
        }
    }
    
   static private class Helper2 extends Thread {

        private Document doc;
        private Exception ex;
        private String xml = "<a xmlns=\'urn:a\'><b><c><d>SomeD</d></c></b></a>";

        @Override
        public void run() {
            try {
             //   this.doc = DocumentUtil.createDocument();
             ByteArrayInputStream bis = new ByteArrayInputStream(xml.getBytes());
        XMLEventReader xmlEventReader = StaxParserUtil.getXMLEventReader(bis);

        StartElement a = StaxParserUtil.getNextStartElement(xmlEventReader);
        StaxParserUtil.validate(a, "a");

        Document resultDocument = DocumentUtil.createDocument();
        DOMResult domResult = new DOMResult(resultDocument);

        // Let us parse <b><c><d> using transformer
        StAXSource source = new StAXSource(xmlEventReader);

        Transformer transformer = TransformerUtil.getStaxSourceToDomResultTransformer();
        transformer.transform(source, domResult);

        this.doc = (Document) domResult.getNode();
        Element elem = doc.getDocumentElement();
        Assert.assertEquals("b", elem.getLocalName());

        XMLEvent xmlEvent = xmlEventReader.nextEvent();
        Assert.assertTrue(xmlEvent instanceof EndElement);
        StaxParserUtil.validate((EndElement) xmlEvent, "a");
            } catch (Exception e) {
                e.printStackTrace();
                this.ex = e;
            }
        }

        public Exception getException() {
            return ex;
        }

        public Document getDocument() {
            return doc;
        }
    }

    static private class Helper extends Thread {

        private Document doc;
        private Exception ex;

        @Override
        public void run() {
            try {
                this.doc = DocumentUtil.createDocument();
            } catch (Exception e) {
                this.ex = e;
            }
        }

        public Exception getException() {
            return ex;
        }

        public Document getDocument() {
            return doc;
        }
    }
}
