package org.jboss.additional.testsuite.jdkall.present.jaxrs.gzip;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.jboss.eap.additional.testsuite.annotations.EAT;
 
@ApplicationPath("/gzip")
@EAT({"modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java"})
public class GzipApplication extends Application {
 
}
