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

import org.jboss.hal.testsuite.util.ConfigUtils;

/**
 * Created by pjelinek on May 14, 2015
 * @deprecated use creaper
 */
public class CliClientFactory {

    private static final String FULL = "full";
    private static final String MANAGEMENT_ADDRESS = ConfigUtils.get("as.managementAddress");
    private static final int MANAGEMENT_PORT = Integer.parseInt(ConfigUtils.get("as.managementPort"));
    private static final CliConfiguration CLI_CONFIGURATION = new CliConfiguration(MANAGEMENT_ADDRESS, MANAGEMENT_PORT);

    /**
     * @return either {@link CliClient} or {@link DomainCliClient} depending on actual mode
     */
    public static CliClient getClient() {
        if (ConfigUtils.isDomain()) {
            return new DomainCliClient(CLI_CONFIGURATION, FULL);
        } else {
            return new CliClient(CLI_CONFIGURATION);
        }
    }

    /**
     * @param profile
     * @return {@link DomainCliClient} with given profile
     */
    public static DomainCliClient getDomainClient(String profile) {
        return new DomainCliClient(CLI_CONFIGURATION, profile);
    }

}
