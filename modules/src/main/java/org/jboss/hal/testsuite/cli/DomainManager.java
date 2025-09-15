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

import org.jboss.dmr.ModelNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Martin Simka
 * @deprecated use creaper
 */
public class DomainManager extends ServerManager {

    private static final Logger log = LoggerFactory.getLogger(DomainManager.class);

    public DomainManager(CliClient cliClient) {
        super(cliClient);
    }

    private String getDefaultHost() {
        if (!(cliClient instanceof DomainCliClient) ) {
            throw new IllegalStateException("CLI client currently in use is not an instance of DomainCliClient");
        }

        String host = ((DomainCliClient) cliClient).getDomainHost();
        return host;
    }

    /**
     * Resolves expression on host
     * i.e. ${jboss.bind.address:127.0.0.1}
     *
     * @param host       name of eap host
     * @param expression expression to evaluate
     * @return value of evaluated expression
     */
    public String resolveExpression(final String host, final String expression) {
        String cmdTemplate = "/host=%s:resolve-expression(expression=\"%s\")";
        return cliClient.executeForResult(String.format(cmdTemplate, host, expression));
    }

    /**
     * Resolves expression on default host of CLI client currently in use.
     *
     * @param expression expression to evaluate
     * @return value of evaluated expression
     */
    public String resolveExpression(final String expression) {
        return resolveExpression(getDefaultHost(), expression);
    }

    /**
     * Lists all hosts in domain
     *
     * @return list of all hosts in domain
     */
    public List<String> listHosts() {
        List<String> list = new ArrayList<String>();
        ModelNode response = cliClient.executeForResponse("/:read-children-names(child-type=host)");
        List<ModelNode> hostNameList = response.get("result").asList();
        for (ModelNode hostName : hostNameList) {
            list.add(hostName.asString());
        }
        return list;
    }

    /**
     * Lists all servers on given host
     *
     * @param host   name of eap host
     * @return list of all servers on given host
     */
    public List<String> listServers(final String host) {
        List<String> list = new ArrayList<String>();
        List<ModelNode> servers = listServerNodes(host);

        for (ModelNode serverName : servers) {
            list.add(serverName.asString());
        }
        return list;
    }

    /**
     * Lists all servers on default host of CLI client currently in use
     *
     * @return list of all servers on given host
     */
    public List<String> listServers() {
        return  listRunningServers(getDefaultHost());
    }

    /**
     * Lists all running servers on given host
     *
     * @param host   name of eap host
     * @return list of all servers on given host
     */
    public  List<String> listRunningServers(final String host) {
        List<String> list = new ArrayList<String>();
        List<ModelNode> serverList = listRunningServerNodes(host);
        for (ModelNode server : serverList) {
            list.add(server.asString());
        }
        return list;
    }

    /**
     * Lists all running servers on default host of CLI client currently in use.
     *
     * @return list of all servers on given host
     */
    public List<String> listRunningServers() {
        return listRunningServers(getDefaultHost());
    }



    /**
     * Checks whether servers on given host are in running state.
     *
     * @param servers list of server name to be checked
     * @param host   name of eap host
     * @return true if host and all servers are in running state, false otherwise
     */
    public boolean isInRunningState(final List<String> servers, final String host) {
        List<String> remaining = new ArrayList<String>(servers);
        try {
            List<ModelNode> serverList = listRunningServerNodes(host);
            for (ModelNode server : serverList) {
                String state = server.get("server-state").asString();
                if (!state.equalsIgnoreCase("running")) {
                    return false;
                }
                remaining.remove(server.asString()); // server is running, remove from remaining
            }

            return remaining.isEmpty(); // empty if all servers are running
        } catch (IllegalStateException ex) {
            log.info("Unable to connect via CLI => host is probably down");
            return false;
        }
    }

    /**
     * Checks whether the default host of CLI client currently in use is in running state.
     *
     * @return true if server is in running state, false otherwise
     */
    @Override
    public boolean isInRunningState() {
        return isInRunningState(getDefaultHost());
    }

    /**
     * Checks whether host is in running state.
     *
     * @return true if server is in running state, false otherwise
     */
    public boolean isInRunningState(String host) {
        try {

            return cliClient.readAttribute("/host=" + host, "host-state").equals("running");
        } catch (IllegalStateException ex) {
            log.info("Unable to read host state  via CLI => host might be down");
            return false;
        }
    }

