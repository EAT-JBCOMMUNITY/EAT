package  org.jboss.additional.testsuite.jdkall.present.shared;

import org.jboss.eap.additional.testsuite.annotations.EAT;

/**
 * @author Kabir Khan
 */
@EAT({"modules/testcases/jdkAll/WildflyRelease-13.0.0.Final/ejb/src/main/java","modules/testcases/jdkAll/Eap73x/ejb/src/main/java","modules/testcases/jdkAll/Eap7Plus/ejb/src/main/java","modules/testcases/jdkAll/Eap72x/ejb/src/main/java","modules/testcases/jdkAll/Eap72x-Proposed/ejb/src/main/java","modules/testcases/jdkAll/Eap71x-Proposed/ejb/src/main/java","modules/testcases/jdkAll/Eap71x/ejb/src/main/java","modules/testcases/jdkAll/Eap7.1.0.Beta/ejb/src/main/java","modules/testcases/jdkAll/WildflyRelease-24.0.0.Final/ejb/src/main/java","modules/testcases/jdkAll/Wildfly/ejb/src/main/java","modules/testcases/jdkAll/WildflyJakarta/ejb/src/main/java","modules/testcases/jdkAll/WildflyRelease-27.0.0.Final/ejb/src/main/java","modules/testcases/jdkAll/WildflyRelease-20.0.0.Final/ejb/src/main/java","modules/testcases/jdkAll/ServerBeta/ejb/src/main/java","modules/testcases/jdkAll/EapJakarta/ejb/src/main/java"})
public class DisableInvocationTestUtil {
    public static void disable() {
        AssumeTestGroupUtil.assumeInvocationTestsEnabled();
    }
}
