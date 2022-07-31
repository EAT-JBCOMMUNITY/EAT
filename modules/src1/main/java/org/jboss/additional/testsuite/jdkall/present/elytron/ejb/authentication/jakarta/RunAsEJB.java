package org.jboss.additional.testsuite.jdkall.present.elytron.ejb.authentication;

import java.security.Principal;

import jakarta.annotation.Resource;
import jakarta.ejb.EJB;
import jakarta.ejb.Remote;
import jakarta.ejb.SessionContext;
import jakarta.ejb.Stateless;
import jakarta.annotation.security.RunAs;

import org.jboss.eap.additional.testsuite.annotations.EAT;

@Stateless
@Remote(RunAsEJBRemote.class)
@RunAs("User2")
@EAT({"modules/testcases/jdkAll/WildflyJakarta/elytron/src/main/java#27.0.0.Alpha4"})
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
