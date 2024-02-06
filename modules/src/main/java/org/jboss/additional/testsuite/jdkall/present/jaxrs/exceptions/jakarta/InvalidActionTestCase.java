package org.jboss.additional.testsuite.jdkall.present.jaxrs.exceptions;

import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.eap.additional.testsuite.annotations.EAT;
import org.jboss.eap.additional.testsuite.annotations.ATTest;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import jakarta.ws.rs.client.Client;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import jakarta.ws.rs.client.Invocation.Builder;


@RunWith(Arquillian.class)
@RunAsClient
@EAT({"modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java#31.0.0"})
public class InvalidActionTestCase {

    @Deployment
    public static Archive<?> deploy() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "exception.war");
        war.addPackage(InvalidActionTestCase.class.getPackage());
        return war;
    }

    @Test
    public void throwsNotFoundExceptionNotAppropriateInfo(@ArquillianResource URL url) throws InterruptedException {
            try {
            	Client client = (ResteasyClient) ResteasyClientBuilder.newClient();
            	Builder request = client.target(url.toExternalForm() + "restexception/path/1").request();
            	String result = request.get(String.class);
            	Assert.fail("Exception should have been thrown for result... " + result);
            } catch (Exception e) {
		Assert.assertTrue(e.getMessage().toString().contains("Not Found"));
            }
    }

}
