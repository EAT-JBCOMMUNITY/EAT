package org.jboss.additional.testsuite.jdkall.present.elytron.ejb.authentication;

import javax.ejb.Stateless;
import javax.ejb.SessionContext;

import javax.annotation.Resource;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@Stateless
@EAT({"modules/testcases/jdkAll/Wildfly/elytron/src/main/java#27.0.0","modules/testcases/jdkAll/WildflyRelease-27.0.0.Final/elytron/src/main/java","modules/testcases/jdkAll/Eap7Plus/elytron/src/main/java#7.4.3"})
public class MyBean {

    @Resource
    private SessionContext ctx;

    public void printIdendity() {

        System.out.println("2 .Caller identity is " + ctx.getCallerPrincipal().getName());

    }

}
