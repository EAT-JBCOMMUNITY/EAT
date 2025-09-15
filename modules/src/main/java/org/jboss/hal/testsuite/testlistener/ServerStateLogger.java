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

import java.io.IOException;
import java.util.Map;

import org.jboss.hal.testsuite.util.ConfigUtils;
import org.junit.runner.notification.RunListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link RunListener} to log if
 * <ul>
 *   <li>restart is required after testcase run,
 *   <li>reload is required after testcase run,
 *   <li>in domain mode servers running state changed after testcase run.
 * </ul>
 * Created by pjelinek on Apr 18, 2016
 */
public class ServerStateLogger extends TestCaseRunListener {

    private static final Logger log = LoggerFactory.getLogger(ServerStateLogger.class);
    private final ServerStateLoggerOperations ops = new ServerStateLoggerOperations();
    private Map<String, Map<String, String>> initServerStateMap;

    @Override
    protected void beforeTestCase() throws Exception {
        if (ConfigUtils.isDomain()) {
            initServerStateMap = ops.getServerStateMap();
        }
    }

    @Override
    protected void afterTestCase() throws Exception {
        checkServerStateMap();
        checkRestartRequired();
        checkReloadRequired();
    }

    private void checkServerStateMap() throws IOException {
        if (ConfigUtils.isDomain()) {
            Map<String, Map<String, String>> actualServerStateMap = ops.getServerStateMap();
            if (!initServerStateMap.equals(actualServerStateMap)) {
                log.warn("Server states don't equal initial ones after '{}' run! Expected '{}' but was '{}'.",
                        getCurrentTestCaseCanonicalName(), initServerStateMap, actualServerStateMap);
            }
        }
    }

    private void checkRestartRequired() throws IOException {
        if (ops.isRestartRequired()) {
            log.warn("Restart is required after '{}' run!", getCurrentTestCaseCanonicalName());
        }
    }

    private void checkReloadRequired() throws IOException {
        if (ops.isReloadRequired()) {
            log.warn("Reload is required after '{}' run!", getCurrentTestCaseCanonicalName());
        }
    }

}
