package org.jboss.additional.testsuite.jdkall.present.basic.beanvalidation;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.constraints.NotNull;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/WildflyRelease-24.0.0.Final/basic/src/main/java","modules/testcases/jdkAll/Wildfly/basic/src/main/java#10.0.0.Final*27.0.0.Beta2","modules/testcases/jdkAll/ServerBeta/basic/src/main/java","modules/testcases/jdkAll/WildflyRelease-17.0.0.Final/basic/src/main/java","modules/testcases/jdkAll/WildflyRelease-20.0.0.Final/basic/src/main/java","modules/testcases/jdkAll/Eap7Plus/basic/src/main/java","modules/testcases/jdkAll/Eap72x/basic/src/main/java","modules/testcases/jdkAll/Eap72x-Proposed/basic/src/main/java","modules/testcases/jdkAll/Protean/quarkus/protean/test-configurations/src/main/java#0.9.1*0.9.2","modules/testcases/jdkAll/Protean/quarkus/protean/test-configurations2/src/main/java#0.10.0*0.12.1","modules/testcases/jdkAll/Protean/quarkus/quark/test-configurations/src/main/java#0.13.0*999.0.1","modules/testcases/jdkAll/Protean/quarkus/quark2/test-configurations/src/main/java#999.0.0"})
@ApplicationScoped
public class GreetingService {

    public String greeting(@NotNull String name) {
        return "hello " + name;
    }

}
