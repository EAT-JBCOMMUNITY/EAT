package org.jboss.additional.testsuite.jdkall.present.jaxrs.parameters;

import org.jboss.eap.additional.testsuite.annotations.EAT;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/restparams")
@EAT({"modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java","modules/testcases/jdkAll/EapJakarta/jaxrs/src/main/java"})
public class JaxRsActivator extends Application {
}
