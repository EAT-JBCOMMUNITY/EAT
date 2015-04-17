package org.jboss.as.test.integration.web.mtom;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

//Service Implementation Bean
@MTOM(enabled = false)
@WebService(endpointInterface = "org.jboss.as.test.integration.web.mtom.ImageServer")

public class ImageServerImpl implements ImageServer {

    @Override
    public Image downloadImage(String name) {

        try {

            File image = new File("/home/panos/Pictures/" + name);
            return ImageIO.read(image);

        } catch (IOException e) {

            e.printStackTrace();
            return null;

        }

    }
}
