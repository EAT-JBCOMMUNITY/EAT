package org.jboss.additional.testsuite.jdkall.present.ejb.async;

import javax.enterprise.context.RequestScoped;
import org.jboss.eap.additional.testsuite.annotations.EapAdditionalTestsuite;

/**
 * 
 * 
 * @author Stuart Douglas
 */
@RequestScoped
@EapAdditionalTestsuite({"modules/testcases/jdkAll/Eap7/ejb/src/main/java","modules/testcases/jdkAll/WildflyRelease/ejb/src/main/java","modules/testcases/jdkAll/Wildfly/ejb/src/main/java","modules/testcases/jdkAll/Eap64x/ejb/src/main/java","modules/testcases/jdkAll/Eap64x-Proposed/ejb/src/main/java","modules/testcases/jdkAll/Eap63x/ejb/src/main/java","modules/testcases/jdkAll/Eap62x/ejb/src/main/java","modules/testcases/jdkAll/Eap61x/ejb/src/main/java"})
public class RequestScopedBean {
    
    private int state = 0;

    public int getState() {
        return state;
    }

    public void setState(final int state) {
        this.state = state;
    }
}
