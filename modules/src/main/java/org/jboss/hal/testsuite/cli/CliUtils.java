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
 * @author Petr Kremensky pkremens@redhat.com, Radim Hatlapatka rhatlapa@redhat.com
 * @deprecated use Creaper
 */
public class CliUtils {

    /**
     * Create command from given address and operation without any attributes.
     *
     * @param address   Address where shall be command executed.
     * @param operation Name of operation which shall be executed.
     * @return Command as String from given address, operation and attributes.
     */
    public static String buildCommand(String address, String operation) {
        String command;
        if (address == null) {
            address = "";
        }
        if (operation == null) {
            throw new IllegalArgumentException("Operation cannot be null");
        }
        command = address + operation;
        return command;
    }

    /**
     * Create command from given address, operation and set of attributes.
     *
     * @param address    Address where shall be command executed.
     * @param operation  Name of operation which shall be executed.
     * @param attributes Array of attributes for given operation.
     * @return Command as String from given address, operation and attributes.
     */
    public static String buildCommand(String address, String operation, String[] attributes) {
        if (address == null) {
            address = "";
        }
        if (operation == null) {
            throw new IllegalArgumentException("Operation cannot be null");
        }

        StringBuilder sb = new StringBuilder();
        sb.append(address);
        sb.append(operation);
        if (attributes != null) {
            sb.append("(");
            for (int i = 0; i < attributes.length; i++) {
                if (i == 0) {
                    sb.append(attributes[i]);
                } else {
                    sb.append("," + attributes[i]);
                }
            }
            sb.append(")");
        }
        return sb.toString();
    }
}
