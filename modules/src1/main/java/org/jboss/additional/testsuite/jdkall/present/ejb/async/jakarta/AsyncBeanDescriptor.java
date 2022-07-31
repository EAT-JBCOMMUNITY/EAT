package org.jboss.additional.testsuite.jdkall.present.ejb.async;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

import jakarta.ejb.AsyncResult;
import jakarta.ejb.Asynchronous;
import jakarta.ejb.Stateless;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/ejb/src/main/java#27.0.0.Alpha4"})
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
