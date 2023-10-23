package org.jboss.additional.testsuite.jdkall.present.web.jsp;

import java.io.IOException;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeoutException;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jboss.as.test.integration.management.util.CLIWrapper;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.junit.AfterClass;
import org.junit.After;
import org.junit.Assert;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@RunWith(Arquillian.class)
@RunAsClient
@EAT({"modules/testcases/jdkAll/Eap7Plus/web/src/main/java#7.4.11","modules/testcases/jdkAll/WildflyJakarta/web/src/main/java#29.0.0"})
public class ExplodedDeploymentWithJspTestCase {

    private static final Logger log = LoggerFactory.getLogger(ExplodedDeploymentWithJspTestCase.class);

    private String path = null;

    @Before
    public void deploy() throws Exception { 
        path = Paths.get("src/test/resources/example.war").toAbsolutePath().toString();
        try (CLIWrapper cli = new CLIWrapper(true)) {
            cli.sendLine(String.format("deploy " + path + " --unmanaged --force"));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void jspExplodedTest() throws InterruptedException, TimeoutException, IOException {

            try {

            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create()
                    .setMaxConnPerRoute(1)
                    .setMaxConnTotal(1)
                    .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false));
            try (CloseableHttpClient httpClient = httpClientBuilder.build()) {
                HttpGet httpGet = new HttpGet("http://localhost:8080/example/index.jsp");
                CloseableHttpResponse response = null;

                response = httpClient.execute(httpGet);
                //      String responseString = new BasicResponseHandler().handleResponse(response);
                //     System.out.println("======= " + responseString);
                Assert.assertTrue(response.getStatusLine().getStatusCode() == 200);
            } catch (Exception e) {
                fail(e.getMessage());
            }

            Path path2 = Paths.get(path + "/test/example.jsp");
            Charset charset = StandardCharsets.UTF_8;

            String content = new String(Files.readAllBytes(path2), charset);
            content = content.replaceAll("<p>example.jsp<p>", "<p>example2.jsp<p>");
            Files.write(path2, content.getBytes(charset));

            Thread.sleep(5000);

            try (CloseableHttpClient httpClient = httpClientBuilder.build()) {
                HttpGet httpGet = new HttpGet("http://localhost:8080/example/index.jsp");
                CloseableHttpResponse response = null;
                log.info("Performing request to: " + httpGet.toString());
                response = httpClient.execute(httpGet);
                String responseString = new BasicResponseHandler().handleResponse(response);
                //    System.out.println("======= " + responseString);
                Assert.assertTrue(response.getStatusLine().getStatusCode() == 200);
                Assert.assertTrue(responseString.contains("2.jsp"));
            } catch (Exception e) {
                fail(e.getMessage());
            }

        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

}
