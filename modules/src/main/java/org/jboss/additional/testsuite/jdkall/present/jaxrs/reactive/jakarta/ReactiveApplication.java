package org.jboss.additional.testsuite.jdkall.present.jaxrs.reactive;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.jboss.eap.additional.testsuite.annotations.EAT;
 
@ApplicationPath("/reactive")
@EAT({"modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java","modules/testcases/jdkAll/EapJakarta/jaxrs/src/main/java"})
public class ReactiveApplication extends Application {
 
}
