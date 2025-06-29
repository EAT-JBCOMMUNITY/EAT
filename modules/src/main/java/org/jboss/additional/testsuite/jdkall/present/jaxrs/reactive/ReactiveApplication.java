package org.jboss.additional.testsuite.jdkall.present.jaxrs.reactive;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.jboss.eap.additional.testsuite.annotations.EAT;
 
@ApplicationPath("/reactive")
@EAT({"modules/testcases/jdkAll/Eap73x/jaxrs/src/main/java","modules/testcases/jdkAll/Eap7Plus/jaxrs/src/main/java"})
public class ReactiveApplication extends Application {
 
}
