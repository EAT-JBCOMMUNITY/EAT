package org.jboss.additional.testsuite.jdkall.present.jaxrs.filters;

import java.io.IOException;

import org.jboss.eap.additional.testsuite.annotations.EAT;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@EAT({"modules/testcases/jdkAll/Eap7Plus/jaxrs/src/main/java"})
@Provider
public class ResponseFilter implements  ContainerResponseFilter {
    
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
    	 System.out.println("ResponseFilter ... ");
    	if (requestContext.getMethod().equals("GET")) {    		 
    		responseContext.getHeaders().add("Content-Length", "66");
    	}
    }
	 
}
