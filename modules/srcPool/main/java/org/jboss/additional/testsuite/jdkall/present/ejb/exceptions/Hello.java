package org.jboss.additional.testsuite.jdkall.present.ejb.exceptions;

import org.jboss.eap.additional.testsuite.annotations.EAT;
import javax.ejb.Local;

/**
 * @author bmaxwell
 *
 */
@EAT({"modules/testcases/jdkAll/WildflyRelease-24.0.0.Final/ejb/src/main/java","modules/testcases/jdkAll/Wildfly/ejb/src/main/java#20.0.0.Beta1", "modules/testcases/jdkAll/Eap72x/ejb/src/main/java#7.2.7", "modules/testcases/jdkAll/Eap72x-Proposed/ejb/main/java#7.2.7","modules/testcases/jdkAll/Eap7Plus/ejb/src/main/java#7.3.1.GA"})
@Local
public interface Hello {
    public String hello();
}
