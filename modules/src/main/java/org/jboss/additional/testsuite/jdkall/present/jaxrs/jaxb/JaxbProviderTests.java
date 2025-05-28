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
package org.jboss.additional.testsuite.jdkall.present.jaxrs.jaxb;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.asset.StringAsset;
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
import java.util.Locale;
import java.util.stream.Collectors;
import org.jboss.eap.additional.testsuite.annotations.EAT;
import org.jboss.eap.additional.testsuite.annotations.ATFeature;
import org.jboss.eap.additional.testsuite.annotations.ATTest;

/**
 * Tests a JAX-RS deployment without an application bundled.
 *
 * The container should register a servlet with the name
 *
 * javax.ws.rs.core.Application
 *
 * It is the app providers responsibility to provide a mapping for the servlet
 *
 * JAX-RS 1.1 2.3.2 bullet point 1
 *
 * @author Stuart Douglas
 */
@EAT({"modules/testcases/jdkAll/OpenLiberty/jaxrs/src/main/java","modules/testcases/jdkAll/WildflyRelease-24.0.0.Final/jaxrs/src/main/java","modules/testcases/jdkAll/Wildfly/jaxrs/src/main/java#17.0.0.Final*27.0.0.Alpha3","modules/testcases/jdkAll/ServerBeta/jaxrs/src/main/java","modules/testcases/jdkAll/WildflyRelease-17.0.0.Final/jaxrs/src/main/java","modules/testcases/jdkAll/WildflyRelease-20.0.0.Final/jaxrs/src/main/java","modules/testcases/jdkAll/Eap72x/jaxrs/src/main/java","modules/testcases/jdkAll/Eap72x-Proposed/jaxrs/src/main/java","modules/testcases/jdkAll/Eap7Plus/jaxrs/src/main/java","modules/testcases/jdkAll/Eap71x-Proposed/jaxrs/src/main/java","modules/testcases/jdkAll/Eap71x/jaxrs/src/main/java","modules/testcases/jdkAll/Eap7.1.0.Beta/jaxrs/src/main/java","modules/testcases/jdkAll/Eap70x/jaxrs/src/main/java","modules/testcases/jdkAll/Eap70x-Proposed/jaxrs/src/main/java","modules/testcases/jdkAll/Eap64x/jaxrs/src/main/java","modules/testcases/jdkAll/Eap64x-Proposed/jaxrs/src/main/java","modules/testcases/jdkAll/Eap63x/jaxrs/src/main/java","modules/testcases/jdkAll/Eap62x/jaxrs/src/main/java","modules/testcases/jdkAll/Eap61x/jaxrs/src/main/java","modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java#27.0.0.Alpha4","modules/testcases/jdkAll/WildflyRelease-27.0.0.Final/jaxrs/src/main/java","modules/testcases/jdkAll/EapJakarta/jaxrs/src/main/java"})
@RunWith(Arquillian.class)
@RunAsClient
public class JaxbProviderTests {

    private final static String WARNAME = "arquillian-managed.war";

    @Deployment(testable = false)
    public static Archive<?> deploy() {
        WebArchive war = ShrinkWrap.create(WebArchive.class,WARNAME);
        war.addClasses(JaxbProviderTests.class, JaxbModel.class, JaxbResource.class,HttpRequestCommands.class,ExampleApplication.class);
        return war;
    }

    @ArquillianResource
    private URL url;

    private String performCall(String urlPattern) throws Exception {
        return HttpRequestCommands.get(url + urlPattern, 10, TimeUnit.SECONDS);
    }

    @Test
    @ATFeature(feature={"jaxrs,jaxb,jsonp,cdi,localConnector,servlet"},minVersion={"2.1,2.2,1.1,2.0,1.0,4.0"},maxVersion={"null,null,null,null,null,null"})
    public void testJaxRs() throws Exception {
        String result = performCall("rest/jaxb");
        Assert.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><jaxbModel><first>John</first><last>Citizen</last></jaxbModel>", result);
    }

    @ATTest({"modules/testcases/jdkAll/Eap7Plus/jaxrs/src/main/java#7.4.15","modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java#38.0.0","modules/testcases/jdkAll/EapJakarta/jaxrs/src/main/java#8.0.11"})
    public void testException() throws Exception {
        String uri = url.getPath() + "rest/jaxb/model";

        try (Socket client = new Socket("127.0.0.1", 8080)) {
            try (PrintWriter out = new PrintWriter(client.getOutputStream(), true)) {
                out.printf(Locale.US, "POST %s HTTP/1.1\nHost: localhost:8080\nAccept: */*\nContent-Type: application/json\nContent-Length: 2\n\n[[", uri);
                String response = new BufferedReader(new InputStreamReader(client.getInputStream())).lines().collect(Collectors.joining("\n"));
                System.out.println("response: " + response);
                Assert.assertNotNull(response);
                Assert.assertTrue("Class data revealed ...", !response.contains("org.jboss.additional.testsuite.jdkall.present.jaxrs.jaxb.JaxbModel"));
            }catch(Exception e) {
                Assert.fail("Printwriter could not be created");
            }
        }catch(Exception ex) {
            Assert.fail("Socket could not be created");
        }
    }

    public void defaultTest() {
    }

}
