package org.spring.testspring;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@RestController
@EAT({"modules/testcases/jdkAll/Eap7Plus/spring/buildapp2-dir/src/main/java","modules/testcases/jdkAll/Wildfly/spring/buildapp2-dir/src/main/java","modules/testcases/jdkAll/WildflyRelease-24.0.0.Final/spring/buildapp2-dir/src/main/java"})
public class JaxbResource {
    @RequestMapping("/restspring/{resource}")
    String Resource(@PathVariable String resource) {
             return resource;
    }
}
