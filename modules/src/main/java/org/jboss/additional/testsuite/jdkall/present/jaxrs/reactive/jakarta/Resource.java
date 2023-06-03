package org.jboss.additional.testsuite.jdkall.present.jaxrs.reactive;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import java.util.List;
import java.util.ArrayList;
import jakarta.ws.rs.core.MediaType;
import io.reactivex.Single;
import java.util.concurrent.CompletionStage;
import org.jboss.resteasy.rxjava2.SingleProvider;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@Path("/")
@EAT({"modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java"})
public class Resource {
   private static SingleProvider singleProvider = new SingleProvider();
   
   @SuppressWarnings("unchecked")
   @POST
   @Path("post/thing")
   @Produces(MediaType.APPLICATION_JSON)
   public CompletionStage<Thing> postThing(String s) {
      return (CompletionStage<Thing>) singleProvider.toCompletionStage(Single.just(new Thing(s)));
   }
}
