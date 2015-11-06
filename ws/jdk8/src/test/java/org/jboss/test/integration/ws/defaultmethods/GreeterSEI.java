package org.jboss.test.integration.ws.defaultmethods;

import javax.jws.WebService;

@WebService
public interface GreeterSEI extends DefaultInterface
{
   default public String sayHello() {
      return "Hello, Default";
   }
}
