package org.jboss.additional.testsuite.jdkall.present.jaxrs.async;

import org.jboss.eap.additional.testsuite.annotations.EAT;
import org.jboss.logging.Logger;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.container.Suspended;
import jakarta.ws.rs.container.AsyncResponse;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CompletableFuture;

@Path("/async")
@EAT({"modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java"})
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
