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

import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @author rhatlapa (rhatlapa@redhat.com)
 */
public class Library {

    /**
     * Sleep for the specified timeout in ms
     * @param timeout time to sleep in ms
     */
    public static void letsSleep(long timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Replaces text in a file based on pattern with provided text
     * @param pattern regex representing text which should be replaced
     * @param replacement new text set
     * @param file file to update
     * @return true if the operation was successful and the result contains the new string, false otherwise
     */
    public static boolean replaceStringInFile(String pattern, String replacement, File file) throws IOException {
        String content = FileUtils.readFileToString((file));
        content = content.replaceAll(pattern, replacement);
        FileUtils.writeStringToFile(file, content);
        return FileUtils.readFileToString(file).contains(replacement);
    }

    /**
     * Replaces text in a file based on predefined substitutions with provided text
     * @param substitutions Set of substitutions (pattern, replacement)
     * @param file file to update
     * @throws IOException
     */
    public static void replaceStringsInFile(Map<String, String> substitutions, File file) throws IOException {
        String content = FileUtils.readFileToString((file));
        for (Map.Entry<String, String> substitutionEntry : substitutions.entrySet()) {
            content = content.replaceAll(substitutionEntry.getKey(), substitutionEntry.getValue());
        }
        FileUtils.writeStringToFile(file, content);
    }
}
