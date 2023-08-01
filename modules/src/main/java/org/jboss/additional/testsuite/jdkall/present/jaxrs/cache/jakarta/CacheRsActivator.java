package org.jboss.additional.testsuite.jdkall.present.jaxrs.cache;

import org.jboss.eap.additional.testsuite.annotations.EAT;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/cachejrs")
@EAT({"modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java#27.0.0.Alpha4","modules/testcases/jdkAll/EapJakarta/jaxrs/src/main/java"})
public class CacheRsActivator extends Application {
}
