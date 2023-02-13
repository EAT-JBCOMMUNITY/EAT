package org.jboss.additional.testsuite.jdkall.present.jaxrs.filters;

import org.jboss.eap.additional.testsuite.annotations.EAT;

import java.io.IOException;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.ext.ReaderInterceptor;
import jakarta.ws.rs.ext.ReaderInterceptorContext;

@Provider
@EAT({"modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java"})
public class ReaderInterceptorImplementation implements ReaderInterceptor {

	@Override
	public Object aroundReadFrom(ReaderInterceptorContext context)
			throws IOException, WebApplicationException {
		System.out.println("Called ReaderInterceptorImplementation");
		System.setProperty("ReaderInterceptorImplementation", "true");
                return context.proceed();
	}
}
