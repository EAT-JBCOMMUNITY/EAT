package org.jboss.additional.testsuite.jdkall.present.ejb.remote.stateless;

import javax.ejb.Remote;
import org.jboss.eap.additional.testsuite.annotations.EapAdditionalTestsuite;

@Remote
@EapAdditionalTestsuite({"modules/testcases/jdkAll/Wildfly/ejb/src/main/java","modules/testcases/jdkAll/Eap7/ejb/src/main/java","modules/testcases/jdkAll/Eap71x/ejb/src/main/java"})
public interface HelloBeanRemote {

    public String hello();

}
