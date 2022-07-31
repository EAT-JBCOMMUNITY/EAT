package org.jboss.additional.testsuite.jdkall.present.ejb.asynctransactions;

import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Remote;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.ejb.Stateless;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/ejb/src/main/java#27.0.0.Alpha4"})
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class AsyncTransaction implements CallerSingleton {

    @EJB
    private AsyncSingletonI asyncSingleton;

    @Override
    public void call() throws Exception {
        try {
            asyncSingleton.doAsyncAction();
            asyncSingleton.doAsyncAction();
         //   asyncSingleton.doAsyncAction();
         //   asyncSingleton.doAsyncAction();
         //   asyncSingleton.doAsyncAction();
         //   asyncSingleton.doAsyncAction();
        }catch(Exception e) {
            throw e;
        }
    }
}
