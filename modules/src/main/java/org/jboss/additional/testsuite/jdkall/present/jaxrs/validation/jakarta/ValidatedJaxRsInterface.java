package org.jboss.additional.testsuite.jdkall.present.jaxrs.validation;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java#29.0.0"})
@Path("/validated")
public interface ValidatedJaxRsInterface {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    void getHelloGreeting(@Valid List<String> foo);

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    void getHelloGreeting(@Valid String foo);
}
