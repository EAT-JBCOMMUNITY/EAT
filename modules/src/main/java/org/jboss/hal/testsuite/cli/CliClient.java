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
import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.*;

/**
 * @deprecated Should be replaced with {@link org.jboss.hal.testsuite.dmr.Dispatcher}
 * Use {@link CliClientFactory} to obtain instance.
 * @author rhatlapa (rhatlapa@redhat.com)
 */
@Deprecated
public class CliClient {

    private static final Logger log = LoggerFactory.getLogger(CliClient.class);

    private final CliConfiguration cliConfig;

    CliClient() {
        cliConfig = new CliConfiguration();
    }

    CliClient(CliConfiguration cliConfig) {
        this.cliConfig = cliConfig;
    }


    /**
     * Executes command and returns full output as CLI.Result
     *
     * @param command        Command to be executed
     * @return CLI.Result object containing request, response and success status of the operation all in one
     */
    public CLI.Result executeCommand(String command) {
        CLI cli = CLI.newInstance();

        log.trace("Connecting to native interface (" + cliConfig.getHost() + ":" + cliConfig.getPort() + ") via CLI");
        cli.connect(cliConfig.getHost(), cliConfig.getPort(), cliConfig.getUser(), cliConfig.getPassword());

        log.debug("Running cli command: " + command);
        CLI.Result result = cli.cmd(command);

        log.info("Command {} finished with {}", result.getCliCommand(), result.isSuccess() ? "success" : "failure" );
        log.trace("The command {} ended with response {}", result.getCliCommand(), result.getResponse());

        cli.disconnect();
        log.trace("Successfully disconnected");
        return result;
    }

    /**
     * Execute the command via CLI and returns its response as ModelNode.
     *
     * @param command String based command to be executed via CLI.
     * @return Response of given command.
     */
    public ModelNode executeForResponse(String command) {
        CLI.Result result = executeCommand(command);
        return result.getResponse();
    }

    /**
     * Execute the command via CLI and returns its command execution status.
     *
     * @param command String based command to be executed via CLI.
     * @return Response <code>true</code>, if the command execution was successful, else <code>false</code>.
     */
    public boolean executeForSuccess(String command) {
        return executeForSuccess(command, 0);
    }

    /**
     * Execute the command via CLI and returns its command execution status.
     *
     * @param command String based command to be executed via CLI.
     * @param timeout A timeout for the command to return successfully
     * @return Response <code>true</code>, if the command execution was successful, else <code>false</code>.
     */
    public boolean executeForSuccess(String command, int timeout) {
        long start = System.currentTimeMillis();
        boolean successful = executeCommand(command).isSuccess();
        if (timeout > 0) {
            while (!successful) {
                if (System.currentTimeMillis() >= start + timeout) {
                    throw new TimeoutException(command, timeout);
                }
                Library.letsSleep(200);
            }
        }
        return successful;
    }


    /**
     * Execute the command via CLI and returns its result as String,
     *
     * @param command String based command to be executed via CLI.
     * @return Result of given command.
     */
    public String executeForResult(String command) {
        return executeForResult(command, 0);
    }

    public String executeForResult(String command, int timeout) {
        long start = System.currentTimeMillis();
        CLI.Result result = executeCommand(command);
        if (timeout > 0) {
            while (!result.isSuccess()) {
                if (System.currentTimeMillis() >= start + timeout) {
                    throw new TimeoutException(command, timeout);
                }
                Library.letsSleep(200);
            }
        }
        return result.getResponse().get(RESULT).asString();
    }

    /**
     * Give access to connected cli
     * @return cli
     */
    public CLI getCLI() {
        CLI cli = CLI.newInstance();

        log.debug("Connecting to native interface (" + cliConfig.getHost() + ":" + cliConfig.getPort() + ") via CLI");
        cli.connect(cliConfig.getHost(), cliConfig.getPort(), cliConfig.getUser(), cliConfig.getPassword());
        return cli;
    }


    /**
     * Read attribute from given address.
     *
     * @param address        Address of node we are interested in.
     * @param name           Name of the attribute.
     * @return Value of given attribute.
     */
    public String readAttribute(String address, String name) {
        return readAttribute(address, name, true, 0);
    }

    public String readAttribute(String address, String name, int timeout) {
        return readAttribute(address, name, true, timeout);
    }

