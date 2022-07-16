package org.jboss.additional.testsuite.jdkall.present.elytron.ejb.authentication;

import java.security.Principal;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import org.jboss.ejb3.annotation.RunAsPrincipal;

import org.jboss.eap.additional.testsuite.annotations.EAT;

@Stateless
@Remote(RunAsPrincipalEJBRemote.class)
@RunAsPrincipal("user2")
@EAT({"modules/testcases/jdkAll/Wildfly/elytron/src/main/java#27.0.0", "modules/testcases/jdkAll/Eap7Plus/elytron/src/main/java#7.4.3","modules/testcases/jdk11/Wildfly/elytron/src/main/java#27.0.0", "modules/testcases/jdk11/Eap7Plus/elytron/src/main/java#7.4.3"})
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
