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
package org.jboss.additional.testsuite.jdkall.present.management.cli;

import java.net.URL;
import org.jboss.as.test.integration.management.base.AbstractCliTestBase;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Map;
import org.junit.Assert;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.as.test.integration.common.JndiServlet;
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

/**
 *
 * @author Dominik Pospisil <dpospisi@redhat.com>
 */
@RunWith(Arquillian.class)
@RunAsClient
@EAT({"modules/testcases/jdkAll/WildflyRelease-13.0.0.Final/management/src/main/java","modules/testcases/jdkAll/Eap72x/management/src/main/java","modules/testcases/jdkAll/Eap72x-Proposed/management/src/main/java","modules/testcases/jdkAll/Eap72x-Proposed/management/src/main/java","modules/testcases/jdkAll/Eap73x/management/src/main/java","modules/testcases/jdkAll/Eap7Plus/management/src/main/java","modules/testcases/jdkAll/Eap71x-Proposed/management/src/main/java","modules/testcases/jdkAll/Eap71x/management/src/main/java","modules/testcases/jdkAll/Eap7.1.0.Beta/management/src/main/java","modules/testcases/jdkAll/Eap70x/management/src/main/java","modules/testcases/jdkAll/Eap70x-Proposed/management/src/main/java","modules/testcases/jdkAll/WildflyRelease-24.0.0.Final/management/src/main/java","modules/testcases/jdkAll/Wildfly/management/src/main/java#13.0.0*27.0.0.Alpha3","modules/testcases/jdkAll/WildflyJakarta/management/src/main/java#27.0.0.Alpha4","modules/testcases/jdkAll/WildflyRelease-27.0.0.Final/management/src/main/java","modules/testcases/jdkAll/ServerBeta/management/src/main/java","modules/testcases/jdkAll/WildflyRelease-17.0.0.Final/management/src/main/java","modules/testcases/jdkAll/WildflyRelease-20.0.0.Final/management/src/main/java"})
public class DataSourceTestCase extends AbstractCliTestBase {

    @ArquillianResource URL url;

    private static final String[][] DS_PROPS = new String[][] {
        {"idle-timeout-minutes", "5"}
    };

