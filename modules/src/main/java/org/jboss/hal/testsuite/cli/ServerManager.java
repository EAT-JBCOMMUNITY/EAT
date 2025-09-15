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

/**
 * @author jcechace
 * @deprecated use creaper
 */
public abstract class ServerManager {

    protected CliClient cliClient;
    public static final int DEFAULT_TIMEOUT = 60000; //ms


    public ServerManager(CliClient cliClient) {
        this.cliClient = cliClient;
    }


    /**
     * Returns an instance of specific implementation of ServerManager based on type
     * of given CLI client.
     *
     * @param cliClient CLI client to be used with this manager
     * @return
     */
    public static ServerManager getInstance(CliClient cliClient) {
        if (cliClient instanceof DomainCliClient) {
            return new DomainManager(cliClient);
        }
        return new StandaloneManager(cliClient);
    }

    /**
     * Detects version of the EAP server via CLI and returns it
     * @return version of EAP server detected via CLI
     */
    public String getVersion() {
        return cliClient.readAttribute("/", "product-version");
    }


    /**
     * Checks whether server is in running state or not using CLI
     *
     * @return true if server is in running state, false otherwise
     */
    public abstract boolean isInRunningState();



    /**
     * Checks whether server will become available before default timeout passes
     *
     * @return true if server becomes available before default timeout passes, false otherwise
     */
    public abstract boolean waitUntilAvailable();

    /**
     * Checks whether server will become available before specified timeout passes
     *
     * @param timeout max time to wait for server to start, in milliseconds
     * @return true if server becomes available before timeout passes, false otherwise
     */
    public abstract boolean waitUntilAvailable(long timeout);

}

