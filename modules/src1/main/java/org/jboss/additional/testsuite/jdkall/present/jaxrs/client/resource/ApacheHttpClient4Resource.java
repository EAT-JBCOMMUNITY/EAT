package org.jboss.additional.testsuite.jdkall.present.jaxrs.client.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@Path("/test")
@EAT({"modules/testcases/jdkAll/WildflyRelease-24.0.0.Final/jaxrs/src/main/java","modules/testcases/jdkAll/WildflyRelease-27.0.0.Final/jaxrs/src/main/java","modules/testcases/jdkAll/Wildfly/jaxrs/src/main/java#10.0.0*24.0.0","modules/testcases/jdkAll/ServerBeta/jaxrs/src/main/java","modules/testcases/jdkAll/WildflyRelease-17.0.0.Final/jaxrs/src/main/java","modules/testcases/jdkAll/WildflyRelease-20.0.0.Final/jaxrs/src/main/java","modules/testcases/jdkAll/Eap7Plus/jaxrs/src/main/java","modules/testcases/jdkAll/Eap72x/jaxrs/src/main/java","modules/testcases/jdkAll/Eap72x-Proposed/jaxrs/src/main/java","modules/testcases/jdkAll/Eap71x-Proposed/jaxrs/src/main/java","modules/testcases/jdkAll/Eap71x/jaxrs/src/main/java"})
public interface ApacheHttpClient4Resource {
    @GET
    @Produces("text/plain")
    String get();

    @GET
    @Path("error")
    @Produces("text/plain")
    String error();

    @POST
    @Path("data")
    @Produces("text/plain")
    @Consumes("text/plain")
    String getData(String data);
}
