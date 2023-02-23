package org.jboss.additional.testsuite.jdkall.present.jaxrs.async;

import org.jboss.eap.additional.testsuite.annotations.EAT;
import org.jboss.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.container.AsyncResponse;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CompletableFuture;

@Path("/async")
@EAT({"modules/testcases/jdkAll/Eap7Plus/jaxrs/src/main/java"})
public class SimpleResource {
    private static Logger log = Logger.getLogger(SimpleResource.class.getName());

    @Context
    UriInfo uriInfo;

    @GET
    @Path("basic")
    @Produces("text/plain")
    public void getBasic(@Suspended final AsyncResponse response) throws Exception
    {
      Thread t = new Thread()
      {
         @Override
         public void run()
         {
            try
            {
               Response jaxrs = Response.ok("basic").type(MediaType.TEXT_PLAIN).build();
               response.resume(jaxrs);
            }
            catch (Exception e)
            {
               e.printStackTrace();
            }
         }
      };
      t.start();
    }

    @GET
    @Path("basicReactive")
    @Produces("text/plain")
    public CompletionStage<Response> getBasic() throws Exception
    {
      final CompletableFuture<Response> response = new CompletableFuture<>();
      Thread t = new Thread()
      {
         @Override
         public void run()
         {
            try
            {
               Response jaxrs = Response.ok("basicReactive").type(MediaType.TEXT_PLAIN).build();
               response.complete(jaxrs);
            }
            catch (Exception e)
            {
               response.completeExceptionally(e);
            }
         }
      };
      t.start();
      return response;
    }
}
