package org.spring.testspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.jboss.eap.additional.testsuite.annotations.EAT;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@EAT({"modules/testcases/jdkAll/Eap7plus/spring/buildapp2-dir/src/main/java","modules/testcases/jdkAll/Wildfly/spring/buildapp2-dir/src/main/java","modules/testcases/jdkAll/WildflyRelease-24.0.0.Final/spring/buildapp2-dir/src/main/java"})
@SpringBootApplication
public class ExampleApplication2 extends SpringBootServletInitializer {
    private static Class<ExampleApplication2> applicationClass = ExampleApplication2.class;

    public static void main(String[] args) {
        SpringApplication.run(applicationClass, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(applicationClass);
    }
}
