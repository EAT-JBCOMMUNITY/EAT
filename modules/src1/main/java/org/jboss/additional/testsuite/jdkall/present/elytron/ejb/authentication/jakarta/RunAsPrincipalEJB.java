package org.jboss.additional.testsuite.jdkall.present.elytron.ejb.authentication;

import java.security.Principal;

import jakarta.annotation.Resource;
import jakarta.ejb.EJB;
import jakarta.ejb.Remote;
import jakarta.ejb.SessionContext;
import jakarta.ejb.Stateless;
import org.jboss.ejb3.annotation.RunAsPrincipal;

import org.jboss.eap.additional.testsuite.annotations.EAT;

@Stateless
@Remote(RunAsPrincipalEJBRemote.class)
@RunAsPrincipal("user2")
@EAT({"modules/testcases/jdkAll/WildflyJakarta/elytron/src/main/java#27.0.0.Alpha4"})
public class RunAsPrincipalEJB implements RunAsPrincipalEJBRemote {

    // Inject the Session Context
    @Resource
    private SessionContext ctx;

    @EJB
    MyBean mbean;

    public String getInfo() {
        mbean.printIdendity();

        Principal principal = ctx.getCallerPrincipal();
        return principal.toString();
    }

}
