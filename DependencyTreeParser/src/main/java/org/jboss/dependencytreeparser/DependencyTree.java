package org.jboss.dependencytreeparser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang3.StringUtils;
import org.jboss.dependencytreeparser.SourceParser.MethodInfo2;

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
            HashMap<String, String> packages = DependencyTreeMethods.listPackages();

            HashMap<String, HashMap<String, MethodInfo2>> sourceClassesMethods = new HashMap<>();
            HashMap<String, HashSet<String>> sourceClassesImports = new HashMap<>();
            HashMap<String, HashMap<String, String>> sourceClassesFields = new HashMap<>();
            HashSet<String> paths = DependencyTreeMethods.getSourceClasses();
            HashMap<String,String> packageClasses = new HashMap<>();

            for (String t : paths) {
                SourceParser.parse(t);
                String clss = t.replaceAll(".java", "");
                if(clss.lastIndexOf("/")>0)
                    clss = clss.substring(clss.lastIndexOf("/")+1);
                sourceClassesFields.put(SourceParser.packageName + "." + clss, new HashMap<String, String>(SourceParser.fields));
                HashMap<String, MethodInfo2> hm = new HashMap<>();
                hm.putAll(SourceParser.methods);
                sourceClassesMethods.put(SourceParser.packageName + "." + clss, hm);
                sourceClassesImports.put(SourceParser.packageName + "." + clss, new HashSet<String>(SourceParser.imports));
                
                packageClasses.put(SourceParser.packageName + "." + clss, SourceParser.packageName + "." + clss);
            //    System.out.println("Methods : " + SourceParser.methods.keySet().toString());
            //    System.out.println("Fields : " + SourceParser.fields.keySet().toString());
            //    System.out.println("Imports : " + SourceParser.imports.toString());
            //    System.out.println("PackageClass : " + SourceParser.packageName + "." + clss);
            }

            System.out.println("\n\n\nAvailable libraries : ");

            for (String s : packages.keySet()) {
                System.out.println(s);
            }

            System.out.println("\n\n\nJar Classes : ");

            HashMap<String, ArrayList<Class[]>> classes = DependencyTreeMethods.listClasses();

            for (String s : classes.keySet()) {
                System.out.println("class : " + s);
                for (int i = 0; i < classes.get(s).size(); i++) {
                    System.out.println("constructor params : ");
                    for (int j = 0; j < classes.get(s).get(i).length; j++) {
                        System.out.println(classes.get(s).get(i)[j].getTypeName());
                    }
                }
            }

            
            System.out.println("\n\n\nJar Methods : ");
            
            HashMap<String,HashMap<String,String[]>> methods = DependencyTreeMethods.listMethods();

        /*    for(String s : methods.keySet()) {
                System.out.println("classs : " + s);
                for (String m : methods.get(s).keySet()) {
                    System.out.println("Method : " + m + " with parameters : ");
                    for (int j=0; j<methods.get(s).get(m).length; j++)
                        System.out.println(methods.get(s).get(m)[j]);
                }
            }*/
             
            System.out.println("\n\n\nJar Fields : ");

            HashMap<String, HashMap<String, String>> fields = DependencyTreeMethods.listFields();

        /*    for (String s : fields.keySet()) {
                System.out.println("class : " + s);
                for (String f : fields.get(s).keySet()) {
                    System.out.println("Field : " + f);

                }
            }*/

            System.out.println("\n\n\nInternal Classes and Methods : ");

            HashMap<String, HashMap<String, String[]>> localMethods = JavaClassParser.getInternalClassMethods();
