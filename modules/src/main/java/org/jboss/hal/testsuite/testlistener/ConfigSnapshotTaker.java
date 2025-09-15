package org.jboss.hal.testsuite.testlistener;

import org.apache.commons.io.FileUtils;
import org.jboss.hal.testsuite.creaper.ManagementClientProvider;
import org.jboss.hal.testsuite.util.PropUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wildfly.extras.creaper.core.online.ModelNodeResult;
import org.wildfly.extras.creaper.core.online.OnlineManagementClient;
import org.wildfly.extras.creaper.core.online.operations.Address;
import org.wildfly.extras.creaper.core.online.operations.Operations;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;

public class ConfigSnapshotTaker extends TestCaseRunListener {

    private static final String XML_SNAPSHOT_DIRECTORY = PropUtils.get("snapshots.target.directory.name");

    private static final Logger log = LoggerFactory.getLogger(ConfigSnapshotTaker.class);

    protected void afterTestCase() throws Exception {
        String currentTestCaseCanonicalName = getCurrentTestCaseCanonicalName();
        if (currentTestCaseCanonicalName == null) {
            log.error("Current test class is null! Snapshot could not be done!");
            return;
        }
        LocalTime time = LocalTime.now();
        String timeStamp = String.format("%02d:%02d:%02d_", time.getHour(), time.getMinute(), time.getSecond());
        File directory = new File(FileUtils.getTempDirectoryPath()
                .concat(File.separator)
                .concat(XML_SNAPSHOT_DIRECTORY)
                .concat(File.separator)
                .concat(timeStamp)
                .concat(currentTestCaseCanonicalName));
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                throw new IOException("Unable to create path '" + directory.toString() + "'.");
            }
        }
        File copyTo = File.createTempFile(PropUtils.get("snapshots.target.file.prefix"),
                PropUtils.get("snapshots.target.file.suffix"), directory);
        File copyFrom = getSourceFile();
        FileUtils.copyFile(copyFrom, copyTo);
        log.debug("Created snapshot of server configuration in '{}'.", copyTo);
    }

    private File getSourceFile() throws IOException {
        try (OnlineManagementClient client = ManagementClientProvider.createOnlineManagementClient()) {
            ModelNodeResult result = new Operations(client).invoke("take-snapshot", Address.root());
            String path = result.stringValue();
            File copyFrom = new File(path);
            if (!copyFrom.exists()) {
                throw new IOException("Source config file does not exits: '" + copyFrom.toString() + "'!");
            }
            return copyFrom;
        }
    }
}
