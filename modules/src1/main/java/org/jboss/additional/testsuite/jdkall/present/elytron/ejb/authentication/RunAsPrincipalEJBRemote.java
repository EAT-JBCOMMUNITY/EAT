package org.jboss.additional.testsuite.jdkall.present.elytron.ejb.authentication;

import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/Wildfly/elytron/src/main/java#27.0.0", "modules/testcases/jdkAll/Eap7Plus/elytron/src/main/java#7.4.3","modules/testcases/jdk11/Wildfly/elytron/src/main/java#27.0.0", "modules/testcases/jdk11/Eap7Plus/elytron/src/main/java#7.4.3"})
public interface RunAsPrincipalEJBRemote {

    /**
     * @return A String containing the name of the current principal.
     */
    String getInfo();

}
