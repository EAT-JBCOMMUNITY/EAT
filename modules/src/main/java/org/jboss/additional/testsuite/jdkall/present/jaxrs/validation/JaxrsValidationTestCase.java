package org.jboss.additional.testsuite.jdkall.present.jaxrs.validation;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.eap.additional.testsuite.annotations.ATTest;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.junit.Assert;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@RunWith(Arquillian.class)
@RunAsClient
@EAT({"modules/testcases/jdkAll/Wildfly/jaxrs/src/main/java#27.0.0.Alpha1*27.0.0.Alpha1","modules/testcases/jdkAll/Eap73x/jaxrs/src/main/java","modules/testcases/jdkAll/Eap7Plus/jaxrs/src/main/java#7.4.4","modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java#29.0.0","modules/testcases/jdkAll/EapJakarta/jaxrs/src/main/java"})
public class JaxrsValidationTestCase {

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive archive = ShrinkWrap.create(WebArchive.class, "validationjaxrs.war")
                .addClasses(GreetingModel.class,JaxRsActivator.class,ValidatedJaxRsInterface.class,ValidatedJaxRsInterfaceImpl.class)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource(new StringAsset("<?xml version=\"1.0\"?>\n" +
                    "<validation-config\n" +
                    "    xmlns=\"http://xmlns.jcp.org/xml/ns/validation/configuration\"\n" +
                    "    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                    "    xsi:schemaLocation=\"http://xmlns.jcp.org/xml/ns/validation/configuration\n" +
                    "            http://xmlns.jcp.org/xml/ns/validation/configuration/validation-configuration-2.0.xsd\"\n" +
                    "    version=\"2.0\">\n" +
                    "\n" +
                    "   <executable-validation enabled=\"true\">\n" +
                    "    <default-validated-executable-types>\n" +
                    "      <executable-type>CONSTRUCTORS</executable-type>\n" +
                    "      <executable-type>NON_GETTER_METHODS</executable-type>\n" +
                    "    </default-validated-executable-types>\n" +
                    "  </executable-validation>\n" +
                    "\n" +
                    "</validation-config>"), "META-INF/validation.xml");

        return archive;
    }

    @ArquillianResource
    private URI serviceUri;

    @ATTest({"modules/testcases/jdkAll/Wildfly/jaxrs/src/main/java#27.0.0","modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java#29.0.0","modules/testcases/jdkAll/Eap73x/jaxrs/src/main/java","modules/testcases/jdkAll/Eap7Plus/jaxrs/src/main/java#7.4.11"}) 
    public void jaxrsValidation() {
        final String targetUrl = serviceUri.toString() + "greeter";
        ResteasyClient client = (ResteasyClient) ResteasyClientBuilder.newClient();
        ResteasyWebTarget target = client.target(targetUrl);

        ValidatedJaxRsInterface validatedJaxRs = target.proxy(ValidatedJaxRsInterface.class);
        try {
            List<String> list = new LinkedList<>();
            validatedJaxRs.getHelloGreeting(list);
        } catch (Exception e) {
            Assert.fail("Validated jaxrs failed ... " + e.getMessage());
        }

    }

    @ATTest({"modules/testcases/jdkAll/Eap73x/jaxrs/src/main/java","modules/testcases/jdkAll/Eap7Plus/jaxrs/src/main/java#7.4.4","modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java#29.0.0","modules/testcases/jdkAll/EapJakarta/jaxrs/src/main/java"})  
    public void jaxrsSValidation() {
        final String targetUrl = serviceUri.toString() + "greeter";
        ResteasyClient client = (ResteasyClient) ResteasyClientBuilder.newClient();
        ResteasyWebTarget target = client.target(targetUrl);

        ValidatedJaxRsInterface validatedJaxRs = target.proxy(ValidatedJaxRsInterface.class);
        try {
            String s = new String();
            validatedJaxRs.getHelloGreeting(s);
        } catch (Exception e) {
            Assert.fail("Validated jaxrs failed ... " + e.getMessage());
        }

    }

}
