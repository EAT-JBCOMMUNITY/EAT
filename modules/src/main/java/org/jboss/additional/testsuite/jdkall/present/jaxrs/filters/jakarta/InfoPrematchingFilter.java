package org.jboss.additional.testsuite.jdkall.present.jaxrs.filters;

import org.jboss.eap.additional.testsuite.annotations.EAT;

import java.io.IOException;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.container.PreMatching;


@EAT({"modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java"})
@Provider 
@PreMatching
public class InfoPrematchingFilter implements ContainerRequestFilter  {
     
        @Override
        public void filter(ContainerRequestContext requestContext) throws IOException {
            if (requestContext.getMethod().equals("POST")) {
                requestContext.setMethod("GET");
            }
            System.out.println("Prematching RequestFilter ... ");
        }	 
}
