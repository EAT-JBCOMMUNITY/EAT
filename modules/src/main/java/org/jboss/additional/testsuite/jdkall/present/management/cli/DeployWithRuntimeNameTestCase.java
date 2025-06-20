/*
 * Copyright (C) 2013 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file
 * in the distribution for a full listing of individual contributors.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package org.jboss.additional.testsuite.jdkall.present.management.cli;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.as.cli.CommandContext;
import org.jboss.as.cli.CommandLineException;
import org.jboss.as.test.integration.common.HttpRequest;
import org.jboss.as.test.integration.management.base.AbstractCliTestBase;
import org.jboss.as.test.integration.management.util.CLITestUtil;
import org.jboss.as.test.integration.management.util.SimpleHelloWorldServlet;
import org.jboss.eap.additional.testsuite.annotations.EAT;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.impl.base.exporter.zip.ZipExporterImpl;
import org.junit.After;
import org.junit.AfterClass;

import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author <a href="mailto:ehugonne@redhat.com">Emmanuel Hugonnet</a> (c) 2013 Red Hat, inc.
 */
@RunWith(Arquillian.class)
@RunAsClient
@EAT({"modules/testcases/jdkAll/WildflyRelease-13.0.0.Final/management/src/main/java","modules/testcases/jdkAll/Eap72x/management/src/main/java","modules/testcases/jdkAll/Eap72x-Proposed/management/src/main/java","modules/testcases/jdkAll/Eap72x-Proposed/management/src/main/java","modules/testcases/jdkAll/Eap73x/management/src/main/java","modules/testcases/jdkAll/Eap7Plus/management/src/main/java","modules/testcases/jdkAll/Eap71x-Proposed/management/src/main/java","modules/testcases/jdkAll/Eap71x/management/src/main/java","modules/testcases/jdkAll/Eap7.1.0.Beta/management/src/main/java","modules/testcases/jdkAll/Eap70x/management/src/main/java","modules/testcases/jdkAll/Eap70x-Proposed/management/src/main/java","modules/testcases/jdkAll/WildflyRelease-24.0.0.Final/management/src/main/java","modules/testcases/jdkAll/Wildfly/management/src/main/java#13.0.0*27.0.0.Alpha3","modules/testcases/jdkAll/WildflyJakarta/management/src/main/java#27.0.0.Alpha4","modules/testcases/jdkAll/WildflyRelease-27.0.0.Final/management/src/main/java","modules/testcases/jdkAll/ServerBeta/management/src/main/java","modules/testcases/jdkAll/WildflyRelease-17.0.0.Final/management/src/main/java","modules/testcases/jdkAll/WildflyRelease-20.0.0.Final/management/src/main/java","modules/testcases/jdkAll/EapJakarta/management/src/main/java"})
public class DeployWithRuntimeNameTestCase {

    @ArquillianResource
    URL url;


    private CommandContext ctx;
    private File warFile;
    private String baseUrl;

    public static final String RUNTIME_NAME = "SimpleServlet.war";
    public static final String OTHER_RUNTIME_NAME = "OtherSimpleServlet.war";
    private static final String APP_NAME = "simple1";
    private static final String OTHER_APP_NAME = "simple2";

     @Deployment
    public static Archive<?> getDeployment() {
        return ShrinkWrap.create(JavaArchive.class, "dummy.jar").addClass(DeployWithRuntimeNameTestCase.class);
    }

    @BeforeClass
    public static void setupCli() throws Exception {
        AbstractCliTestBase.initCLI();
    }

    @Before
    public void setUp() throws Exception {
        ctx = CLITestUtil.getCommandContext();
        ctx.connectController();
        baseUrl = getBaseURL(url);
    }

    private File createWarFile(String content) throws IOException {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "HelloServlet.war");
        war.addClass(SimpleHelloWorldServlet.class);
        war.addAsWebInfResource("web.xml");
        war.addAsWebResource(new StringAsset(content), "page.html");
        String path = DeployWithRuntimeNameTestCase.class.getResource("/").getPath();
        System.out.println("path : " + path);
        File tempFile = new File(path, "HelloServlet.war");
        new ZipExporterImpl(war).exportTo(tempFile, true);
        return tempFile;
    }

    @AfterClass
    public static void cleanup() throws Exception {
        AbstractCliTestBase.closeCLI();
    }

    @After
    public void undeployAll() {
        assertThat(warFile.delete(), is(true));
        if(ctx != null) {
            ctx.handleSafe("undeploy " + APP_NAME);
            ctx.handleSafe("undeploy " +OTHER_APP_NAME);
            ctx.terminateSession();
        }
    }

    @Test
    public void testDeployWithSameRuntimeName() throws Exception {
        warFile = createWarFile("Version1");
        ctx.handle(buildDeployCommand(RUNTIME_NAME, APP_NAME));
        checkURL("SimpleServlet/page.html", "Version1",  false);

        warFile = createWarFile("Shouldn't be deployed, as runtime already exist");
        try {
        ctx.handle(buildDeployCommand(RUNTIME_NAME, OTHER_APP_NAME));
        } catch(CommandLineException ex) {
            assertThat(ex.getMessage(), containsString("WFLYSRV0205"));
        }
        checkURL("SimpleServlet/page.html", "Version1",  false);
    }

    @Test
    public void testDeployWithDifferentRuntimeName() throws Exception {
        warFile = createWarFile("Version1");
        ctx.handle(buildDeployCommand(RUNTIME_NAME, APP_NAME));
        checkURL("SimpleServlet/page.html", "Version1",  false);
        warFile = createWarFile("Version2");
        ctx.handle(buildDeployCommand(OTHER_RUNTIME_NAME, OTHER_APP_NAME));
        checkURL("SimpleServlet/page.html", "Version1", false);
        checkURL("OtherSimpleServlet/page.html", "Version2",false);
    }

    private String buildDeployCommand(String runtimeName, String name) {
        return "deploy " + warFile.getAbsolutePath() + " --runtime-name=" + runtimeName + " --name=" + name;
    }

    private void checkURL(String path, String content, boolean shouldFail) throws Exception {
        boolean failed = false;
        try {
            String response = HttpRequest.get(baseUrl + path, 10, TimeUnit.SECONDS);
            assertThat(response, containsString(content));
        } catch (Exception e) {
            failed = true;
            if (!shouldFail) {
                throw new Exception("Http request failed.", e);
            }
        }
        if (shouldFail) {
            assertThat(baseUrl + path, failed, is(true));
        }
    }

    private final String getBaseURL(URL url) throws MalformedURLException {
        return new URL(url.getProtocol(), url.getHost(), url.getPort(), "/").toString();
    }
}
