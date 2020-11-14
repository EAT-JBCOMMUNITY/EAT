package org.jboss.additional.testsuite.jdkall.present.jaxrs.openkube;

import java.util.concurrent.TimeUnit;

import org.jboss.as.test.integration.common.HttpRequest;
import org.junit.Assert;
import org.junit.Test;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/OpenKube-1/jaxrs/src/main/java","modules/testcases/jdkAll/OpenKube-2/jaxrs/src/main/java"})
public class OpenkubeRestclientTestCase {

    private String performCall(String urlPattern) throws Exception {
        return HttpRequest.get(urlPattern, 10, TimeUnit.SECONDS);
    }

    @Test
    public void openkubeRestclientTest() throws Exception {
        String result = performCall(System.getenv("KUBENDPOINTURL") + "/restapp-1.0-SNAPSHOT/rest/at/test");
        Assert.assertEquals("Test AT ....", result);
    }


}