    /**
     * List all server groups in domain
     *
     * @param client cli client to use for running cli operations
     * @return list of all server groups in domain
     */
    public List<String> listServerGroups(final CliClient client) {
        List<String> list = new ArrayList<String>();
        ModelNode hostMasterResponse = client.executeForResponse("/:read-children-names(child-type=server-group)");
        List<ModelNode> serverNameList = hostMasterResponse.get("result").asList();
        for (ModelNode serverName : serverNameList) {
            list.add(serverName.asString());
        }
        return list;
    }

    /**
     *  Starts specified server on specified host
     *
     * @param host   hosts used to find the server
     * @param server server which should be started
     * @param block  flag whether the operation should be blocking or not
     * @return
     */
    public boolean startServer(final String host, final String server, boolean block) {
        return manageServer(host, server, ":start", block);
    }

    /**
     *  Stops specified server on specified host
     *
     * @param host   hosts used to find the server
     * @param server server which should be stopped
     * @param block  flag whether the operation should be blocking or not
     * @return true if operation stop-servers was successful
     */
    public boolean stopServer(final String host, final String server,
                                     boolean block) {
        return manageServer(host, server, ":stop", block);
    }

    /**
     * Retrieves all running servers across all hosts
     * @return a map with hosts as keys and lists of running servers as values
     */
    public  Map<String, List<String>> listAllRunningServers() {
        Map<String, List<String>>  running = new HashMap<String, List<String>>();

        for (String host : listHosts()) {
            List<String> servers = listRunningServers(host);
            if (!servers.isEmpty()) {
                running.put(host, servers);
            }
        }

        return running;
    }

    /**
     * Restores state of running domain - listed servers will be started, any other running server  will be stopped.
     * @param topology host-servers map as returned by {@link #listAllRunningServers()}
     */
    public void restoreDomainState(Map<String, List<String>> topology, boolean block) {
        for (String host : listHosts()) {
            List<String> servers = listServers(host);
                for (String server : servers) {
                    if (topology.containsKey(host) && topology.get(host).contains(server)) {
                        startServer(host, server, block);
                    } else {
                        stopServer(host, server, block);
                    }
                }
            }
    }

    /**
     * Stops all running servers in domain
     *
     * @param sleepMillis the length of time to sleep in milliseconds or null
     * @return true if operation stop-servers was successful
     */
    public boolean stopAllServers(final Long sleepMillis) {
        boolean retValue = cliClient.executeForSuccess("/:stop-servers()");
        if (!retValue) {
            log.error("Operation stop-servers() wasn't successful");
        }
        if (retValue && sleepMillis != null) {
            Library.letsSleep(sleepMillis);
        }
        return retValue;
    }

    /**
     * Starts all servers in domain
     *
     * @param sleepMillis the length of time to sleep in milliseconds or null
     * @return true if operation start-servers was successful
     */
    public boolean startAllServers(final Long sleepMillis) {
        boolean retValue = cliClient.executeForSuccess("/:start-servers()");
        if (!retValue) {
            log.error("Operation start-servers() wasn't successful");
        }
        if (retValue && sleepMillis != null) {
            Library.letsSleep(sleepMillis);
        }
        return retValue;
    }

    /**
     * Restarts all servers in domain
     *
     * @param sleepMillis the length of time to sleep in milliseconds or null
     * @return true if operation restart-servers was successful
     */
    public boolean restartAllServers(final Long sleepMillis) {
        boolean retValue = cliClient.executeForSuccess("/:restart-servers()");
        if (!retValue) {
            log.error("Operation restart-servers() wasn't successful");
        }
        if (retValue && sleepMillis != null) {
            Library.letsSleep(sleepMillis);
        }
        return retValue;
    }

    /**
     * Removes server from host
     *
     * @param host       name of eap host
     * @param serverName name of server to be removed
     * @return true if operation was successful
     */
    public boolean removeServer( final String host, final String serverName) {
        return cliClient.executeForSuccess("/host=" + host + "/server-config=" + serverName + ":remove()");
    }

    /**
     * Removes server from default host of cliClient.
     *
     * @param serverName name of server to be removed
     * @return true if operation was successful
     */
    public boolean removeServer(final String serverName) {
        return removeServer(getDefaultHost(), serverName);
    }

