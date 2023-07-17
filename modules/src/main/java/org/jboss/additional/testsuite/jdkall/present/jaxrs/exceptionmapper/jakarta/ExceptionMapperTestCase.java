package org.jboss.additional.testsuite.jdkall.present.jaxrs.exceptionmapper;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.BadRequestException;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.junit.Assert;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@RunWith(Arquillian.class)
@RunAsClient
@EAT({"modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java#29.0.0"})
public class ExceptionMapperTestCase {

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive archive = ShrinkWrap.create(WebArchive.class, "exceptions.war")
                .addClasses(GreetingService.class,GreetingModel.class,JaxRsActivator.class,MyException.class,MyExceptionMapper.class)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

        return archive;
    }

    @ArquillianResource
    private URI serviceUri;

    @Test
    public void exceptionMapperTest() {
        try {
            final String targetUrl = serviceUri.toString() + "exceptionmapper/greeting";
            Client client = ClientBuilder.newClient();
            String response = client.target(targetUrl).request().get(String.class);
	    Assert.fail("An exception should have been thrown...");
	}catch(BadRequestException e) {
	    Assert.assertTrue(e.getResponse().readEntity(String.class).contains("MyException Error: You need an Id"));
	}
    }


}
