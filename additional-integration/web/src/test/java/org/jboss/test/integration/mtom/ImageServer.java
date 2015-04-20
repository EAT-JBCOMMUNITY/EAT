package org.jboss.test.integration.mtom;

import java.awt.Image;

import javax.jws.WebMethod;
import javax.jws.WebService;

//Service Endpoint Interface
@WebService
public interface ImageServer{
	
	//download a image from server
	@WebMethod Image downloadImage(String image);
	
}
