package org.jboss.additional.testsuite.jdkall.present.jaxrs.exceptions;

import org.jboss.eap.additional.testsuite.annotations.EAT;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/restexception")
@EAT({"modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java"})
public class JaxRsActivator extends Application {
}
