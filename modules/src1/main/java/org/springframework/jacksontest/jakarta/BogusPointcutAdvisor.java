package org.springframework.jacksontest;

import jakarta.xml.bind.annotation.XmlRootElement;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java#27.0.0.Alpha4"})
@XmlRootElement
public class BogusPointcutAdvisor extends AbstractPointcutAdvisor {
    public BogusPointcutAdvisor() {
        super();
    }
}
