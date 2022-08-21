package org.jboss.additional.testsuite.jdkall.present.elytron.ejb.authentication;

import jakarta.ejb.Stateless;
import jakarta.ejb.SessionContext;

import jakarta.annotation.Resource;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@Stateless
@EAT({"modules/testcases/jdkAll/WildflyJakarta/elytron/src/main/java#27.0.0.Alpha4","modules/testcases/jdkAll/WildflyRelease-27.0.0.Final/elytron/src/main/java"})
public class MyBean {

    @Resource
    private SessionContext ctx;

    public void printIdendity() {

        System.out.println("2 .Caller identity is " + ctx.getCallerPrincipal().getName());

    }

}
