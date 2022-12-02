package org.jboss.additional.testsuite.jdkall.present.jaxrs.openkube;

import java.util.concurrent.TimeUnit;

import org.jboss.as.test.integration.common.HttpRequest;
import org.junit.Assert;
import org.junit.Test;
import org.jboss.eap.additional.testsuite.annotations.EAT;

/**
** The current test case tests the app https://github.com/EAT-JBCOMMUNITY/app deployed on a server on various openkube clusters.
** These clusters should be made available in order for the app to be tested, e.g. via exposing the KUBENDPOINTURL.
**/
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
