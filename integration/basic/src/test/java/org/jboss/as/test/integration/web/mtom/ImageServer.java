package org.jboss.as.test.integration.web.mtom;

import java.awt.Image;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

//Service Endpoint Interface
@WebService
@SOAPBinding(style = Style.RPC)
public interface ImageServer{
	
	//download a image from server
	@WebMethod Image downloadImage(String name);
	
}
