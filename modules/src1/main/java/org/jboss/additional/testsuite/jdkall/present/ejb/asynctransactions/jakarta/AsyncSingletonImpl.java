package org.jboss.additional.testsuite.jdkall.present.ejb.asynctransactions;

import jakarta.ejb.Asynchronous;
import jakarta.ejb.Remote;
import jakarta.ejb.Singleton;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/ejb/src/main/java#27.0.0.Alpha4","modules/testcases/jdkAll/WildflyRelease-27.0.0.Final/ejb/src/main/java"})
@Singleton
@Remote(AsyncSingletonI.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
// @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class AsyncSingletonImpl implements AsyncSingletonI {

    @Override
    @Asynchronous
    // @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void doAsyncAction() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ignore) {
        }
    }

}
