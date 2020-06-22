package org.jboss.additional.testsuite.jdkall.present.jaxrs.client.resource;

import org.jboss.resteasy.spi.NoLogWebApplicationException;
import org.jboss.resteasy.util.HttpResponseCodes;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/Wildfly/jaxrs/src/main/java","modules/testcases/jdkAll/WildflyRelease-20.0.0.Final/jaxrs/src/main/java","modules/testcases/jdkAll/Eap7/jaxrs/src/main/java","modules/testcases/jdkAll/Eap72x/jaxrs/src/main/java","modules/testcases/jdkAll/Eap72x-Proposed/jaxrs/src/main/java","modules/testcases/jdkAll/Eap71x/jaxrs/src/main/java","modules/testcases/jdkAll/Eap71x-Proposed/jaxrs/src/main/java","modules/testcases/jdkAll/Eap71x/jaxrs/src/main/java"})
public class ApacheHttpClient4ResourceImpl implements ApacheHttpClient4Resource {
    public String get() {
        return "hello world";
    }

    public String error() {
        throw new NoLogWebApplicationException(HttpResponseCodes.SC_NOT_FOUND);
    }

    public String getData(String data) {
        return "Here is your string:" + data;
    }
}
