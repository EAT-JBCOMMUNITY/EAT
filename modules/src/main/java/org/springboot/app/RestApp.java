package org.springboot.app;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/Eap7plus/spring/buildapp-dir3/src/main/java","modules/testcases/jdkAll/WildflyJakarta/spring/buildapp-dir3/src/main/java"})
@ApplicationPath("/rest")
public class RestApp extends Application {
}
