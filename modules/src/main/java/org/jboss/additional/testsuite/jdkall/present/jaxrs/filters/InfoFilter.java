package org.jboss.additional.testsuite.jdkall.present.jaxrs.filters;

import org.jboss.eap.additional.testsuite.annotations.EAT;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

@EAT({"modules/testcases/jdkAll/Eap7Plus/jaxrs/src/main/java"})
@Provider 
public class InfoFilter implements ContainerRequestFilter  {

    public void filter(ContainerRequestContext requestContext) throws IOException {
        System.out.println("Method : " + requestContext.getMethod());
        System.out.println("Headers : " + requestContext.getHeaders().toString());  
    }	 
}
