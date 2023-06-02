package org.jboss.additional.testsuite.jdkall.present.jaxrs.reactive;

import java.net.URL;
import java.util.List;
import java.util.ArrayList;

import java.util.concurrent.CompletionStage;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.client.Entity;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import jakarta.ws.rs.client.CompletionStageRxInvoker;
import org.jboss.resteasy.rxjava2.FlowableRxInvoker;
import io.reactivex.Flowable;
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

@RunWith(Arquillian.class)
@RunAsClient
@EAT({"modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java"})
public class ReactiveTestCase {

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

    private static final Entity<String> aEntity = Entity.entity("a", MediaType.TEXT_PLAIN_TYPE);

    @SuppressWarnings("unchecked")
    @Test
    public void testPostThing() throws Exception {
      ResteasyClient client = (ResteasyClient)ClientBuilder.newClient();
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
      ResteasyClient client = (ResteasyClient)ClientBuilder.newClient();
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
}
