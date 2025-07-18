/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2018, Red Hat, Inc., and individual contributors
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
 * 2110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.additional.testsuite.jdkall.present.ejb.lookup;

import java.util.Properties;
import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.InitialContext;
import org.jboss.eap.additional.testsuite.annotations.EAT;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Properties;
import org.jboss.as.test.shared.TestSuiteEnvironment;

/**
 * @author Jiri Bilek
 * jbilek@redhat.com on 12/03/18.
 * Test for XNIO-320
 *
 * IllegalArgumentException: Parameter 'address' may not be null
 * if invalid-hostname does not resolve to an IP, then you will see the error.
 *
 * Correct behavior should return
 * javax.naming.CommunicationException: WFNAM00018: Failed to connect to remote host [Root exception is java.net.UnknownHostException: invalid-hostname]
 * ...
 * Caused by: java.net.UnknownHostException: invalid-hostname
 *
 * Incorrect behavior returns
 * java.lang.IllegalArgumentException: Parameter 'address' may not be null
 */
@RunWith(Arquillian.class)
@RunAsClient
@EAT({"modules/testcases/jdkAll/Eap72x/ejb/src/main/java","modules/testcases/jdkAll/Eap73x/ejb/src/main/java","modules/testcases/jdkAll/Eap7Plus/ejb/src/main/java","modules/testcases/jdkAll/Eap72x-Proposed/ejb/src/main/java","modules/testcases/jdkAll/Eap71x-Proposed/ejb/src/main/java","modules/testcases/jdkAll/Eap71x/ejb/src/main/java","modules/testcases/jdkAll/WildflyJakarta/ejb/src/main/java#27.0.0.Alpha4","modules/testcases/jdkAll/WildflyRelease-27.0.0.Final/ejb/src/main/java","modules/testcases/jdkAll/EapJakarta/ejb/src/main/java"})
public class LookupToInvalidHostNameTestCase {

   private static final String ARCHIVE_NAME = "test-simple-ejb";
   private static final String DEPLOYMENT = ARCHIVE_NAME + ".jar";

   @Deployment(name = DEPLOYMENT)
   public static Archive<?> deploy() {
      JavaArchive jar = ShrinkWrap.create(JavaArchive.class, ARCHIVE_NAME + ".jar");
      jar.addPackage(TestRemote.class.getPackage());
      jar.addPackage(TestSLSB.class.getPackage());
      return jar;
   }

   /*
   get inicial context and lookup
    */
   public TestRemote lookup(String host) throws Exception {
      Properties props = new Properties();
      props.put(Context.INITIAL_CONTEXT_FACTORY,  "org.wildfly.naming.client.WildFlyInitialContextFactory");
      props.put(Context.PROVIDER_URL, String.format("remote+http://%s:%d", host, 8080));
      Context ic = new InitialContext(props);
      // from some reason the deployment does not have name DEPLOYMENT but "test.war"
      final String lookup = "test-???//" + TestSLSB.class.getSimpleName() + "!" + TestRemote.class.getName();
      return (TestRemote) ic.lookup(lookup);
   }

   /*
   check if correct exception is returned in case invalid hostname
    */
   @Test(expected = CommunicationException.class)
   @OperateOnDeployment(DEPLOYMENT)
   public void testLookUpWithBadHostname() throws Exception {
      TestRemote proxy = lookup("invalid-hostname");
      String echo = "Answer to the Ultimate Question of Life, the Universe, and Everything";
      proxy.invoke(echo);
   }

}
