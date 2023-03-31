package org.jboss.additional.testsuite.jdkall.present.jaxrs.atom;

import org.jboss.eap.additional.testsuite.annotations.EAT;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;


@ApplicationPath("/atomjrs")
@EAT({"modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java#27.0.0.Alpha4"})
public class AtomRsActivator extends Application {
}
