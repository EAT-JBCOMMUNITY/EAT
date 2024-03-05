package org.jboss.additional.testsuite.jdkall.present.web.listeners;

import java.util.Date;
import java.lang.Throwable;

import javax.servlet.*;
import javax.servlet.http.*;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/Eap7Plus/web/src/main/java#7.4.15"})
public class CustomHttpSessionListener implements HttpSessionListener{


  public void sessionCreated(HttpSessionEvent sessionEvent){
   //print timestamp & session & getMaxInactiveInterval
   // use date.toString() for the time
      HttpSession session = sessionEvent.getSession();
      Date date = new Date();
      System.out.println(">>>> Created Session : ["+session.getId() +"] at ["+ date.toString()+"] <<<");
      //Thread.dumpStack();
      new Throwable().printStackTrace(System.out);
  }
  public void sessionDestroyed(HttpSessionEvent sessionEvent){
   // print timestamp & sessionId at the point it is destroyed
   // use date.toString() for the time
      HttpSession session = sessionEvent.getSession();
      Date date = new Date();
      System.out.println(">>>> Destroyed Session : ["+session.getId() +"] at ["+ date.toString()+"] <<<");
      //Thread.dumpStack();
      new Throwable().printStackTrace(System.out);
  }
}
