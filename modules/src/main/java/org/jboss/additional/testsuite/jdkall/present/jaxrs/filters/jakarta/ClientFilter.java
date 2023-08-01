package org.jboss.additional.testsuite.jdkall.present.jaxrs.filters;

import org.jboss.eap.additional.testsuite.annotations.EAT;

import java.io.IOException;

import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;
import jakarta.ws.rs.client.ClientResponseContext;
import jakarta.ws.rs.client.ClientResponseFilter;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java","modules/testcases/jdkAll/EapJakarta/jaxrs/src/main/java"})
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

