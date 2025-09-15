/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat, Inc., and individual contributors
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
package org.jboss.hal.testsuite.dmr;

import org.jboss.as.controller.client.ModelControllerClient;
import org.jboss.dmr.ModelNode;
import org.jboss.hal.testsuite.cli.Library;
import org.jboss.hal.testsuite.cli.TimeoutException;
import org.jboss.hal.testsuite.util.ConfigUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.sasl.RealmCallback;
import java.io.IOException;
import java.net.UnknownHostException;

/**
 * Central class to execute {@link Operation}s.
 *
 * @author Harald Pehl
 * @deprecated use creaper
 */
public class Dispatcher {

    private static class AuthCallback implements CallbackHandler {

        String[] args;

        AuthCallback(String[] args) {
            this.args = args;
        }

        public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
            for (Callback current : callbacks) {
                if (current instanceof NameCallback) {
                    NameCallback ncb = (NameCallback) current;
                    ncb.setName(args[0]);
                } else if (current instanceof PasswordCallback) {
                    PasswordCallback pcb = (PasswordCallback) current;
                    pcb.setPassword(args[1].toCharArray());
                } else if (current instanceof RealmCallback) {
                    RealmCallback rcb = (RealmCallback) current;
                    rcb.setText(rcb.getDefaultText());
                } else {
                    throw new UnsupportedCallbackException(current);
                }
            }
        }
    }


    private static final Logger log = LoggerFactory.getLogger(Dispatcher.class);

    private static final String MANAGEMENT_HOST = ConfigUtils.get("as.managementAddress");
    private static final int MANAGEMENT_PORT = Integer.parseInt(ConfigUtils.get("as.managementPort"));
    private static final String USERNAME = "authentication";
    private static final String PASSWORD = "must be disabled";
    private static final int WAIT = 200;

    private ModelControllerClient client;

    /**
     * Creates a new instance and opens a connection to the management interface which needs to be closed in {@link
     * #close()}.
     */
    public Dispatcher() {
        try {
            client = ModelControllerClient.Factory.create(MANAGEMENT_HOST, MANAGEMENT_PORT,
                    new AuthCallback(new String[]{USERNAME, PASSWORD}));
        } catch (UnknownHostException e) {
            throw new DmrException(e);
        }
    }

    // ------------------------------------------------------ execute

    /**
     * Execute the given operation. If a {@linkplain Operation#getTimeout()} timeout} is given the operation is
     * executed repeatedly until it was successfully executed.
     *
     * @throws TimeoutException if the operation could not be executed successfully within the given timeout.
     */
    public DmrResponse execute(Operation operation) {
        log.debug("Executing operation {}", operation);

        try {
            long start = System.currentTimeMillis();
            DmrResponse response = executeSingle(operation);
            if (operation.getTimeout() > 0) {
                while (!response.isSuccessful()) {
                    if (System.currentTimeMillis() >= start + operation.getTimeout()) {
                        throw new TimeoutException(operation.toString(), operation.getTimeout());
                    }
                    Library.letsSleep(WAIT);
                    response = executeSingle(operation);
                }
            }
            log.info("Operation {} finished with {}", operation, response.isSuccessful() ? "success" : "failure");
            if (!response.isSuccessful())
                log.warn("Operation '{}' failed because of '{}'.", operation, response.getFailureDescription().asString());
            return response;

        } catch (IOException e) {
            throw new DmrException(operation, e);
        }
    }

    public void close() {
        try {
            client.close();
        } catch (IOException e) {
            throw new DmrException(e);
        }
    }

    private DmrResponse executeSingle(final Operation operation) throws IOException {
        ModelNode response = client.execute(operation);
        return new DmrResponse(response);
    }
}
