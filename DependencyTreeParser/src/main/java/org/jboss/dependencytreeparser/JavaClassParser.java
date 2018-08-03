/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.dependencytreeparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author panos
 */
public class JavaClassParser {
    static HashMap<String,ArrayList<String>> testLibraries = new HashMap<>();
    static HashMap<String,String> internalPackagesAndClasses = new HashMap<>();
    
    public static HashMap<String,ArrayList<String>> testLibraryUsage() {
        String basedir = System.getProperty("BaseDir");
        String sourcePath = System.getProperty("SourcePath");
        String server = System.getProperty("Server");
        String version = System.getProperty("Version");
        String versionOrderDir = System.getProperty("VersionOrderDir");
        
        HashMap<String,ArrayList<String>> testLibraries = fileProcessing(basedir, sourcePath, server, version, versionOrderDir, "@EapAdditionalTestsuite");
        
        return testLibraries;
    }

    public static HashMap<String,ArrayList<String>> getTestLibraries() {
        return testLibraries;
    }

    public static HashMap<String, String> getInternalPackagesAndClasses() {
        return internalPackagesAndClasses;
    }
    
    private static void readInternalPackagesAndClasses(FileData fd) {
        internalPackagesAndClasses.put(fd.packageName.replaceAll("/","."),fd.fileName.replaceAll("\\.java", ""));
    }
    
    public static HashMap<String,ArrayList<String>> fileProcessing(String basedir, String sourcePath, String server, String version, String versionOrderDir, String searchString) {
        File folder = new File(sourcePath);
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles == null) {
            return null;
        }

