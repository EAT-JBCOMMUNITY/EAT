/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2015, Red Hat, Inc., and individual contributors
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

package org.jboss.hal.testsuite.cli;

import org.jboss.as.cli.scriptsupport.CLI;

/**
 * Use {@link CliClientFactory} to obtain instance.
 * @author rhatlapa (rhatlapa@redhat.com)
 * @deprecated use creaper
 */
public class DomainCliClient extends CliClient {
    private final String profile;
    private final String domainHost;

    DomainCliClient(String profile) {
        this.profile = profile;
        this.domainHost = "master";
    }

    DomainCliClient(String profile, String domainHost) {
        this.profile = profile;
        this.domainHost = domainHost;
    }

    DomainCliClient(CliConfiguration cliConfig, String profile) {
        super(cliConfig);
        this.profile = profile;
        this.domainHost = "master";
    }

    DomainCliClient(CliConfiguration cliConfig, String profile, String host) {
        super(cliConfig);
        this.profile = profile;
        this.domainHost = host;
    }

    /**
     * Executes command and returns full output as CLI.Result, it automatically updates the command address to match set profile
     *
     * @param command Command to be executed
     * @return CLI.Result object containing request, response and success status of the operation all in one
     */
    @Override
    public CLI.Result executeCommand(String command) {
        String updatedCommand = command;
        if (command.startsWith("/subsystem")) {
            updatedCommand = "/profile=" + profile + command;
        }
        if (command.startsWith("/core-service")) {
            updatedCommand = "/host=" + domainHost + command;
        }
        return super.executeCommand(updatedCommand);
    }

    /**
     * Shutdown and re-start the server only if the server is in restart required state or forced
     *
     * @param forced whether server should be restarted even when server is not in restart required state
     */
    @Override
    public void restart(boolean forced) {
        if (forced || restartRequired()) {
            restart();
        }
    }

    /**
     * Check whether given host is in restart-required state.
     *
     * @return <code>true</code>, if server is in restart-required state, else <code>false</code>.
     */
    public boolean restartRequired(String host) {
        String result = readAttribute("/host=" + host, "host-state");
        return result.equals("restart-required");
    }

    /**
     * Check whether a server on default host is in restart-required state.
     *
     * @return <code>true</code>, if server is in restart-required state, else <code>false</code>.
     */
    @Override
    public boolean restartRequired() {
        return restartRequired(domainHost);
    }

    /**
     * Shutdown and re-start the host.
     */
    public boolean restart(String host) {
        String cmd = CliUtils.buildCommand("/host=" + host, ":shutdown", new String[]{"restart=true"});
        executeCommand(cmd);
        Library.letsSleep(1500);

        DomainManager dm = new DomainManager(this);
        return dm.waitUntilAvailable();
    }


    /**
     * Shutdown and re-start the default host.
     */
    @Override
    public boolean restart() {
        return restart(domainHost);
    }

    @Override
    public boolean reloadRequired() {
        return reloadRequired(domainHost);
    }

    @Override
    public boolean reload() {
        return reload(domainHost);
    }

    public boolean reloadRequired(String host) {
        String result = readAttribute("/host=" + host, "host-state");
        return result.equals("reload-required");
    }

    public boolean reload(String host) {
        String cmd = "/host=" + host + ":reload()";
        executeCommand(cmd);
        Library.letsSleep(500);

        DomainManager dm = new DomainManager(this);
        return dm.waitUntilAvailable();
    }

    /**
     * @return the name of default host used in CLI commands
     */
    public String getDomainHost() {
        return domainHost;
    }
}
