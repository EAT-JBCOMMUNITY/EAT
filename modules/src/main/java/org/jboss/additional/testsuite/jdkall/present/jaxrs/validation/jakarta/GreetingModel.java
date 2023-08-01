package org.jboss.additional.testsuite.jdkall.present.jaxrs.validation;

import jakarta.validation.constraints.NotNull;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java#29.0.0","modules/testcases/jdkAll/EapJakarta/jaxrs/src/main/java"})
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
