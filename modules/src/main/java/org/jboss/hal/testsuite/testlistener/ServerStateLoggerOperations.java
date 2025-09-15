/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2016, Red Hat, Inc., and individual contributors
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

package org.jboss.hal.testsuite.testlistener;

import org.jboss.hal.testsuite.creaper.ManagementClientProvider;
import org.wildfly.extras.creaper.core.online.ModelNodeResult;
import org.wildfly.extras.creaper.core.online.OnlineManagementClient;
import org.wildfly.extras.creaper.core.online.operations.Address;
import org.wildfly.extras.creaper.core.online.operations.Operations;
import org.wildfly.extras.creaper.core.online.operations.admin.Administration;

import java.io.IOException;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static org.jboss.as.controller.client.helpers.ClientConstants.HOST;
import static org.jboss.as.controller.client.helpers.ClientConstants.SERVER;
import static org.jboss.as.controller.client.helpers.ClientConstants.SERVER_CONFIG;
import static org.jboss.as.controller.client.helpers.ClientConstants.STATUS;

/**
 * Helper class to read server state informations from model for {@link ServerStateLogger}. <br />
 * Created by pjelinek on Apr 19, 2016
 */
public class ServerStateLoggerOperations {

    public Map<String, Map<String, String>> getServerStateMap() throws IOException {
        return tryWithClient(client -> {
            ModelReader reader = new ModelReader(client);
            return reader.getHostStream().collect(toMap(identity(), reader.getServerOfHostStatesFunction()));
        });
    }

    public boolean isReloadRequired() throws IOException {
        return tryWithClient(client -> new Administration(client).isReloadRequired());
    }

    public boolean isRestartRequired() throws IOException {
        return tryWithClient(client -> new Administration(client).isRestartRequired());
    }

    private <T> T tryWithClient(Function<OnlineManagementClient, T> modelReadFunction) throws IOException {
        try (OnlineManagementClient client = ManagementClientProvider.createOnlineManagementClient()) {
            return modelReadFunction.apply(client);
        }
    }

    private static class ModelReader {

        private final Operations ops;

        private ModelReader(OnlineManagementClient client) {
            this.ops = new Operations(client);
        }

        /**
         * {@link Function} instance to return map of server -> status pairs for given host
         */
        private Function<String, Map<String, String>> getServerOfHostStatesFunction() {
            return host -> {
                return getServerStream(host).collect(toMap(identity(), server -> {
                    return readServerConfigStatus(host, server);
                }));
            };
        }

        private Stream<String> getHostStream() {
            ModelNodeResult hostsResult = null;
            try {
                hostsResult = ops.readChildrenNames(Address.root(), HOST);
            } catch (IOException e) {
                throw new RuntimeException("Unable to read hosts.", e);
            }
            hostsResult.assertDefinedValue();
            return hostsResult.stringListValue().stream();
        }

        private Stream<String> getServerStream(String host) {
            ModelNodeResult serversResult;
            try {
                serversResult = ops.readChildrenNames(Address.host(host), SERVER);
            } catch (IOException e) {
                throw new RuntimeException("Unable to read servers of host.", e);
            }
            serversResult.assertDefinedValue();
            return serversResult.stringListValue().stream();
        }

        private String readServerConfigStatus(String host, String server) {
            ModelNodeResult serverConfigStatusResult;
            try {
                serverConfigStatusResult = ops.readAttribute(Address.host(host).and(SERVER_CONFIG, server), STATUS);
            } catch (IOException e) {
                throw new RuntimeException("Unable to read server config status.", e);
            }
            serverConfigStatusResult.assertDefinedValue();
            return serverConfigStatusResult.stringValue();
        }
    }

}
