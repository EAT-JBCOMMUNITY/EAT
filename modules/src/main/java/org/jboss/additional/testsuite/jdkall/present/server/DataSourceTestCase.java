/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2011, Red Hat, Inc., and individual contributors
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
package org.jboss.additional.testsuite.jdkall.present.server;

import org.jboss.as.test.integration.management.base.AbstractCliTestBase;
import static org.junit.Assert.assertTrue;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.as.test.integration.management.util.CLIOpResult;
import org.jboss.eap.additional.testsuite.annotations.EAT;
import org.jboss.eap.additional.testsuite.annotations.ATTest;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
@RunAsClient
@EAT({"modules/testcases/jdkAll/Eap73x/server/src/main/java","modules/testcases/jdkAll/Eap7Plus/server/src/main/java#7.4.11","modules/testcases/jdkAll/WildflyJakarta/server/src/main/java#29.0.0","modules/testcases/jdkAll/EapJakarta/server/src/main/java"})
public class DataSourceTestCase extends AbstractCliTestBase {

    @Deployment
    public static Archive<?> getDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "DataSourceTestCase.war");
        war.addClass(DataSourceTestCase.class);
        return war;
    }

    @BeforeClass
    public static void before() throws Exception {
        AbstractCliTestBase.initCLI();
    }

    @AfterClass
    public static void after() throws Exception {
        AbstractCliTestBase.closeCLI();
    }


    @Test
    public void testExpressionInDataSource() throws Exception {
        testExpressionInJavaContext();
    }

    @ATTest({"modules/testcases/jdkAll/Eap73x/server/src/main/java","modules/testcases/jdkAll/Eap7Plus/server/src/main/java#7.4.13","modules/testcases/jdkAll/WildflyJakarta/server/src/main/java#29.0.0"})
    public void testReoveServerViaCli() throws Exception {
        testRemoveServer();
    }

    private void testExpressionInJavaContext() throws Exception {

        // remove data source
        cli.sendLine("/subsystem=datasources/data-source=ExampleDS:read-attribute(name=use-java-context)");

        //check the data source is not listed
        cli.sendLine("/subsystem=datasources/data-source=ExampleDS:read-attribute(name=use-java-context,resolve-expressions=true)");
        cli.sendLine("/subsystem=datasources/data-source=ExampleDS:test-connection-in-pool");
        CLIOpResult result = cli.readAllAsOpResult();
        assertTrue(result.isIsOutcomeSuccess());
    }

    private void testRemoveServer() throws Exception {

        cli.sendLine("/subsystem=undertow/server=abc:remove()");
        CLIOpResult result = cli.readAllAsOpResult();
        assertTrue(result.isIsOutcomeSuccess());
    }
}
