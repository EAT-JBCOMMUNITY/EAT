package org.jboss.additional.testsuite.jdkall.present.elytron.ejb.authentication;

import jakarta.annotation.Resource;
import jakarta.annotation.security.RolesAllowed;
import jakarta.annotation.security.RunAs;
import jakarta.ejb.Remote;
import jakarta.ejb.SessionContext;
import jakarta.ejb.Stateless;
import org.jboss.ejb3.annotation.RunAsPrincipal;
import org.jboss.ejb3.annotation.SecurityDomain;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@Stateless
@Remote(RolePrincipalPairValidityEJBRemote.class)
@SecurityDomain("other")
@RolesAllowed({"Admin", "User"})
@RunAs("Admin")
@RunAsPrincipal("admin")
@EAT({"modules/testcases/jdkAll/WildflyJakarta/elytron/src/main/java#27.0.0.Alpha4"})
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
