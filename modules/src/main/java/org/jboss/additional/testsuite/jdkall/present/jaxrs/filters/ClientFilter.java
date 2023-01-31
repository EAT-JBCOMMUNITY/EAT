package org.jboss.additional.testsuite.jdkall.present.jaxrs.filters;

import org.jboss.eap.additional.testsuite.annotations.EAT;

import java.io.IOException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;

@EAT({"modules/testcases/jdkAll/Eap7Plus/jaxrs/src/main/java"})
public class ClientFilter implements ClientRequestFilter,ClientResponseFilter {

	public void filter(ClientRequestContext req) {
	    System.out.println("Client Request Filter ...");	
	    System.setProperty("ClientRequestFilter", "true");
	}

	@Override
	public void filter(ClientRequestContext arg0, ClientResponseContext crc1) throws IOException {
	    System.out.println("Client Response Filter ...");
	    System.setProperty("ClientResponseFilter", "true");
	}

}