    /**
     * Read attribute from the given address
     *
     * @param address           Address of node we are interested in.
     * @param name              Name of the attribute.
     * @param includeDefaults   Whether use default value or not for attribute with undefined value
     * @return Value of given attribute.
     */
    public String readAttribute(String address, String name, boolean includeDefaults) {
        return readAttribute(address, name, includeDefaults, 0);
    }

    public String readAttribute(String address, String name, boolean includeDefaults, int timeout) {
        String command = CliUtils.buildCommand(address, ":read-attribute", new String[]{"name=" + name, "include-defaults=" + String.valueOf(includeDefaults)});
        return executeForResult(command, timeout);
    }

    /**
     * returns attribute type
     * <p/>
     * @param address  address of node containing the attribute
     * @param name     the attribute name
     * @return type of the specified attribute or null if attribute doesn't exist
     */
    public ModelType readAttributeType(String address, String name) {
        String command = CliUtils.buildCommand(address, ":read-resource-description");
        CLI.Result res = executeCommand(command);
        if (res.isSuccess() && res.getResponse().get(RESULT).get(ATTRIBUTES).hasDefined(name)) {
            return ModelType.valueOf(res.getResponse().get(RESULT).get(ATTRIBUTES).get(name).get(TYPE).asString().toUpperCase());
        } else {
            return null;
        }
    }

    /**
     * Write new value to attribute.
     *
     * @param address        Address of node we are interested in.
     * @param name           Name of the attribute.
     * @param value          New value of attribute.
     * @return Result <code>true</code>, if :write-attribute operation was successful, else <code>false</code>.
     */
    public boolean writeAttribute(String address, String name, String value) {
        String valueInImprovedFormat = value;
        // handling backslash behavior of CLI
        if (!CliConstants.UNDEFINED.equals(value) && ModelType.STRING.equals(readAttributeType(address, name))) {
            valueInImprovedFormat = "\"" + value.replaceAll("\\\\", "\\\\\\\\") + "\"";
        }
        String[] attributes = {"name=" + name, "value=" + valueInImprovedFormat};
        String command = CliUtils.buildCommand(address, ":write-attribute", attributes);
        return executeForSuccess(command);
    }

    /**
     * Reads system property via CLI from server config
     *
     * @param name Name of the property
     * @return value of the system property as defined in the server configuration
     */
    public String getSystemProperty(String name) {
        return readAttribute("/system-property=" + name, "value");
    }

    /**
     * Add system property.
     *
     * @param name  Name of the property.
     * @param value New value of the property.
     * @return Result <code>true</code>, if :add system property was successful, <code>false</code> otherwise.
     */
    public boolean addSystemProperty(String name, String value) {
        String command = CliUtils.buildCommand("/system-property=" + name, ":add", new String[]{"value=" + value});
        return executeForSuccess(command);
    }

    /**
     * Removes system property
     * @param name  Name of the property.
     * @return true if :remove operation was successful, false otherwise
     */
    public boolean removeSystemProperty(String name) {
        String command = CliUtils.buildCommand("/system-property=" + name, ":remove", new String[]{});
        return executeForSuccess(command);
    }

    public boolean removeResource(String dmrPath) {
        String command = CliUtils.buildCommand(dmrPath, ":remove");
        return executeForSuccess(command);
    }


    /**
     * Check whether server is in reload-required state.
     *
     * @return <code>true</code>, if server is in reload-required state, else <code>false</code>.
     */
    public boolean reloadRequired() {
        String result = readAttribute(null, "server-state");
        return result.equals("reload-required");
    }

    /**
     * Check operation response and find out whether it left server in reload-required state. Server could be in
     * reload-required state before this operation!
     *
     * @param response Response of CLI operation.
     * @return Return <code>true</code>, if operation left server in reload-required state, else <code>false</code>.
     */
    public boolean reloadRequired(ModelNode response) {
        String result = response.get(RESPONSE_HEADERS).get(PROCESS_STATE).asString();
        System.out.println(response);
        return result.equals("reload-required");
    }

    /**
     * Check whether server is in restart-required state.
     *
     * @return <code>true</code>, if server is in restart-required state, else <code>false</code>.
     */
    public boolean restartRequired() {
        String result = readAttribute(null, "server-state");
        return result.equals("restart-required");
    }

    /**
     * Check operation response and find out whether it left server in restart-required state. Server could be in
     * reload-required state before this operation!
     *
     * @param response Response of CLI operation.
     * @return Return <code>true</code>, if operation left server in restart-required state, else <code>false</code>.
     */
    public boolean restartRequired(ModelNode response) {
        String result = response.get(RESPONSE_HEADERS).get(PROCESS_STATE).asString();
        System.out.println(response);
        return result.equals("restart-required");
    }

