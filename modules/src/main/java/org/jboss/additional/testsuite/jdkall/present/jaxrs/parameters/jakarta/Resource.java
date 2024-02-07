package org.jboss.additional.testsuite.jdkall.present.jaxrs.parameters;

import org.jboss.eap.additional.testsuite.annotations.EAT;
import org.jboss.logging.Logger;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.util.Map;

@Path("")
@EAT({"modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java","modules/testcases/jdkAll/EapJakarta/jaxrs/src/main/java"})
public class Resource {

        @Path("form")
	@POST
	@Consumes("application/x-www-form-urlencoded")
	public String login(@FormParam("email") String e, @FormParam("password") String p) {
		return "Logged with " + e + " " + p;
	}

	@GET
	@Path("path/{id}")
	public String loginPath(@PathParam("id") String id) {
		return "Id is " +id;
	}

	@GET
	@Path("query")
	public String loginQuery(@DefaultValue("11111")  @QueryParam("id") String id) {
		return "Id is " +id;
	}


	@GET
	@Path("header")
	public String checkBrowser(@HeaderParam("User-Agent") String whichBrowser) {
         return "Browser is "+whichBrowser;
	}

	@GET
	@Path("session")
	public String checkSession(@CookieParam("JSESSIONID") String sessionid) {
		return "Cookie session id is "+sessionid;
	}
	@GET
	@Path("httpheaders")
	public String getRequestHeaders(@Context HttpHeaders hh) {
        System.out.println("called!!!");
		MultivaluedMap<String, String> headerParameters = hh.getRequestHeaders();
		Map<String, Cookie> params = hh.getCookies();
		StringBuffer sb = new StringBuffer();
		for (String key : params.keySet()) {
			sb.append(key + ": " + params.get(key));
		}
		return sb.toString();
	}

	@GET
	@Path("matrix")
	public String callMatrix(@MatrixParam("name") String name,
							  @MatrixParam("surname") String surname) {
           return "name is "+name + " - surname is "+surname;
	}

	@POST
	@Path("bean")
	public String post(@BeanParam ParamBean bean) {
		return bean.dumpData();
	}

	@GET
	@Path("context")
	public Response login(@Context UriInfo info) {
		String id = info.getQueryParameters().getFirst("username");
		return Response
				.status(200)
				.entity("login called with id: " + id).build();
	}
}
