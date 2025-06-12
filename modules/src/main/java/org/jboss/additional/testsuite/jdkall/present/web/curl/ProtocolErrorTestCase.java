package org.jboss.additional.testsuite.jdkall.present.web.curl;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.File;
import io.undertow.Undertow;
import io.undertow.UndertowOptions;
import io.undertow.server.handlers.HttpContinueAcceptingHandler;
import io.undertow.server.HttpHandler;
import io.undertow.util.Headers;
import io.undertow.server.HttpServerExchange;
import org.jboss.eap.additional.testsuite.annotations.EAT;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/web/src/main/java#30.0.0","modules/testcases/jdkAll/Eap7Plus/web/src/main/java#7.4.12"})
public class ProtocolErrorTestCase {

    @Test
    public void testKeepAliveJsp() throws Exception {
        Undertow server = Undertow.builder()
                .addHttpListener(8081, "localhost")
                .setServerOption(UndertowOptions.ENABLE_HTTP2, true)
                .setHandler(
                    new HttpContinueAcceptingHandler(
                    new HttpHandler() {
                    @Override
                    public void handleRequest(final HttpServerExchange exchange) throws Exception {
                        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
                        exchange.getResponseSender().send("Hello World");
                    }
                })).build();
        server.start();
        
        ProcessBuilder pb = new ProcessBuilder("./curlprotocolerror.sh").redirectErrorStream(true);
        pb.redirectError(new File("output.txt"));
        pb.start();
        
        Thread.sleep(5000);
        
        String content = new String(Files.readAllBytes(Paths.get("output.txt")), StandardCharsets.UTF_8);
        assertTrue(!content.contains("PROTOCOL_ERROR"));
        
        server.stop();
    }

  
}
