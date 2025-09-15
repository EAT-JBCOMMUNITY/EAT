package org.jboss.hal.testsuite.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Collections;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * @author mkrajcov <mkrajcov@redhat.com>
 */
public class ZipUtils {

    private static Logger log = LoggerFactory.getLogger(ZipUtils.class);

    /**
     * Updates one specified file inside of archive
     * @param zip target archive file
     * @param file new file that will override old one inside of the archive
     * @param fileName file path of file that should be replaced
     * @throws IOException
     */
    public static void updateOneFileInZip(File zip, File file, String fileName) throws IOException {
        log.info("Updating file {} using {} in zip archive {}", new String[] {fileName, file.getAbsolutePath(), zip.getAbsolutePath()});
        Path filePath = file.toPath();
        try (FileSystem fileSystem = FileSystems.newFileSystem(zip.toPath(), Collections.emptyMap())) {
            Path fileInsideZipPath = fileSystem.getPath(fileName);
            Files.copy(filePath, fileInsideZipPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Unable to update " + fileName + " in " + zip.getAbsolutePath() + " using " + file.getAbsolutePath(), e);
        }
    }

    /**
     * Extracts one file from archive
     * @param zip target archive file
     * @param fileName file path of requested file inside of the archive
     * @param output file will be extract into this output file
     * @return
     */
    public static void extractOneFileFromZip(File zip, String fileName, File output) throws IOException {
        log.info("Extracting {} from {} to {}", new String[]{fileName, zip.getAbsolutePath(), output.getAbsolutePath()});
        try (FileSystem fileSystem = FileSystems.newFileSystem(zip.toPath(), Collections.emptyMap())) {
            Path source = fileSystem.getPath(fileName);
            Files.copy(source, output.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Unable to extract " + fileName +  " in " + zip.getAbsolutePath() + " into " + output.getAbsolutePath(), e);
        }
    }
}
