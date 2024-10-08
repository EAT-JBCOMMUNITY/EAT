package org.spring.testspring;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.jboss.eap.additional.testsuite.annotations.EAT;

/** 
author : Somnath Musib
**/

@EAT({"modules/testcases/jdkAll/Eap7Plus/spring/buildapp-dir/src/main/java","modules/testcases/jdkAll/Wildfly/spring/buildapp-dir/src/main/java","modules/testcases/jdkAll/WildflyRelease-24.0.0.Final/spring/buildapp-dir/src/main/java"})
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ExampleApplication.class);
    }

}
