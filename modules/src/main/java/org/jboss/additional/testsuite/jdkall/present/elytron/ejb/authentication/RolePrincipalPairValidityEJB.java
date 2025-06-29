package org.jboss.additional.testsuite.jdkall.present.elytron.ejb.authentication;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.annotation.security.RunAs;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import org.jboss.ejb3.annotation.RunAsPrincipal;
import org.jboss.ejb3.annotation.SecurityDomain;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@Stateless
@Remote(RolePrincipalPairValidityEJBRemote.class)
@SecurityDomain("other")
@RolesAllowed({"Admin", "User"})
@RunAs("Admin")
@RunAsPrincipal("admin")
@EAT({"modules/testcases/jdkAll/Wildfly/elytron/src/main/java#27.0.0", "modules/testcases/jdkAll/Eap73x/elytron/src/main/java","modules/testcases/jdkAll/Eap7Plus/elytron/src/main/java#7.4.5"})
public class RolePrincipalPairValidityEJB implements RolePrincipalPairValidityEJBRemote {

    @Resource
    private SessionContext ctx;

    public Boolean getRolePrincipalPairValidity() {

        boolean valid = true;
        if (ctx.isCallerInRole("User2") && ctx.getCallerPrincipal().getName().compareTo("admin") == 0) {
            valid = false;
        }

        if (ctx.isCallerInRole("User2") && ctx.getCallerPrincipal().getName().compareTo("user") != 0) {
            valid = false;
        }

        if (ctx.isCallerInRole("Admin") && ctx.getCallerPrincipal().getName().compareTo("admin") != 0) {
            valid = false;
        }

        return valid;
    }
}
