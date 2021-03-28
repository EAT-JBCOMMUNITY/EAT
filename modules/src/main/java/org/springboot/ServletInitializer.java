package org.spring.testspring;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.jboss.eap.additional.testsuite.annotations.EAT;

/** 
author : Somnath Musib
**/

@EAT({"modules/testcases/jdkAll/Wildfly/spring/buildapp-dir/src/main/java"})
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ExampleApplication.class);
    }

}
