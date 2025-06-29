package org.jboss.additional.testsuite.jdkall.present.jaxrs.client.resource;

import javax.ws.rs.core.Response;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/WildflyRelease-24.0.0.Final/jaxrs/src/main/java","modules/testcases/jdkAll/Wildfly/jaxrs/src/main/java#10.0.0*24.0.0","modules/testcases/jdkAll/ServerBeta/jaxrs/src/main/java","modules/testcases/jdkAll/WildflyRelease-17.0.0.Final/jaxrs/src/main/java","modules/testcases/jdkAll/WildflyRelease-20.0.0.Final/jaxrs/src/main/java","modules/testcases/jdkAll/Eap73x/jaxrs/src/main/java","modules/testcases/jdkAll/Eap7Plus/jaxrs/src/main/java","modules/testcases/jdkAll/Eap72x/jaxrs/src/main/java","modules/testcases/jdkAll/Eap72x-Proposed/jaxrs/src/main/java","modules/testcases/jdkAll/Eap71x-Proposed/jaxrs/src/main/java","modules/testcases/jdkAll/Eap71x/jaxrs/src/main/java"})
public class ApacheHttpClient43ResourceImpl implements ApacheHttpClient43Resource {
    public Response get() {
    /*    try {
            Thread.sleep(7000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }*/
        return Response.ok().build();
    }
}
