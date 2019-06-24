/*
 * Copyright (C) 2014 Red Hat, inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package org.jboss.additional.testsuite.jdkall.present.management.deploy.runtime.jaxrs;

import java.util.concurrent.atomic.AtomicReference;
import javax.ejb.Singleton;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import org.jboss.eap.additional.testsuite.annotations.EapAdditionalTestsuite;

/**
 *
 * @author <a href="mailto:ehugonne@redhat.com">Emmanuel Hugonnet</a> (c) 2014
 * Red Hat, inc.
 */
@Path("/")
@Singleton
@EapAdditionalTestsuite({"modules/testcases/jdkAll/Eap72x/management/src/main/java","modules/testcases/jdkAll/Eap72x-Proposed/management/src/main/java","modules/testcases/jdkAll/Eap72x-Proposed/management/src/main/java","modules/testcases/jdkAll/Eap7/management/src/main/java","modules/testcases/jdkAll/Eap71x-Proposed/management/src/main/java","modules/testcases/jdkAll/Eap71x/management/src/main/java","modules/testcases/jdkAll/Eap7.1.0.Beta/management/src/main/java","modules/testcases/jdkAll/Eap70x/management/src/main/java","modules/testcases/jdkAll/Eap70x-Proposed/management/src/main/java","modules/testcases/jdkAll/WildflyRelease-10.1.0.Final/management/src/main/java","modules/testcases/jdkAll/WildflyRelease-13.0.0.Final/management/src/main/java","modules/testcases/jdkAll/Wildfly/management/src/main/java","modules/testcases/jdkAll/WildflyRelease-17.0.0.Final/management/src/main/java"})
public class HelloResource {

    private AtomicReference<String> message = new AtomicReference<>("World");

    private String createHelloMessage(final String msg) {
        return "Hello " + msg + "!";
    }

    @GET
    @Path("/")
    @Produces({"text/plain"})
    public String getHelloWorld() {
        return createHelloMessage(message.get());
    }

    @GET
    @Path("/json")
    @Produces({"application/json"})
    public JsonObject getHelloWorldJSON() {
        return Json.createObjectBuilder()
                .add("result", createHelloMessage(message.get()))
                .build();
    }

    @GET
    @Path("/xml")
    @Produces({"application/xml"})
    public String getHelloWorldXML() {
        return "<xml><result>" + createHelloMessage(message.get()) + "</result></xml>";
    }

    @PUT
    @Consumes("text/plain")
    @Path("/update")
    public void updateMessage(String content) {
        message.set(content);
    }
}
