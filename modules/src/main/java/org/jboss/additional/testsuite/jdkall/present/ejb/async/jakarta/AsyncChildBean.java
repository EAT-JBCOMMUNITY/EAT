package org.jboss.additional.testsuite.jdkall.present.ejb.async;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import jakarta.ejb.Stateless;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@Stateless
@EAT({"modules/testcases/jdkAll/WildflyJakarta/ejb/src/main/java#27.0.0.Alpha4","modules/testcases/jdkAll/WildflyRelease-27.0.0.Final/ejb/src/main/java","modules/testcases/jdkAll/EapJakarta/ejb/src/main/java"})
public class AsyncChildBean extends AsyncParentClass {

    public void asyncMethod(CountDownLatch latch, CountDownLatch latch2) throws InterruptedException {
        latch.await(5, TimeUnit.SECONDS);
        voidMethodCalled = true;
        latch2.countDown();
    }
    
}
