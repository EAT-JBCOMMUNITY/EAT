package org.jboss.additional.testsuite.jdkall.present.jaxrs.exceptionmapper;

import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java#29.0.0","modules/testcases/jdkAll/Eap73x/jaxrs/src/main/java","modules/testcases/jdkAll/Eap7Plus/jaxrs/src/main/java#7.4.11","modules/testcases/jdkAll/EapJakarta/jaxrs/src/main/java"})
public class GreetingModel {

    private String greeting;

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greetingText) {
        greeting = greetingText;
    }
}
