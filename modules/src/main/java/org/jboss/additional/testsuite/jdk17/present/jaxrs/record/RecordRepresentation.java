package org.jboss.additional.testsuite.jdk17.present.jaxrs.record;

import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdk17/WildflyJakarta/jaxrs/src/main/java","modules/testcases/jdk17/Eap7Plus/jaxrs/src/main/java#7.4.12"})
public record RecordRepresentation(String message) {

}
