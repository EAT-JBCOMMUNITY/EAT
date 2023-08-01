package org.jboss.additional.testsuite.jdkall.present.jaxrs.filters;

import org.jboss.eap.additional.testsuite.annotations.EAT;

import java.io.IOException;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java","modules/testcases/jdkAll/EapJakarta/jaxrs/src/main/java"})
@Provider 
public class InfoFilter implements ContainerRequestFilter  {

    public void filter(ContainerRequestContext requestContext) throws IOException {
        System.out.println("RequestFilter ... ");
        System.out.println("Method : " + requestContext.getMethod());
        System.out.println("Headers : " + requestContext.getHeaders().toString());  
    }	 
}
