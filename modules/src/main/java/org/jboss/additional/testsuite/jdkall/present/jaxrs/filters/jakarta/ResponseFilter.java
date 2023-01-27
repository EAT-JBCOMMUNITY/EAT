package org.jboss.additional.testsuite.jdkall.present.jaxrs.filters;

import java.io.IOException;

import org.jboss.eap.additional.testsuite.annotations.EAT;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java"})
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
