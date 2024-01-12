package org.springboot.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.jboss.eap.additional.testsuite.annotations.EAT;


/**
 * SpringBoot entry point application
 *
 * To deploy to Wildfly, this class must extend SpringBootServletInitializer.
 */
@EAT({"modules/testcases/jdkAll/Eap7plus/spring/buildapp-dir3/src/main/java","modules/testcases/jdkAll/WildflyJakarta/spring/buildapp-dir3/src/main/java"})
@SpringBootApplication
public class SpringApp extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SpringApp.class, args);
    }

}
