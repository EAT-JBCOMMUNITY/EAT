package org.jboss.additional.testsuite.jdkall.present.jaxrs.exceptionmapper;

import jakarta.ws.rs.WebApplicationException;

import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java#29.0.0"})
public class MyException extends WebApplicationException {

	public MyException() {
		super();
	}

	public MyException(String message) {
		super("MyException Error: " +message);
	}

}
