package org.jboss.additional.testsuite.jdkall.present.quarkus.testBeanValidation;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import org.jboss.additional.testsuite.jdkall.present.basic.beanvalidation.custom.*;
import org.jboss.additional.testsuite.jdkall.present.basic.beanvalidation.*;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import org.jboss.eap.additional.testsuite.annotations.EapAdditionalTestsuite;

/**
 * Test various Bean Validation operations running in Shamrock
 */

@EapAdditionalTestsuite({"modules/testcases/jdkAll/Protean/quarkus/protean/test-configurations2/src/test/java#0.10.0*0.12.1","modules/testcases/jdkAll/Protean/quarkus/quark/test-configurations/src/test/java#0.13.0*999.0.1"})
@QuarkusTest
public class BeanValidationFunctionalityTest {

    @Test
    public void testBasicFeatures() throws Exception {
        StringBuilder expected = new StringBuilder();
        expected.append("failed: additionalEmails[0].<list element> (must be a well-formed email address)").append(", ")
                .append("categorizedEmails<K>[a].<map key> (length must be between 3 and 2147483647)").append(", ")
                .append("categorizedEmails[a].<map value>[0].<list element> (must be a well-formed email address)").append(", ")
                .append("email (must be a well-formed email address)").append(", ")
                .append("score (must be greater than or equal to 0)").append("\n");
        expected.append("passed");

        RestAssured.when()
                .get("/bean-validation/test/basic-features")
                .then()
                .body(is(expected.toString()));
    }

    @Test
    public void testCustomClassLevelConstraint() throws Exception {
        StringBuilder expected = new StringBuilder();
        expected.append("failed:  (invalid MyOtherBean)").append("\n");
        expected.append("passed");

        RestAssured.when()
                .get("/bean-validation/test/custom-class-level-constraint")
                .then()
                .body(is(expected.toString()));
    }

    @Test
    public void testCDIBeanMethodValidation() {
        StringBuilder expected = new StringBuilder();
        expected.append("passed").append("\n");
        expected.append("failed: greeting.arg0 (must not be null)");

        RestAssured.when()
                .get("/bean-validation/test/cdi-bean-method-validation")
                .then()
                .body(is(expected.toString()));
    }

    @Test
    public void testRestEndPointValidation() {
        RestAssured.when()
                .get("/bean-validation/test/rest-end-point-validation/plop/")
                .then()
                .statusCode(400)
                .body(containsString("numeric value out of bounds"));

        RestAssured.when()
                .get("/bean-validation/test/rest-end-point-validation/42/")
                .then()
                .body(is("42"));
    }
}
