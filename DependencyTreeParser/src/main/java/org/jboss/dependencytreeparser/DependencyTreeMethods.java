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
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 *
 * @author panos
 */
public class DependencyTreeMethods {
    
    public static HashMap<String,String> jarClassPaths = new HashMap<>();
    public static HashSet<Artifact> artifacts = new HashSet<>();
    
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
    
    public static HashSet<Artifact> getArtifacts() throws IOException {
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
                        
                        artifacts.add(art);
                    }else if(parts.length==6) {
                        art.artifactId = parts[0];
                        art.groupId = parts[1];
                        art.type = parts[2];
                        art.version = parts[4];
                        art.scope = parts[5];
                        
                        artifacts.add(art);
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
            
        //    for(Artifact a:artifacts){
        //        System.out.println(a.artifactId + " " + a.groupId + " " + a.version + " " + a.type + " " + a.scope);
        //    }
            
        } catch(Exception e) {
            e.printStackTrace();
        }finally {
            br.close();
            return artifacts;
        }
    }
    
    public static HashSet<String> addExcludedLibraries() throws IOException {
        HashSet<String> excludedLibraries = new HashSet<>();
        String filePath = System.getProperty("ExcludedDependenciesPath");
                
        BufferedReader br = null;
        try {
            FileReader fr = new FileReader(filePath);
            if(fr!=null) {
                br = new BufferedReader(fr);
                String line = br.readLine();

                while (line != null) {
                    excludedLibraries.add(line);
                    line = br.readLine();
                }

            }
            
        } catch(Exception e) {
            e.printStackTrace();
        }finally {
            if(br!=null)
                br.close();
            return excludedLibraries;
        }
    }
    
    public static HashSet<Artifact> addExternalLibraries(HashSet<Artifact> artifacts, String filePath) throws IOException {
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
    
    public static HashMap<String,String> listJarClassPaths(){
        HashMap<String,String> jarClassPaths = new HashMap<>();
         
        try {
            String repoPath = System.getProperty("MavenRepoPath");
            
            for(Artifact ar : artifacts) {
                if(ar.type.contains("jar")) {
                //    System.out.println(repoPath + "/" + ar.artifactId.replaceAll("\\.", "//")+"/"+ar.groupId+"/"+ar.version+"/"+ar.groupId + "-" + ar.version+".jar");
                    jarClassPaths.putAll(DependencyTreeMethods.listJarClassPaths(repoPath + "/"+ ar.artifactId.replaceAll("\\.", "//")+"/"+ar.groupId+"/"+ar.version+"/"+ar.groupId + "-" + ar.version+".jar"));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return jarClassPaths;
        }
    }
    
    public static HashMap<String,String> listFieldsOfJarClass(String path, String className) {
        HashMap<String,String> jarClassFields = new HashMap<>();
        
        try{
            if (path!=null) {
                JarFile jarFile = new JarFile(path);
                
               
                URL[] urls = { new URL("jar:file:" + path+"!/") };
                URLClassLoader cl = URLClassLoader.newInstance(urls);

                Enumeration<JarEntry> allEntries = jarFile.entries();
                while (allEntries.hasMoreElements()) {
                    JarEntry entry = (JarEntry) allEntries.nextElement();
                    String name = entry.getName();
                    
            //        System.out.println("Entry Name : " + name);
                    
                    if(name.replaceAll("/", ".").replaceAll("-", ".").contains(className) && name.endsWith(".class") && !name.contains("$") && !entry.isDirectory()) {
                        name = name.substring(0,name.lastIndexOf(".class"));
                        name=name.replaceAll("/", ".");
                        name=name.replaceAll("-", ".");
                        
                        try{
                            Class clas = cl.loadClass(name);

                            for (Class<?> c = clas; c != null; c = c.getSuperclass()) {
                                for(Field f : c.getFields()) {
                                    if(Modifier.toString(f.getModifiers()).contains("public")){
                                        if(!jarClassFields.keySet().contains(f))
                                            jarClassFields.put(f.getName(),f.getType().toString());
                                    }
                                }
                            }       
                         
                        }catch(Exception ex){
                        //    ex.printStackTrace();
                        }
                    }

                }
                jarFile.close();
            }
        }catch(Exception e){
        //    e.printStackTrace();
            System.out.println(path + " is not available.");
        }finally {
            return jarClassFields;
        }
    }
    
    public static HashMap<String,ArrayList<Class[]>> listClasses(){
        HashMap<String,ArrayList<Class[]>> jarClasses = new HashMap<>();
         
        try {
            String repoPath = System.getProperty("MavenRepoPath");
            
            for(Artifact ar : artifacts) {
                if(ar.type.contains("jar")) {
                //    System.out.println(repoPath + "/" + ar.artifactId.replaceAll("\\.", "//")+"/"+ar.groupId+"/"+ar.version+"/"+ar.groupId + "-" + ar.version+".jar");
                    jarClasses.putAll(DependencyTreeMethods.listJarClasses(repoPath + "/"+ ar.artifactId.replaceAll("\\.", "//")+"/"+ar.groupId+"/"+ar.version+"/"+ar.groupId + "-" + ar.version+".jar"));
                    jarClassPaths.putAll(listJarClassPaths(repoPath + "/"+ ar.artifactId.replaceAll("\\.", "//")+"/"+ar.groupId+"/"+ar.version+"/"+ar.groupId + "-" + ar.version+".jar"));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return jarClasses;
        }
    }
    
    public static HashMap<String,HashMap<String,Class[]>> listMethods(){
        HashMap<String,HashMap<String,Class[]>> classMethods = new HashMap<>();
         
        try {
            String repoPath = System.getProperty("MavenRepoPath");
            
            for(Artifact ar : artifacts) {
                if(ar.type.contains("jar")) {
                //    System.out.println(repoPath + "/" + ar.artifactId.replaceAll("\\.", "//")+"/"+ar.groupId+"/"+ar.version+"/"+ar.groupId + "-" + ar.version+".jar");
                    classMethods.putAll(DependencyTreeMethods.listClassMethods(repoPath + "/"+ ar.artifactId.replaceAll("\\.", "//")+"/"+ar.groupId+"/"+ar.version+"/"+ar.groupId + "-" + ar.version+".jar"));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return classMethods;
        }
    }
    
    public static HashMap<String,HashMap<String,Class[]>> listUsedMethods(HashSet<String> usedLibraries, HashMap<String,String> packages){
        HashMap<String,HashMap<String,Class[]>> usedMethods = new HashMap<>();
         
        try {
            
            for(String lb : usedLibraries) {
                //    System.out.println(repoPath + "/" + ar.artifactId.replaceAll("\\.", "//")+"/"+ar.groupId+"/"+ar.version+"/"+ar.groupId + "-" + ar.version+".jar");
                usedMethods.putAll(DependencyTreeMethods.listUsedClassMethods(packages.get(lb),lb));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return usedMethods;
        }
    }
    
    public static HashMap<String,HashMap<String,String>> listFields(){
        HashMap<String,HashMap<String,String>> classFields = new HashMap<>();
         
        try {
            String repoPath = System.getProperty("MavenRepoPath");
            
            for(Artifact ar : artifacts) {
                if(ar.type.contains("jar")) {
                //    System.out.println("AAA " + repoPath + "/" + ar.artifactId.replaceAll("\\.", "//")+"/"+ar.groupId+"/"+ar.version+"/"+ar.groupId + "-" + ar.version+".jar");
                    classFields.putAll(DependencyTreeMethods.listClassFields(repoPath + "/"+ ar.artifactId.replaceAll("\\.", "//")+"/"+ar.groupId+"/"+ar.version+"/"+ar.groupId + "-" + ar.version+".jar"));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return classFields;
        }
    }
    
    public static HashMap<String,String> listPackages(){
        HashMap<String,String> jarPackages = new HashMap<>();
         
        try {
            String repoPath = System.getProperty("MavenRepoPath");
            
            for(Artifact ar : artifacts) {
                if(ar.type.contains("jar")) {
                 //   System.out.println(repoPath + "/" + ar.artifactId.replaceAll("\\.", "//")+"/"+ar.groupId+"/"+ar.version+"/"+ar.groupId + "-" + ar.version+".jar");
                    DependencyTreeMethods.listJarPackages(repoPath + "/"+ ar.artifactId.replaceAll("\\.", "//")+"/"+ar.groupId+"/"+ar.version+"/"+ar.groupId + "-" + ar.version+".jar",jarPackages);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return jarPackages;
        }
    }
    
    public static HashSet<String> listAvailablePackages(){
        HashSet<String> packages = new HashSet<String>();
        
        HashMap<String,String> jarPackages = DependencyTreeMethods.listPackages();
        
        System.out.println("jarPackages.size() : " + jarPackages.size());
        
        for(String jc : jarPackages.keySet()){
            if(jc.lastIndexOf(".")!=-1) {
                String packageName = jc.substring(0, jc.lastIndexOf("."));
            //    System.out.println("packageName : " + packageName);
                    if(!packages.contains(packageName))
                        packages.add(packageName);
            }
        }
        
        return packages;
    }
    
    private static HashMap<String,String> listJarPackages(String path, HashMap<String,String> jarPackages) {
        
        try{
            if (path!=null) {
                JarFile jarFile = new JarFile(path);
               
                URL[] urls = { new URL("jar:file:" + path+"!/") };

                Enumeration allEntries = jarFile.entries();
                while (allEntries.hasMoreElements()) {
                    JarEntry entry = (JarEntry) allEntries.nextElement();
                    String name = entry.getName();
                    
                //    System.out.println("Entry Name 1 : " + name);
                    
                    if(name.contains(".class"))
                        jarPackages.put(name.substring(0,name.lastIndexOf(".")).replaceAll("/", "."),path);
                    else
                        jarPackages.put(name.replaceAll("/", "."),path);
                //    System.out.println("nnn " + name);
                }
            }
        }catch(Exception e){
            System.out.println(path + " is not available.");
          //  e.printStackTrace();
        }finally {
            return jarPackages;
        }
    }
    
    private static HashMap<String,String> listJarClassPaths(String path) {
        HashMap<String,String> jarClasses = new HashMap<>();
        
        try{
            if (path!=null) {
                JarFile jarFile = new JarFile(path);
               
                URL[] urls = { new URL("jar:file:" + path+"!/") };

                Enumeration allEntries = jarFile.entries();
                while (allEntries.hasMoreElements()) {
                    JarEntry entry = (JarEntry) allEntries.nextElement();
                    String name = entry.getName();
                    
                //    System.out.println("Entry Name 1 : " + name);
                    
                    if(name.contains(".class"))
                        jarClasses.put(name.replaceAll("/", ".").replaceAll(".class", ""),path);
                   
                }
            }
        }catch(Exception e){
            System.out.println(path + " is not available.");
          //  e.printStackTrace();
        }finally {
            return jarClasses;
        }
    }
    
    private static HashMap<String,ArrayList<Class[]>> listJarClasses(String path) {
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
                    
            //        System.out.println("Entry Name 2 : " + name);
                    
                    if(name.contains(".class") && !name.contains("$")) {
                        name = name.substring(0,name.lastIndexOf(".class"));
                        name=name.replaceAll("/", ".");
                        name=name.replaceAll("-", ".");
                        try{
                            Class clas = cl.loadClass(name);
                            Constructor[] constructors = clas.getConstructors();
                            ArrayList<Class[]> constructorParams = new ArrayList<>();
                            for(Constructor c : constructors) {
                                Class[] parameterTypes = c.getParameterTypes();
                                constructorParams.add(parameterTypes);
                            }
                            jarClasses.put(name,constructorParams);
                        }catch(Exception ex){
                        //    ex.printStackTrace();
                        }
                    }
                //    System.out.println("nnn " + name);
                }
            }
        }catch(Exception e){
           // e.printStackTrace();
           System.out.println(path + " is not available.");
        }finally {
            return jarClasses;
        }
    }
    
    private static HashMap<String,HashMap<String,Class[]>> listClassMethods(String path) {
        HashMap<String,HashMap<String,Class[]>> classMethods = new HashMap<>();
        
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
                        name=name.replaceAll("-", ".");
                        
                        try{
                            Class clas = cl.loadClass(name);

                            HashMap<String,Class[]> allMethods = new HashMap<>();

                            for (Class<?> c = clas; c != null; c = c.getSuperclass()) {
                                for (Method method : c.getMethods()) {
                                    if(Modifier.toString(method.getModifiers()).contains("public")) {
                                        allMethods.put(method.getName(), method.getParameterTypes());
                                        allMethods.put(method.getName()+"_Return_Type", new Class[]{method.getReturnType()});
                                    }
                                }
                            }       
                            classMethods.put(name,allMethods);
                        }catch(Exception ex){
                        //    ex.printStackTrace();
                        }
                    }

                }
            }
        }catch(Exception e){
        //    e.printStackTrace();
            System.out.println(path + " is not available.");
        }finally {
            return classMethods;
        }
    }
    
    private static HashMap<String,HashMap<String,Class[]>> listUsedClassMethods(String path, String lib) {
        HashMap<String,HashMap<String,Class[]>> classMethods = new HashMap<>();
        
        try{
            if (path!=null) {
                JarFile jarFile = new JarFile(path);
               
                URL[] urls = { new URL("jar:file:" + path+"!/") };
                URLClassLoader cl = URLClassLoader.newInstance(urls);

                Enumeration allEntries = jarFile.entries();
            //    System.out.println("path : " + path);
                while (allEntries.hasMoreElements()) {
                    JarEntry entry = (JarEntry) allEntries.nextElement();
                    String name = entry.getName();
                    
                //    System.out.println("name : " + name + " lib : " + lib);
                    if(name.contains(".class") && !name.contains("$") && name.replaceAll("/", ".").contains(lib)) {
                        name = name.substring(0,name.lastIndexOf(".class"));
                        name=name.replaceAll("/", ".");
                        name=name.replaceAll("-", ".");
                        
                        try{
                            Class clas = cl.loadClass(name);

                            HashMap<String,Class[]> allMethods = new HashMap<>();

                            for (Class<?> c = clas; c != null; c = c.getSuperclass()) {
                                for (Method method : c.getMethods()) {
                                    if(Modifier.toString(method.getModifiers()).contains("public")) {
                                        allMethods.put(method.getName(), method.getParameterTypes());
                                        allMethods.put(method.getName()+"_Return_Type", new Class[]{method.getReturnType()});
                                    }
                                }
                            }       
                            classMethods.put(name,allMethods);
                        }catch(Exception ex){
                        //    ex.printStackTrace();
                        }
                    }

                }
            }
        }catch(Exception e){
        //    e.printStackTrace();
            System.out.println(path + " is not available.");
        }finally {
            return classMethods;
        }
    }
    
    
    private static HashMap<String,HashMap<String,String>> listClassFields(String path) {
        HashMap<String,HashMap<String,String>> classFields = new HashMap<>();
        
        try{
            if (path!=null) {
                JarFile jarFile = new JarFile(path);
                
               
                URL[] urls = { new URL("jar:file:" + path+"!/") };
                URLClassLoader cl = URLClassLoader.newInstance(urls);

                Enumeration<JarEntry> allEntries = jarFile.entries();
                while (allEntries.hasMoreElements()) {
                    JarEntry entry = (JarEntry) allEntries.nextElement();
                    String name = entry.getName();
                    
            //        System.out.println("Entry Name : " + name);
                    
                    if(name.endsWith(".class") && !name.contains("$") && !entry.isDirectory()) {
                        name = name.substring(0,name.lastIndexOf(".class"));
                        name=name.replaceAll("/", ".");
                        name=name.replaceAll("-", ".");
                        
                        try{
                            Class clas = cl.loadClass(name);

                            HashMap<String,String> allFields = new HashMap<>();

                            for (Class<?> c = clas; c != null; c = c.getSuperclass()) {
                                for(Field f : c.getFields()) {
                                    if(Modifier.toString(f.getModifiers()).contains("public")){
                                        if(!allFields.keySet().contains(f))
                                            allFields.put(f.getName(),f.getType().toString());
                                    }
                                }
                            }       
                            classFields.put(name,allFields);
                        }catch(Exception ex){
                        //    ex.printStackTrace();
                        }
                    }

                }
                jarFile.close();
            }
        }catch(Exception e){
        //    e.printStackTrace();
            System.out.println(path + " is not available.");
        }finally {
            return classFields;
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
