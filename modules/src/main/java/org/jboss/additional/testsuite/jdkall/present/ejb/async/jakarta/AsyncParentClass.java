package org.jboss.additional.testsuite.jdkall.present.ejb.async;

import jakarta.ejb.Asynchronous;
import org.jboss.eap.additional.testsuite.annotations.EAT;

/**
 * @author Ondrej Chaloupka
 */
@Asynchronous
@EAT({"modules/testcases/jdkAll/WildflyJakarta/ejb/src/main/java#27.0.0.Alpha4","modules/testcases/jdkAll/WildflyRelease-27.0.0.Final/ejb/src/main/java","modules/testcases/jdkAll/EapJakarta/ejb/src/main/java"})
public class AsyncParentClass {
    public static volatile boolean voidMethodCalled = false;
}
