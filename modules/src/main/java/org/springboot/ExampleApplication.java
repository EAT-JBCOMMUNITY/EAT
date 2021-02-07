package org.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.jboss.eap.additional.testsuite.annotations.EAT;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
 
@SpringBootApplication
@EAT({"modules/testcases/jdkAll/Wildfly/spring/src/main/java"})
public class ExampleApplication extends SpringBootServletInitializer {
    private static Class<ExampleApplication> applicationClass = ExampleApplication.class;

    public static void main(String[] args) {
        SpringApplication.run(applicationClass, args);
    }
 
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(applicationClass);
    }
}
