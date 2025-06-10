package org.jboss.additional.testsuite.jdkall.present.jaxrs.reactive;

import java.net.URL;
import java.util.List;
import java.util.ArrayList;

import java.util.concurrent.CompletionStage;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Response;
import io.reactivex.Flowable;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.jboss.resteasy.rxjava2.FlowableRxInvoker;
import javax.ws.rs.client.CompletionStageRxInvoker;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.TimeUnit;
import java.util.Arrays;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jboss.eap.additional.testsuite.annotations.EAT;

/**
 *  Documentation added : https://play.google.com/store/apps/details?id=edu.eatproposals.eatapp ( RESTEASY DOC / ResteasyReactive.pdf )
 **/
@RunWith(Arquillian.class)
@RunAsClient
@EAT({"modules/testcases/jdkAll/Eap73x/jaxrs/src/main/java","modules/testcases/jdkAll/Eap7Plus/jaxrs/src/main/java"})
public class ReactiveTestCase {

    private static final Entity<String> aEntity = Entity.entity("a", MediaType.TEXT_PLAIN_TYPE);

    @Deployment(testable = false)
    public static Archive<?> deploy() {
        WebArchive war = ShrinkWrap.create(WebArchive.class,"reactive.war");
        war.addClasses(ReactiveTestCase.class, Thing.class, Resource.class, ReactiveApplication.class);
        war.setManifest(new StringAsset("Manifest-Version: 1.0\n"
                + "Dependencies: org.jboss.resteasy.resteasy-rxjava2 services\n"));
        return war;
    }

    @ArquillianResource
    private URL url;

    @SuppressWarnings("unchecked")
    @Test
    public void testPostThing() throws Exception {
      ResteasyClient client = new ResteasyClientBuilder().build();
      CompletionStageRxInvoker invoker = client.target(url+"/reactive/post/thing").request().rx(CompletionStageRxInvoker.class);
      CompletionStage<Thing> completionStage = invoker.post(aEntity, Thing.class);
      Assert.assertEquals(new Thing("a"), completionStage.toCompletableFuture().get());
       
    }

   @SuppressWarnings("unchecked")
   @Test
   public void testPostThingList() throws Exception {
      CountDownLatch latch = new CountDownLatch(1);
      AtomicInteger errors = new AtomicInteger(0);
      List<Thing>  aThingList =  new ArrayList<Thing>();
      List<List<Thing>> aThingListList = new ArrayList<List<Thing>>();
      ArrayList<List<?>> thingListList = new ArrayList<List<?>>();
      for (int i = 0; i < 3; i++) {aThingList.add(new Thing("a"));}
      for (int i = 0; i < 2; i++) {aThingListList.add(aThingList);}
      GenericType<List<Thing>> LIST_OF_THING = new GenericType<List<Thing>>() {};
      ResteasyClient client = new ResteasyClientBuilder().build();
      FlowableRxInvoker invoker = client.target(url+"/reactive/post/thing/list").request().rx(FlowableRxInvoker.class);
      Flowable<List<Thing>> flowable = (Flowable<List<Thing>>) invoker.post(aEntity, LIST_OF_THING);
      flowable.subscribe(
         (List<?> l) -> thingListList.add(l),
         (Throwable t) -> errors.incrementAndGet(),
         () -> latch.countDown());
      boolean waitResult = latch.await(30, TimeUnit.SECONDS);
      Assert.assertTrue("Waiting for event to be delivered has timed out.", waitResult);
      Assert.assertEquals(0, errors.get());
      Assert.assertEquals(aThingListList, thingListList);
   }
   
   @Test
   public void testPost() throws Exception {
        String aString = "data: a";

        ResteasyClient client = (ResteasyClient)ClientBuilder.newClient();
        Builder request = client.target(url + "/reactive/post/string").request();
        Response response = request.post(aEntity);
        Assert.assertTrue(response.readEntity(String.class).contains(aString));
   }
}
