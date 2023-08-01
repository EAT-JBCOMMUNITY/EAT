package org.jboss.additional.testsuite.jdkall.present.jaxrs.validation;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java#29.0.0","modules/testcases/jdkAll/EapJakarta/jaxrs/src/main/java"})
@ApplicationPath("/greeter")
public class JaxRsActivator extends Application {
}
