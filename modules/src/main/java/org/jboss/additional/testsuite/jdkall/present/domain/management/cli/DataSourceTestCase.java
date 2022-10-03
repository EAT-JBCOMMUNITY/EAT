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
package org.jboss.additional.testsuite.jdkall.present.domain.management.cli;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Map;

import org.jboss.additional.testsuite.jdkall.present.domain.driver.FooDriver;
import org.jboss.as.test.integration.domain.management.util.DomainTestSupport;
import org.jboss.additional.testsuite.jdkall.present.domain.suites.CLITestSuite;
import org.jboss.as.test.integration.management.base.AbstractCliTestBase;
import org.jboss.as.test.integration.management.util.CLIOpResult;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.impl.base.exporter.zip.ZipExporterImpl;
import org.jboss.eap.additional.testsuite.annotations.EAT;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Dominik Pospisil <dpospisi@redhat.com>
 */
@EAT({"modules/testcases/jdkAll/WildflyRelease-13.0.0.Final/domain/src/main/java","modules/testcases/jdkAll/WildflyRelease-24.0.0.Final/domain/src/main/java","modules/testcases/jdkAll/Wildfly/domain/src/main/java#10.0.0*24.0.0","modules/testcases/jdkAll/ServerBeta/domain/src/main/java","modules/testcases/jdkAll/WildflyRelease-17.0.0.Final/domain/src/main/java","modules/testcases/jdkAll/WildflyRelease-20.0.0.Final/domain/src/main/java","modules/testcases/jdkAll/Eap72x/domain/src/main/java","modules/testcases/jdkAll/Eap72x-Proposed/domain/src/main/java","modules/testcases/jdkAll/Eap7Plus/domain/src/main/java#7.3.0*7.3.9","modules/testcases/jdkAll/Eap71x-Proposed/domain/src/main/java","modules/testcases/jdkAll/Eap71x/domain/src/main/java"})
public class DataSourceTestCase extends AbstractCliTestBase {

    private static String[] profileNames;

    private static final String[][] DS_PROPS = new String[][] {
        {"idle-timeout-minutes", "5"}
    };

    @BeforeClass
    public static void before() throws Exception {

        CLITestSuite.createSupport(DataSourceTestCase.class.getSimpleName());
        AbstractCliTestBase.initCLI(DomainTestSupport.masterAddress);
    }

    @AfterClass
    public static void after() throws Exception {
        AbstractCliTestBase.closeCLI();
        CLITestSuite.stopSupport();
    }

    @Before
    public void init() {
         profileNames = CLITestSuite.serverProfiles.keySet().toArray(new String[CLITestSuite.serverProfiles.size()]);
    }

    @Test
    public void testDataSource() throws Exception {
        testAddDataSource("h2");
        testModifyDataSource("h2");
        testRemoveDataSource("h2");
    }

    @Test
    public void testXaDataSource() throws Exception {
        testAddXaDataSource();
        testModifyXaDataSource();
        testRemoveXaDataSource();
    }

    private void testAddDataSource(String driverName) throws Exception {

        // add data source
        cli.sendLine("data-source add --profile=" + profileNames[0] + " --jndi-name=java:jboss/datasources/TestDS_" + driverName +" --name=java:jboss/datasources/TestDS_" + driverName + " --driver-name=" + driverName + " --connection-url=jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");

        // check the data source is listed
        cli.sendLine("cd /profile=" + profileNames[0] + "/subsystem=datasources/data-source");
        cli.sendLine("ls");
        String ls = cli.readOutput();
        assertTrue("Datasource not found: " + ls, ls.contains("java:jboss/datasources/TestDS_" + driverName));

        // check that it is available through JNDI
        // TODO implement when @ArquillianResource InitialContext is done

    }

    private void testRemoveDataSource(String driverName) throws Exception {

        // remove data source
        cli.sendLine("data-source remove --profile=" + profileNames[0] + " --name=java:jboss/datasources/TestDS_" + driverName);

        //check the data source is not listed
        cli.sendLine("cd /profile=" + profileNames[0] + "/subsystem=datasources/data-source");
        cli.sendLine("ls");
        String ls = cli.readOutput();
        assertFalse(ls.contains("java:jboss/datasources/TestDS_" + driverName));

    }

    private void testModifyDataSource(String jndiName) throws Exception {
        StringBuilder cmd = new StringBuilder("data-source --profile=" + profileNames[0] + " --name=java:jboss/datasources/TestDS_" + jndiName);
        for (String[] props : DS_PROPS) {
            cmd.append(" --");
            cmd.append(props[0]);
            cmd.append("=");
            cmd.append(props[1]);
        }
        cli.sendLine(cmd.toString());

        // check that datasource was modified
        cli.sendLine("/profile=" + profileNames[0] + "/subsystem=datasources/data-source=java\\:jboss\\/datasources\\/TestDS_" + jndiName + ":read-resource(recursive=true)");
        CLIOpResult result = cli.readAllAsOpResult();
        assertTrue(result.isIsOutcomeSuccess());
        assertTrue(result.getResult() instanceof Map);
        Map dsProps = (Map) result.getResult();
        for (String[] props : DS_PROPS) assertTrue(dsProps.get(props[0]).equals(props[1]));

    }

    private void testAddXaDataSource() throws Exception {

        // add data source
        cli.sendLine("xa-data-source add --profile=" + profileNames[0] +
                " --jndi-name=java:jboss/datasources/TestXADS --name=java:jboss/datasources/TestXADS --driver-name=h2 --xa-datasource-properties=ServerName=localhost,PortNumber=50011");

        //check the data source is listed
        cli.sendLine("cd /profile=" + profileNames[0] + "/subsystem=datasources/xa-data-source");
        cli.sendLine("ls");
        String ls = cli.readOutput();
        assertTrue(ls.contains("java:jboss/datasources/TestXADS"));

    }

    private void testModifyXaDataSource() throws Exception {
        StringBuilder cmd = new StringBuilder("xa-data-source --profile=" + profileNames[0] + " --name=java:jboss/datasources/TestXADS");
        for (String[] props : DS_PROPS) {
            cmd.append(" --");
            cmd.append(props[0]);
            cmd.append("=");
            cmd.append(props[1]);
        }
        cli.sendLine(cmd.toString());

        // check that datasource was modified
        cli.sendLine("/profile=" + profileNames[0] + "/subsystem=datasources/xa-data-source=java\\:jboss\\/datasources\\/TestXADS:read-resource(recursive=true)");
        CLIOpResult result = cli.readAllAsOpResult();
        assertTrue(result.isIsOutcomeSuccess());
        assertTrue(result.getResult() instanceof Map);
        Map dsProps = (Map) result.getResult();
        for (String[] props : DS_PROPS) assertTrue(dsProps.get(props[0]).equals(props[1]));

    }

    private void testRemoveXaDataSource() throws Exception {

        // remove data source
        cli.sendLine("xa-data-source remove  --profile=" + profileNames[0] + " --name=java:jboss/datasources/TestXADS");

        //check the data source is not listed
        cli.sendLine("cd /profile=" + profileNames[0] + "/subsystem=datasources/xa-data-source");
        cli.sendLine("ls");
        String ls = cli.readOutput();
        Assert.assertNull(ls);

    }

}