package org.jboss.additional.testsuite.jdkall.present.web.websockets;

import java.io.IOException;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.concurrent.TimeoutException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.junit.Assert;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import java.io.File;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@RunWith(Arquillian.class)
@RunAsClient
@EAT({"modules/testcases/jdkAll/Eap7Plus/web/src/main/java#7.4.9","modules/testcases/jdkAll/WildflyJakarta/web/src/main/java#28.0.0"})
public class WebSocketsTestCase {

    private static final String WAR_DEPLOYMENT = "websockets";

    private static final Logger log = LoggerFactory.getLogger(WebSocketsTestCase.class);

     private static final String PAGE_CONTENT = "<html>"
            + "<head>"
            + "<meta http-equiv=\"content-type\" content=\"text/html; charset=ISO-8859-1\">"
            + "</head>"
            + "<body onload='send_message()'>"
            + "<meta charset=\"utf-8\">"
            + "<title>HelloWorld Web sockets</title>"
            + "<script language=\"javascript\" type=\"text/javascript\">"
            + "var wsUri = getRootUri() + \"/websockets/hello\";"
            + "function getRootUri() {"
            + "    return \"ws://\" + (document.location.hostname == \"\" ? \"localhost\" : document.location.hostname) + \":\" + "
            + "            (document.location.port == \"\" ? \"8080\" : document.location.port);"
            + "}"
            + "function init() {"
            + "    output = document.getElementById(\"output\");"
            + "}"
            + "function send_message() {"
            + "    websocket = new WebSocket(wsUri);"
            + "    websocket.onopen = function(evt) {"
            + "        onOpen(evt)"
            + "    };"
            + "    websocket.onmessage = function(evt) {"
            + "        onMessage(evt); var now = new Date().getTime(); while(new Date().getTime() < now + 30000){ }"
            + "    };"
            + "    websocket.onerror = function(evt) {"
            + "        onError(evt)"
            + "    };"
            + "    websocket.onclose = function(evt) {"
            + "        onClose(evt)"
            + "    };"
            + "}"
            + "function onOpen(evt) {"
            + "    writeToScreen(\"Connected to Endpoint!\");"
            + "    doSend(textID.value);"
            + "}"
            + "function onClose(evt) {"
            + "    writeToScreen(\"Closed Endpoint!\");"
            + "}"
            + "function onMessage(evt) {"
            + "    writeToScreen(\"Message Received: \" + evt.data);"
            + "}"
            + "function onError(evt) {"
            + "    writeToScreen('<span style=\"color: red;\">ERROR:</span> ' + evt.data);"
            + "}"
            + "function doSend(message) {"
            + "    writeToScreen(\"Message Sent: \" + message);"
            + "    websocket.send(message);"
            + "}"
            + "function writeToScreen(message) {"
            + "    var pre = document.createElement(\"p\");"
            + "    pre.style.wordWrap = \"break-word\";"
            + "    pre.innerHTML = message;"
            + "    output.appendChild(pre);"
            + "}"
            + "window.addEventListener(\"load\", init, false);"
            + "</script>"
            + "<h1 style=\"text-align: center;\">Hello World WebSocket Client</h2>"
            + "<br>"
            + "<div style=\"text-align: center;\">"
            + "<form action=\"\">"
            + "    <input onclick=\"send_message()\" value=\"Send\" type=\"button\">"
            + "    <input id=\"textID\" name=\"message\" value=\"Hello WebSocket!\" type=\"text\"><br>"
            + "</form>"
            + "</div>"
            + "<div id=\"output\"></div>"
            + "</body>"
            + "</html>";

     private static final String WEBXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<web-app xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://java.sun.com/xml/ns/javaee\" xsi:schemaLocation=\"http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd\" id=\"WebApp_ID\" version=\"2.5\">\n"
            + "</web-app>";

    @Deployment
    public static WebArchive getDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, WAR_DEPLOYMENT + ".war");
        war.addClass(HelloWorldEndpoint.class);
        war.add(new StringAsset(PAGE_CONTENT), "index.html");
        war.addAsWebInfResource(new StringAsset(WEBXML), "web.xml");
        log.info(war.toString(true));
        war.as(ZipExporter.class).exportTo(new File("/home/psotirop/websockets.war"), true);
        return war;
    }

    @Test
    public void jspJastowTest(@ArquillianResource URL url) throws InterruptedException, TimeoutException {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create()
                .setMaxConnPerRoute(1)
                .setMaxConnTotal(1)
                .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false));
        try (CloseableHttpClient httpClient = httpClientBuilder.build()) {
            HttpGet httpGet = new HttpGet(url.toExternalForm() + "index.html");
            CloseableHttpResponse response = null;
            try {
                log.info("Performing request to: " + httpGet.toString());
                response = httpClient.execute(httpGet);

                Assert.assertTrue(response.getStatusLine().getStatusCode() == 200);
            } catch (HttpHostConnectException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            log.warn("The request threw an exception", e);
        }
    }

}
