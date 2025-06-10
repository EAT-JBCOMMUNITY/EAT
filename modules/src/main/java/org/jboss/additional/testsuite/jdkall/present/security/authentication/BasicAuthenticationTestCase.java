package org.jboss.additional.testsuite.jdkall.present.security.authentication;

import org.jboss.eap.additional.testsuite.annotations.EAT;
import org.jboss.eap.additional.testsuite.annotations.ATTest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Assert;

import java.util.Base64;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
@RunAsClient
@EAT({"modules/testcases/jdkAll/Wildfly/security/src/main/java#27.0.0.Alpha1*27.0.0.Alpha3","modules/testcases/jdkAll/WildflyJakarta/security/src/main/java#27.0.0.Alpha4","modules/testcases/jdkAll/WildflyRelease-27.0.0.Final/security/src/main/java","modules/testcases/jdkAll/Eap73x/security/src/main/java","modules/testcases/jdkAll/Eap7Plus/security/src/main/java#7.4.4","modules/testcases/jdkAll/EapJakarta/security/src/main/java"})
public class BasicAuthenticationTestCase {

    @Deployment
    public static WebArchive deployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "basicauth.war");
        war.addClasses(BasicAuthenticationTestCase.class);
        war.addClasses(SecuredServlet.class);
        war.addAsWebInfResource(new StringAsset("<jboss-web><security-domain>FSAppSecurityDomain</security-domain></jboss-web>"), "jboss-web.xml");
        war.setWebXML("WEB-INF2/web.xml");
        return war;
    }

    @Test
    public void basicAuthTest(@ArquillianResource URL url) throws Exception {
        try {
            String requestUrlString = url.toExternalForm() + "/secure";
            HttpGet httpGet = new HttpGet(new URL(requestUrlString).toExternalForm());
            httpGet.addHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString(("myidentity:mypassword").getBytes()));
            try (CloseableHttpClient httpClient = HttpClients.custom()
                    .build()) {
                HttpResponse response = httpClient.execute(httpGet);
                Assert.assertEquals(HttpURLConnection.HTTP_OK, response.getStatusLine().getStatusCode());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail();
        }
    }

    @ATTest({"modules/testcases/jdkAll/Eap73x/security/src/main/java","modules/testcases/jdkAll/Eap7Plus/security/src/main/java#7.4.4"})
    public void basicAuthTest2(@ArquillianResource URL url) throws Exception {
        try {
            String requestUrlString = url.toExternalForm() + "/secure";
            HttpGet httpGet = new HttpGet(new URL(requestUrlString).toExternalForm());
            httpGet.addHeader("Authorization", "BASIC " + Base64.getEncoder().encodeToString(("myidentity:mypassword").getBytes()));
            try (CloseableHttpClient httpClient = HttpClients.custom()
                    .build()) {
                HttpResponse response = httpClient.execute(httpGet);
                Assert.assertEquals(HttpURLConnection.HTTP_OK, response.getStatusLine().getStatusCode());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail();
        }
    }

}