    /**
     * Removes server group from domain
     *
     * @param groupName name of group to be removed
     * @return true if operation was successful
     */
    public boolean removeServerGroup(final String groupName) {
        return cliClient.executeForSuccess("/server-group=" + groupName + ":remove()");
    }

    /**
     * Stops all servers belonging to the server group currently running in the domain.
     * <p/>
     * @param groupName   name of group
     * @param sleepMillis the length of time to sleep in milliseconds or null
     * @return true if operation was successful
     */
    public  boolean stopServerGroup(final String groupName, final Long sleepMillis) {
        String cmd = "/server-group=" + groupName + ":stop-servers()";
        boolean retValue = cliClient.executeForSuccess(cmd);
        if (!retValue) {
            log.error("Operation {} wasn't successful", cmd);
        }
        if (retValue && sleepMillis != null) {
            Library.letsSleep(sleepMillis);
        }
        return retValue;
    }

    /**
     * Starts all configured servers belonging to the server group in the domain that are not
     * currently running.
     * <p/>
     * @param groupName   name of group
     * @param sleepMillis the length of time to sleep in milliseconds or null
     * @return true if operation was successful
     */
    public boolean startServerGroup(final String groupName, final Long sleepMillis) {
        String cmd = "/server-group=" + groupName + ":start-servers()";
        boolean retValue = cliClient.executeForSuccess(cmd);
        if (!retValue) {
            log.error("Operation {} wasn't successful", cmd);
        }
        if (retValue && sleepMillis != null) {
            Library.letsSleep(sleepMillis);
        }
        return retValue;
    }

    /**
     *  Starts or stops specified server on specified host
     *
     * @param host   hosts used to find the server
     * @param server server which should be managed
     * @param block  flag whether the operation should be blocking or not
     * @return true if operation stop-servers was successful
     */
    private boolean manageServer(final String host, final String server,
                                 String operation, boolean block) {
        String[] params = block ? new String[]{"block=true"} : null;

        String cmd = CliUtils.buildCommand("/host=" + host + "/server-config=" + server, operation, params);
        boolean retValue = cliClient.executeForSuccess(cmd);

        if (!retValue) {
            log.error("Operation start() wasn't successful");
        }

        return retValue;
    }

    public List<ModelNode> listRunningServerNodes(String host) {
        ModelNode response =  cliClient
                .executeForResponse("/host=" + host + ":read-children-names(child-type=server)");

        // workaround for BZ1093030
        if (response.has("failure-description") && response.get("failure-description")
                .asString().contains("JBAS014793")) {
            return Collections.emptyList();
        }

        return response.get("result").asList();
    }

    public List<ModelNode> listServerNodes(String host) {
        ModelNode response = cliClient
                .executeForResponse("/host=" + host + ":read-children-names(child-type=server-config)");

        return response.get("result").asList();
    }

    /**
     * Checks whether default host will become available before default timeout passes
     *
     * @return true if server becomes available before default timeout passes, false otherwise
     */
    @Override
    public boolean waitUntilAvailable() {
        return waitUntilAvailable(getDefaultHost(), DEFAULT_TIMEOUT);
    }

    /**
     * Checks whether default host will become available before specified timeout passes
     *
     * @param timeout max time to wait for server to start, in milliseconds
     * @return true if server becomes available before timeout passes, false otherwise
     */
    @Override
    public boolean waitUntilAvailable(long timeout) {
        return waitUntilAvailable(getDefaultHost(), timeout);
    }


    /**
     * Checks whether default host will become available before specified timeout passes
     *
     * @param host host which is checked
     * @param timeout max time to wait for server to start, in milliseconds
     * @return true if server becomes available before timeout passes, false otherwise
     */
    public boolean waitUntilAvailable(String host, long timeout) {
        long timePassed = 0;
        boolean isRunning = false;
        long waitTimeInterval = 500;
        while (timePassed < timeout && !isRunning) {
            isRunning = isInRunningState(host);
            if (!isRunning) {
                log.info("Waiting for additional {} ms for server to get in running state (already waited for {} ms out of {} ms)",
                        waitTimeInterval, timePassed, timeout);
                Library.letsSleep(waitTimeInterval);
                timePassed += waitTimeInterval;
            }
        }
        return isRunning;
    }

