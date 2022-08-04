package org.jboss.additional.testsuite.jdkall.present.ejb.container.interceptor;

import org.jboss.ejb.client.EJBClientInterceptor;
import org.jboss.ejb.client.EJBClientInvocationContext;
import org.jboss.eap.additional.testsuite.annotations.EAT;

//@AT({"modules/testcases/jdkAll/Wildfly/ejb/src/main/java#27.0.0.Alpha2*27.0.0.Alpha3"})
public class EjbClientInterceptor implements EJBClientInterceptor {

   public void handleInvocation(EJBClientInvocationContext context) throws Exception {
      context.getContextData().put("AnyKey", "AnyValue");
      context.sendRequest();
      System.out.println("Added context data!");
   }

   public Object handleInvocationResult(EJBClientInvocationContext context) throws Exception {
      System.out.println("Rcontext " + context.getContextData());
      return context.getResult();
   }

}
