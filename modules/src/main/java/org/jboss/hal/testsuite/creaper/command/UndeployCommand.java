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

package org.jboss.hal.testsuite.creaper.command;

import org.jboss.hal.testsuite.util.ConfigUtils;
import org.wildfly.extras.creaper.core.online.OnlineCommand;
import org.wildfly.extras.creaper.core.online.OnlineCommandContext;

/**
 * Creaper command for application undeploy. Feel free to enhance as needed.
 */
public class UndeployCommand implements OnlineCommand {

    private final String deploymentName;
    private final boolean fromAllGroups;
    private final String particularGroup;

    private UndeployCommand(Builder builder) {
        this.deploymentName = builder.deploymentName;
        this.fromAllGroups = builder.fromAllGroups;
        this.particularGroup = builder.particularGroup;
    }

    @Override
    public void apply(OnlineCommandContext ctx) throws Exception {
        StringBuilder cmd = new StringBuilder("undeploy ").append(deploymentName);
        if (ctx.options.isDomain) {
            if (fromAllGroups) {
                cmd.append(" --all-relevant-server-groups");
            } else {
                cmd.append(" --server-groups=").append(particularGroup);
            }
        }
        ctx.client.executeCli(cmd.toString());
    }

    public static final class Builder {

        private boolean fromAllGroups = false;
        private String particularGroup;
        private String deploymentName;

        public Builder(String deploymentName) {
            this.deploymentName = deploymentName;
        }

        /**
         * Domain mode only. Then either this xor {@link #particularGroup(String)} has to be called.
         * @return
         */
        public Builder fromAllGroups() {
            this.fromAllGroups = true;
            return this;
        }

        /**
         * Domain mode only. Then either this xor {@link #fromAllGroups} has to be called.
         * @return
         */
        public Builder particularGroup(String groupName) {
            if (groupName != null && groupName.trim().isEmpty()) {
                throw new IllegalArgumentException("Group name should be neither empty nor whitespace!");
            }
            this.particularGroup = groupName;
            return this;
        }

        public UndeployCommand build() {
            if (ConfigUtils.isDomain()) {
                if (fromAllGroups == (particularGroup != null)) {
                    throw new IllegalArgumentException("In domain mode either nonempty particularGroup XOR fromAllGroups should be specified!");
                }
            } else {
                if (fromAllGroups || (particularGroup != null)) {
                    throw new IllegalArgumentException("In standalone mode neither particularGroup nor toAllGroups=true should be specified!");
                }
            }
            return new UndeployCommand(this);
        }
    }


}
