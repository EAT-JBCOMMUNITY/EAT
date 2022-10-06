package org.jboss.additional.testsuite.jdkall.present.quarkus.exceptionjaxrs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/Protean/quarkus/quark2/test-configurations2/src/main/java#999.0.0"})
@Path("/")
public class ExceptionJaxrsResource {

    @GET
    @Path("exception/{id}")
    public ObjectId exceptionById(@PathParam("id") ObjectId id) {
        return id;
    }

    public static class ObjectId {
        public ObjectId(String s) {
            throw new RuntimeException();
        }
    }
}
