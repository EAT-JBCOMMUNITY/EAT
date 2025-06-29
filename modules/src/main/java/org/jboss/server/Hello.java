package org.jboss.server;

import javax.ejb.Remote;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@Remote
@EAT({"modules/testcases/jdkAll/Eap73x/ejb/src/main/java","modules/testcases/jdkAll/Eap7Plus/ejb/src/main/java#7.4.11"})
public interface Hello {
    String greet(String name);
}
