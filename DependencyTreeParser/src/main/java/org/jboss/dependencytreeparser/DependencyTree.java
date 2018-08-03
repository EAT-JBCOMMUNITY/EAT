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
       
            HashMap<String,ArrayList<Class[]>> classes = DependencyTreeMethods.listClasses();
            
            for(String s : classes.keySet()) {
                System.out.println("class : " + s);
                for (int i=0; i<classes.get(s).size(); i++) {
                    System.out.println("constructor params : ");
                    for (int j=0; j<classes.get(s).get(i).length; j++)
                        System.out.println(classes.get(s).get(i)[j].getTypeName());
                }
            }
            HashMap<String,ArrayList<String>> usedLibraries = JavaClassParser.testLibraryUsage();

            System.out.println("\n\n\nTest libraries used : ");

         //   for(String s : usedLibraries.keySet()){
        //        System.out.println(s + " " + usedLibraries.get(s).toString());
         //   }
            
            HashMap<String,String> internalPackagesAndClasses = JavaClassParser.getInternalPackagesAndClasses();
            
            System.out.println("\n\n\nInternal Packages And Classes used : ");
            
            for(String s : internalPackagesAndClasses.keySet()){
                System.out.println(s + " " + internalPackagesAndClasses.get(s));
            }
            
            int found = 0;
            int notFound = 0;
            HashSet<String> foundArray = new HashSet<>();
            HashSet<String> notfoundArray = new HashSet<>();
            for(String s : usedLibraries.keySet()){
                if(packages.contains(s) || internalPackagesAndClasses.keySet().contains(s)) {
                    found++;
                    foundArray.add(s);
                }else{
                    notFound++;
                    notfoundArray.add(s);
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
            
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    
}
