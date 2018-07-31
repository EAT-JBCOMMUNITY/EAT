/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.dependencytreeparser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import static javax.management.DefaultLoaderRepository.loadClass;

/**
 *
 * @author panos
 */
public class DependencyTreeMethods {
    
    public static void printDependencies() throws IOException {
        String filePath = System.getProperty("DependencyTreeFilePath");
        boolean deleteAll =true;
        String keyWord = "--- maven-dependency-plugin";
        String keyWord2 = "--------------------------------------------------";

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filePath));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                if(line.trim().isEmpty() || line.contains(keyWord2))
                    deleteAll = true;
                
                if(!deleteAll) {
                    if(line.contains("+") || line.contains("\\"))
                        sb.append(line.replaceAll("\\+", "").replaceFirst("-", "").replaceAll("\\|", "").replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("INFO", "").replace("\\", "").trim());
                    else
                        sb.append(line.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("INFO", "").trim());
                    sb.append(System.lineSeparator());
                }
                
                line = br.readLine();
                
                if(line!=null && line.contains(keyWord)) {
                    deleteAll = false;
                    line = br.readLine();
                }
                
                
            }
            String everything = sb.toString();
            System.out.println(everything);
        } catch(Exception e) {
            e.printStackTrace();
        }finally {
            br.close();
        }
    }
    
    public static ArrayList<Artifact> getArtifacts() throws IOException {
        String filePath = System.getProperty("DependencyTreeFilePath");
        ArrayList<Artifact> artifacts = new ArrayList<Artifact>();
        boolean deleteAll =true;
        String keyWord = "--- maven-dependency-plugin";
        String keyWord2 = "--------------------------------------------------";

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filePath));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                if(line.trim().isEmpty() || line.contains(keyWord2))
                    deleteAll = true;
                
                if(!deleteAll) {
                    Artifact art = new Artifact();
                    String[] parts;
                    if(line.contains("+") || line.contains("\\"))
                        parts = line.replaceAll("\\+", "").replaceFirst("-", "").replaceAll("\\|", "").replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("INFO", "").replace("\\", "").trim().split(":");
                    else
                        parts = line.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("INFO", "").trim().split(":");
                    
                    if (parts.length==5) {
                        art.artifactId = parts[0];
                        art.groupId = parts[1];
                        art.type = parts[2];
                        art.version = parts[3];
                        art.scope = parts[4];
                        
                        if (!parts[4].contains("optional") && !parts[4].contains("test"))
                            artifacts.add(art);
                    }else if(parts.length==6) {
                        art.artifactId = parts[0];
                        art.groupId = parts[1];
                        art.type = parts[2];
                        art.version = parts[4];
                        art.scope = parts[5];
                    }
                    
                    
                }
                
                line = br.readLine();
                
                if(line!=null && line.contains(keyWord)) {
                    deleteAll = false;
                    line = br.readLine();
                }
                
                
            }
            
            String filePath2 = System.getProperty("ExternalDependencyPath");
            artifacts = addExternalLibraries(artifacts, filePath2);
            
            for(Artifact a:artifacts){
                System.out.println(a.artifactId + " " + a.groupId + " " + a.version + " " + a.type + " " + a.scope);
            }
            
        } catch(Exception e) {
            e.printStackTrace();
        }finally {
            br.close();
            return artifacts;
        }
    }
    
    public static ArrayList<Artifact> addExternalLibraries(ArrayList<Artifact> artifacts, String filePath) throws IOException {
        BufferedReader br = null;
        try {
            FileReader fr = new FileReader(filePath);
            if(fr!=null) {
                br = new BufferedReader(fr);
                String line = br.readLine();

                while (line != null) {
                        Artifact art = new Artifact();
                        String[] parts;
                        parts = line.trim().split(":");

                        if (parts.length==4) {
                            art.artifactId = parts[0];
                            art.groupId = parts[1];
                            art.type = parts[2];
                            art.version=parts[3];
                                    

                            artifacts.add(art);
                        }        

                    line = br.readLine();
                }

            //    for(Artifact a:artifacts){
            //        System.out.println(a.artifactId + " " + a.groupId + " " + a.version + " " + a.type + " " + a.scope);
            //    }
            }
            
        } catch(Exception e) {
            e.printStackTrace();
        }finally {
            if(br!=null)
                br.close();
            return artifacts;
        }
    }
    
    public static HashMap<String,ArrayList<Class[]>> listClasses(){
        HashMap<String,ArrayList<Class[]>> jarClasses = new HashMap<>();
         
        try {
            ArrayList<Artifact> artifacts = DependencyTreeMethods.getArtifacts();
            String repoPath = System.getProperty("MavenRepoPath");
            
            for(Artifact ar : artifacts) {
                if(ar.type.contains("jar")) {
                 //   System.out.println(repoPath + "/" + ar.artifactId.replaceAll("\\.", "//")+"/"+ar.groupId+"/"+ar.version+"/"+ar.groupId + "-" + ar.version+".jar");
                    jarClasses.putAll(DependencyTreeMethods.listJars(repoPath + "/"+ ar.artifactId.replaceAll("\\.", "//")+"/"+ar.groupId+"/"+ar.version+"/"+ar.groupId + "-" + ar.version+".jar"));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return jarClasses;
        }
    }
    
    public static HashSet<String> listPackages(){
        HashSet<String> packages = new HashSet<String>();
        
        HashMap<String,ArrayList<Class[]>> jarClasses = DependencyTreeMethods.listClasses();
        
        System.out.println("jarClasses.size() : " + jarClasses.size());
        
        for(String jc : jarClasses.keySet()){
            if(jc.contains(".class") && jc.lastIndexOf("/")!=-1) {
                String packageName = jc.substring(0, jc.lastIndexOf("/")).replaceAll("/", ".");
           //     System.out.println("packageName : " + packageName);
                    if(!packages.contains(packageName))
                        packages.add(packageName);
            }
        }
        
        return packages;
    }
    
    private static HashMap<String,ArrayList<Class[]>> listJars(String path) {
        HashMap<String,ArrayList<Class[]>> jarClasses = new HashMap<>();
        
        try{
            if (path!=null) {
                JarFile jarFile = new JarFile(path);
               
                URL[] urls = { new URL("jar:file:" + path+"!/") };
                URLClassLoader cl = URLClassLoader.newInstance(urls);

                Enumeration allEntries = jarFile.entries();
                while (allEntries.hasMoreElements()) {
                    JarEntry entry = (JarEntry) allEntries.nextElement();
                    String name = entry.getName();
                    
                    if(name.contains(".class") && !name.contains("$")) {
                        name = name.substring(0,name.lastIndexOf(".class"));
                        name=name.replaceAll("/", ".");
                        Class clas = cl.loadClass(name);
                        Constructor[] constructors = clas.getConstructors();
                        ArrayList<Class[]> constructorParams = new ArrayList<>();
                        for(Constructor c : constructors) {
                            Class[] parameterTypes = c.getParameterTypes();
                            constructorParams.add(parameterTypes);
                        }
                        jarClasses.put(name,constructorParams);
                    }
                //    System.out.println(name);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            return jarClasses;
        }
    }

}

class Artifact {
    String groupId;
    String artifactId;
    String version;
    String type;
    String scope;
}
