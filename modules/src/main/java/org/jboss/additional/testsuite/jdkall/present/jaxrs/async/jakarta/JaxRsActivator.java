package org.jboss.additional.testsuite.jdkall.present.jaxrs.async;

import org.jboss.eap.additional.testsuite.annotations.EAT;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * @author Ivo Studensky
 */
@ApplicationPath("/myasyncjrs")
@EAT({"modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java"})
public class JaxRsActivator extends Application {
}
