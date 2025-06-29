package org.jboss.additional.testsuite.jdkall.present.jaxrs.exceptionmapper;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/Eap73x/jaxrs/src/main/java","modules/testcases/jdkAll/Eap7Plus/jaxrs/src/main/java#7.4.11"})
@ApplicationPath("/exceptionmapper")
public class JaxRsActivator extends Application {
}
