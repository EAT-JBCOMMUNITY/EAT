package org.spring.testspring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.jboss.eap.additional.testsuite.annotations.EAT;

/** 
author : Somnath Musib
**/

@EAT({"modules/testcases/jdkAll/Wildfly/spring/buildapp-dir/src/main/java","modules/testcases/jdkAll/WildflyRelease-24.0.0.Final/spring/buildapp-dir/src/main/java"})
@Controller
public class IndexController {

    @GetMapping("/")
    public String index(){
        return "index";
    }
}
