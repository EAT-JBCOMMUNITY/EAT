package org.jboss.dependencytreeparser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author panos
 */
public class DependencyTree {

    Object lock = new Object();
    
    public static void main(String[] args) throws Exception {
        try {
        //    System.out.println("Print Dependencies : ");
        //    DependencyTreeMethods.getArtifacts();
            
            HashSet<String> packages = DependencyTreeMethods.listAvailablePackages();

            System.out.println("\n\n\nAvailable libraries : ");

            for(String s : packages){
                System.out.println(s);
            }
       
            
            System.out.println("\n\n\nJar Classes : ");
            
            HashMap<String,ArrayList<Class[]>> classes = DependencyTreeMethods.listClasses();
            
            for(String s : classes.keySet()) {
                System.out.println("class : " + s);
                for (int i=0; i<classes.get(s).size(); i++) {
                    System.out.println("constructor params : ");
                    for (int j=0; j<classes.get(s).get(i).length; j++)
                        System.out.println(classes.get(s).get(i)[j].getTypeName());
                }
            }
            
            
            System.out.println("\n\n\nJar Methods : ");
            
            HashMap<String,HashMap<String,Class[]>> methods = DependencyTreeMethods.listMethods();
            
            for(String s : methods.keySet()) {
                System.out.println("class : " + s);
                for (String m : methods.get(s).keySet()) {
                    System.out.println("Method : " + m + " with parameters : ");
                    for (int j=0; j<methods.get(s).get(m).length; j++)
                        System.out.println(methods.get(s).get(m)[j].getTypeName());
                }
            }
        
            
        
            System.out.println("\n\n\nJar Fields : ");
            
            HashMap<String,ArrayList<String>> fields = DependencyTreeMethods.listFields();
            
            for(String s : fields.keySet()) {
                System.out.println("class : " + s);
                for (String f : fields.get(s)) {
                    System.out.println("Field : " + f );
                    
                }
            }
            
            HashMap<String,ArrayList<String>> usedLibraries = JavaClassParser.testLibraryUsage();
            HashMap<String,ParsedTests> testData = JavaClassParser.getTestData();
            
            for(String key:testData.keySet()) {
                ParsedTests ps = testData.get(key);
                System.out.println("Class : " + key.toString() + " extends " + ps.extension);
                System.out.println("usedLibraries : " + ps.imports);
                
                HashSet<String> classLibraries = ps.imports;
                HashSet<String> availableFields = new HashSet<>();
                if(classLibraries!=null) {
                    for(String lib : classLibraries) {
                        for(String cl : classes.keySet()) {
                            if(cl.contains(lib)){
                                if(fields.get(cl)!=null)
                                    availableFields.addAll(fields.get(cl));
                                if(!cl.replaceAll(lib+".", "").equals(cl))
                                    availableFields.add(cl.replaceAll(lib+".", ""));
                                else {
                                    availableFields.add(lib.substring(lib.lastIndexOf(".")+1));
                                //    System.out.println("cccccccc " + lib.substring(lib.lastIndexOf(".")+1));
                                }
                            }
                        }
                    }
                }
                
                System.out.println("Types : =========="+availableFields.size());
                ArrayList<String> acceptedTypes = TestsuiteParser.readAcceptedTypesFromFile(System.getProperty("AcceptedTypesFilePath") + "/" + "types.txt");
                System.out.println(acceptedTypes.toString());
                for(String type : ps.types){
                    if(!acceptedTypes.contains(type) && !availableFields.contains(type))
                        System.out.println(type);
                }
                System.out.println("Types Not Resolved : ==========");
                for(String typeNotResolved : ps.typesNotResolved){
                    if(!acceptedTypes.contains(typeNotResolved))
                        System.out.println(typeNotResolved);
                }
                System.out.println("Method Invocations : ==========");
                ArrayList<String> acceptedMethods = TestsuiteParser.readAcceptedTypesFromFile(System.getProperty("AcceptedTypesFilePath") + "/" + "methods.txt");
                for(MethodInfo methodInfo : ps.methodInvocations){
                    String methodI = methodInfo.methodName;
                    if(methodInfo.expression!=null)
                        methodI=methodInfo.expression + "." + methodInfo.methodName;
                    if(!acceptedMethods.contains(methodI) && !methodInfo.isResolvedParam.contains("false"))
                        System.out.println(methodInfo.methodName + " " + methodInfo.expression + " " + methodInfo.params.toString() + " " + methodInfo.isResolvedParam.toString());
                }
                System.out.println("Methods Not Resolved : ==========");
                for(String methodNotResolved : ps.methodsNotResolved){
                    if(!acceptedMethods.contains(methodNotResolved))
                        System.out.println(methodNotResolved);
                }
                System.out.println("Class Instance Creations : ==========");
                ArrayList<String> acceptedclasses = TestsuiteParser.readAcceptedTypesFromFile(System.getProperty("AcceptedTypesFilePath") + "/" + "classInstances.txt");
                for(ClassInfo classInfo : ps.classInstanceCreations){
                    if(!acceptedclasses.contains(classInfo.className) && !classInfo.isResolvedParam.contains("false"))
                        System.out.println(classInfo.className + " " + classInfo.params.toString() + " " + classInfo.isResolvedParam.toString());
                }
            }
            
/*
            System.out.println("\n\n\nInternal Classes and Methods : ");
            
            HashMap<String,HashMap<String,String[]>> localMethods = JavaClassParser.getInternalClassMethods();
             
            
            for(String s : localMethods.keySet()) {
                System.out.println("class : " + s);
                for (String m : localMethods.get(s).keySet()) {
                    System.out.println("Method : " + m + " with parameters : ");
                    for (int j=0; j<localMethods.get(s).get(m).length; j++)
                        System.out.println(localMethods.get(s).get(m)[j]);
                }
            }
            
            
            System.out.println("\n\n\nTest libraries used : " + usedLibraries.keySet().size());

            for(String s : usedLibraries.keySet()){
                System.out.println(s + " " + usedLibraries.get(s).toString());
            }
            
            HashMap<String,String> internalPackagesAndClasses = JavaClassParser.getInternalPackagesAndClasses();
            
            System.out.println("\n\n\nInternal Packages And Classes used : ");
            
            for(String s : internalPackagesAndClasses.keySet()){
                System.out.println(s + " " + internalPackagesAndClasses.get(s));
            }
            
            HashSet<String> excludedLibraries = DependencyTreeMethods.addExcludedLibraries();
            
            int found = 0;
            int notFound = 0;
            HashSet<String> foundArray = new HashSet<>();
            HashSet<String> notfoundArray = new HashSet<>();
            for(String s : usedLibraries.keySet()){
                if(packages.contains(s) || internalPackagesAndClasses.keySet().contains(s)) {
                    found++;
                    foundArray.add(s);
                }else{
                    boolean excluded = false;
                    
                    for(String d : excludedLibraries) {
                        if(s.startsWith(d)){
                            excluded = true;
                            break;
                        }
                            
                    }
                    
                    if(!excluded) {
                        notFound++;
                        notfoundArray.add(s);
                    }
                }
            }
            
            
            
            System.out.println("found : " + found + " , notFound : " + notFound);
            
            System.out.println("Found : ");
            for(String s : foundArray) {
                System.out.println("Found : " + s);
            }
            
            System.out.println("Not Found : ");
            for(String s : notfoundArray) {
                System.out.println("Not Found : " + s);
            }
            */
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    
}
