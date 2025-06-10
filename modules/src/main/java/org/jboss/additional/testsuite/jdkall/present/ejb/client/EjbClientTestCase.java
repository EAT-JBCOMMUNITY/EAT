/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2012, Red Hat, Inc., and individual contributors
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

package org.jboss.additional.testsuite.jdkall.present.ejb.client;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import org.jboss.server.Hello;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Properties;
import static org.junit.Assert.assertTrue;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.eap.additional.testsuite.annotations.EAT;
import org.jboss.logging.Logger;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;


import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.File;
import io.undertow.server.handlers.HttpContinueAcceptingHandler;
import io.undertow.server.HttpHandler;
import io.undertow.util.Headers;
import io.undertow.server.HttpServerExchange;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.runner.RunWith;
import java.io.File;
import java.io.FileInputStream;
import org.apache.commons.io.IOUtils;
import org.jboss.eap.additional.testsuite.annotations.EAT;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/ejb/src/main/java#30.0.0","modules/testcases/jdkAll/Eap73x/ejb/src/main/java","modules/testcases/jdkAll/Eap7Plus/ejb/src/main/java#7.4.12"})
@RunWith(Arquillian.class)
@RunAsClient
public class EjbClientTestCase {

    private final String outputLogPath = "exec/output.txt";

    @Deployment
    public static Archive<?> deploy() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "ejbClientTestCase.war");
        return war;
    }

    @Test
    public void testClientwithExec() throws Exception {
        
        ProcessBuilder pb = new ProcessBuilder("./exec/mvnexec.sh").redirectErrorStream(true);
        pb.redirectError(new File("output1.txt"));
        pb.start();
        
        Thread.sleep(5000);
        
        String content = new String(Files.readAllBytes(Paths.get("output1.txt")), StandardCharsets.UTF_8);
        assertTrue(!content.contains("ERROR"));
        
        String path = new File("").getAbsolutePath() + "/" + outputLogPath;
        File serverlogfile = new File(path);

            FileInputStream inputStream = new FileInputStream(path);
            try {
                String everything = IOUtils.toString(inputStream);
                assertTrue(!everything.contains("DISCOVERY_ADDITIONAL_TIMEOUT = 200"));
            } finally {
                inputStream.close();
            }
        
    }
}
