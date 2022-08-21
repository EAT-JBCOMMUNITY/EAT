package org.jboss.additional.testsuite.jdkall.present.jaxrs.emptyhostheader;

import org.jboss.eap.additional.testsuite.annotations.EAT;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * @author Ivo Studensky
 */
@ApplicationPath("/myjaxrs")
@EAT({"modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java#27.0.0.Alpha4","modules/testcases/jdkAll/WildflyRelease-27.0.0.Final/jaxrs/src/main/java"})
public class JaxRsActivator extends Application {
}
