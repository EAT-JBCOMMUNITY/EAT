package org.remote.tests.jaxrs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/WildflyRelease-24.0.0.Final/basic/src/main/java","modules/testcases/jdkAll/Wildfly/basic/src/main/java","modules/testcases/jdkAll/ServerBeta/basic/src/main/java","modules/testcases/jdkAll/WildflyRelease-20.0.0.Final/basic/src/main/java","modules/testcases/jdkAll/Eap73x/basic/src/main/java","modules/testcases/jdkAll/Eap7Plus/basic/src/main/java","modules/testcases/jdkAll/Eap72x/basic/src/main/java","modules/testcases/jdkAll/Eap72x-Proposed/basic/src/main/java","modules/testcases/jdkAll/master/thorntail/src/main/java"})
@Path("/")
public class MyResource {

    @GET
    @Produces("text/plain")
    public String get() {
        return "Howdy at ...";
    }
}
