package org.jboss.additional.testsuite.jdkall.present.jaxrs.exceptionmapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/Eap7Plus/jaxrs/src/main/java#7.4.11"})
@Provider
public class MyExceptionMapper  implements ExceptionMapper<MyException> {
	    @Override
	    public Response toResponse(MyException exception) 
	    {
	        return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build();  
	    }
}
