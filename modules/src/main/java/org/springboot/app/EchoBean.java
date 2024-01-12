package org.springboot.app;

import org.springframework.stereotype.Component;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/Eap7plus/spring/buildapp-dir3/src/main/java","modules/testcases/jdkAll/WildflyJakarta/spring/buildapp-dir3/src/main/java"})
@Component
public class EchoBean {
    public String echo(String val) {
        return val;
    }
}
