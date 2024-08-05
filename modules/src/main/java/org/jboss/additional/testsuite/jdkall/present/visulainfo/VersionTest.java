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
Import java.io.*;
import org.junit.runner.RunWith;
import static org.junit.Assert.fail;

@RunWith(Arquillian.class)
@EAT({"modules/testcases/jdkAll/WildflyJakarta/visualinfo/src/main/java#27.0.0.Alpha4","modules/testcases/jdkAll/EapJakarta/visualinfo/src/main/java","modules/testcases/jdkAll/Eap7Plus/visualinfo/src/main/java"})
public class BasicTest {

    @Deployment
    public static Archive<?> getDeployment() {
        JavaArchive archive = ShrinkWrap.create(JavaArchive.class);
        archive.addClass(VisualInfoTest.class);
        return archive;
    }

    @Test
    public void versionExistsTest() {
	String server_dist = System.getProperty("jboss.dist");

	if (server_dist == null)
	    fail("JBoss dist does not exist ...");

	File f = new File(server_dist + "/version.txt");

	if(!f.exists()) {
	    fail("JBoss version.txt file does not exist ...");
	}
    }
}
