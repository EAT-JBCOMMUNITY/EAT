package org.jboss.additional.testsuite.jdkall.present.elytron.ejb.authentication;

import java.security.Principal;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.annotation.security.RunAs;

import org.jboss.eap.additional.testsuite.annotations.EAT;

@Stateless
@Remote(RunAsEJBRemote.class)
@RunAs("User2")
@EAT({"modules/testcases/jdkAll/Wildfly/elytron/src/main/java#27.0.0", "modules/testcases/jdkAll/Eap73x/elytron/src/main/java","modules/testcases/jdkAll/Eap7Plus/elytron/src/main/java#7.4.3"})
public class RunAsEJB implements RunAsEJBRemote {

    // Inject the Session Context
    @Resource
    private SessionContext ctx;

    @EJB
    MyBean mbean;

    public boolean getInfo() {
        mbean.printIdendity();

        return ctx.isCallerInRole("User2");
    }

}
