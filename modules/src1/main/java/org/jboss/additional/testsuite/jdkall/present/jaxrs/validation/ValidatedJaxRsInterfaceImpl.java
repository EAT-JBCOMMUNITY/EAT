package org.jboss.additional.testsuite.jdkall.present.jaxrs.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/Wildfly/jaxrs/src/main/java#27.0.0","modules/testcases/jdkAll/WildflyRelease-27.0.0.Final/jaxrs/src/main/java","modules/testcases/jdkAll/Eap7Plus/jaxrs/src/main/java#7.4.4"})
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
