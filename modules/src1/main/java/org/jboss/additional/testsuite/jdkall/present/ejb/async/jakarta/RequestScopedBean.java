package org.jboss.additional.testsuite.jdkall.present.ejb.async;

import javax.enterprise.context.RequestScoped;
import jakarta.ejb.Stateless;
import org.jboss.eap.additional.testsuite.annotations.EAT;

/**
 * 
 * 
 * @author Stuart Douglas
 */
@RequestScoped
@Stateless
@EAT({"modules/testcases/jdkAll/WildflyJakarta/ejb/src/main/java#27.0.0.Alpha4"})
public class RequestScopedBean {
    
    private int state = 0;

    public int getState() {
        return state;
    }

    public void setState(final int state) {
        this.state = state;
    }
}
