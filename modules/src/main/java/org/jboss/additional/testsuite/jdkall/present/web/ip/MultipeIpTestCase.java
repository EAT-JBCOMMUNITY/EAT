package org.jboss.additional.testsuite.jdkall.present.web.ip;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.regex.Pattern;
import java.net.URL;
import java.net.HttpURLConnection;

import org.apache.commons.io.input.Tailer;
import org.jboss.additional.testsuite.jdkall.present.shared.ServerLogPatternListener;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jboss.eap.additional.testsuite.annotations.EAT;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;
import static org.junit.Assert.fail;

@RunWith(Arquillian.class)
@RunAsClient
@EAT({"modules/testcases/jdkAll/Eap73x/web/src/main/java","modules/testcases/jdkAll/Eap7Plus/web/src/main/java#7.4.11","modules/testcases/jdkAll/WildflyJakarta/web/src/main/java#29.0.0"})
public class MultipeIpTestCase {

    private final static String WARNAME = "MultipeIpTestCase.war";

    @Deployment(name = "war")
    public static Archive<?> createWar() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, WARNAME);
        war.add(new StringAsset("Mulitple IP Test"), "index.html");
        war.addClasses(MultipeIpTestCase.class);
        return war;
    }

    @Test
    public void multipleIpXForwardTest(@ArquillianResource URL url) throws Exception {

        URL testUrl = new URL(url.toString() + "index.html");

        HttpGet httpGet = new HttpGet(testUrl.toURI());
        httpGet.addHeader("X-Forwarded-For", "127.0.0.2, 192.168.0.1");

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response = httpClient.execute(httpGet);
        int statusCode = response.getStatusLine().getStatusCode();

        Assert.assertEquals(HttpURLConnection.HTTP_OK, statusCode);
        EntityUtils.consume(response.getEntity());
        httpClient.close();

    }

}
