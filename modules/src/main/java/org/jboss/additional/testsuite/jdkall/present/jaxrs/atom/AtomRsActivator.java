package org.jboss.additional.testsuite.jdkall.present.jaxrs.atom;

import org.jboss.eap.additional.testsuite.annotations.EAT;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/atomjrs")
@EAT({"modules/testcases/jdkAll/Eap7Plus/jaxrs/src/main/java"})
public class AtomRsActivator extends Application {
}
