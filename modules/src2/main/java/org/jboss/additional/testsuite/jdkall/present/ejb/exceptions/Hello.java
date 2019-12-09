package org.jboss.additional.testsuite.jdkall.present.ejb.exceptions;

import org.jboss.eap.additional.testsuite.annotations.EapAdditionalTestsuite;
import javax.ejb.Local;

/**
 * @author bmaxwell
 *
 */
@EapAdditionalTestsuite({"modules/testcases/jdkAll/Wildfly/ejb/src/main/java", "modules/testcases/jdkAll/Eap72x/ejb/src/main/java", "modules/testcases/jdkAll/Eap72x-Proposed/ejb/main/java","modules/testcases/jdkAll/Eap7/ejb/src/main/java"})
@Local
public interface Hello {
    public String hello();
}