    /**
     * Reloads server(s) and waits until server(s) is(are) running again
     *
     * @param timeout max time to wait for server to start, in milliseconds
     */
    public void reloadIfRequiredAndWaitUntilRunning(long timeout) {
        if (isReloadRequired(listRunningServers(getDefaultHost()))) {
            reloadAndWaitUntilRunning(timeout);
        }
    }

    /**
     * Reloads server(s), which require reload and waits until server(s) is(are) running again
     *
     * @param timeout max time to wait for server to start, in milliseconds
     */
    public void reloadAndWaitUntilRunning(long timeout) {
        List<String> all = listRunningServers(getDefaultHost());
        List<String> servers = filterAllRunningServers(all);
        reloadServers();
        waitUntilAvailable(timeout);
        waitUntilRunning(servers, timeout);
    }

    /**
     * Wait for servers to become running in given timeout
     * @param servers names of servers which will be waited for
     * @param timeout time is milliseconds
     * @throws TimeoutException when all servers are not running in given timeout
     */
    public void waitUntilRunning(final List<String> servers, final long timeout) throws TimeoutException {
        if (servers.isEmpty()) {
            log.debug("No servers for waiting were specified!");
        } else {
            final int step = 500;
            long decTimeout = timeout;
            while (isServersRunning(servers)) {
                if (decTimeout <= 0) {
                    throw new TimeoutException("Servers are not running in " + timeout);
                }
                decTimeout -= step;
                log.debug("Waiting another " + step + "ms for server to become running.");
                Library.letsSleep(step);
            }
        }

    }

    public List<String> filterNotStoppedOrSuspendedServers(List<String> servers) {
        return servers.stream().filter(server -> !isServerStoppedOrSuspended(server)).collect(Collectors.toList());
    }

    /**
     * Returns true if all given servers are running
     * @param servers name of servers which state will be checked
     * @return true if all servers are running
     */
    public boolean isServersRunning(List<String> servers) {
        return servers.stream().anyMatch(server -> !isServerRunning(server));
    }

    /**
     * Return true if server is running
     * @param server name of server
     * @return True if given server is running
     */
    public boolean isServerRunning(String server) {
        return checkServerState(server, "running");
    }

    /**
     * Return list of servers whose status is STARTED
     * @param servers list of servers from which will be filtered non-running servers
     * @return List of all running servers
     */
    public List<String> filterAllRunningServers(List<String> servers) {
        List<String> running = new LinkedList<>();
        for (String server : servers) {
            if (checkServerStatus(server, "STARTED")) running.add(server);
        }
        return running;
    }

    /**
     * Returns true if for any server in list is required reload
     * @param servers list of server names which state will be checked
     * @return true if any server requires reload
     */
    public boolean isReloadRequired(List<String> servers) {
        return servers.stream().anyMatch(this::isReloadRequiredForServerOnDomain);
    }

    /**
     * Return true if given server requires reload
     * @param server server which state will be checked
     * @return true if server requires reload
     */
    public boolean isReloadRequiredForServerOnDomain(String server) {
        return checkServerState(server, "reload-required");
    }

    /**
     * Return true if given server requires restart
     * @param server server which state will be checked
     * @return true if server requires restart
     */
    public boolean isRestartRequiredForServerOnDomain(String server) {
        return checkServerState(server, "restart-required");
    }

    /**
     * Return true if server is stopped or suspended
     * @param server server which state will be checked
     * @return true if server is stopped or suspended
     */
    public boolean isServerStoppedOrSuspended(String server) {
        return  checkServerState(server, "stopped") ||
                checkServerState(server, "suspended");
    }

    public void reloadServers() {
        cliClient.executeForSuccess(":reload-servers");
    }

    public boolean checkServerState(String server, String state) {
        final String result = cliClient.executeForResult(
                "/host=" + getDefaultHost() +
                "/server=" + server +
                ":read-attribute(name=server-state,include-defaults=true)");
        return result.toLowerCase().contains(state);
    }

    public boolean checkServerStatus(String server, String status) {
        final String result = cliClient.executeForResult(
                "/host=" + getDefaultHost() +
                "/server-config=" + server +
                ":read-attribute(name=status,include-defaults=true)");
        return result.equalsIgnoreCase(status);
    }
}
