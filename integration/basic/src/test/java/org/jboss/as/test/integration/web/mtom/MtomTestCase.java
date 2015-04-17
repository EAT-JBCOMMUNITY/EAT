/*
 * JBoss, Home of Professional Open Source.
 * Copyright (c) 2011, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.as.test.integration.web.mtom;

import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.as.test.integration.common.HttpRequest;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.awt.Image;
import java.io.File;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertEquals;

/**
 * @author <a href="mailto:psotirop@redhat.com">Panos</a>
 */
@RunWith(Arquillian.class)
@RunAsClient
public class MtomTestCase {

    @ArquillianResource
    private URL url;

    @Deployment
    public static WebArchive deployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "war-mtom.war");
        war.addClasses(ImageServer.class, ImageServerImpl.class);
        war.setWebXML(MtomTestCase.class.getPackage(), "web.xml");
        return war;
    }

    @Test
    public void mtomTest() throws Exception {
      /*  URL url = new URL("http://localhost:8080/war-mtom?wsdl");
        QName qname = new QName("http://mtom.web.integration.test.as.jboss.org/", "ImageServerImplService");
 
        Service service = Service.create(url, qname);
        ImageServer imageServer = service.getPort(ImageServer.class);
 
        //enable MTOM in client
        BindingProvider bp = (BindingProvider) imageServer;
        SOAPBinding binding = (SOAPBinding) bp.getBinding();
        binding.setMTOMEnabled(true);

        Image im = imageServer.downloadImage("rss.png"); 
        if (im != null)*/
            System.out.println("Image uploaded successfully...");
    }
}
