package org.jboss.additional.testsuite.jdkall.present.jaxrs.yaml;

import org.jboss.eap.additional.testsuite.annotations.EAT;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * @author Ivo Studensky
 */
@ApplicationPath("/myaml")
@EAT({"modules/testcases/jdkAll/Eap73x/jaxrs/src/main/java","modules/testcases/jdkAll/Eap7Plus/jaxrs/src/main/java"})
public class JaxRsActivator extends Application {
}
