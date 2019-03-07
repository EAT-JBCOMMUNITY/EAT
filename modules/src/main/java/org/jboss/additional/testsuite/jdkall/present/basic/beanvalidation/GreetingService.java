package org.jboss.additional.testsuite.jdkall.present.basic.beanvalidation;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.constraints.NotNull;
import org.jboss.eap.additional.testsuite.annotations.EapAdditionalTestsuite;

@EapAdditionalTestsuite({"modules/testcases/jdkAll/Wildfly/basic/src/main/java","modules/testcases/jdkAll/Eap72x/basic/src/main/java","modules/testcases/jdkAll/Eap72x-Proposed/basic/src/main/java","modules/testcases/jdkAll/Protean/basic/test-configurations/src/main/java#0.9.1**0.9.1","modules/testcases/jdkAll/Protean/basic/test-configurations2/src/main/java#1.0.0"})
@ApplicationScoped
public class GreetingService {

    public String greeting(@NotNull String name) {
        return "hello " + name;
    }

}