    @Deployment
    public static Archive<?> getDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "DataSourceTestCase.war");
        war.addClass(DataSourceTestCase.class);
        war.addClass(JndiServlet.class);
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
    public void testDataSource() throws Exception {
        testAddDataSource();
        testModifyDataSource();
        //testRemoveDataSource();
    }

    //@Test
    public void testXaDataSource() throws Exception {
        testAddXaDataSource();
        testModifyXaDataSource();
        //testRemoveXaDataSource();
    }

    @ATTest({"modules/testcases/jdkAll/Eap73x/management/src/main/java","modules/testcases/jdkAll/Eap7Plus/management/src/main/java#7.4.6","modules/testcases/jdkAll/Wildfly/management/src/main/java#27.0.0.Alpha1"})
    public void testExpressionInDataSource() throws Exception {
        testExpressionInJavaContext();
    }

    private void testAddDataSource() throws Exception {

        // add data source
        cli.sendLine("data-source add --name=TestDS2 --jndi-name=java:jboss/datasources/TestDS2 --driver-name=h2 --connection-url=jdbc:h2:mem:test;DB_CLOSE_DELAY=-1 --enabled=true");

        // check the data source is listed
        cli.sendLine("cd /subsystem=datasources/data-source");
        cli.sendLine("ls");
        String ls = cli.readOutput();
        assertTrue(ls.contains("TestDS2"));

        // check that it is available through JNDI
        String jndiClass = JndiServlet.lookup(url.toString(), "java:jboss/datasources/TestDS2");
        Assert.assertTrue(javax.sql.DataSource.class.isAssignableFrom(Class.forName(jndiClass)));

    }

    private void testRemoveDataSource() throws Exception {

        // disable data source
        //cli.sendLine("data-source disable --name=TestDS2");

        // remove data source
        //cli.sendLine("data-source remove --name=TestDS2");
        cli.sendLine("/subsystem=datasources/data-source=TestDS2:remove{allow-resource-service-restart=true}");
        cli.sendLine("reload");

        //check the data source is not listed
        cli.sendLine("cd /subsystem=datasources/data-source");
        cli.sendLine("ls");
        String ls = cli.readOutput();
        assertFalse(ls.contains("java:jboss/datasources/TestDS2"));

        // check that it is not available through JNDI
        String jndiClass = JndiServlet.lookup(url.toString(), "java:jboss/datasources/TestDS2");
        Assert.assertEquals(JndiServlet.NOT_FOUND, jndiClass);

    }

    private void testModifyDataSource() throws Exception {
        StringBuilder cmd = new StringBuilder("data-source --name=TestDS2");
        for (String[] props : DS_PROPS) {
            cmd.append(" --");
            cmd.append(props[0]);
            cmd.append("=");
            cmd.append(props[1]);
        }
        cli.sendLine(cmd.toString());

        // check that datasource was modified
        cli.sendLine("/subsystem=datasources/data-source=TestDS2:read-resource(recursive=true)");
        CLIOpResult result = cli.readAllAsOpResult();
        assertTrue(result.getResult().toString(), result.isIsOutcomeSuccess());
        assertTrue(result.getResult() instanceof Map);
        Map dsProps = (Map) result.getResult();
        for (String[] props : DS_PROPS) assertTrue(dsProps.get(props[0]).equals(props[1]));

    }


    private void testAddXaDataSource() throws Exception {

        // add data source
        cli.sendLine("xa-data-source add --name=TestXADS --jndi-name=java:jboss/datasources/TestXADS --driver-name=h2 --xa-datasource-properties=ServerName=localhost,PortNumber=50011 --enabled=false");

        //check the data source is listed
        cli.sendLine("cd /subsystem=datasources/xa-data-source");
        cli.sendLine("ls");
        String ls = cli.readOutput();
        assertTrue(ls.contains("TestXADS"));

        // add URL property
        cli.sendLine(
                "/subsystem=datasources/xa-data-source=TestXADS/xa-datasource-properties=URL:add(value=\"jdbc:h2:mem:test\")");

        // enable data source
        cli.sendLine("xa-data-source enable --name=TestXADS");

        // check that it is available through JNDI
        String jndiClass = JndiServlet.lookup(url.toString(), "java:jboss/datasources/TestXADS");
        Assert.assertTrue(javax.sql.DataSource.class.isAssignableFrom(Class.forName(jndiClass)));


    }

    private void testRemoveXaDataSource() throws Exception {

        // remove data source
        cli.sendLine("xa-data-source remove --name=TestXADS");

        //check the data source is not listed
        cli.sendLine("cd /subsystem=datasources/xa-data-source");
        cli.sendLine("ls");
        String ls = cli.readOutput();
        Assert.assertNull(ls);

        // check that it is no more available through JNDI
        String jndiClass = JndiServlet.lookup(url.toString(), "java:jboss/datasources/TestXADS");
        Assert.assertEquals(JndiServlet.NOT_FOUND, jndiClass);

    }

    private void testModifyXaDataSource() throws Exception {
        StringBuilder cmd = new StringBuilder("xa-data-source --name=TestXADS");
        for (String[] props : DS_PROPS) {
            cmd.append(" --");
            cmd.append(props[0]);
            cmd.append("=");
            cmd.append(props[1]);
        }
        cli.sendLine(cmd.toString());

        // check that datasource was modified
        cli.sendLine("/subsystem=datasources/xa-data-source=TestXADS:read-resource(recursive=true)");
        CLIOpResult result = cli.readAllAsOpResult();
        assertTrue(result.isIsOutcomeSuccess());
        assertTrue(result.getResult() instanceof Map);
        Map<String,Object> dsProps = (Map<String, Object>) result.getResult();
        for (String[] props : DS_PROPS) assertTrue(dsProps.get(props[0]).equals(props[1]));

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
}
