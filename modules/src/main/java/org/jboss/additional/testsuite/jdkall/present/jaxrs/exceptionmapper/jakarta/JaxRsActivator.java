package org.jboss.additional.testsuite.jdkall.present.jaxrs.exceptionmapper;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java#29.0.0"})
@ApplicationPath("/exceptionmapper")
public class JaxRsActivator extends Application {
}
