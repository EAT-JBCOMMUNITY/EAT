package org.jboss.dependencytreeparser;

import java.util.ArrayList;
import java.util.Collections;
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
            DependencyTreeMethods.artifacts = DependencyTreeMethods.getArtifacts();
            
            HashMap<String,String> packages = DependencyTreeMethods.listPackages();

            System.out.println("\n\n\nAvailable libraries : ");

            for(String s : packages.keySet()){
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
            
            /*
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
        */
            
        
            System.out.println("\n\n\nJar Fields : ");
            
            HashMap<String,HashMap<String,String>> fields = DependencyTreeMethods.listFields();
            
            for(String s : fields.keySet()) {
                System.out.println("class : " + s);
                for (String f : fields.get(s).keySet()) {
                    System.out.println("Field : " + f );
                    
                }
            }
            
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
                
                ArrayList<String> acceptedFieldsFromLibrary = TestsuiteParser.loadFieldsFromFile(System.getProperty("AcceptedTypesFilePath") + "/" + "classesToLoadFields.txt");
                
                HashSet<String> availableFields = new HashSet<>();
                HashMap<String,String> availableExtensionFields = new HashMap<>();
                
                for(String s : localClasses.keySet()){
                    if(s.contains(path)){
                   //     System.out.println("++++++++ " + s + " " + localClasses.get(s).toString());
                        availableFields.addAll(localClasses.get(s));
                    }
                }
                
                for(String s : acceptedFieldsFromLibrary){
                    if(ps.imports.contains(s)){
                        System.out.println("S : " + s);
                        if(fields.get(s)!=null){
                            System.out.println("FieldsFromFile : " + fields.get(s).toString());
                            availableFields.addAll(fields.get(s).keySet());
                        }else{
                            System.out.println("LLL " + fields.keySet());
                        }
                    }
                }
                
                if(ps.extension!=null) {
                    if(testData.get(ps.extension)!=null) {
                        String ex = ps.extension;
                        do{
                            
                            if(testData.get(ex)!=null) {
                                System.out.println("ps.extension : " + ps.extension + " : " + testData.get(ex).fields.keySet());
                                availableFields.addAll(testData.get(ex).fields.keySet());
                                availableExtensionFields.putAll(testData.get(ex).fields);
                                ex = testData.get(ex).extension;
                            }else 
                                ex = null;
                        }while(ex!=null);
                    }else {
                        System.out.println("org.jboss.as.test.integration.management.base " + classes.keySet().contains("org.jboss.as.test.integration.management.base.AbstractCliTestBase"));
                        for(String cl : classes.keySet()) {
                            if(cl.equals(ps.extension)){
                                if(fields.get(cl)!=null) {
                                    availableFields.addAll(fields.get(cl).keySet());
                                    availableExtensionFields.putAll(fields.get(cl));
                                }
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
                HashMap<String,String> rMethods = new HashMap<>();
                ArrayList<String> acceptedMethods = TestsuiteParser.readAcceptedTypesFromFile(System.getProperty("AcceptedTypesFilePath") + "/" + "methods.txt");
                ArrayList<String> acceptedExpressions = TestsuiteParser.readAcceptedTypesFromFile(System.getProperty("AcceptedTypesFilePath") + "/" + "expressions.txt");
                ArrayList<String> acceptedStartWithExpressions = TestsuiteParser.readAcceptedTypesFromFile(System.getProperty("AcceptedTypesFilePath") + "/" + "excludeStartWith.txt");
                ArrayList<String> acceptedEndWithExpressions = TestsuiteParser.readAcceptedTypesFromFile(System.getProperty("AcceptedTypesFilePath") + "/" + "excludeEndWith.txt");
                Collections.reverse(ps.methodInvocations);
                HashMap<String,HashMap<String,Class[]>> methods2 = DependencyTreeMethods.listUsedMethods(ps.imports,packages);
                System.out.println("methods2 : " + methods2.keySet());
                for(MethodInfo methodInfo : ps.methodInvocations){
                    
                    if(methodInfo.expression!=null && availableExtensionFields.keySet().contains(methodInfo.expression)){
                        System.out.println("availableExtensionFields " + availableExtensionFields.keySet());
                        methodInfo.expression = availableExtensionFields.get(methodInfo.expression);
                    }
                    
                    if(methodInfo.expression!=null)
                        
                        for(String s : rMethods.keySet()) {
                            if(methodInfo.expression.contains(s))
                                methodInfo.expression = rMethods.get(s);
                        }

                    if(methodInfo.expression!=null && !methodInfo.expression.equals("")) {
                    //    System.out.println("methodInfo.expression : " + methodInfo.expression);
                        for(String s : methods2.keySet()) {
                            if(s.contains(methodInfo.expression)){
                                for(String meth : methods2.get(s).keySet()){
                                    if(meth.equals(methodInfo.methodName)){
                                        acceptedMethods.add(methodInfo.methodName);
                                        if(methods2.get(s).get(meth+"_Return_Type")[0].toString().contains("class "))
                                            rMethods.put(methodInfo.methodName, methods2.get(s).get(meth+"_Return_Type")[0].toString().replaceAll("class ", ""));
                                        else if(methods2.get(s).get(meth+"_Return_Type")[0].toString().contains("interface "))
                                            rMethods.put(methodInfo.methodName, methods2.get(s).get(meth+"_Return_Type")[0].toString().replaceAll("interface ", ""));
                                    }
                                }
                            }
                        }
                        
                    }else{
                        
                        String c = key.toString();

                        while(c!=null) {
                            for(String s : localMethods.keySet()) {
                                if(s.contains(c)){
                                    System.out.println("localMethods " + localMethods.get(s).keySet());
                                    for(String meth : localMethods.get(s).keySet()){
                                        if(meth.equals(methodInfo.methodName)){
                                            acceptedMethods.add(methodInfo.methodName);
                                        //    rMethods.put(methodInfo.methodName, localMethods.get(s).get(meth+"_Return_Type")[0].toString());
                                        }
                                    }
                                }
                                
                            }
                            if(ps.extension!=null && !ps.extension.equals("") && !ps.extension.contains(c))
                                c = ps.extension;
                            else
                                c=null;

                        }
                    }
                    
                    String libName = null;
                    if(methodInfo.expression!=null) {
                        for(String s : ps.imports) {
                            if(s.contains(methodInfo.expression)) {
                                libName = s;
                                break;
                            }
                        }
                    }
                    
                    if(methodInfo.expression!=null) {
                        for(String s : acceptedEndWithExpressions) {
                            if(methodInfo.expression.contains(s)) {
                                acceptedMethods.add(methodInfo.methodName);
                                rMethods.put(methodInfo.methodName, "Object");
                            }
                        }
                        
                        for(String s : acceptedStartWithExpressions) {
                            if(methodInfo.expression.contains(s) || (libName!=null && libName.contains(s))) {
                                acceptedMethods.add(methodInfo.methodName);
                                rMethods.put(methodInfo.methodName, "Object");
                            }
                        }
                        
                        for(String s : acceptedExpressions) {
                            if(methodInfo.expression.contains(s)) {
                                acceptedMethods.add(methodInfo.methodName);
                                rMethods.put(methodInfo.methodName, "Object");
                            }
                        }
                    }
                    
                    int m=0;
                    for(String param : methodInfo.params) {
                        if((availableFields.contains(param)|| classLibraries.contains(param) || acceptedMethods.contains(param) || param==null || param.equals("this")) && !methodInfo.isResolvedParam.get(m))
                            methodInfo.isResolvedParam.set(m, Boolean.TRUE);
                        m++;
                    }
                    if((!acceptedMethods.contains(methodInfo.methodName)) || methodInfo.isResolvedParam.contains("false"))
                        System.out.println("----" + methodInfo.methodName + " " + methodInfo.expression + " " + methodInfo.params.toString() + " " + methodInfo.isResolvedParam.toString());
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
