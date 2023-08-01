package org.jboss.additional.testsuite.jdkall.present.jaxrs.reactive;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.Consumes;
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
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@Path("/")
@EAT({"modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java","modules/testcases/jdkAll/EapJakarta/jaxrs/src/main/java"})
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

    @POST
    @Path("post/string")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    @Stream
    public Observable<String> post(String s) {
        return buildObservableString(s, 1);
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
    
    static Observable<String> buildObservableString(String s, int n) {
        return Observable.create(
                new ObservableOnSubscribe<String>() {

                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        for (int i = 0; i < n; i++) {
                            emitter.onNext(s);
                        }
                        emitter.onComplete();
                    }
                });
    }

}
