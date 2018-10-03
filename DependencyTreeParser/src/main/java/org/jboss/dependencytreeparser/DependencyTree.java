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
            
            System.out.println("\n\n\nInternal Classes and Methods : ");
            
          //  HashMap<String,HashMap<String,String[]>> localClasses = JavaClassParser.getInternalClassMethods();
            
            /*
            for(String s : localClasses.keySet()) {
                System.out.println("class : " + s);
                for (String m : localClasses.get(s).keySet()) {
                    System.out.println("Method : " + m + " with parameters : ");
                    for (int j=0; j<localClasses.get(s).get(m).length; j++)
                        System.out.println(localClasses.get(s).get(m)[j]);
                }
            }*/
            
            HashMap<String,ArrayList<String>> usedLibraries = JavaClassParser.testLibraryUsage();
            HashMap<String,HashSet<String>> localClasses = JavaClassParser.getInternalPackagesAndClasses();
            HashMap<String,ArrayList<String>> internalClasses = JavaClassParser.getInternalClasses();
            System.out.println("internalClasses size : " + internalClasses.size());
            HashMap<String,ParsedTests> testData = JavaClassParser.getTestData();
            
            ArrayList<String> acceptedLibraries = TestsuiteParser.readAcceptedTypesFromFile(System.getProperty("AcceptedTypesFilePath") + "/" + "libraries.txt");
             
            for(String key:testData.keySet()) {
                ParsedTests ps = testData.get(key);
                System.out.println("Class : " + key.toString() + " extends " + ps.extension);
                if(internalClasses.get(key.toString())!=null)
                    System.out.println("All Classes : " + internalClasses.get(key.toString()).toString());
                String path = key.toString().substring(0,key.toString().lastIndexOf("."));
                System.out.println("Path : " + path);
                System.out.println("usedLibraries : " + ps.imports);
                
                HashSet<String> availableFields = new HashSet<>();
                
                for(String s : localClasses.keySet()){
                    if(s.contains(path)){
                   //     System.out.println("++++++++ " + s + " " + localClasses.get(s).toString());
                        availableFields.addAll(localClasses.get(s));
                    }
                }
                
                if(ps.extension!=null) {
                    if(testData.get(ps.extension)!=null) {
                        String ex = ps.extension;
                        do{
                            System.out.println("ps.extension : " + ps.extension + " : " + testData.get(ex).fields.keySet());
                            availableFields.addAll(testData.get(ex).fields.keySet());
                            if(testData.get(ex)!=null)
                                ex = testData.get(ex).extension;
                            else 
                                ex = null;
                        }while(ex!=null);
                    }else {
                        for(String cl : classes.keySet()) {
                            if(cl.equals(ps.extension)){
                                if(fields.get(cl)!=null)
                                    availableFields.addAll(fields.get(cl));
                                break;
                            }
                        }
                        
                    }
                }
                
                
                HashSet<String> classLibraries = ps.imports;
                
                if(classLibraries!=null) {
                    for(String lib : classLibraries) {
                        for(String acclib : acceptedLibraries) {
                            if(lib.startsWith(acclib)){
                                availableFields.add(lib.substring(lib.lastIndexOf(".")+1));
                                break;
                            }
                        }
                        for(String cl : classes.keySet()) {
                            if(cl.contains(lib)){
                            //    if(fields.get(cl)!=null)
                            //        availableFields.addAll(fields.get(cl));
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
                
               
                
                System.out.println("Types : ==========");
                ArrayList<String> acceptedTypes = TestsuiteParser.readAcceptedTypesFromFile(System.getProperty("AcceptedTypesFilePath") + "/" + "types.txt");
            //    System.out.println(acceptedTypes.toString());
                for(String type : ps.types){
                    boolean b = false;
                    type = type.replaceAll("\\.Builder", "");
                    type = type.replaceAll("\\.RolloutPolicy", "");
                    if(!acceptedTypes.contains(type) && !availableFields.contains(type) && !classLibraries.contains(type) && !internalClasses.keySet().contains(type) && !internalClasses.get(key.toString()).contains(type)) {
                    //    System.out.println(type + "------" + classLibraries.toString());

                        
                        for(String im : classLibraries) {
                            if(im.contains(type)){
                                b=true;
                                break;
                            }
                        }
                        
                        
                        if(!b) {
                            for(String acclib : acceptedLibraries) {
                                if(type.startsWith(acclib)){
                                    b = true;
                                    break;
                                }
                            }
                        }
                        
                        if(!b) {
                            String packageName =  key.toString().substring(0,key.toString().lastIndexOf("."));
                            for(String locClass : localClasses.get(packageName)) {
                                if(locClass.contains(type)){
                                    b = true;
                                    break;
                                }
                            }
                        }
                        
                        if(!b){
                            System.out.println(type + "------");
                        }
                    }
                        
                }
                System.out.println("Types Not Resolved : ==========");
                for(String typeNotResolved : ps.typesNotResolved){
                    boolean b = false;
                    if(!acceptedTypes.contains(typeNotResolved) && !availableFields.contains(typeNotResolved) && !classLibraries.contains(typeNotResolved) && !internalClasses.keySet().contains(typeNotResolved) && !internalClasses.get(key.toString()).contains(typeNotResolved)) {
                        for(String im : classLibraries) {
                            if(im.contains(typeNotResolved)){
                                b=true;
                                break;
                            }
                        }
                        
                        if(!b) {
                            String packageName =  key.toString().substring(0,key.toString().lastIndexOf("."));
                            for(String locClass : localClasses.get(packageName)) {
                                if(locClass.contains(typeNotResolved)){
                                    b = true;
                                    break;
                                }
                            }
                        }
                        
                        if(!b){
                            System.out.println(typeNotResolved + "------");
                        }
                }
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
                System.out.println("Libraries : =========="+availableFields.size());
                

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
