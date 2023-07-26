package org.jboss.additional.testsuite.jdkall.present.jaxrs.exceptionmapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java#29.0.0"})
@Provider
public class MyExceptionMapper  implements ExceptionMapper<MyException> {
	    @Override
	    public Response toResponse(MyException exception) 
	    {
	        return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build();  
	    }
}
