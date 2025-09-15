package org.jboss.hal.testsuite.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @author mkrajcov <mkrajcov@redhat.com>
 */
public class Library {

    public static void replaceStringsInFile(Map<String, String> substitutions, File file) throws IOException {
        String content = FileUtils.readFileToString((file));
        for (Map.Entry<String, String> substitutionEntry : substitutions.entrySet()) {
            content = content.replaceAll(substitutionEntry.getKey(), substitutionEntry.getValue());
        }
        FileUtils.writeStringToFile(file, content);
    }

    public static boolean replaceStringInFile(String pattern, String replacement, File file) throws IOException {
        String content = FileUtils.readFileToString((file));
        content = content.replaceAll(pattern, replacement);
        FileUtils.writeStringToFile(file, content);
        return FileUtils.readFileToString(file).contains(replacement);
    }

    public static void letsSleep(long timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException var3) {
            throw new RuntimeException(var3);
        }
    }
}
