/*
 * JBoss, Home of Professional Open Source.
 * Copyright (c) 2015, Red Hat, Inc., and individual contributors
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
package org.jboss.test.integration.mtom;

import java.awt.Image;
import java.io.FileInputStream;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.soap.SOAPBinding;
import org.apache.commons.io.IOUtils;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import static org.junit.Assert.assertFalse;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author <a href="mailto:psotirop@redhat.com">Panos</a>
 */
@RunWith(Arquillian.class)
@RunAsClient
public class MtomSoapTestCase {

    private final String serverLogPath = "/../surefire-reports/org.jboss.test.integration.mtom.MtomSoapTestCase-output.txt";

    @Deployment
    public static WebArchive deployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "war-mtom.war");
        war.addClasses(ImageServer.class, ImageServerImpl.class);
        war.setWebXML("META-INF/web.xml");
        return war;
    }

    @Test
    public void mtomTest() throws Exception {
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();  
  
        factory.getInInterceptors().add(new LoggingInInterceptor());  
        factory.getOutInterceptors().add(new LoggingOutInterceptor());  
        factory.setServiceClass(ImageServer.class);  
        factory.setAddress("http://localhost:8080/war-mtom");  
        ImageServer imageServer = (ImageServer) factory.create();  
        
        //enable MTOM in client
        BindingProvider bp = (BindingProvider) imageServer;
        SOAPBinding binding = (SOAPBinding) bp.getBinding();
        binding.setMTOMEnabled(true);
        
        String path = this.getClass().getClassLoader().getResource("").getPath();
        Image im = imageServer.downloadImage(path + "rss.png");

        FileInputStream inputStream = new FileInputStream(path + serverLogPath);
        try {
            String everything = IOUtils.toString(inputStream);
            assertFalse("The soap message uses MTOM (xop)", everything.contains("xop:Include"));
        } finally {
            inputStream.close();
        }
    }
}
