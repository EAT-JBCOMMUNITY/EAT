package org.jboss.additional.testsuite.jdkall.present.ejb.jndi.logging;

import org.jboss.eap.additional.testsuite.annotations.EAT;

import javax.ejb.Remote;
import javax.ejb.Stateless;

@Stateless(name="Hello")
@Remote(Hello.class)
@EAT({"modules/testcases/jdkAll/WildflyRelease-24.0.0.Final/ejb/src/main/java","modules/testcases/jdkAll/Wildfly/ejb/src/main/java#16.0.0.Beta1","modules/testcases/jdkAll/WildflyRelease-20.0.0.Final/ejb/src/main/java", "modules/testcases/jdkAll/Eap72x/ejb/src/main/java#7.2.2", "modules/testcases/jdkAll/Eap72x-Proposed/ejb/main/java#7.2.2","modules/testcases/jdkAll/Eap73x/ejb/src/main/java","modules/testcases/jdkAll/Eap7Plus/ejb/src/main/java"})
public class HelloBean implements Hello {
}