    /**
     * Reloads server via CLI and waits for preset timeout for server to become available
     *
     * @return Return <code>true</code>, if server becomes available after reload operation is processed, <code>false</code> otherwise
     */
    public boolean reload() {
        executeCommand("reload");
        Library.letsSleep(500);
        return ServerManager.getInstance(this).waitUntilAvailable();
    }

    /**
     * Runs reload on the server, if forced it is executed always, if not forced it is run only if server state is reload-required
     *
     * @param forced if true then reload is done, if false, it is done only if server state is reload-required
     */
    public void reload(boolean forced) {
        if (forced || reloadRequired()) {
            reload();
        }
    }

    /**
     * Shutdown and re-start the server only if the server is in restart required state or forced
     * @param forced whether server should be restarted even when server is not in restart required state
     */
    public void restart(boolean forced) {
        if (forced || restartRequired()) {
            restart();
        }
    }

    /**
     * Shutdown and re-start the server.
     */
    public boolean restart() {
        executeCommand("shutdown --restart=true");
        Library.letsSleep(1500);
        return ServerManager.getInstance(this).waitUntilAvailable();
    }

    /**
     * Get list of childnodes' names of specified type for given node.
     * @param nodeAddress Address of node we are interested in
     * @param childNodeType Type of child nodes to found
     * @return Names of children of given type
     */
    public List<String> getChildNames(String nodeAddress, String childNodeType) {
        List<String> list = new ArrayList<String>();

        String cmd = CliUtils.buildCommand(nodeAddress, ":read-children-names",
                new String[]{"child-type=" + childNodeType});

        List<ModelNode> asList = executeForResponse(cmd).get(RESULT).asList();

        for (ModelNode modelNode : asList) {
            list.add(modelNode.asString());
        }

        return list;
    }

    /**
     * Check if any child node of given type exists for given parent node.
     *
     * @param parentAddress Address of node we are interested in
     * @param childNodeType Name of childnode type
     * @return Whether there exists direct child node of given type
     */
    public boolean hasChildNode(String parentAddress, String childNodeType) {
        return !getChildNames(parentAddress, childNodeType).isEmpty();
    }

    /**
     * Check if child node of given type and exactly the same name exists for given parent node.
     *
     * @param parentAddress Address of node we are interested in
     * @param childNodeType Name of childnode type
     * @param childNodeName Name of childnode we are looking for
     * @return Whether there exists direct child node of given type and name
     */
    public boolean childNodeExists(String parentAddress, String childNodeType, String childNodeName) {
        if (childNodeName == null) {
            throw new IllegalArgumentException("Child node name cannot be null");
        }
        List<String> childNodeList = getChildNames(parentAddress, childNodeType);
        return childNodeList.contains(childNodeName);
    }

    /**
     * Resolve full filesystem path of path defined by /path=&lt;pathName&gt;. Path is resolved
     * recursively until relative-to attribute is specified.
     * <p/>
     * @param pathName name of path in configuration
     * @return full path
     */
    public String resolveFullPath(String pathName) {
        log.debug("Resolving full path of " + pathName);
        String path = this.readAttribute("/path=" + pathName, "path");
        log.debug("Resolved path");
        String relativeTo = this.readAttribute("/path=" + pathName, "relative-to");
        if (relativeTo == null || relativeTo.isEmpty() || relativeTo.equals(CliConstants.UNDEFINED)) {
            return new File(path).getAbsolutePath();
        } else {
            return new File(resolveFullPath(relativeTo), path).getPath();
        }
    }

    public int getStatusManagerPort() {
        /*    /socket-binding-group=standard-sockets/socket-binding=txn-status-manager:read-attribute(name=port)  */
        final String address = "/socket-binding-group=standard-sockets/socket-binding=txn-status-manager";

        return Integer.parseInt(readAttribute(address, "port"));
    }

    public int getRecoveryListenerPort() {
        /*    /socket-binding-group=standard-sockets/socket-binding=txn-recovery-environment:read-attribute(name=port)  */
        final String address = "/socket-binding-group=standard-sockets/socket-binding=txn-recovery-environment";

        return Integer.parseInt(readAttribute(address, "port"));
    }

    public String getHost() {
        return cliConfig.getHost();
    }
}

