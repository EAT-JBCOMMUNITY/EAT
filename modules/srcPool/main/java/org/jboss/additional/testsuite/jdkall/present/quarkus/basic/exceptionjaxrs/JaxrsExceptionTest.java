package org.jboss.additional.testsuite.jdkall.present.quarkus.exceptionjaxrs;

import io.restassured.RestAssured;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import org.jboss.eap.additional.testsuite.annotations.EAT;

/**
 * Test various Bean Validation operations running in Shamrock
 */

@EAT({"modules/testcases/jdkAll/Protean/quarkus/quark2/test-configurations2/src/test/java#999.0.0"})
@QuarkusTest
public class JaxrsExceptionTest {

    @Test
    public void JaxrsExceptionInfo() throws InterruptedException {
        RestAssured.when()
                .get("/jaxrsexception/exception/1")
                .then()
                .body(not(containsString("Unable to extract parameter from http req: javax.ws.rs.PathParam(\"id\") value is '1' for public")));
    }

}
