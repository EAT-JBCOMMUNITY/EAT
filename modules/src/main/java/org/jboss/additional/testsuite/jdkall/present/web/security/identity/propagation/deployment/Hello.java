package org.jboss.additional.testsuite.jdkall.present.web.security.identity.propagation.deployment;

import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/WildflyRelease-24.0.0.Final/web/src/main/java","modules/testcases/jdkAll/Wildfly/web/src/main/java#15.0.0.Beta1","modules/testcases/jdkAll/WildflyRelease-20.0.0.Final/web/src/main/java", "modules/testcases/jdkAll/Eap72x/web/src/main/java#7.2.1", "modules/testcases/jdkAll/Eap72x-Proposed/web/src/main/java#7.2.1","modules/testcases/jdkAll/Eap73x/web/src/main/java","modules/testcases/jdkAll/Eap7Plus/web/src/main/java"})
public interface Hello {

    void call();
}
