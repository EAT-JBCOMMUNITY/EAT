package org.eat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import org.apache.commons.io.FileUtils;

public class Main {

    private static HashSet<String> processedFiles = new HashSet();

    public static void main(String[] args) throws IOException, InterruptedException {
        String userHomeDir = System.getProperty("user.home");
        //Define the repo to check/process the tests
        String originUrl = "https://github.com/wildfly/wildfly.git";
        String dir = userHomeDir + "/test";
        String outputdir = userHomeDir + "/test/output";
        //Define the tags to check/process the tests (versions should be in descending order)
        String tags = "25.0.1.Final,25.0.0.Final,25.0.0.Beta1,24.0.1.Final,24.0.0.Final,24.0.0.Beta1,23.0.2.Final,23.0.1.Final,23.0.0.Final,23.0.0.Beta1";
        // The directory where the tests exist withing the cloned branch
        String testdir = "testsuite";
        // Annotation addition to be done ... (comment of the version range available at the end of each processed file)
        String annotation = "@EAT({\"modules/testcases/jdkAll/Wildfly/server/src/main/java\"})";
        Main.cloneRepos(originUrl, dir, tags);
        Main.processFiles(dir, tags, testdir, outputdir, annotation);
    }

    private static void processFiles(String dir, String tags, String testdir, String outputdir, String annotation) throws IOException {
        String[] stringTags = tags.split(",");

        for (int tag = 0; tag < stringTags.length; tag++) {
            File path = new File(dir + "/" + stringTags[tag] + "/" + testdir);
            Main.fetchFiles(path, tags, tag, annotation, outputdir, testdir, dir);
        }
    }

    public static void fetchFiles(File dir, String tags, int tag, String annotation, String outputdir, String testdir, String initdir) throws IOException {

        String[] stringTags = Arrays.copyOfRange(tags.split(","), tag, tags.split(",").length);

        if (dir.isDirectory()) {
            for (File file1 : dir.listFiles()) {
                fetchFiles(file1, tags, tag, annotation, outputdir, testdir, initdir);
            }
        } else if (dir.isFile() && dir.getName().endsWith(".java") && !processedFiles.contains(dir.getName())) {
            tag = 0;
            processedFiles.add(dir.getName());

            String lastTag = stringTags[tag];
            String firstTag = stringTags[tag];
            File existent = null;
            for (String tagName : stringTags) {
                if (!FileUtils.contentEquals(new File(dir.toString().replaceAll(stringTags[tag], lastTag)), new File(dir.toString().replaceAll(stringTags[tag], tagName)))) {
                    File n = new File(dir.toString().replaceAll(stringTags[tag], firstTag));
                    File d = new File(dir.toString().replaceAll(stringTags[tag], firstTag).replaceAll(initdir, outputdir));
                    Files.createDirectories(d.toPath().getParent());
                    if (n.exists()) {
                        existent = n;
                    } else {
                        Files.copy(existent.toPath(), d.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        Files.write(d.toPath(), String.valueOf("//#" + firstTag + "*" + lastTag).getBytes(), StandardOpenOption.APPEND);
                        break;
                    }
                    firstTag = tagName;
                    if (tagName.compareTo(stringTags[stringTags.length - 1]) == 0) {
                        n = new File(dir.toString().replaceAll(stringTags[tag], firstTag));
                        d = new File(dir.toString().replaceAll(stringTags[tag], firstTag).replaceAll(initdir, outputdir));
                        if (n.exists()) {
                            Files.createDirectories(d.toPath().getParent());
                            Files.copy(n.toPath(), d.toPath(), StandardCopyOption.REPLACE_EXISTING);
                            Files.write(d.toPath(), String.valueOf("//#" + firstTag + "*" + lastTag).getBytes(), StandardOpenOption.APPEND);
                        }
                    }
                } else {
                    firstTag = tagName;
                    if (tagName.compareTo(stringTags[stringTags.length - 1]) == 0) {
                        File n = new File(dir.toString().replaceAll(stringTags[tag], firstTag));
                        File d = new File(dir.toString().replaceAll(stringTags[tag], firstTag).replaceAll(initdir, outputdir));
                        if (n.exists()) {
                            Files.createDirectories(d.toPath().getParent());
                            Files.copy(n.toPath(), d.toPath(), StandardCopyOption.REPLACE_EXISTING);
                            Files.write(d.toPath(), String.valueOf("//#" + firstTag + "*" + lastTag).getBytes(), StandardOpenOption.APPEND);
                        }
                    }
                }
            }
        }
    }

    private static void cloneRepos(String originUrl, String dir, String tags) throws IOException, InterruptedException {
        String[] stringTags = tags.split(",");
        for (String tag : stringTags) {
            Path directory = Paths.get(dir + "/" + tag.trim());
            gitClone(directory, originUrl, tag.trim());
        }

    }

    public static void gitClone(Path directory, String originUrl, String tag) throws IOException, InterruptedException {
        runCommand(directory.getParent(), "git", "clone", originUrl, "-b", tag, directory.getFileName().toString());
    }

    public static void gitClone(Path directory, String originUrl) throws IOException, InterruptedException {
        runCommand(directory.getParent(), "git", "clone", originUrl, directory.getFileName().toString());
    }

    public static void runCommand(Path directory, String... command) throws IOException, InterruptedException {
        Objects.requireNonNull(directory, "directory");
        if (!Files.exists(directory)) {
            throw new RuntimeException("can't run command in non-existing directory '" + directory + "'");
        }
        ProcessBuilder pb = new ProcessBuilder()
                .command(command)
                .directory(directory.toFile());
        Process p = pb.start();
        int exit = p.waitFor();
        if (exit != 0) {
            System.out.println(String.format("runCommand " + Arrays.toString(command) + " returned %d", exit));

        }
    }
}
