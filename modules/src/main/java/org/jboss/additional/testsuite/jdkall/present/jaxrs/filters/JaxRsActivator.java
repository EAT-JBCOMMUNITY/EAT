package org.jboss.additional.testsuite.jdkall.present.jaxrs.filters;

import org.jboss.eap.additional.testsuite.annotations.EAT;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * @author Ivo Studensky
 */
@ApplicationPath("/myjrs")
@EAT({"modules/testcases/jdkAll/Eap7Plus/jaxrs/src/main/java"})
public class JaxRsActivator extends Application {
}
