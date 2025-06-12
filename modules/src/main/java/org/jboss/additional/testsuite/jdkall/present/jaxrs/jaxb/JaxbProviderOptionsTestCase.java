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
import org.jboss.as.test.integration.common.HttpRequest;
import org.jboss.eap.additional.testsuite.annotations.EAT;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MultivaluedMap;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@RunWith(Arquillian.class)
@RunAsClient
@EAT({"modules/testcases/jdkAll/Eap7Plus/jaxrs/src/main/java#7.4.10"})
public class JaxbProviderOptionsTestCase {

    @Deployment(testable = false)
    public static Archive<?> deploy() {
        WebArchive war = ShrinkWrap.create(WebArchive.class,"optionswar.war");
        war.addClasses(JaxbProviderOptionsTestCase.class, JaxbModel.class, JaxbOptionResource.class,ExampleApplication2.class,CacheFilter.class,GlobalExceptionHandler.class, ErrorMessage.class, OrderDetails.class);
        return war;
    }

    @ArquillianResource
    private URL url;

    @Test
    public void testOptions() throws Exception {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url + "myjaxrs/options");
        Response response = target.request().options();
        MultivaluedMap<String, Object> headers = response.getHeaders();
        System.out.printf("Allow Header: %s%n", headers.get("Allow"));
        System.out.printf("status: %s%n", response.getStatus());
        System.out.printf("body: '%s'%n", response.readEntity(String.class));
        Assert.assertTrue(response.getStatus()==200);
    }

    @Test
    public void testOptionsAnnotation() throws Exception {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url + "myjaxrs/options/optionsAnnotation");
        Response response = target.request().options();
        MultivaluedMap<String, Object> headers = response.getHeaders();
        System.out.printf("Allow Header: %s%n", headers.get("Allow"));
        System.out.printf("status: %s%n", response.getStatus());
        System.out.printf("body: '%s'%n", response.readEntity(String.class));
        Assert.assertTrue(response.getStatus()==200);
    }
    
    @Test
    public void testSubOptionsAnnotation() throws Exception {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url + "myjaxrs/options/orders/1/getOrderDetails");
        Response response = target.request().options();
        MultivaluedMap<String, Object> headers = response.getHeaders();
        System.out.printf("Allow Header: %s%n", headers.get("Allow"));
        System.out.printf("status: %s%n", response.getStatus());
        System.out.printf("body2: '%s'%n", response.readEntity(String.class));
        Assert.assertTrue(response.getStatus()==200);
    }
}
