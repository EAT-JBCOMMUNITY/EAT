package org.jboss.additional.testsuite.jdkall.present.jaxrs.validation;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/Wildfly/jaxrs/src/main/java#27.0.0","modules/testcases/jdkAll/WildflyRelease-27.0.0.Final/jaxrs/src/main/java","modules/testcases/jdkAll/Eap7Plus/jaxrs/src/main/java#7.4.4"})
@ApplicationPath("/greeter")
public class JaxRsActivator extends Application {
}
