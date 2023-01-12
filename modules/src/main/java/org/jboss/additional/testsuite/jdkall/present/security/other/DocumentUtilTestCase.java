package org.jboss.additional.testsuite.jdkall.present.security.other;

import org.junit.Assert;
import org.junit.Test;
import org.picketlink.common.util.DocumentUtil;
import org.w3c.dom.Document;
import org.jboss.eap.additional.testsuite.annotations.EAT;

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
