package org.jboss.additional.testsuite.jdkall.present.web.json;

import java.net.URL;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@RunWith(Arquillian.class)
@RunAsClient
@EAT({"modules/testcases/jdkAll/Eap7Plus/web/src/main/java#7.4.4", "modules/testcases/jdkAll/Wildfly/web/src/main/java#27.0.0.Alpha1*27.0.0.Alpha3"})
public class JsonSerializerTestCase {

    private static final String DEPLOYMENT = "JsonSerializerApp";

    @Deployment(name = DEPLOYMENT)
    public static WebArchive deployment() {
        final WebArchive war = ShrinkWrap.create(WebArchive.class, DEPLOYMENT + ".war");
        war.addClass(JsonSerializerServlet.class);
        war.addPackage("org.eclipse.yasson");
        war.addAsWebInfResource(new StringAsset("<?xml version=\"1.0\" encoding=\"UTF-8\"?><jboss-deployment-structure><deployment><dependencies><module name=\"org.eclipse.yasson\" slot=\"main\" services=\"import\"/><module name=\"javax.json.api\" slot=\"main\" services=\"import\"/></dependencies></deployment></jboss-deployment-structure>"), "jboss-deployment-structure.xml");
        return war;
    }

    @Test
    public void testCustomSerializer(@ArquillianResource URL url) throws Exception {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create()
                .setMaxConnPerRoute(1)
                .setMaxConnTotal(1)
                .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false));
        try (CloseableHttpClient httpClient = httpClientBuilder.build()) {
            HttpGet httpGet = new HttpGet(url.toExternalForm() + JsonSerializerServlet.URL_PATTERN);
            CloseableHttpResponse response = null;
            try {
                response = httpClient.execute(httpGet);

                Assert.assertTrue(response.getStatusLine().getStatusCode() == 200);
            } catch (Exception e) {
                e.printStackTrace();
                Assert.fail(e.getMessage());
            }
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }

    }

}
