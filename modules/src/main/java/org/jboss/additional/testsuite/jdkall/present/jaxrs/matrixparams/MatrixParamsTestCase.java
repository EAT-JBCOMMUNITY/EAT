/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.additional.testsuite.jdkall.present.jaxrs.matrixparams;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.eap.additional.testsuite.annotations.EAT;
import org.jboss.eap.additional.testsuite.annotations.ATTest;
import org.jboss.logging.Logger;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.Locale;
import java.util.stream.Collectors;

@RunWith(Arquillian.class)
@RunAsClient
@EAT({"modules/testcases/jdkAll/Eap7Plus/jaxrs/src/main/java#7.4.9","modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java#28.0.0","modules/testcases/jdkAll/EapJakarta/jaxrs/src/main/java"})
public class MatrixParamsTestCase {

    private static Logger log = Logger.getLogger(MatrixParamsTestCase.class.getName());
    private static final String CRLF = "\r\n";

    @Deployment
    public static Archive<?> deploy() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "matrixparams.war");
        war.addPackage(MatrixParamsTestCase.class.getPackage());
        return war;
    }

    @ArquillianResource
    private URL url;

    @ATTest({"modules/testcases/jdkAll/Eap7Plus/jaxrs/src/main/java#7.4.10","modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java#29.0.0"})
    public void testMatrixParams() throws Exception {
        String uri = url.getPath() + "myjaxrs/host";
        log.info("uri: " + uri);

        try (Socket client = new Socket("127.0.0.1", 8080)) {
            try (PrintWriter out = new PrintWriter(client.getOutputStream(), true)) {
                // reproduce the issue by flushing request in the middle of http request path parameter
		out.write("GET");
		out.write(" ");
		out.write("/matrixparams/rest/matrix/example;");
		out.write("foo=bar;");
		out.write("hoge=fu");
		out.flush();
		Thread.sleep(10);
		out.write("ga;");
		out.write("test=te");
		out.flush();
		Thread.sleep(10);
		out.write("st;");
		out.write(" HTTP/1.1");
		out.write(CRLF); // end of http request line

		out.write("User-Agent: TestClient" + CRLF);
		out.write("Host: 127.0.0.1:8080" + CRLF);
		out.write(CRLF);
		out.flush();
                String response = new BufferedReader(new InputStreamReader(client.getInputStream())).lines().collect(Collectors.joining("\n"));
                log.info("response: " + response);
                Assert.assertNotNull(response);
                Assert.assertTrue(response.contains("HTTP/1.1 200 OK"));
                Assert.assertTrue(response.contains("Path: example, Matrix Params {test=[test], hoge=[fuga], foo=[bar]}"));
            }catch(Exception e) {
                Assert.fail("Printwriter could not be created");
            }
        }catch(Exception ex) {
            Assert.fail("Socket could not be created");
        }
    }
    
    @Test
    public void testMatrixParam() throws Exception {
        String uri = url.getPath() + "rest/matrix;test=foobar";
        log.info("uri: " + uri);

        try (Socket client = new Socket("127.0.0.1", 8080)) {
            try (PrintWriter out = new PrintWriter(client.getOutputStream(), true)) {
                out.printf(Locale.US, "GET %s HTTP/1.1\nHost: \n\n", uri);
                String response = new BufferedReader(new InputStreamReader(client.getInputStream())).lines().collect(Collectors.joining("\n"));
                log.info("response: " + response);
                Assert.assertNotNull(response);
                Assert.assertTrue(response.contains("HTTP/1.1 200 OK"));
                Assert.assertTrue(response.contains("matrix param test = foobar"));
            }catch(Exception e) {
                Assert.fail("Printwriter could not be created");
            }
        }catch(Exception ex) {
            Assert.fail("Socket could not be created");
        }
    }
    
    @ATTest({"modules/testcases/jdkAll/Eap7Plus/jaxrs/src/main/java#7.4.19","modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java#35.0.0"})
    public void testServiceJson() throws Exception {
        String uri = url.getPath() + "rest/json";
        log.info("uri: " + uri);

        try (Socket client = new Socket("127.0.0.1", 8080)) {
            try (PrintWriter out = new PrintWriter(client.getOutputStream(), true)) {
                out.printf(Locale.US, "GET %s HTTP/1.1\nHost: \n\n", uri);
                String response = new BufferedReader(new InputStreamReader(client.getInputStream())).lines().collect(Collectors.joining("\n"));
                log.info("response: " + response);
                Assert.assertNotNull(response);
                Assert.assertTrue(response.contains("HTTP/1.1 200 OK"));
                Assert.assertTrue(response.contains("{\"result\":\"Hello World!\"}"));
            }catch(Exception e) {
                Assert.fail("Printwriter could not be created");
            }
        }catch(Exception ex) {
            Assert.fail("Socket could not be created");
        }
    }
    
    @ATTest({"modules/testcases/jdkAll/Eap7Plus/jaxrs/src/main/java#7.4.19","modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java#35.0.0"})
    public void testServiceXml() throws Exception {
        String uri = url.getPath() + "rest/xml";
        log.info("uri: " + uri);

        try (Socket client = new Socket("127.0.0.1", 8080)) {
            try (PrintWriter out = new PrintWriter(client.getOutputStream(), true)) {
                out.printf(Locale.US, "GET %s HTTP/1.1\nHost: \n\n", uri);
                String response = new BufferedReader(new InputStreamReader(client.getInputStream())).lines().collect(Collectors.joining("\n"));
                log.info("response: " + response);
                Assert.assertNotNull(response);
                Assert.assertTrue(response.contains("HTTP/1.1 200 OK"));
                Assert.assertTrue(response.contains("<xml><result>Hello World!</result></xml>"));
            }catch(Exception e) {
                Assert.fail("Printwriter could not be created");
            }
        }catch(Exception ex) {
            Assert.fail("Socket could not be created");
        }
    }

}
