package org.jboss.additional.testsuite.jdkall.present.jaxrs.gzip;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.jboss.eap.additional.testsuite.annotations.EAT;
 
@ApplicationPath("/gzip")
@EAT({"modules/testcases/jdkAll/Eap73x/jaxrs/src/main/java","modules/testcases/jdkAll/Eap7Plus/jaxrs/src/main/java"})
public class GzipApplication extends Application {
 
}
