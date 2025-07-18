package org.jboss.additional.testsuite.jdkall.present.ejb.asynctransactions;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Remote;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.ejb.Stateless;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/Eap72x/ejb/src/main/java#7.2.7","modules/testcases/jdkAll/Eap72x-Proposed/ejb/src/main/java#7.2.7","modules/testcases/jdkAll/Eap73x/ejb/src/main/java","modules/testcases/jdkAll/Eap7Plus/ejb/src/main/java#7.3.0.GA","modules/testcases/jdkAll/WildflyRelease-24.0.0.Final/ejb/src/main/java","modules/testcases/jdkAll/Wildfly/ejb/src/main/java#19.0.0.Beta1"})
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
