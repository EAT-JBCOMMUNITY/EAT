package org.jboss.additional.testsuite.jdkall.present.ejb.async;

import javax.ejb.Asynchronous;
import org.jboss.eap.additional.testsuite.annotations.EapAdditionalTestsuite;

/**
 * @author Ondrej Chaloupka
 */
@Asynchronous
@EapAdditionalTestsuite({"modules/testcases/jdkAll/Eap7/ejb/src/main/java","modules/testcases/jdkAll/Wildfly-Release/ejb/src/main/java","modules/testcases/jdkAll/Wildfly/ejb/src/main/java","modules/testcases/jdkAll/Eap64x/ejb/src/main/java","modules/testcases/jdkAll/Eap64x-Proposed/ejb/src/main/java","modules/testcases/jdkAll/Eap63x/ejb/src/main/java","modules/testcases/jdkAll/Eap62x/ejb/src/main/java"})
public class AsyncParentClass {
    public static volatile boolean voidMethodCalled = false;
}
