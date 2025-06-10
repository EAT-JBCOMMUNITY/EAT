package org.jboss.additional.testsuite.jdkall.present.jaxrs.filters;

import org.jboss.eap.additional.testsuite.annotations.EAT;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.container.PreMatching;


@EAT({"modules/testcases/jdkAll/Eap73x/jaxrs/src/main/java","modules/testcases/jdkAll/Eap7Plus/jaxrs/src/main/java"})
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
