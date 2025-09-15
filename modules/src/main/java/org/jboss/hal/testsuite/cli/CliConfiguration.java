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

import java.util.Arrays;

/**
 * @author rhatlapa (rhatlapa@redhat.com)
 * @deprecated use creaper
 */
public class CliConfiguration {
    private String host = "127.0.0.1";
    private int port = 9999;
    private String user = null;
    private char[] password = null;

    public CliConfiguration() {
    }

    public CliConfiguration(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public CliConfiguration(String host, int port, String user, String password) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password.toCharArray();
    }

    public CliConfiguration(String user, String password) {
        this.user = user;
        this.password = password.toCharArray();
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUser() {
        return user;
    }

    public char[] getPassword() {
        if (password != null) {
            return Arrays.copyOf(password, password.length);
        } else {
            return null;
        }
    }
}

