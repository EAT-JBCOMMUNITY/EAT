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
import org.jboss.resteasy.annotations.Stream;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
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

   @POST
   @Path("post/thing/list")
   @Produces(MediaType.APPLICATION_JSON)
   @Stream
   public Flowable<List<Thing>> postThingList(String s) {
       return buildFlowableThingList(s, 2, 3);
   }

   static Flowable<List<Thing>> buildFlowableThingList(String s, int listSize, int elementSize) {
        return Flowable.create(
                new FlowableOnSubscribe<List<Thing>>() {

                    @Override
                    public void subscribe(FlowableEmitter<List<Thing>> emitter) throws Exception {
                        for (int i = 0; i < listSize; i++) {
                            List<Thing> list = new ArrayList<Thing>();
                            for (int j = 0; j < elementSize; j++) {
                                list.add(new Thing(s));
                            }
                            emitter.onNext(list);
                        }
                        emitter.onComplete();
                    }
                },
                BackpressureStrategy.BUFFER);
    }
}
