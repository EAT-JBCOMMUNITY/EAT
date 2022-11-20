package org.jboss.additional.testsuite.jdkall.present.jaxrs.validation;

import javax.validation.constraints.NotNull;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/Wildfly/jaxrs/src/main/java#27.0.0","modules/testcases/jdkAll/Eap7Plus/jaxrs/src/main/java#7.4.4"})
public class GreetingModel {

    @NotNull
    private String greeting;

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greetingText) {
        greeting = greetingText;
    }
}
