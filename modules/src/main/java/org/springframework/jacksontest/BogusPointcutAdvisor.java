package org.springframework.jacksontest;

import javax.xml.bind.annotation.XmlRootElement;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/OpenLiberty/jaxrs/src/main/java","modules/testcases/jdkAll/WildflyRelease-13.0.0.Final/jaxrs/src/main/java","modules/testcases/jdkAll/ServerBeta/jaxrs/src/main/java","modules/testcases/jdkAll/WildflyRelease-17.0.0.Final/jaxrs/src/main/java","modules/testcases/jdkAll/WildflyRelease-24.0.0.Final/jaxrs/src/main/java","modules/testcases/jdkAll/Wildfly/jaxrs/src/main/java","modules/testcases/jdkAll/WildflyRelease-20.0.0.Final/jaxrs/src/main/java","modules/testcases/jdkAll/Eap72x/jaxrs/src/main/java","modules/testcases/jdkAll/Eap72x-Proposed/jaxrs/src/main/java","modules/testcases/jdkAll/Eap73x/jaxrs/src/main/java","modules/testcases/jdkAll/Eap7Plus/jaxrs/src/main/java","modules/testcases/jdkAll/Eap71x/jaxrs/src/main/java","modules/testcases/jdkAll/Eap71x-Proposed/jaxrs/src/main/java","modules/testcases/jdkAll/Eap70x/jaxrs/src/main/java"})
@XmlRootElement
public class BogusPointcutAdvisor extends AbstractPointcutAdvisor {
    public BogusPointcutAdvisor() {
        super();
    }
}
