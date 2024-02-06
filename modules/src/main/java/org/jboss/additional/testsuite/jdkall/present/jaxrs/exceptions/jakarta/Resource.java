package org.jboss.additional.testsuite.jdkall.present.jaxrs.exceptions;

import org.jboss.eap.additional.testsuite.annotations.EAT;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Path;

@Path("/")
@EAT({"modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java"})
public class Resource {

    @GET
    @Path("test/{id}")
    public ObjectId findById(@PathParam("id") ObjectId id) {
        return id;
    }

    public static class ObjectId {
        public ObjectId(String s) {
            throw new RuntimeException();
        }
    }
}
