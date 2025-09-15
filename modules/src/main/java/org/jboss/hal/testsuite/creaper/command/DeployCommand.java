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

import java.io.File;

/**
 * Creaper command for application deploy. Feel free to enhance as needed.
 */
public class DeployCommand implements OnlineCommand {

    private final String path;
    private final boolean toAllGroups;
    private final String particularGroup;
    private final String deploymentName;
    private final String runtimeName;
    private final boolean disabled;
    private final boolean unmanaged;

    private DeployCommand(Builder builder) {
        this.path = builder.path;
        this.toAllGroups = builder.toAllGroups;
        this.particularGroup = builder.particularGroup;
        this.deploymentName = builder.deploymentName;
        this.runtimeName = builder.runtimeName;
        this.disabled = builder.disabled;
        this.unmanaged = builder.unmanaged;
    }

    @Override
    public void apply(OnlineCommandContext ctx) throws Exception {
        StringBuilder cmd = new StringBuilder("deploy ").append(path);
        if (ctx.options.isDomain && !disabled) {
            if (toAllGroups) {
                cmd.append(" --all-server-groups");
            } else {
                cmd.append(" --server-groups=").append(particularGroup);
            }
        }
        if (deploymentName != null) {
            cmd.append(" --name=").append(deploymentName);
        }
        if (runtimeName != null) {
            cmd.append(" --runtime-name=").append(runtimeName);
        }
        if (disabled) {
            cmd.append(" --disabled");
        }
        if (unmanaged) {
            cmd.append(" --unmanaged");
        }
        ctx.client.executeCli(cmd.toString());
    }

    public static final class Builder {
        private final String path;
        private boolean toAllGroups = false;
        private String particularGroup;
        private String deploymentName;
        private String runtimeName;
        private boolean disabled = false;
        private boolean unmanaged;

        public Builder(String path) {
            if (!new File(path).exists()) {
                throw new IllegalArgumentException(path + " doesn't exist!");
            }
            this.path = path;
        }

        public Builder(File file) {
            if (!file.exists()) {
                throw new IllegalArgumentException(file + " doesn't exist!");
            }
            this.path = file.getAbsolutePath();
        }

        /**
         * Domain mode only. Then either this xor {@link #particularGroup(String)} has to be called.
         * @return
         */
        public Builder toAllGroups() {
            this.toAllGroups = true;
            return this;
        }

        /**
         * Domain mode only. Then either this xor {@link #toAllGroups} has to be called.
         * @return
         */
        public Builder particularGroup(String groupName) {
            if (groupName == null || groupName.trim().isEmpty()) {
                throw new IllegalArgumentException("Group name should be neither empty nor whitespace!");
            }
            this.particularGroup = groupName;
            return this;
        }

        /**
         * Optional. If not specified filename will be used.
         * @param deploymentName should be neither empty nor whitespace
         * @return
         */
        public Builder name(String deploymentName) {
            if (deploymentName == null || deploymentName.trim().isEmpty()) {
                throw new IllegalArgumentException("Deployment name should be neither empty nor whitespace!");
            }
            this.deploymentName = deploymentName;
            return this;
        }

        /**
         * Optional, if not specified deploymentName will be used. May not be unique.
         * @param runtimeName should be neither empty nor whitespace
         * @return
         */
        public Builder runtimeName(String runtimeName) {
            if (runtimeName == null || runtimeName.trim().isEmpty()) {
                throw new IllegalArgumentException("Runtime name should be neither empty nor whitespace!");
            }
            this.runtimeName = runtimeName;
            return this;
        }

        public Builder disabled() {
            this.disabled = true;
            return this;
        }

        public Builder unmanaged() {
            this.unmanaged = true;
            return this;
        }

        public DeployCommand build() {
            if (ConfigUtils.isDomain()) {
                if (!disabled && toAllGroups == (particularGroup != null)) {
                    throw new IllegalArgumentException("In domain mode either nonempty particularGroup XOR toAllGroups should be specified!");
                }
            } else {
                if (toAllGroups || (particularGroup != null)) {
                    throw new IllegalArgumentException("In standalone mode neither particularGroup nor toAllGroups=true should be specified!");
                }
            }
            return new DeployCommand(this);
        }
    }

}
