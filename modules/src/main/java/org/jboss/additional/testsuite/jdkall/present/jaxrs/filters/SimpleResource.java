package org.jboss.additional.testsuite.jdkall.present.jaxrs.filters;

import org.jboss.eap.additional.testsuite.annotations.EAT;
import org.jboss.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

@Path("/filter")
@EAT({"modules/testcases/jdkAll/Eap7Plus/jaxrs/src/main/java"})
public class SimpleResource {
    private static Logger log = Logger.getLogger(SimpleResource.class.getName());

    @Context
    UriInfo uriInfo;

    @GET
    @Path("getUriInfo")
    @Produces("text/plain")
    public String getUriInfo() {
        log.info("uriInfo = " + uriInfo.getRequestUri());
        return "uriInfo: " + uriInfo.getRequestUri().toString();
    }

}
