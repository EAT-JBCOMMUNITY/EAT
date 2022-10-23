package org.jboss.additional.testsuite.jdkall.present.ejb.async;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.jboss.eap.additional.testsuite.annotations.EAT;

/**
 * Bean with asynchronous methods.
 * 
 * @author Ondrej Chaloupka
 */
@Stateless
@Asynchronous
@EAT({"modules/testcases/jdkAll/WildflyRelease-13.0.0.Final/ejb/src/main/java","modules/testcases/jdkAll/Eap72x/ejb/src/main/java","modules/testcases/jdkAll/Eap72x-Proposed/ejb/src/main/java","modules/testcases/jdkAll/Eap7Plus/ejb/src/main/java","modules/testcases/jdkAll/Eap71x-Proposed/ejb/src/main/java","modules/testcases/jdkAll/Eap71x/ejb/src/main/java","modules/testcases/jdkAll/Eap7.1.0.Beta/ejb/src/main/java","modules/testcases/jdkAll/WildflyRelease-24.0.0.Final/ejb/src/main/java","modules/testcases/jdkAll/Wildfly/ejb/src/main/java#10.0.0.Final*27.0.0.Beta2","modules/testcases/jdkAll/ServerBeta/ejb/src/main/java","modules/testcases/jdkAll/WildflyRelease-17.0.0.Final/ejb/src/main/java","modules/testcases/jdkAll/WildflyRelease-20.0.0.Final/ejb/src/main/java"})
public class AsyncBeanRemote implements AsyncBeanRemoteInterface {   
    @EJB
    AsyncBean asyncBean;

    @Override
    public void asyncMethod() throws InterruptedException {
        AsyncBean.voidMethodCalled = false;
        final CountDownLatch latch = new CountDownLatch(1);
        final CountDownLatch latch2 = new CountDownLatch(1);
        asyncBean.asyncMethod(latch, latch2);
        latch.countDown();
        latch2.await();
        if(!AsyncBean.voidMethodCalled) {
            throw new IllegalArgumentException("voidMethodCalled");
        }
    }

    @Override
    public Future<Boolean> futureMethod() throws InterruptedException, ExecutionException {
        AsyncBean.futureMethodCalled = false;
        final CountDownLatch latch = new CountDownLatch(1);
        final Future<Boolean> future = asyncBean.futureMethod(latch);
        latch.countDown();
        return new AsyncResult<Boolean>(future.get());
    }  
}
