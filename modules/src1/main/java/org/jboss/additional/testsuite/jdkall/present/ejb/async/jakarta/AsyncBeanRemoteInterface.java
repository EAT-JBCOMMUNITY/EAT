package org.jboss.additional.testsuite.jdkall.present.ejb.async;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import jakarta.ejb.Remote;
import org.jboss.eap.additional.testsuite.annotations.EAT;

/**
 * @author Ondrej Chaloupka
 */
@Remote
@EAT({"modules/testcases/jdkAll/WildflyJakarta/ejb/src/main/java#27.0.0.Alpha4"})
public interface AsyncBeanRemoteInterface {
    public void asyncMethod() throws InterruptedException;
    public Future<Boolean> futureMethod() throws InterruptedException, ExecutionException;
}
