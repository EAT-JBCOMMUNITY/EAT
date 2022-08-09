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
package org.jboss.additional.testsuite.jdkall.present.jaxrs.emptyhostheader;

import org.jboss.eap.additional.testsuite.annotations.EAT;
import org.jboss.logging.Logger;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;

/**
 * @author Ivo Studensky
 */
@Path("/host")
@EAT({"modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java#27.0.0.Alpha4"})
public class SimpleResource {
    private static Logger log = Logger.getLogger(SimpleResource.class.getName());

    @Context
    UriInfo uriInfo;

    @GET
    @Produces("text/plain")
    public String getUriInfo() {
        log.info("uriInfo = " + uriInfo.getRequestUri());
        return "uriInfo: " + uriInfo.getRequestUri().toString();
    }

}
