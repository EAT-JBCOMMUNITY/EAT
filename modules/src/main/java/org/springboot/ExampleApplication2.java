package org.spring.testspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.jboss.eap.additional.testsuite.annotations.EAT;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@EAT({"modules/testcases/jdkAll/Wildfly/spring/buildapp-dir2/src/main/java"})
@SpringBootApplication
public class ExampleApplication2 extends SpringBootServletInitializer {
    private static Class<ExampleApplication> applicationClass = ExampleApplication2.class;

    public static void main(String[] args) {
        SpringApplication.run(applicationClass, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(applicationClass);
    }
}
