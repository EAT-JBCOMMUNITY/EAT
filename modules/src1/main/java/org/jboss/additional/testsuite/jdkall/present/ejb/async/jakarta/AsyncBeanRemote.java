package org.jboss.additional.testsuite.jdkall.present.ejb.async;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import jakarta.ejb.AsyncResult;
import jakarta.ejb.Asynchronous;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import org.jboss.eap.additional.testsuite.annotations.EAT;

/**
 * Bean with asynchronous methods.
 * 
 * @author Ondrej Chaloupka
 */
@Stateless
@Asynchronous
@EAT({"modules/testcases/jdkAll/WildflyJakarta/ejb/src/main/java#27.0.0.Alpha4"})
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
