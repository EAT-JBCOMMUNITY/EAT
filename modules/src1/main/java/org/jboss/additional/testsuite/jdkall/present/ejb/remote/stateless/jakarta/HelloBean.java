package org.jboss.additional.testsuite.jdkall.present.ejb.remote.stateless;


import java.util.concurrent.TimeUnit;
import jakarta.annotation.Resource;
import jakarta.ejb.SessionContext;
import jakarta.ejb.Stateless;
import org.jboss.eap.additional.testsuite.annotations.EAT;


@Stateless
@EAT({"modules/testcases/jdkAll/WildflyJakarta/ejb/src/main/java#27.0.0.Alpha4"})
public class HelloBean implements HelloBeanRemote {

    @Resource
    SessionContext ctx;

    public HelloBean() {
    }

    @Override
    public String hello() {
        System.out.println("method hello() invoked by user " + ctx.getCallerPrincipal().getName());
        try {
            for(int i=10; i>0; i--) {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("returning q");
        return ctx.getCallerPrincipal().getName();
    }

}
