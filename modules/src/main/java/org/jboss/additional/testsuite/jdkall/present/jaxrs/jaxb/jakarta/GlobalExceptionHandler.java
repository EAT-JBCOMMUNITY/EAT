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

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.ext.Provider;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java#28.0.0.Beta1","modules/testcases/jdkAll/EapJakarta/jaxrs/src/main/java"})
@Provider
public class GlobalExceptionHandler
  extends Object
  implements ExceptionMapper<Exception>
{
  public Response toResponse(Exception ex) {
    int status = getHttpStatus(ex);
    ErrorMessage message = new ErrorMessage();
    message.setStatus(status);
    message.setMessage(ex.getMessage());
    return Response.status(status)
      .entity(message)
      .type("application/json")
      .build();
  }
  
  private int getHttpStatus(Exception ex) {
    if (ex instanceof WebApplicationException)
      return ((WebApplicationException)ex).getResponse().getStatus();     
    return Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
  }
}

