/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.onetomanyversions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author panos
 */
public class OneToManyVersions {

    static int linenum;
    static int packagenum;

    public static void main(String[] args) throws Exception {
        String fileName = System.getProperty("MultipleVersionsFilePath");
        ArrayList<FileData> fd = checkFileForVersions(fileName);
        String annotationparam = "";
        String destination = System.getProperty("MultipleVersionsDestination");
        String packageName = null;

        for (int i = 0; i < fd.size(); i++) {
            if (annotationparam.compareTo("") != 0) {
                annotationparam = annotationparam + ",";
            }
            packageName = fd.get(i).packageName.replaceAll("\\.", "\\/");
            System.out.println(packageName);
            annotationparam = annotationparam + "\"modules/testcases/jdkAll/" + fd.get(i).server + "/" + fd.get(i).module + "/src/main/java";
            if (fd.get(i).minVersion != null) {
                annotationparam = annotationparam + "#" + fd.get(i).minVersion;
            } else if(!annotationparam.endsWith("\"")){
                annotationparam = annotationparam + "\"";
            }

            if (fd.get(i).maxVersion != null) {
                annotationparam = annotationparam + "*" + fd.get(i).maxVersion + "\"";
            } else if(!annotationparam.endsWith("\"")){
                annotationparam = annotationparam + "\"";
            }

            System.out.println(fd.get(i).fileName + " " + fd.get(i).server + " " + fd.get(i).module + " " + fd.get(i).minVersion + " " + fd.get(i).maxVersion + " " + fd.get(i).packageName);
        }

        String annotation = "@EAT({" + annotationparam + "})";
        List<String> list = Files.readAllLines(new File(fileName).toPath(), Charset.defaultCharset());
        list.add(linenum, annotation);
        list.add(packagenum, "import org.jboss.eap.additional.testsuite.annotations.EapAdditionalTestsuite;");

        Path dir = Paths.get(destination+"/"+packageName);
        Path out = Paths.get(destination+"/"+packageName+"/"+fileName.substring(fileName.lastIndexOf("/")));
        System.out.println(out.toString());
        if(Files.notExists(out)) {
            Files.createDirectories(dir);
            Files.createFile(out);
        }
        Files.write(out, list, Charset.defaultCharset());
    }

    private static ArrayList<FileData> checkFileForVersions(String file) throws ClassNotFoundException {
        String[] destinations = null;
        String annotationLine = null;
        ArrayList<FileData> result = new ArrayList<FileData>();
        File f = new File(file);
        ArrayList<String> servers = new ArrayList<>();
        ArrayList<String> modules = new ArrayList<>();
        ArrayList<String> lowerlimit = new ArrayList<>();
        ArrayList<String> upperlimit = new ArrayList<>();
        String packageName = null;

        linenum = 0;
        packagenum = 0;

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("EATSERVERS") || line.contains("EATSERVERMODULE") || line.contains("EATLOWERLIMITVERSIONS") || line.contains("EATUPPERLIMITVERSIONS")) {
                    annotationLine = line;
                    destinations = annotationLine.split(":");
                    destinations = destinations[1].split(",");
                    for (String elem : destinations) {
                        if (line.contains("EATSERVERS")) {
                            servers.add(elem.trim());
                        } else if (line.contains("EATSERVERMODULE")) {
                            modules.add(elem.trim());
                        } else if (line.contains("EATLOWERLIMITVERSIONS")) {
                            lowerlimit.add(elem.trim());
                        } else if (line.contains("EATUPPERLIMITVERSIONS")) {
                            upperlimit.add(elem.trim());
                        }
                    }

                }

                if (line.contains(" class ")) {
                    break;
                } else {
                    linenum++;
                    if (line.startsWith("package")) {
                        packageName = line.replaceAll("package ", "").replaceAll(";", "").trim();
                        packagenum = linenum;
                    }
             
                }
            }

            int versions = servers.size();

            for (int i = 0; i < versions; i++) {
                String server = servers.get(i);
                String module = null;
                if (modules.size() == 1) {
                    module = modules.get(0);
                } else {
                    module = modules.get(i);
                }
                String lower = null;
                if (lowerlimit.size() == servers.size()) {
                    lower = lowerlimit.get(i);
                }
                String upper = null;
                if (upperlimit.size() == servers.size()) {
                    upper = upperlimit.get(i);
                }

                FileData fd = new FileData(file, lower, upper, server, module, packageName);
                result.add(fd);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        return result;
    }

}
