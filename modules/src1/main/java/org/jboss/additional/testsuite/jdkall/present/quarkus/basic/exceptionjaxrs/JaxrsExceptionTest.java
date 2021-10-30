package org.jboss.additional.testsuite.jdkall.present.quarkus.exceptionjaxrs;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Response;
import org.junit.Assert;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import org.jboss.eap.additional.testsuite.annotations.EAT;

/**
 * Test various Bean Validation operations running in Shamrock
 */

@EAT({"modules/testcases/jdkAll/Protean/quarkus/quark2/test-configurations/src/test/java#999.0.0"})
@QuarkusTest
public class JaxrsExceptionTest {

    @Test
    public void JaxrsExceptionInfo() throws InterruptedException {
        try {
            Client client = (ResteasyClient) ResteasyClientBuilder.newClient();
            Builder req = client.target("/jaxrsexception/exception/1").request();
            String outcome = req.get(String.class);
            Assert.fail("Should not reach this point ... " + outcome);
        } catch (NotFoundException e) {
            Response response = e.getResponse();
            String s = response.readEntity(String.class);
            Assert.assertFalse(s.contains("Unable to extract parameter from http req: javax.ws.rs.PathParam(\"id\") value is '1' for public"));
        }
    }

}
