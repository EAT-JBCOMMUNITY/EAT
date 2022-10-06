package org.jboss.additional.testsuite.jdkall.present.elytron.ejb.authentication;

import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/Wildfly/elytron/src/main/java#27.0.0","modules/testcases/jdkAll/WildflyJakarta/elytron/src/main/java#27.0.0.Alpha4","modules/testcases/jdkAll/WildflyRelease-27.0.0.Final/elytron/src/main/java", "modules/testcases/jdkAll/Eap7Plus/elytron/src/main/java#7.4.5"})
public interface RolePrincipalPairValidityEJBRemote {

    Boolean getRolePrincipalPairValidity();

}
