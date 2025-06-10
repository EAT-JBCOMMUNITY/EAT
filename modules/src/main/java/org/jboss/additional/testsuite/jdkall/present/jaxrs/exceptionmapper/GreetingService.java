package org.jboss.additional.testsuite.jdkall.present.jaxrs.exceptionmapper;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.inject.Inject;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/Eap73x/jaxrs/src/main/java","modules/testcases/jdkAll/Eap7Plus/jaxrs/src/main/java#7.4.11"})
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
