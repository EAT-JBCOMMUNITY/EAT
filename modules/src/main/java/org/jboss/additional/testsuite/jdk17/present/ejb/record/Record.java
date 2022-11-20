package org.jboss.additional.testsuite.jdk17.present.ejb.record;

import java.io.Serializable;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdk17/Eap7Plus/ejb/src/main/java#7.4.8","modules/testcases/jdk17/WildflyJakarta/ejb/src/main/java#28.0.0.Final"})
public record Record(String name, String message) implements Serializable {
}
