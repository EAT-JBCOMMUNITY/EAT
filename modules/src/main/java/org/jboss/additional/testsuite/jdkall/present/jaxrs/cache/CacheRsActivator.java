package org.jboss.additional.testsuite.jdkall.present.jaxrs.cache;

import org.jboss.eap.additional.testsuite.annotations.EAT;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/cachejrs")
@EAT({"modules/testcases/jdkAll/Eap73x/jaxrs/src/main/java","modules/testcases/jdkAll/Eap7Plus/jaxrs/src/main/java"})
public class CacheRsActivator extends Application {
}
