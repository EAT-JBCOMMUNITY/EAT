/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2017, Red Hat, Inc., and individual contributors
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
package org.jboss.additional.testsuite.jdkall.present.elytron.audit;

import java.net.URL;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.as.test.integration.security.common.Utils;
import org.jboss.as.test.syslogserver.BlockedSyslogServerEventHandler;
import org.junit.Ignore;
import org.junit.Test;
import org.productivity.java.syslog4j.server.SyslogServer;
import org.productivity.java.syslog4j.server.SyslogServerConfigIF;
import org.productivity.java.syslog4j.server.SyslogServerEventIF;

import static javax.servlet.http.HttpServletResponse.SC_OK;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.junit.Assert.assertTrue;
import static org.jboss.additional.testsuite.jdkall.present.elytron.audit.AbstractAuditLogTestCase.SUCCESSFUL_AUTH_EVENT;
import org.jboss.eap.additional.testsuite.annotations.EAT;

/**
 * Abstract class for Elytron Audit Logging tests. Tests are placed here as well as a couple of syslog-specific helper methods.
 *
 * @author Jan Tymel
 */
@EAT({"modules/testcases/jdkAll/WildflyRelease-13.0.0.Final/elytron/src/main/java","modules/testcases/jdkAll/WildflyRelease-24.0.0.Final/elytron/src/main/java","modules/testcases/jdkAll/Wildfly/elytron/src/main/java#10.0.0*24.0.0","modules/testcases/jdkAll/ServerBeta/elytron/src/main/java","modules/testcases/jdkAll/WildflyRelease-17.0.0.Final/elytron/src/main/java","modules/testcases/jdkAll/WildflyRelease-20.0.0.Final/elytron/src/main/java","modules/testcases/jdkAll/Eap72x/elytron/src/main/java","modules/testcases/jdkAll/Eap72x-Proposed/elytron/src/main/java","modules/testcases/jdkAll/Eap73x/elytron/src/main/java","modules/testcases/jdkAll/Eap7Plus/elytron/src/main/java","modules/testcases/jdkAll/Eap71x-Proposed/elytron/src/main/java","modules/testcases/jdkAll/Eap71x/elytron/src/main/java"})
public abstract class AbstractSyslogAuditLogTestCase extends AbstractAuditLogTestCase {

    /**
     * Tests whether successful authentication was logged.
     */
    @Test
    @OperateOnDeployment(SD_DEFAULT)
    public void testSuccessfulAuth(@ArquillianResource URL url) throws Exception {
        final URL servletUrl = new URL(url.toExternalForm() + "role1");
        final BlockingQueue<SyslogServerEventIF> queue = BlockedSyslogServerEventHandler.getQueue();
        queue.clear();

        Utils.makeCallWithBasicAuthn(servletUrl, USER, PASSWORD, SC_OK);
        assertTrue("Successful authentication was not logged", loggedSuccessfulAuth(queue, USER));
    }

    /**
     * Tests whether failed authentication was logged.
     */
    @Test
    @OperateOnDeployment(SD_DEFAULT)
    public void testFailedAuth(@ArquillianResource URL url) throws Exception {
        final URL servletUrl = new URL(url.toExternalForm() + "role1");
        final BlockingQueue<SyslogServerEventIF> queue = BlockedSyslogServerEventHandler.getQueue();
        queue.clear();

        Utils.makeCallWithBasicAuthn(servletUrl, UNKNOWN_USER, PASSWORD, SC_UNAUTHORIZED);
        assertTrue("Failed authentication was not logged", loggedFailedAuth(queue, UNKNOWN_USER));
    }

    /**
     * Tests whether authentication with empty username was logged.
     */
    @Ignore("https://issues.jboss.org/browse/JBEAP-10997")
    @Test
    @OperateOnDeployment(SD_DEFAULT)
    public void testAuthWithEmptyName() throws Exception {
        final URL servletUrl = new URL(url.toExternalForm() + "role1");
        final BlockingQueue<SyslogServerEventIF> queue = BlockedSyslogServerEventHandler.getQueue();
        queue.clear();

        Utils.makeCallWithBasicAuthn(servletUrl, "", PASSWORD, SC_UNAUTHORIZED);

        assertTrue("Authentication with empty username was not logged", loggedFailedAuth(queue, USER));
    }

    /**
     * Tests whether successful permission check was logged.
     */
    @Test
    @OperateOnDeployment(SD_DEFAULT)
    public void testSuccessfulPermissionCheck() throws Exception {
        final URL servletUrl = new URL(url.toExternalForm() + "role1");
        final BlockingQueue<SyslogServerEventIF> queue = BlockedSyslogServerEventHandler.getQueue();
        queue.clear();

        Utils.makeCallWithBasicAuthn(servletUrl, USER, PASSWORD, SC_OK);

        assertTrue("Successful permission check was not logged", loggedSuccessfulPermissionCheck(queue, USER));
    }

    /**
     * Tests whether failed permission check was logged.
     */
    @Test
    @OperateOnDeployment(SD_WITHOUT_LOGIN_PERMISSION)
    public void testFailedPermissionCheck() throws Exception {
        final URL servletUrl = new URL(url.toExternalForm() + "role1");
        final BlockingQueue<SyslogServerEventIF> queue = BlockedSyslogServerEventHandler.getQueue();
        queue.clear();

        Utils.makeCallWithBasicAuthn(servletUrl, USER, PASSWORD, SC_UNAUTHORIZED);

        assertTrue("Failed permission check was not logged", loggedFailedPermissionCheck(queue, USER));
    }

    protected static boolean loggedSuccessfulAuth(BlockingQueue<SyslogServerEventIF> queue, String user) throws Exception {
        return loggedAuthResult(queue, user, SUCCESSFUL_AUTH_EVENT);
    }

    protected static boolean loggedFailedAuth(BlockingQueue<SyslogServerEventIF> queue, String user) throws Exception {
        return loggedAuthResult(queue, user, UNSUCCESSFUL_AUTH_EVENT);
    }

    protected static boolean loggedSuccessfulPermissionCheck(BlockingQueue<SyslogServerEventIF> queue, String user) throws Exception {
        return loggedAuthResult(queue, user, SUCCESSFUL_PERMISSION_CHECK_EVENT);
    }

    protected static boolean loggedFailedPermissionCheck(BlockingQueue<SyslogServerEventIF> queue, String user) throws Exception {
        return loggedAuthResult(queue, user, UNSUCCESSFUL_PERMISSION_CHECK_EVENT);
    }

    protected static boolean loggedAuthResult(BlockingQueue<SyslogServerEventIF> queue, String user, String expectedEvent) throws Exception {
        SyslogServerEventIF log = queue.poll(15L, TimeUnit.SECONDS);

        if (log == null) {
            return false;
        }

        String logString = log.getMessage();

        return (logString.contains(expectedEvent) && logString.contains(user));
    }

    protected static void setupAndStartSyslogServer(SyslogServerConfigIF config, String host, int port, String protocol) throws Exception {
        // clear created server instances (TCP/UDP)
        SyslogServer.shutdown();

        config.setPort(port);
        config.setHost(host);
        config.setUseStructuredData(true);
        config.addEventHandler(new BlockedSyslogServerEventHandler());
        SyslogServer.createInstance(protocol, config);
        // start syslog server
        SyslogServer.getThreadedInstance(protocol);
    }

    protected static void stopSyslogServer() throws Exception {
        SyslogServer.shutdown();
    }
}
