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
package org.jboss.additional.testsuite.jdkall.present.jaxrs.parameters;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.eap.additional.testsuite.annotations.EAT;
import org.jboss.eap.additional.testsuite.annotations.ATTest;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 *  Documentation added : https://play.google.com/store/apps/details?id=edu.eatproposals.eatapp ( RESTEASY DOC / ResteasyParams.pdf )
 **/
@RunWith(Arquillian.class)
@RunAsClient
@EAT({"modules/testcases/jdkAll/Eap7Plus/jaxrs/src/main/java#7.4.11","modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java#29.0.0","modules/testcases/jdkAll/EapJakarta/jaxrs/src/main/java"})
public class ParamsTestCase {

    private static final String CRLF = "\r\n";

    @Deployment
    public static Archive<?> deploy() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "params.war");
        war.addPackage(ParamsTestCase.class.getPackage());
        return war;
    }

    @Test
    public void testFormParams() throws Exception {
        HttpClient client = HttpClient.newHttpClient();

	HttpRequest request = HttpRequest.newBuilder()
	    .uri(URI.create("http://localhost:8080/params/restparams/form"))
	    .POST(BodyPublishers.ofString("email=user@email.com&password=mypassword"))
	    .setHeader("Content-Type", "application/x-www-form-urlencoded")
	    .build();

	HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assert.assertNotNull(response.body());
        Assert.assertTrue(response.body().contains("user@email.com"));
        Assert.assertTrue(response.body().contains("mypassword"));
    }
    
    @Test
    public void testPathParams() throws Exception {

        try (Socket client = new Socket("127.0.0.1", 8080)) {
            try (PrintWriter out = new PrintWriter(client.getOutputStream(), true)) {
                // reproduce the issue by flushing request in the middle of http request path parameter
		out.write("GET");
		out.write(" ");
		out.write("/params/restparams/path/11");
		out.write(" HTTP/1.1");
		out.write(CRLF); // end of http request line
		out.write("User-Agent: TestClient" + CRLF);
		out.write("Host: 127.0.0.1:8080" + CRLF);
		out.write(CRLF);
		out.flush();
                String response = new BufferedReader(new InputStreamReader(client.getInputStream())).lines().collect(Collectors.joining("\n"));
                Assert.assertNotNull(response);
                Assert.assertTrue(response.contains("HTTP/1.1 200 OK"));
                Assert.assertTrue(response.contains("Id is 11"));
            }catch(Exception e) {
                Assert.fail("Printwriter could not be created");
            }
        }catch(Exception ex) {
            Assert.fail("Socket could not be created");
        }
    }
    
    @Test
    public void testQueryParams() throws Exception {

        try (Socket client = new Socket("127.0.0.1", 8080)) {
            try (PrintWriter out = new PrintWriter(client.getOutputStream(), true)) {
                // reproduce the issue by flushing request in the middle of http request path parameter
		out.write("GET");
		out.write(" ");
		out.write("/params/restparams/query?id=11");
		out.write(" HTTP/1.1");
		out.write(CRLF); // end of http request line
		out.write("User-Agent: TestClient" + CRLF);
		out.write("Host: 127.0.0.1:8080" + CRLF);
		out.write(CRLF);
		out.flush();
                String response = new BufferedReader(new InputStreamReader(client.getInputStream())).lines().collect(Collectors.joining("\n"));
                Assert.assertNotNull(response);
                Assert.assertTrue(response.contains("HTTP/1.1 200 OK"));
                Assert.assertTrue(response.contains("Id is 11"));
            }catch(Exception e) {
                Assert.fail("Printwriter could not be created");
            }
        }catch(Exception ex) {
            Assert.fail("Socket could not be created");
        }
    }
    
    @Test
    public void testHeaderParams() throws Exception {

        try (Socket client = new Socket("127.0.0.1", 8080)) {
            try (PrintWriter out = new PrintWriter(client.getOutputStream(), true)) {
                // reproduce the issue by flushing request in the middle of http request path parameter
		out.write("GET");
		out.write(" ");
		out.write("/params/restparams/header");
		out.write(" HTTP/1.1");
		out.write(CRLF); // end of http request line
		out.write("User-Agent: TestClient" + CRLF);
		out.write("Host: 127.0.0.1:8080" + CRLF);
		out.write(CRLF);
		out.flush();
                String response = new BufferedReader(new InputStreamReader(client.getInputStream())).lines().collect(Collectors.joining("\n"));
                Assert.assertNotNull(response);
                Assert.assertTrue(response.contains("HTTP/1.1 200 OK"));
                Assert.assertTrue(response.contains("Browser is"));
            }catch(Exception e) {
                Assert.fail("Printwriter could not be created");
            }
        }catch(Exception ex) {
            Assert.fail("Socket could not be created");
        }
    }
    
    @Test
    public void testCookieParams() throws Exception {
        HttpClient client = HttpClient.newHttpClient();

	HttpRequest request = HttpRequest.newBuilder()
	    .uri(URI.create("http://localhost:8080/params/restparams/session"))
	    .GET()
	    .setHeader("Cookie", "JSESSIONID=12345")
	    .build();

	HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	Assert.assertNotNull(response.body());
        Assert.assertTrue(response.body().contains("Browser is 12345"));

    }
    
    @Test
    public void testHttpHeadersParams() throws Exception {
        HttpClient client = HttpClient.newHttpClient();

	HttpRequest request = HttpRequest.newBuilder()
	    .uri(URI.create("http://localhost:8080/params/restparams/httpheaders"))
	    .GET()
	    .setHeader("Cookie", "Pragma=no-cache;Keep-Alive=300")
	    .build();

	HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	Assert.assertNotNull(response.body());
        Assert.assertTrue(response.body().contains("Pragma=no-cache"));
        Assert.assertTrue(response.body().contains("Keep-Alive=300"));
    }
    
    @Test
    public void testMatrixParams() throws Exception {

        try (Socket client = new Socket("127.0.0.1", 8080)) {
            try (PrintWriter out = new PrintWriter(client.getOutputStream(), true)) {
                // reproduce the issue by flushing request in the middle of http request path parameter
		out.write("GET");
		out.write(" ");
		out.write("/params/restparams/matrix;name=john;surname=smith");
		out.write(" HTTP/1.1");
		out.write(CRLF); // end of http request line
		out.write("User-Agent: TestClient" + CRLF);
		out.write("Host: 127.0.0.1:8080" + CRLF);
		out.write(CRLF);
		out.flush();
                String response = new BufferedReader(new InputStreamReader(client.getInputStream())).lines().collect(Collectors.joining("\n"));
                Assert.assertNotNull(response);
                Assert.assertTrue(response.contains("HTTP/1.1 200 OK"));
                Assert.assertTrue(response.contains("name is john"));
            }catch(Exception e) {
                Assert.fail("Printwriter could not be created");
            }
        }catch(Exception ex) {
            Assert.fail("Socket could not be created");
        }
    }
    
    @Test
    public void testBeanParams() throws Exception {

        HttpClient client = HttpClient.newHttpClient();

	HttpRequest request = HttpRequest.newBuilder()
	    .uri(URI.create("http://localhost:8080/params/restparams/bean"))
	    .POST(BodyPublishers.ofString("email=user@email.com&username=myuser"))
	    .setHeader("Content-Type", "application/x-www-form-urlencoded")
	    .build();

	HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	Assert.assertNotNull(response.body());
        Assert.assertTrue(response.body().contains("ParamBean"));
        Assert.assertTrue(response.body().contains("user@email.com"));
        Assert.assertTrue(response.body().contains("myuser"));
    }
}
