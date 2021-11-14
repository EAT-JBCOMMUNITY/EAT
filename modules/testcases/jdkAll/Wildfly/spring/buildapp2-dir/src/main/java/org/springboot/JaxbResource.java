package org.springboot;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@RestController
@EAT({"modules/testcases/jdkAll/Wildfly/spring/buildapp2-dir/src/main/java"})
public class JaxbResource {
    @RequestMapping("/restspring/{resource}")
    String Resource(@PathVariable String resource) {
             return resource;
    }
}