/*
            for (String s : localMethods.keySet()) {
                System.out.println("class : " + s);
                for (String m : localMethods.get(s).keySet()) {
                    System.out.println("Method : " + m + " with parameters : ");
                    for (int j = 0; j < localMethods.get(s).get(m).length; j++) {
                        System.out.println(localMethods.get(s).get(m)[j]);
                    }
                }
            }*/

            HashMap<String, ArrayList<String>> usedLibraries = JavaClassParser.testLibraryUsage();
            HashMap<String, HashSet<String>> localClasses = JavaClassParser.getInternalPackagesAndClasses();
           HashMap<String, ArrayList<String>> internalClasses = JavaClassParser.getInternalClasses();
        //    System.out.println("internalClasses size : " + internalClasses.size());
            HashMap<String, ParsedTests> testData = JavaClassParser.getTestData();

            ArrayList<String> acceptedLibraries = TestsuiteParser.readAcceptedTypesFromFile(System.getProperty("AcceptedTypesFilePath") + "/" + "libraries.txt");

            ArrayList<String> acceptedExpressionTranslations = TestsuiteParser.readAcceptedTypesFromFile(System.getProperty("AcceptedTypesFilePath") + "/" + "expressionTranslations.txt");
            HashMap<String,String> expMethodMap = new HashMap<>();
            
            for (String str : acceptedExpressionTranslations){
                expMethodMap.put(str.substring(0,str.indexOf(";")), str.substring(str.indexOf(";")+1));
            }
            
            ArrayList<String> acceptedMethodsSet = TestsuiteParser.readAcceptedTypesFromFile(System.getProperty("AcceptedTypesFilePath") + "/" + "methodInclusion.txt");
            HashMap<String,String> acceptedMethodMap = new HashMap<>();
            
            for (String str : acceptedMethodsSet){
                acceptedMethodMap.put(str.substring(0,str.indexOf(";")), str.substring(str.indexOf(";")+1));
            }
            
            HashMap<String, String> importFields = new HashMap<>();
            HashMap<String, HashMap<String, String[]>> methodsTest2 = new HashMap<>();

            
            for (String p : sourceClassesMethods.keySet()) {
                     //   System.out.println("SSSSS " + p + " " + path2);
                        HashMap<String, String[]> mp = new HashMap<>();
                    //    if (p.contains(path2)) {
                      //      System.out.println("MMMMM " + p + " " + path2);

                            HashMap<String,MethodInfo2> it = sourceClassesMethods.get(p);
                            
                            for (String keyName : it.keySet()) {
                                MethodInfo2 mdinfo2 = it.get(keyName);
                                String[] params = new String[mdinfo2.paramTypes.size()];
                                int i=0;
                                for (String par : mdinfo2.paramTypes) {
                                    params[i]=par;
                                    i++;
                                }

                                mp.put(mdinfo2.name, params);
                                mp.put(mdinfo2.name+"_Return_Type", new String[]{mdinfo2.returnType});
                            }

                            importFields.putAll(sourceClassesFields.get(p));
                            
                            if (mp.size() != 0) {
                        //        System.out.println("packageClasses.get(p) " + packageClasses.get(p));
                                methodsTest2.put(packageClasses.get(p), mp);
                            }
                    //    }

                        
                    }
            for (String key : testData.keySet()) {
                ParsedTests ps = testData.get(key);
                System.out.println("Class : " + key.toString() + " extends " + ps.extension);
           /*     if (internalClasses.get(key.toString()) != null) {
                    System.out.println("All Classes : " + internalClasses.get(key.toString()).toString());
                }*/
                String path = key.toString().substring(0, key.toString().lastIndexOf("."));
           //     System.out.println("Path : " + path);
                if(sourceClassesFields.get(path)!=null)
                    ps.fields.putAll(sourceClassesFields.get(path));
                if(sourceClassesImports.get(path)!=null)
                    ps.imports.addAll(sourceClassesImports.get(path));
            //    System.out.println("usedLibraries : " + ps.imports);

                
                ArrayList<String> arr = new ArrayList<>();
                for(String str : ps.imports) {
                    if(StringUtils.isAllUpperCase(str.substring(str.lastIndexOf(".")+1))){
                        arr.add(str);
                    } 
                }
                
                for(String str : arr){
                    ps.imports.add(str.substring(0,str.lastIndexOf(".")));
                }
                
                HashMap<String, HashMap<String, String[]>> methodsTest = new HashMap<>();
                methodsTest.putAll(methodsTest2);

                for (String p : DependencyTreeMethods.testsuiteArtifactsPaths) {
                    methodsTest.putAll(DependencyTreeMethods.listUsedTestMethods(ps.imports, p));
                    //        System.out.println("mmm " + DependencyTreeMethods.listUsedTestMethods(ps.imports, p).keySet());
                }
                HashMap<String, String> availableImportFields = new HashMap<>();
                availableImportFields.putAll(importFields);

                /*
           //     for (String psimp : ps.imports) {
            //        String path2 = psimp;
                    for (String p : sourceClassesMethods.keySet()) {
                     //   System.out.println("SSSSS " + p + " " + path2);
                        HashMap<String, String[]> mp = new HashMap<>();
                    //    if (p.contains(path2)) {
                      //      System.out.println("MMMMM " + p + " " + path2);

                            HashMap<String,MethodInfo2> it = sourceClassesMethods.get(p);
                            
                            for (String keyName : it.keySet()) {
                                MethodInfo2 mdinfo2 = it.get(keyName);
                                String[] params = new String[mdinfo2.paramTypes.size()];
                                int i=0;
                                for (String par : mdinfo2.paramTypes) {
                                    params[i]=par;
                                    i++;
                                }

                                mp.put(mdinfo2.name, params);
                                mp.put(mdinfo2.name+"_Return_Type", new String[]{mdinfo2.returnType});
                            }

                            availableImportFields.putAll(sourceClassesFields.get(p));
                            
                            if (mp.size() != 0) {
                        //        System.out.println("packageClasses.get(p) " + packageClasses.get(p));
                                methodsTest.put(packageClasses.get(p), mp);
                            }
                    //    }

                        
                    }
            //    }
                */
              

                ArrayList<String> acceptedFieldsFromLibrary = TestsuiteParser.loadFieldsFromFile(System.getProperty("AcceptedTypesFilePath") + "/" + "classesToLoadFields.txt");

                HashSet<String> availableFields = new HashSet<>();
                HashMap<String, String> availableExtensionFields = new HashMap<>();

                for (String s : localClasses.keySet()) {
                    if (s.contains(path)) {
                        //     System.out.println("++++++++ " + s + " " + localClasses.get(s).toString());
                        availableFields.addAll(localClasses.get(s));
                    }
                }

                for (String s : acceptedFieldsFromLibrary) {
                    if (ps.imports.contains(s)) {
                    //    System.out.println("S : " + s);
                        if (fields.get(s) != null) {
                        //    System.out.println("FieldsFromFile : " + fields.get(s).toString());
                            availableFields.addAll(fields.get(s).keySet());
                        }// else {
                        //    System.out.println("LLL " + fields.keySet());
                       // }
                    }
                }

                if (ps.extension != null) {
                    if (testData.get(ps.extension) != null) {
                        String ex = ps.extension;
                        do {

                            if (testData.get(ex) != null) {
                            //    System.out.println("ps.extension : " + ps.extension + " : " + testData.get(ex).fields.keySet());
                                availableFields.addAll(testData.get(ex).fields.keySet());
                                availableExtensionFields.putAll(testData.get(ex).fields);
                                ex = testData.get(ex).extension;
                            } else {
                                ex = null;
                            }
                        } while (ex != null);
                    } else {
                    //    System.out.println("org.jboss.as.test.integration.management.base " + classes.keySet().contains("org.jboss.as.test.integration.management.base.AbstractCliTestBase"));
                        for (String cl : classes.keySet()) {
                            if (cl.equals(ps.extension)) {
                                if (fields.get(cl) != null) {
                                    availableFields.addAll(fields.get(cl).keySet());
                                    availableExtensionFields.putAll(fields.get(cl));
                                }
                                break;
                            }
                        }

                    }
                }

                HashSet<String> classLibraries = ps.imports;

                if (classLibraries != null) {
                    for (String lib : classLibraries) {
                        for (String acclib : acceptedLibraries) {
                            if (lib.startsWith(acclib)) {
                                availableFields.add(lib.substring(lib.lastIndexOf(".") + 1));
                                break;
                            }
                        }
                        for (String cl : classes.keySet()) {
                            if (cl.contains(lib)) {
                                //    if(fields.get(cl)!=null)
                                //        availableFields.addAll(fields.get(cl));
                                if (!cl.replaceAll(lib + ".", "").equals(cl)) {
                                    availableFields.add(cl.replaceAll(lib + ".", ""));
                                } else {
                                    availableFields.add(lib.substring(lib.lastIndexOf(".") + 1));

                                    //    System.out.println("cccccccc " + lib.substring(lib.lastIndexOf(".")+1));
                                }
                            }
                        }
                    }
                }

                System.out.println("Types : ==========");
                ArrayList<String> acceptedTypes = TestsuiteParser.readAcceptedTypesFromFile(System.getProperty("AcceptedTypesFilePath") + "/" + "types.txt");
                //    System.out.println(acceptedTypes.toString());
                for (String type : ps.types) {
                    boolean b = false;
                    type = type.replaceAll("\\.Builder", "");
                    type = type.replaceAll("\\.RolloutPolicy", "");
                    if (!acceptedTypes.contains(type) && !availableFields.contains(type) && !classLibraries.contains(type) && !internalClasses.keySet().contains(type) && !internalClasses.get(key.toString()).contains(type)) {
                        //    System.out.println(type + "------" + classLibraries.toString());

                        for (String im : classLibraries) {
                            if (im.contains(type)) {
                                b = true;
                                break;
                            }
                        }

                        if (!b) {
                            for (String acclib : acceptedLibraries) {
                                if (type.startsWith(acclib)) {
                                    b = true;
                                    break;
                                }
                            }
                        }

                        if (!b) {
                            String packageName = key.toString().substring(0, key.toString().lastIndexOf("."));
                            for (String locClass : localClasses.get(packageName)) {
                                if (locClass.contains(type)) {
                                    b = true;
                                    break;
                                }
                            }
                        }

                        if (!b) {
                            System.out.println(type + "------");
                        }
                    }

                }
                System.out.println("Types Not Resolved : ==========");
                for (String typeNotResolved : ps.typesNotResolved) {
                    boolean b = false;
                    if (!acceptedTypes.contains(typeNotResolved) && !availableFields.contains(typeNotResolved) && !classLibraries.contains(typeNotResolved) && !internalClasses.keySet().contains(typeNotResolved) && !internalClasses.get(key.toString()).contains(typeNotResolved)) {
                        for (String im : classLibraries) {
                            if (im.contains(typeNotResolved)) {
                                b = true;
                                break;
                            }
                        }

                        if (!b) {
                            String packageName = key.toString().substring(0, key.toString().lastIndexOf("."));
                            for (String locClass : localClasses.get(packageName)) {
                                if (locClass.contains(typeNotResolved)) {
                                    b = true;
                                    break;
                                }
                            }
                        }

                        if (!b) {
                            System.out.println(typeNotResolved + "------");
                        }
                    }
                }
                System.out.println("Method Invocations : ==========");
                HashMap<String, String> rMethods = new HashMap<>();
                ArrayList<String> acceptedMethods = TestsuiteParser.readAcceptedTypesFromFile(System.getProperty("AcceptedTypesFilePath") + "/" + "methods.txt");
                ArrayList<String> acceptedExpressions = TestsuiteParser.readAcceptedTypesFromFile(System.getProperty("AcceptedTypesFilePath") + "/" + "expressions.txt");
                ArrayList<String> acceptedStartWithExpressions = TestsuiteParser.readAcceptedTypesFromFile(System.getProperty("AcceptedTypesFilePath") + "/" + "excludeStartWith.txt");
                ArrayList<String> acceptedEndWithExpressions = TestsuiteParser.readAcceptedTypesFromFile(System.getProperty("AcceptedTypesFilePath") + "/" + "excludeEndWith.txt");
                Collections.reverse(ps.methodInvocations);
                HashMap<String, HashMap<String, String[]>> methods2 = new HashMap<String, HashMap<String, String[]>>();
           //     methodsTest.putAll(DependencyTreeMethods.listUsedMethods2(ps.packageName, packages));
                methods2.putAll(methodsTest);
            //    System.out.println("ssssszzzzzz " + methodsTest.keySet().toString());
                
                methods2.putAll(DependencyTreeMethods.listUsedMethods(ps.imports, packages));
            
                for(String s : ps.imports) {
                    if(methods.get(s)!=null) {
                        for(String m : methods.get(s).keySet()){
                            if(methods2.get(s)!=null) {
                                if(!methods2.get(s).keySet().contains(m))
                                    methods2.get(s).put(m, methods.get(s).get(m));
                            }else{
                                methods2.put(s, methods.get(s));
                            }
                        }
                        System.out.println("Ext : " + s + methods.get(s).keySet().contains(s+"_Extensions"));
                        if(methods.get(s).keySet().contains(s+"_Extensions")) {
                            addExtensions(methods,methods2,s,s,acceptedMethods,acceptedMethodMap,rMethods);   
                        }
                    }
                }
               
                
                
                acceptedMethods.addAll(expMethodMap.keySet());
               
                
            //    System.out.println("ssssszzzzzz " + methodsTest2.keySet().toString());
                
              //   for(String str : methods2.keySet()) {
             //        System.out.println("Method2 : " + str);
              //   }
                
                System.out.println("methods2 : " + methods2.keySet());
            //    System.out.println("ps.imports : " + ps.imports.toString());
                for (MethodInfo methodInfo : ps.methodInvocations) {

                  //  System.out.println("methodInfo.expression : " + methodInfo.expression + " " + availableExtensionFields.toString());
                    
                    if (methodInfo.expression != null && availableExtensionFields.keySet().contains(methodInfo.expression)) {
                        methodInfo.expression = availableExtensionFields.get(methodInfo.expression);
                    } else if (methodInfo.expression != null && availableImportFields.keySet().contains(methodInfo.expression)) {
                        acceptedMethods.add(methodInfo.methodName);
                    }
                    
                 //   System.out.println("methodInfo.expression after : " + methodInfo.expression);

                    if (methodInfo.expression != null) {
                        for (String s : rMethods.keySet()) {
                        //    System.out.println("methodInfo.expression sss : " + methodInfo.expression + " sss : " + s);
                            String lastPart = methodInfo.expression;
                            if(methodInfo.expression.contains("."))
                                lastPart = methodInfo.expression.substring(methodInfo.expression.lastIndexOf(".")+1);
                        //    System.out.println("uuuu " + lastPart + " " + lastPart.substring(lastPart.indexOf("(")+1));
                            if(lastPart.contains("("))
                                lastPart = lastPart.substring(0,lastPart.indexOf("("));
                            if (lastPart.endsWith(s)) {
                                if (rMethods.get(s).contains("$")) {
                                //    System.out.println("Before : " + methodInfo.expression + " After : " + rMethods.get(s).substring(0, rMethods.get(s).indexOf("$")) + " for method : " + methodInfo.methodName);
                                    if(rMethods.get(s).substring(0, rMethods.get(s).indexOf("$")).trim().compareTo("")!=0) {
                                        methodInfo.expression = rMethods.get(s).substring(0, rMethods.get(s).indexOf("$"));
                                   //     System.out.println("methodInfo.expression1 : " + methodInfo.expression);
                                    }
                                } else {
                                //    System.out.println("Before : " + methodInfo.expression + " After : " + rMethods.get(s) + " for method : " + methodInfo.methodName);
                                    if(rMethods.get(s).trim().compareTo("")!=0) {
                                        methodInfo.expression = rMethods.get(s);
                                  //      System.out.println("methodInfo.expression2 : " + methodInfo.expression);
                                    }
                                }
                            }
                        }
                    }
                    
                    if(methodInfo.expression!=null && methodInfo.expression.contains(".")){
                        String outcome = resolveMethods(methodInfo.expression.substring(0, methodInfo.expression.indexOf(".")), methodInfo.expression.substring(methodInfo.expression.indexOf(".")+1), methods);
                        if(outcome!=null && outcome!="") {
                            methodInfo.expression = outcome;
                       //     System.out.println("OOO : " + outcome  +"o");
                        }
                    }

                    boolean added = false;
                    
                    for(String str : ps.imports) {
                            
                        if(str.contains(methodInfo.expression)){
                            for(String lb : acceptedLibraries){
                                if(str.startsWith(lb)) {
                                    acceptedMethods.add(methodInfo.methodName);
                                    rMethods.put(methodInfo.methodName,methodInfo.expression);
                                    added = true;
                                }
                            }
                        }
                    }
                    if (methodInfo.expression != null && !methodInfo.expression.equals("") && !added) {
                        if(methodInfo.expression.endsWith("]")){
                            methodInfo.expression = methodInfo.expression.substring(0,methodInfo.expression.lastIndexOf("["));
                        //    System.out.println("methodInfo.expression3 : " + methodInfo.expression);
                        }
                        if(methodInfo.expression.endsWith(">")){
                            methodInfo.expression = methodInfo.expression.substring(0,methodInfo.expression.indexOf("<"));
                      //      System.out.println("methodInfo.expression4 : " + methodInfo.expression);
                        }
                        
                        if(methods.keySet().contains(methodInfo.expression)) {
                            methods2.put(methodInfo.expression, methods.get(methodInfo.expression));
                        }
                      //    System.out.println("methodInfo.expression : " + methodInfo.expression);
                        for (String s : methods2.keySet()) {
                            
                            String s1=s;
                            if (s.contains("$")) {
                                s1 = s.substring(0, s.indexOf("$"));
                            }
                        //    System.out.println("yyy : " + methodInfo.expression + " " + s);
                            
                            if (s1.endsWith(methodInfo.expression)) {
                                if(s.contains("."))
                                    s1 = s1.substring(0, s.lastIndexOf(".")+1);
                                
                            //    for (String ms : methods2.keySet()) {
                                 //   System.out.println("mmm1 " + s1 + " " + s + " " + methodInfo.methodName);
                                    String ms1 = s.replaceAll(s1, "");
                                    if(ms1.compareTo("")!=0 && !ms1.contains(".")) {
                                     //   System.out.println("mmm " + methods.keySet().toString() + " " + ms1);
                                        for (String meth : methods2.get(s).keySet()) {

                                            if (meth.equals(methodInfo.methodName)) {
                                         //       System.out.println("sss : " + methodInfo.methodName + " " + meth + " " + methods2.get(s).keySet().toString());
                                                acceptedMethods.add(methodInfo.methodName);
                                                if (methods2.get(s) != null && methods2.get(s).get(meth + "_Return_Type") != null && methods2.get(s).get(meth + "_Return_Type")[0].toString().contains("class ")) {
                                                    rMethods.put(methodInfo.methodName, methods2.get(s).get(meth + "_Return_Type")[0].toString().replaceAll("class ", ""));
                                                } else if (methods2.get(s) != null && methods2.get(s).get(meth + "_Return_Type") != null && methods2.get(s).get(meth + "_Return_Type")[0].toString().contains("interface ")) {
                                                    rMethods.put(methodInfo.methodName, methods2.get(s).get(meth + "_Return_Type")[0].toString().replaceAll("interface ", ""));
                                                } else if (methods2.get(s) != null && methods2.get(s).get(meth + "_Return_Type") != null) {
                                                    rMethods.put(methodInfo.methodName, methods2.get(s).get(meth + "_Return_Type")[0].toString());
                                                }
                                            }
                                        }
                                        if(expMethodMap.containsKey(methodInfo.methodName))
                                            rMethods.put(methodInfo.methodName,expMethodMap.get(methodInfo.methodName));
                                        
                                    }
                            //    }
                            }
                        }
                        //  

                        if(methodInfo.expression.startsWith("."))
                            methodInfo.expression = methodInfo.expression.replaceFirst(".", "");
                        if(!rMethods.keySet().contains(methodInfo.expression)){
                            System.out.println("ttt : " + methodInfo.expression + " " + Arrays.asList(methodInfo.expression.split("\\.")).toString());
                            ArrayList<String>  longExpression = new ArrayList<>();
                            longExpression.addAll(Arrays.asList(methodInfo.expression.split("\\.")));
                            int j=0;
                            int siz = longExpression.size();
                            for(int i=0; i<siz; i++) {
                                Character c = longExpression.get(j++).charAt(0);
                                if(Character.isUpperCase(c))
                                    break;
                                else {
                                    longExpression.remove(0);
                                    j--;
                                }
                            }
                            if(longExpression.size()>1) {
                            //    System.out.println("ooo : " );
                                while(longExpression.size()>1) {
                                    boolean breakValue = false;
                                    for (String s : methods2.keySet()) {
                                        String s1=s;
                                        if (s.contains("$")) {
                                            s1 = s.substring(0, s.indexOf("$"));
                                        }
                                    //    if(longExpression.size()>1)
                                    //        System.out.println("xxz" + longExpression.get(0) + " " + longExpression.get(1));
                                        while(longExpression.get(0).contains(")") && !longExpression.get(0).contains("("))
                                                longExpression.remove(0);
                                        while(longExpression.size()>1 && longExpression.get(1).contains(")") && !longExpression.get(1).contains("("))
                                                longExpression.remove(1);

                                        if(s1.contains("ShrinkWrap"))
                                            System.out.println("eee " + longExpression.get(0) + " " + s1.endsWith(longExpression.get(0)));
                                        
                                        if (s1.endsWith(longExpression.get(0)) && longExpression.size()>1) {
                                            if(longExpression.get(1).contains("(")){
                                                if(!longExpression.get(1).startsWith("as(") && !longExpression.get(1).startsWith("create(")) {
                                                    String exp = longExpression.remove(1);
                                                    longExpression.add(1,exp.substring(0,exp.indexOf("(")));
                                                }else{
                                                    String exp = longExpression.remove(1);
                                                    longExpression.add(1,exp.substring(exp.indexOf("(")+1));
                                                    longExpression.remove(0);
                                                }
                                            }
                                            if(longExpression.get(0).contains("<")){
                                                String exp = longExpression.remove(0);
                                                longExpression.add(0,exp.substring(0,exp.indexOf("<")));
                                            }
                                            if(longExpression.get(1).contains("<")){
                                                String exp = longExpression.remove(1);
                                                longExpression.add(1,exp.substring(0,exp.indexOf("<")));
                                            }
                                        //    System.out.println("iii" + longExpression.get(0) + " " + longExpression.get(1) + " " + methods2.get(s).containsKey(longExpression.get(1) + "_Return_Type") + " " + s);
                                            String rt = longExpression.get(1) + "_Return_Type";
                                            if(methods2.get(s).get(rt)!=null){
                                                longExpression.remove(0);
                                                String exp = longExpression.remove(0);
                                                System.out.println("exp " + exp + " " + s + " " + methods2.get(s)!=null);
                                                if(methods2.get(s).get(exp + "_Return_Type")[0]!=null) {
                                                    if (methods2.get(s) != null && methods2.get(s).get(exp + "_Return_Type") != null && methods2.get(s).get(exp + "_Return_Type")[0].toString().contains("class ")) {
                                                        longExpression.add(0, methods2.get(s).get(exp + "_Return_Type")[0].toString().replaceAll("class ", ""));
                                                    } else if (methods2.get(s) != null && methods2.get(s).get(exp + "_Return_Type") != null && methods2.get(s).get(exp + "_Return_Type")[0].toString().contains("interface ")) {
                                                        longExpression.add(0, methods2.get(s).get(exp + "_Return_Type")[0].toString().replaceAll("interface ", ""));
                                                    } else if (methods2.get(s) != null && methods2.get(s).get(exp + "_Return_Type") != null) {
                                                        longExpression.add(0, methods2.get(s).get(exp + "_Return_Type")[0].toString());
                                                    }
            
                                                   System.out.println("RT : " + longExpression.get(0));
                                                }
                                                
                                                if(longExpression.size()<=1)
                                                    break;
                                                
                                            }
                                        }else{
                                            breakValue=true;
                                        }
                                    }
                                    if(breakValue)
                                        break;
                                } 
                                
                            }
                            
                            if(longExpression.size()==1 ) {

                                System.out.println("xxx" + longExpression.get(0) + " " + methodInfo.methodName + " " + methodInfo.expression + " " + methods2.keySet().toString());
                                boolean exists = false;
                                for(String c : methods2.keySet()){
                                    if(c.contains(longExpression.get(0))) {
                                
                                        if((methods2.get(c).keySet().contains(methodInfo.methodName)) || (acceptedMethodMap.get(longExpression.get(0))!=null && acceptedMethodMap.get(longExpression.get(0)).equals(methodInfo.methodName))){
                                            acceptedMethods.add(methodInfo.methodName);
                                            if(methods2.get(c).get(longExpression.get(0) + "_Return_Type")!=null && methods2.get(c).get(longExpression.get(0) + "_Return_Type")[0]!=null) {
                                                    if (methods2.get(c) != null && methods2.get(c).get(longExpression.get(0) + "_Return_Type") != null && methods2.get(c).get(longExpression.get(0) + "_Return_Type")[0].toString().contains("class ")) {
                                                        rMethods.put(methodInfo.methodName, methods2.get(c).get(longExpression.get(0) + "_Return_Type")[0].toString().replaceAll("class ", ""));
                                                    } else if (methods2.get(c) != null && methods2.get(c).get(longExpression.get(0) + "_Return_Type") != null && methods2.get(c).get(longExpression.get(0) + "_Return_Type")[0].toString().contains("interface ")) {
                                                        rMethods.put(methodInfo.methodName, methods2.get(c).get(longExpression.get(0) + "_Return_Type")[0].toString().replaceAll("interface ", ""));
                                                    } else if (methods2.get(c) != null && methods2.get(c).get(longExpression.get(0) + "_Return_Type") != null) {
                                                        rMethods.put(methodInfo.methodName, methods2.get(c).get(longExpression.get(0) + "_Return_Type")[0].toString());
                                                    }
            
                                                   System.out.println("RT : " + longExpression.get(0));
                                                }
                                            exists = true;
                                            System.out.println("xxy" + longExpression.get(0) + " " + methodInfo.methodName + " " + methodInfo.expression);
                                            break;
                                        }
                                    }
                                }
                                
                                if(!exists){
                                    System.out.println("Not resolved long expression : " + longExpression.toString() + " " + methodInfo.methodName);
                                }
                            }
                        }
                    }

                    String c = key.toString();

                    for (String s : localMethods.keySet()) {
                        if (s.contains(c) || s.contains(ps.packageName)) {
                        //    System.out.println("localMethods " + localMethods.get(s).keySet());
                            for (String meth : localMethods.get(s).keySet()) {
                                if (meth.equals(methodInfo.methodName)) {
                                    acceptedMethods.add(methodInfo.methodName);
                                    //    rMethods.put(methodInfo.methodName, localMethods.get(s).get(meth+"_Return_Type")[0].toString());
                                }
                            }
                        }

                    }

                    if (availableFields.contains(c) || availableExtensionFields.containsKey(c)) {
                        acceptedMethods.add(methodInfo.methodName);
                    }
                    

                    String libName = null;
                    if (methodInfo.expression != null) {
                        for (String s : ps.imports) {
                            if (s.endsWith(methodInfo.expression)) {
                                //    System.out.println("libName : " + s);
                                libName = s;
                                break;
                            }
                        }
                    }

                    if (methodInfo.expression != null) {
                        for (String s : acceptedEndWithExpressions) {
                            if (methodInfo.expression.endsWith(s)) {
                                acceptedMethods.add(methodInfo.methodName);
                                rMethods.put(methodInfo.methodName, "Object");
                            }
                        }

                        for (String s : acceptedStartWithExpressions) {
                            if (methodInfo.expression.startsWith(s) || (libName != null && libName.startsWith(s))) {
                                acceptedMethods.add(methodInfo.methodName);
                                rMethods.put(methodInfo.methodName, "Object");
                            }
                        }

                        if (acceptedExpressions.contains(methodInfo.expression)) {
                            acceptedMethods.add(methodInfo.methodName);
                            rMethods.put(methodInfo.methodName, "Object");
                        }
                        
                        for (String s : acceptedMethodMap.keySet()) {
                            if (methodInfo.expression.equals(s) && (methodInfo.methodName.equals(acceptedMethodMap.get(s)))) {
                                acceptedMethods.add(methodInfo.methodName);
                                rMethods.put(methodInfo.methodName, methodInfo.expression);
                            }
                        }
                    }

                    int m = 0;
                    for (String param : methodInfo.params) {
                        if(param.contains("."))
                            param = param.substring(param.lastIndexOf(".")+1);
                        if ((availableImportFields.containsKey(param) || availableFields.contains(param) || classLibraries.contains(param) || acceptedMethods.contains(param) || param == null || param.equals("this")) && !methodInfo.isResolvedParam.get(m)) {
                            methodInfo.isResolvedParam.set(m, Boolean.TRUE);
                        }
                        m++;
                    }
                    if (!acceptedMethods.contains(methodInfo.methodName) /*|| methodInfo.isResolvedParam.contains("false")*/) {
                        System.out.println("----" + methodInfo.methodName + " " + methodInfo.expression + " " + methodInfo.params.toString() + " " + methodInfo.isResolvedParam.toString());
                    }
                }
        /*        System.out.println("Methods Not Resolved : ==========");
                for (String methodNotResolved : ps.methodsNotResolved) {
                    if (!acceptedMethods.contains(methodNotResolved)) {
                        System.out.println(methodNotResolved);
                    }
                }
                System.out.println("Class Instance Creations : ==========");
                ArrayList<String> acceptedclasses = TestsuiteParser.readAcceptedTypesFromFile(System.getProperty("AcceptedTypesFilePath") + "/" + "classInstances.txt");
                for (ClassInfo classInfo : ps.classInstanceCreations) {
                    if (!acceptedclasses.contains(classInfo.className) && !classInfo.isResolvedParam.contains("false")) {
                        System.out.println(classInfo.className + " " + classInfo.params.toString() + " " + classInfo.isResolvedParam.toString());
                    }
                }*/
                System.out.println("Libraries : ==========" + availableFields.size());

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
            
            HashMap<String,HashSet<String>> internalPackagesAndClasses = JavaClassParser.getInternalPackagesAndClasses();
            
            System.out.println("\n\n\nInternal Packages And Classes used : ");
            
            for(String s : internalPackagesAndClasses.keySet()){
                System.out.println(s + " " + internalPackagesAndClasses.get(s).toString());
            }
            
            HashSet<String> excludedLibraries = DependencyTreeMethods.addExcludedLibraries();
             HashSet<String> allPackages = DependencyTreeMethods.listAvailablePackages();
            
            int found = 0;
            int notFound = 0;
            HashSet<String> foundArray = new HashSet<>();
            HashSet<String> notfoundArray = new HashSet<>();
            for(String s : usedLibraries.keySet()){
                if(allPackages.contains(s) || internalPackagesAndClasses.keySet().contains(s)) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static String resolveMethods(String className, String methodName, HashMap<String,HashMap<String,String[]>> methods){
        String returnName = null;
        
        String methName = methodName;
        if(methodName.contains("."))
            methName = methodName.substring(methodName.indexOf(".")+1);
        if(methName!=null && methName.contains("("))
            methName=methName.substring(0,methName.indexOf("("));
        
        if(methName!=null && className!=null) {
            
            for(String pth : methods.keySet()) {
                if(pth.contains(className)) {
              //      System.out.println("rrr " + className + " " + methName + " " + methodName);
                    for(String mmm : methods.get(pth).keySet()) {
                        if(mmm.equals(methName)){
                            String className2 = null;

                            if(methName.contains("["))
                                methodName = methName.substring(0,methodName.indexOf("["));
                            
                            String classNamePrev = methods.get(pth).get(methName+"_Return_Type")[0];
                            if(methods.get(pth).get(methName+"_Return_Type")!=null){
                                className2 = methods.get(pth).get(methName+"_Return_Type")[0];
                            
                                if(!className2.equals(classNamePrev)) {
                                //    System.out.println("resolutions : " + className2 + " " + methName);
                                    if(className2!=null && className2.startsWith("."))
                                        className2 = className2.replaceFirst(".","");
                                //    System.out.println("resolutions2 : " + className2 + " " + methName);
                                    returnName = resolveMethods(className2, methName, methods);
                                }
                            }
                        }
                    }
                }
            }
        }else
            returnName = className;
        
        return returnName;
    }
    
    private static void addExtensions(HashMap<String,HashMap<String,String[]>> methods, HashMap<String,HashMap<String,String[]>> methods2, String extensionName, String extNameConst,ArrayList<String> acceptedMethods,HashMap<String, String> acceptedMethodMap,HashMap<String,String> rMethods){
        if(methods.get(extensionName)!=null && methods.get(extensionName).get(extensionName + "_Extensions")!=null){
        //    System.out.println("AllExt : " + Arrays.toString(methods.get(extensionName).get(extensionName + "_Extensions")));
            for(String e : methods.get(extensionName).get(extensionName + "_Extensions")) {
                if(methods.get(e)!=null) {
                    if(methods2.get(extNameConst)==null) {
                        methods2.put(extNameConst, methods.get(e));
                 //       System.out.println("extensionName " + extensionName);
                    } else {
                        for(String m : methods.get(e).keySet()){
                            if(methods2.get(extNameConst).get(m)==null) {
                                methods2.get(extNameConst).put(m, methods.get(e).get(m));
                         //       System.out.println("extensionM " + extNameConst + " " + m);
                            }
                        }
                    }
                    addExtensions(methods, methods2, e, extNameConst,acceptedMethods,acceptedMethodMap,rMethods);
                }else{
                    for (String s : acceptedMethodMap.keySet()) {
                  //      System.out.println("uuu : " + extensionName + " " + s + " " + acceptedMethodMap.get(s));
                        if (e.equals(s)) {
                            acceptedMethods.add(acceptedMethodMap.get(s));
                            rMethods.put(acceptedMethodMap.get(s), extNameConst);
                        }
                    }
                }
            }
        }
    }

}
