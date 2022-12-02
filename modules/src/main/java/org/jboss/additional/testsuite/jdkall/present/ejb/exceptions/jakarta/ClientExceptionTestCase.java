package org.jboss.additional.testsuite.jdkall.present.ejb.exceptions;

import org.jboss.eap.additional.testsuite.annotations.EAT;


import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;

import jakarta.transaction.Synchronization;
import jakarta.transaction.TransactionSynchronizationRegistry;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
/**
 * @author bmaxwell
 *
 */

@RunWith(Arquillian.class)
@EAT({"modules/testcases/jdkAll/WildflyJakarta/ejb/src/main/java#27.0.0.Alpha4","modules/testcases/jdkAll/WildflyRelease-27.0.0.Final/ejb/src/main/java","modules/testcases/jdkAll/EapJakarta/ejb/src/main/java"})
public class ClientExceptionTestCase {

    @EJB
    Hello bean;

    private static final String ARCHIVE_NAME = "ClientExceptionTestCase";

    @Deployment
    public static Archive<?> deploy() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class, ARCHIVE_NAME + ".jar");
        jar.addClasses(ClientExceptionTestCase.class, Hello.class, HelloBean.class, MyApplicationExceptionNoRollbackRuntimeException.class);
        return jar;
    }

    @Test
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void checkException() {
	try {
	    bean.hello();
            fail("Exception should have been through already...");
	} catch(Exception ex) {
	    if(ex instanceof EJBException == false)
                fail("Exception should be different...");
	} 
    }
}
