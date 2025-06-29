package org.jboss.additional.testsuite.jdkall.present.jaxrs.exceptionmapper;

import javax.ws.rs.WebApplicationException;

import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/Eap73x/jaxrs/src/main/java","modules/testcases/jdkAll/Eap7Plus/jaxrs/src/main/java#7.4.11"})
public class MyException extends WebApplicationException {

	public MyException() {
		super();
	}

	public MyException(String message) {
		super("MyException Error: " +message);
	}

}
