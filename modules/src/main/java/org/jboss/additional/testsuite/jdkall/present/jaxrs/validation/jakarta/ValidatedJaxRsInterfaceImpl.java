package org.jboss.additional.testsuite.jdkall.present.jaxrs.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import javax.annotation.PostConstruct;
import jakarta.ejb.Stateless;
import javax.inject.Inject;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java#29.0.0","modules/testcases/jdkAll/EapJakarta/jaxrs/src/main/java"})
@Stateless
public class ValidatedJaxRsInterfaceImpl implements ValidatedJaxRsInterface {

    private static final Logger log = LoggerFactory.getLogger(ValidatedJaxRsInterfaceImpl.class);

    @Inject
    GreetingModel greetingModel;

    @Override
    public void getHelloGreeting(List<String> foo) {
        log.info("getHelloGreeting called");
    }

    @Override
    public void getHelloGreeting(String foo) {
        log.info("getHelloGreeting called");
    }

    @PostConstruct
    private void init() {
        log.info("Post construct called");
        greetingModel.setGreeting("Hello World");
    }
}
