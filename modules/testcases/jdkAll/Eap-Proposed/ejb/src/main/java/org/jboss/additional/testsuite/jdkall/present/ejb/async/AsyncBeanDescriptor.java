package org.jboss.additional.testsuite.jdkall.present.ejb.async;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import org.jboss.eap.additional.testsuite.annotations.EapAdditionalTestsuite;

@EapAdditionalTestsuite({"modules/testcases/jdkAll/Eap7/ejb/src/main/java","modules/testcases/jdkAll/Wildfly-Release/ejb/src/main/java","modules/testcases/jdkAll/Wildfly/ejb/src/main/java","modules/testcases/jdkAll/Eap/ejb/src/main/java","modules/testcases/jdkAll/Eap-Proposed/ejb/src/main/java"})
@Stateless
@Asynchronous
public class AsyncBeanDescriptor {
    public static volatile boolean futureMethodCalled = false;

    public Future<Boolean> futureMethod(CountDownLatch latch) throws InterruptedException {
        futureMethodCalled = true;
        latch.countDown();
        return new AsyncResult<Boolean>(true);
    }
}
