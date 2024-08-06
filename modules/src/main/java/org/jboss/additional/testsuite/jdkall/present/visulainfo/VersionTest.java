/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2016, Red Hat, Inc., and individual contributors
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
package org.jboss.additional.testsuite.jdkall.present.visualinfo;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.eap.additional.testsuite.annotations.EAT;
import org.junit.Test;
import java.io.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.FileUtils;
import org.junit.runner.RunWith;
import org.jboss.arquillian.container.test.api.RunAsClient;
import static org.junit.Assert.fail;

@RunWith(Arquillian.class)
@RunAsClient
@EAT({"modules/testcases/jdkAll/EapJakarta/visualinfo/src/main/java","modules/testcases/jdkAll/Eap7Plus/visualinfo/src/main/java"})
public class VersionTest {
    private static final String serverLogPath = "../../../../../servers/eap7/build/target/jbossas";

    @Deployment
    public static Archive<?> getDeployment() {
        JavaArchive archive = ShrinkWrap.create(JavaArchive.class);
        archive.addClass(VersionTest.class);
        return archive;
    }

    @Test
    public void versionExistsTest() {
	String server_dist = getJBossHome();
	if (server_dist == null)
	    fail("JBoss dist does not exist ...");

	File f = new File(server_dist + "/version.txt");

	if(!f.exists()) {
	    fail("JBoss version.txt file does not exist ...");
	}
    }

    @Test
    public void printVersionTest() throws Exception {
	String server_dist = getJBossHome();
	if (server_dist == null)
	    fail("JBoss dist does not exist ...");

            FileInputStream inputStream = new FileInputStream(server_dist + "/version.txt");
            try {
                String everything = IOUtils.toString(inputStream);
		File file = new File(server_dist + "/../../../../../../visualInfo.txt");
                FileUtils.writeStringToFile(file, everything, true);
            } finally {
                inputStream.close();
            }
	

    }
    
    private static String getJBossHome() {
        String jbossHome = System.getProperty("jboss.dist");
	    if (jbossHome == null) {
	        jbossHome = System.getProperty("jboss.home");
	    }
	    if (jbossHome == null) {
	        jbossHome = new File("").getAbsolutePath() + "/" + serverLogPath;
	    }
	    return jbossHome == null ? System.getenv("JBOSS_HOME") : jbossHome;
    }
}
