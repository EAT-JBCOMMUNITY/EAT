package org.jboss.additional.testsuite.jdkall.present.quarkus.exceptionjaxrs;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/Protean/quarkus/quark2/test-configurations2/src/main/java#999.0.0"})
@ApplicationPath("/jaxrsexception")
public class ExceptionJaxrsApplication extends Application {

}
