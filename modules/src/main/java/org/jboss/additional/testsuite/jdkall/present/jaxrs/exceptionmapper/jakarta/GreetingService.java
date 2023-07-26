package org.jboss.additional.testsuite.jdkall.present.jaxrs.exceptionmapper;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import javax.inject.Inject;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java#29.0.0"})
@Path("/greeting")
public class GreetingService {

        @Inject
        GreetingModel greetingModel;
    
	@GET
	public String hello(@QueryParam("id") final Integer id) {
		if (id == null) throw new MyException("You need an Id");
		greetingModel.setGreeting("Hello World");
		return "Hello " +id;
	}
}
