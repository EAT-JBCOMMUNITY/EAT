package org.jboss.server;

import jakarta.ejb.Remote;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@Remote
@EAT({"modules/testcases/jdkAll/WildflyJakarta/ejb/src/main/java#29.0.0"})
public interface Hello {
    String greet(String name);
}
