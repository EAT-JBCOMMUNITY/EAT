package org.jboss.additional.testsuite.jdk17.present.jaxrs.record;

import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdk17/WildflyJakarta/jaxrs/src/main/java","modules/testcases/jdk17/Eap7Plus/jaxrs/src/main/java#7.4.12"})
public class ClassRepresentation {

	private String message;

	public ClassRepresentation(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
