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
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
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

@RunWith(Arquillian.class)
@RunAsClient
@EAT({"modules/testcases/jdkAll/Eap7Plus/jaxrs/src/main/java"})
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
      ResteasyClient client = new ResteasyClientBuilder().build();
      CompletionStageRxInvoker invoker = client.target(url+"/reactive/post/thing").request().rx(CompletionStageRxInvoker.class);
      CompletionStage<Thing> completionStage = invoker.post(aEntity, Thing.class);
      Assert.assertEquals(new Thing("a"), completionStage.toCompletableFuture().get());
       
    }


}