        try {
            for (File file : listOfFiles) {
                if (file.isDirectory()) {
                    fileProcessing(basedir, file.getAbsolutePath(), server, version, versionOrderDir, searchString);
                } else {
                    ArrayList<FileData> output = checkFileForAnnotation(file.getAbsolutePath(), searchString, server);
                    for (FileData dest : output) {
                        if (dest.minVersion != null) {
                            String[] versionRelease = version.split("-");
                            int verRelease1 = 0;
                            String[] verPart = versionRelease[0].split("\\.");
                            if (verPart.length > 2) {
                                verRelease1 = Integer.parseInt(verPart[0] + verPart[1] + verPart[2]);
                            }
                            String[] subVersions = dest.minVersion.split("-");
                            verPart = subVersions[0].split("\\.");
                            int verRelease2 = 0;
                            if (verPart.length > 2) {
                                verRelease2 = Integer.parseInt(verPart[0] + verPart[1] + verPart[2]);
                            }

                            int verRelease3 = 0;

                            if (dest.maxVersion != null) {
                                String[] subVersionsMax = dest.maxVersion.split("-");
                                verPart = subVersionsMax[0].split("\\.");

                                if (verPart.length > 2) {
                                    verRelease3 = Integer.parseInt(verPart[0] + verPart[1] + verPart[2]);
                                }
                            }

                            if (subVersions.length > 1 && verRelease1 == verRelease2) {
                                File versionFolder = new File(basedir + "/" + versionOrderDir + "/" + server + "/" + subVersions[0]);
                                if (versionFolder.exists()) {
                                    String versionsString = readFile(basedir + "/" + versionOrderDir + "/" + server + "/" + subVersions[0]);
                                    if (versionsString != null && versionsString.contains(subVersions[1])) {
                                        String[] versions = versionsString.substring(versionsString.indexOf(subVersions[1])).split(",");
                                        for (String versionPart : versionRelease) {
                                            if (versionPart.contains(".")) {
                                                String[] versionNums = versionPart.split("\\.");
                                                String lastPart = versionNums[versionNums.length - 1];
                                                if (!lastPart.matches("[0-9]+")) {
                                                    for (String ver : versions) {
                                                        if (lastPart.contains(ver) || lastPart.compareTo(ver) == 0) {
                                                            String f = basedir + "/" + dest.fileBaseDir + "/" + dest.packageName + "/" + dest.fileName;
                                                            readTestLibrariesFromFile(f, testLibraries, searchString);
                                                            readInternalPackagesAndClasses(dest);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            } else if (verRelease1 >= verRelease2 && (verRelease3 == 0 || verRelease1 <= verRelease3)) {
                                String f = basedir + "/" + dest.fileBaseDir + "/" + dest.packageName + "/" + dest.fileName;
                                readTestLibrariesFromFile(f, testLibraries, searchString);
                                readInternalPackagesAndClasses(dest);
                            }
                        } else {
                            String f = basedir + "/" + dest.fileBaseDir + "/" + dest.packageName + "/" + dest.fileName;
                            readTestLibrariesFromFile(f, testLibraries, searchString);
                            readInternalPackagesAndClasses(dest);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return testLibraries;
        }
    }
    
    private static void readTestLibrariesFromFile(String file,  HashMap<String,ArrayList<String>> testLibraries, String searchString) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));

        try {
            String line = br.readLine();

            while (line != null) {
                if(line.trim().startsWith("package")) {
                    String library = line.trim().replaceFirst("package", "").replaceAll(";", "");
                    if(!testLibraries.keySet().contains(library)) {
              //          testLibraries.add(library);
                    }
                }else if(line.trim().startsWith("import")) {
                    String library = line.trim().replaceFirst("import", "").trim().replaceFirst("static", "").trim().replaceAll(";", "");
                    String lib2=library;
                    String className = null;
                    while(lib2.lastIndexOf(".")!=-1) {
                        if(Character.isUpperCase(lib2.substring(lib2.lastIndexOf(".")+1).charAt(0))){
                            library = lib2.substring(0, lib2.lastIndexOf("."));
                            className = lib2.substring(lib2.lastIndexOf(".")+1);
                        }
                        lib2=lib2.substring(0,lib2.lastIndexOf(".")-1);
                    }
                    if(!testLibraries.keySet().contains(library)) {
                        if (testLibraries.get(library)==null) {
                            ArrayList<String> al = new ArrayList<>();
                            if(className!=null)
                                al.add(className);
                            testLibraries.put(library,al);
                        }else if(className!=null){
                            if(!testLibraries.get(library).contains(className))
                                testLibraries.get(library).add(className);
                        }
                    }
                }else if(line.contains(searchString))
                    break;
                
                line = br.readLine();
            }
        } finally {
            br.close();
        }
    }
    
    private static String readFile(String file) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuilder sb = new StringBuilder();
        try {
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
        } finally {
            br.close();
        }

        return sb.toString();
    }
    
    private static ArrayList<FileData> checkFileForAnnotation(String file, String annotationName, String server) throws ClassNotFoundException {
        String[] destinations = null;
        String annotationLine = null;
        ArrayList<FileData> result = new ArrayList<FileData>();
        String packageName = "";
        File f = new File(file);

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("package")) {
                    packageName = line.replaceAll("package ", "").replaceAll(";", "").trim();
                }
                if (line.contains(annotationName)) {
                    annotationLine = line;
                    destinations = annotationLine.split("\"");
                    for (String path : destinations) {
                        if (!path.contains(",") && path.contains("/" + server + "/")) {
                            if (!path.contains("#") && !path.contains("*")) {
                                result.add(new FileData(f.getName(), packageName.replaceAll("\\.", "/"), path, null, null));
                            } else if (!path.contains("*")) {
                                String[] pathVersion = path.split("#");
                                result.add(new FileData(f.getName(), packageName.replaceAll("\\.", "/"), pathVersion[0], pathVersion[1], null));
                            } else {
                                String[] pathVersion = path.split("\\*");
                                String[] pathVersion2 = pathVersion[0].split("#");
                                result.add(new FileData(f.getName(), packageName.replaceAll("\\.", "/"), pathVersion2[0], pathVersion2[1], pathVersion[1]));
                            }
                        }
                    }
                    break;
                }
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

class FileData {

    protected String fileName;
    protected String packageName;
    protected String fileBaseDir;
    protected String minVersion;
    protected String maxVersion;
    protected int lineNum;

    public FileData(String fileName, String packageName, String fileBaseDir, String minVersion, String maxVersion) {
        this.fileName = fileName;
        this.packageName = packageName;
        this.fileBaseDir = fileBaseDir;
        this.minVersion = minVersion;
        this.maxVersion = maxVersion;
    }

    public FileData(String fileName, String packageName, String fileBaseDir, String minVersion, String maxVersion, int lineNum) {
        this.fileName = fileName;
        this.packageName = packageName;
        this.fileBaseDir = fileBaseDir;
        this.minVersion = minVersion;
        this.maxVersion = maxVersion;
        this.lineNum = lineNum;
    }

}
