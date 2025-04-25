package org.jboss.additional.testsuite.jdk17.present.jaxrs.record;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdk17/Eap7Plus/jaxrs/src/main/java#7.4.12"})
@ApplicationPath("")
public class JaxRsConfigurator extends Application {

}
