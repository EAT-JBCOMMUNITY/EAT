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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * @author rhatlapa (rhatlapa@redhat.com)
 * @deprecated use creaper
 */
public class StandaloneManager extends ServerManager {

    private static Logger log = LoggerFactory.getLogger(StandaloneManager.class);

    public StandaloneManager(CliClient cliClient) {
        super(cliClient);
    }

    /**
     * Checks whether server is in running state or not using CLI
     *
     * @return true if server is running, false otherwise
     */
    public boolean isInRunningState() {
        try {
            return cliClient.readAttribute(null, "server-state").equals("running");
        } catch (IllegalStateException ex) {
            log.info("Unable to connect via CLI => server is probably down");
            return false;
        }
    }


    /**
     * Replace content of current configuration file with content of file passed, for instance
     * previously backuped.
     * <p/>
     * @param originalFile where the desired content is, must not be null
     */
    public void restoreStandaloneConfig(File originalFile) throws IOException {
        if (originalFile == null) {
            throw new IllegalArgumentException("originalFile must not be null");
        }
        log.info("Restoring config file...");
        File fileCurrent = new File(cliClient.readAttribute("/core-service=server-environment", "config-file"));

        FileUtils.copyFile(originalFile, fileCurrent);
        log.info("Config file {} restored with content of {}", fileCurrent.getAbsolutePath(), originalFile.getAbsolutePath());
    }

    /**
     * Copies current configuration file into file passed. If null is passed, the backup will be
     * saved in server temp directory as file with generated unique name. The actual file where the
     * backup is saved is returned.
     * <p/>
     * @param backupFile where the config file should be saved, existing file will be overwritten
     * @return where the backup is actually saved
     */
    public File backupStandaloneConfig(File backupFile) throws IOException {
        log.info("Backuping config file...");

        File fileOriginal = new File(cliClient.readAttribute("/core-service=server-environment", "config-file"));
        File fileBackup = backupFile;

        if (fileBackup == null) {
            fileBackup = File.createTempFile("config_", ".backup", new File(cliClient.readAttribute("/core-service=server-environment", "temp-dir")));
        }

        FileUtils.copyFile(fileOriginal, fileBackup);
        log.info("Config file {} backuped to {}", fileOriginal.getAbsolutePath(), fileBackup.getAbsolutePath());
        return fileBackup;
    }

    /**
     * Checks whether server will become available before default timeout passes
     *
     * @return true if server becomes available before default timeout passes, false otherwise
     */
    @Override
    public boolean waitUntilAvailable() {
        return waitUntilAvailable(DEFAULT_TIMEOUT);
    }

    /**
     * Checks whether server will become available before specified timeout passes
     *
     * @param timeout max time to wait for server to start, in milliseconds
     * @return true if server becomes available before timeout passes, false otherwise
     */
    @Override
    public boolean waitUntilAvailable(long timeout) {
        long timePassed = 0;
        boolean isRunning = false;
        long waitTimeInterval = 500;
        while (timePassed < timeout && !isRunning) {
            isRunning = isInRunningState();
            if (!isRunning) {
                log.info("Waiting for additional {} ms for server to get in running state (already waited for {} ms out of {} ms)",
                        waitTimeInterval, timePassed, timeout);
                Library.letsSleep(waitTimeInterval);
                timePassed += waitTimeInterval;
            }
        }
        return isRunning;
    }

}
