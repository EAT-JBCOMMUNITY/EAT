package org.jboss.test.integration.ws.defaultmethods;

public interface DefaultInterface
{
   default public String sayHi() {
      return "Hi, Default";
   }
}
