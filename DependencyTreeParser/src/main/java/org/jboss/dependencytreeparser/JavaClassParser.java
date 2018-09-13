/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.dependencytreeparser;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
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
    static HashMap<String,HashMap<String,String[]>> internalClassMethods = new HashMap<>();
    
    public static HashMap<String,ArrayList<String>> testLibraryUsage() {
        String basedir = System.getProperty("BaseDir");
        String sourcePath = System.getProperty("SourcePath");
        String server = System.getProperty("Server");
        String version = System.getProperty("Version");
        String versionOrderDir = System.getProperty("VersionOrderDir");
        String disableSnapshotVersions = System.getProperty("DisableSnapshotVersions");
        
        HashMap<String,ArrayList<String>> testLibraries = fileProcessing(basedir, sourcePath, server, version, versionOrderDir, "@EapAdditionalTestsuite", disableSnapshotVersions);
        
        return testLibraries;
    }

    public static HashMap<String,ArrayList<String>> getTestLibraries() {
        return testLibraries;
    }

    public static HashMap<String, String> getInternalPackagesAndClasses() {
        return internalPackagesAndClasses;
    }
    
    public static HashMap<String,HashMap<String,String[]>> getInternalClassMethods() {
        return internalClassMethods;
    }
    
    private static void readInternalPackagesAndClasses(FileData fd) {
        internalPackagesAndClasses.put(fd.packageName.replaceAll("/","."),fd.fileName.replaceAll("\\.java", ""));
    }

    private static void readInternalClassMethods(String file, FileData fd) throws IOException {
        InputStream in = null;
        CompilationUnit cu = null;
        try {
            in = new FileInputStream(file);
            cu = JavaParser.parse(in);
            MethodVisitor visitor = new MethodVisitor();
            if(internalClassMethods.get(fd.packageName.replaceAll("/", ".") + "." + fd.fileName.replaceAll("\\.java", ""))==null) {
                internalClassMethods.put(fd.packageName.replaceAll("/", ".") + "." + fd.fileName.replaceAll("\\.java", ""), new HashMap<String,String[]>());
            }
            visitor.visit(cu, internalClassMethods.get(fd.packageName.replaceAll("/", ".") + "." + fd.fileName.replaceAll("\\.java", "")));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            in.close();
        }
    }

    public static HashMap<String,ArrayList<String>> fileProcessing(String basedir, String sourcePath, String server, String version, String versionOrderDir, String searchString, String disableSnapshotVersions) {
        File folder = new File(sourcePath);
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles == null) {
            return null;
        }
        
        try {
            for (File file : listOfFiles) {
                if (file.isDirectory()) {
                    fileProcessing(basedir, file.getAbsolutePath(), server, version, versionOrderDir, searchString, disableSnapshotVersions);
                } else {
                    ArrayList<FileData> output = checkFileForAnnotation(file.getAbsolutePath(), "@EapAdditionalTestsuite", server);
                    for (FileData dest : output) {
                        if (dest.minVersion != null) {
                            boolean isSnapshot = false;
                            if (disableSnapshotVersions != null && disableSnapshotVersions.contains("true")) {
                                isSnapshot = version.contains("SNAPSHOT");
                            }
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
                            String[] subVersionsMax = null;

                            if (dest.maxVersion != null) {
                                subVersionsMax = dest.maxVersion.split("-");
                                verPart = subVersionsMax[0].split("\\.");

                                if (verPart.length > 2) {
                                    verRelease3 = Integer.parseInt(verPart[0] + verPart[1] + verPart[2]);
                                }
                            }
                            
                            if ((subVersions.length >= 1 && verRelease1 == verRelease2)) {

                                String[] vf = new String[2];
                                if (verRelease1 == verRelease2 && subVersions[0].split("\\.").length > 3) {
                                    vf[0] = subVersions[0].substring(0, subVersions[0].lastIndexOf("."));
                                    vf[1] = subVersions[0].substring(subVersions[0].lastIndexOf(".") + 1);
                                } else {
                                    vf[0] = subVersions[0];
                                    vf[1] = null;
                                }

                                File versionFolder = new File(basedir + "/" + versionOrderDir + "/" + server + "/" + vf[0]);
                                if (vf != null && versionFolder.exists()) {
                                    String versionsString = readFile(basedir + "/" + versionOrderDir + "/" + server + "/" + vf[0]);
                                    if (versionsString != null) {
                                        String[] versions = null;
                                        if (vf[1] != null) {
                                            versions = versionsString.substring(versionsString.indexOf(vf[1])).split(",");
                                        } else {
                                            versions = versionsString.split(",");
                                        }
                                        for (String versionPart : versionRelease) {
                                            if (versionPart.contains(".")) {
                                                String[] versionNums = versionPart.split("\\.");
                                                String lastPart = versionNums[versionNums.length - 1];
                                                if (!lastPart.matches("[0-9]+")) {
                                                    for (String ver : versions) {
                                                        if (lastPart.contains(ver)) {
                                                            if (!(ver.equals(lastPart) && isSnapshot)) {
                                                                if ((verRelease3 == 0 || verRelease1 < verRelease3)) {
                                                                    String f = basedir + "/" + dest.fileBaseDir + "/" + dest.packageName + "/" + dest.fileName;
                                                                    System.out.println(f);
                                                                    readTestLibrariesFromFile(f, testLibraries, searchString);
                                                                    readInternalPackagesAndClasses(dest);
                                                                    readInternalClassMethods(f,dest);
                                                                } else if (verRelease1 == verRelease3) {
                                                                    procedure0(dest, server, basedir, versionOrderDir, verRelease1, verRelease3, subVersionsMax, versionRelease, isSnapshot, searchString);
                                                                }
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    if (!(verRelease1 == verRelease2 && isSnapshot)) {
                                                        if ((verRelease3 == 0 || verRelease1 < verRelease3)) {
                                                            String f = basedir + "/" + dest.fileBaseDir + "/" + dest.packageName + "/" + dest.fileName;
                                                            System.out.println(f);
                                                            readTestLibrariesFromFile(f, testLibraries, searchString);
                                                            readInternalPackagesAndClasses(dest);
                                                            readInternalClassMethods(f,dest);
                                                        } else if (verRelease1 == verRelease3) {
                                                            procedure0(dest, server, basedir, versionOrderDir, verRelease1, verRelease3, subVersionsMax, versionRelease, isSnapshot, searchString);
                                                        }
                                                    }
                                                }

                                            }
                                        }
                                    }
                                }
                            } else if (verRelease1 >= verRelease2) {
                                if (!(verRelease1 == verRelease2 && isSnapshot)) {
                                    if (verRelease3 == 0 || verRelease1 < verRelease3) {
                                        if (verRelease1 != verRelease2 || !isSnapshot) {
                                            String f = basedir + "/" + dest.fileBaseDir + "/" + dest.packageName + "/" + dest.fileName;
                                            System.out.println(f);
                                            readTestLibrariesFromFile(f, testLibraries, searchString);
                                            readInternalPackagesAndClasses(dest);
                                            readInternalClassMethods(f,dest);
                                        }
                                    } else if (verRelease1 == verRelease3) {
                                        procedure0(dest, server, basedir, versionOrderDir, verRelease1, verRelease3, subVersionsMax, versionRelease, isSnapshot, searchString);
                                    }
                                }
                            }
                        } else {
                            String f = basedir + "/" + dest.fileBaseDir + "/" + dest.packageName + "/" + dest.fileName;
                            System.out.println(f);
                            readTestLibrariesFromFile(f, testLibraries, searchString);
                            readInternalPackagesAndClasses(dest);
                            readInternalClassMethods(f,dest);
                        }
                    }

                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            return testLibraries;
        }
    }
    
    private static void procedure0(FileData dest, String server, String basedir, String versionOrderDir, int verRelease1, int verRelease3, String[] subVersionsMax, String[] versionRelease, boolean isSnapshot, String searchString) throws IOException, ClassNotFoundException {
        if (verRelease1 == verRelease3) {

            String[] vf = new String[2];
            if (subVersionsMax != null && verRelease1 == verRelease3 && subVersionsMax[0].split("\\.").length > 3) {
                vf[0] = subVersionsMax[0].substring(0, subVersionsMax[0].lastIndexOf("."));
                vf[1] = subVersionsMax[0].substring(subVersionsMax[0].lastIndexOf(".") + 1);
            } else {
                vf[0] = subVersionsMax[0];
                vf[1] = null;
            }

            File versionFolder = new File(basedir + "/" + versionOrderDir + "/" + server + "/" + vf[0]);
            if (vf != null && versionFolder.exists()) {
                String versionsString = readFile(basedir + "/" + versionOrderDir + "/" + server + "/" + vf[0]);
                if (versionsString != null) {
                    String[] versions = null;
                    if (vf[1] != null) {
                        versions = versionsString.substring(0, versionsString.indexOf(vf[1])).concat(vf[1]).split(",");
                    } else {
                        versions = versionsString.split(",");
                    }
                    for (String versionPart : versionRelease) {
                        if (versionPart.contains(".")) {
                            String[] versionNums = versionPart.split("\\.");
                            String lastPart = versionNums[versionNums.length - 1];
                            if (!lastPart.matches("[0-9]+")) {
                                for (String ver : versions) {
                                    if (lastPart.contains(ver) && !(ver.equals(lastPart) && isSnapshot)) {
                                        String f = basedir + "/" + dest.fileBaseDir + "/" + dest.packageName + "/" + dest.fileName;
                                        System.out.println(f);
                                        readTestLibrariesFromFile(f, testLibraries, searchString);
                                        readInternalPackagesAndClasses(dest);
                                        readInternalClassMethods(f,dest);
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (verRelease1 <= verRelease3) {
                if (!(verRelease1 == verRelease3 && isSnapshot)) {
                    String f = basedir + "/" + dest.fileBaseDir + "/" + dest.packageName + "/" + dest.fileName;
                    System.out.println(f);
                    readTestLibrariesFromFile(f, testLibraries, searchString);
                    readInternalPackagesAndClasses(dest);
                    readInternalClassMethods(f,dest);
                }
            }
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
                        }
                    }else if(className!=null){
                        if(!testLibraries.get(library).contains(className))
                            testLibraries.get(library).add(className);
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

class MethodVisitor extends VoidVisitorAdapter
{
    public void visit(MethodDeclaration n, Object arg)
    {
        String[] params = new String[n.getParameters().size()];

        for(int i=0; i<n.getParameters().size(); i++)
            params[i]=n.getParameters().get(i).getTypeAsString();
        
        
        ((HashMap<String,String[]>)arg).put(n.getName().toString(), params);
        ((HashMap<String,String[]>)arg).put(n.getName().toString()+"_Return_Type", new String[]{n.getTypeAsString()});
        
    }
}