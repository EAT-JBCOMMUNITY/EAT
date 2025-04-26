package org.jboss.additional.testsuite.jdk17.present.jaxrs.record;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdk17/WildflyJakarta/jaxrs/src/main/java"})
@ApplicationPath("")
public class JaxRsConfigurator extends Application {

}
