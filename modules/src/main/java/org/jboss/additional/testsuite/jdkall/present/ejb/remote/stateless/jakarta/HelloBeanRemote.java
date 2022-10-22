package org.jboss.additional.testsuite.jdkall.present.ejb.remote.stateless;

import jakarta.ejb.Remote;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@Remote
@EAT({"modules/testcases/jdkAll/WildflyJakarta/ejb/src/main/java#27.0.0.Final"})
public interface HelloBeanRemote {

    public String hello();

}
