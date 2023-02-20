package org.jboss.additional.testsuite.jdkall.present.jaxrs.filters;

import org.jboss.eap.additional.testsuite.annotations.EAT;

import java.io.IOException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;

@Provider
@EAT({"modules/testcases/jdkAll/Eap7Plus/jaxrs/src/main/java"})
public class WriterInterceptorImplementation implements WriterInterceptor {
 
    @Override
    public void aroundWriteTo(WriterInterceptorContext context)
                    throws IOException, WebApplicationException {
    	System.out.println("Called WriterInterceptorImplementation");
    	System.setProperty("WriterInterceptorContext", "true");
        context.proceed();
    }
}
