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
import org.jboss.logging.Logger;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.Locale;
import java.util.stream.Collectors;

@RunWith(Arquillian.class)
@RunAsClient
@EAT({"modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java#32.0.0","modules/testcases/jdkAll/Eap73x/jaxrs/src/main/java","modules/testcases/jdkAll/Eap7Plus/jaxrs/src/main/java#7.4.15","modules/testcases/jdkAll/EapJakarta/jaxrs/src/main/java"})
public class MultipartTestCase {

    private static Logger log = Logger.getLogger(MatrixParamsTestCase.class.getName());

    @Deployment
    public static Archive<?> deploy() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "multipartTestCase.war");
        return war;
    }

    @Test
    public void testMatrixParam() throws Exception {
        HttpClient client = HttpClient.newBuilder()
    	.followRedirects(HttpClient.Redirect.NORMAL)
   	.build();

	HttpRequest request = HttpRequest.newBuilder()
	    .uri(URI.create("http://localhost:8080/testdeployment/rest/test/multipart"))
	    .POST(BodyPublishers.ofString("--WebAppBoundary\nContent-Disposition: name=\"IMAGE\"; filename=\"test.png\"\nContent-Transfer-Encoding: BASE64\nContent-Type: image/png\n\niVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAIAAAAC64paAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAAfSURBVDhPYxg48J8CMKqZRDCqmUQwqplEMFCa//8HAJfZNAWgNbc2AAAAAElFTkSuQmCC\n\n--WebAppBoundary--"))
	    .setHeader("Content-Type", "multipart/mixed; boundary=WebAppBoundary")
	    .setHeader("Accept", "text/plain")
	    .build();

	HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	
	Assert.assertTrue(response.body().toString().contains("OK"));

    }
    
}
