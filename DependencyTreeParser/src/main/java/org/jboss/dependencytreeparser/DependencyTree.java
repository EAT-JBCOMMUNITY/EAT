package org.jboss.dependencytreeparser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author panos
 */
public class DependencyTree {

    public static void main(String[] args) throws Exception {
        try {
        //    System.out.println("Print Dependencies : ");
        //    DependencyTreeMethods.getArtifacts();
            
        //    HashSet<String> packages = DependencyTreeMethods.listPackages();

        //    System.out.println("\n\n\nAvailable libraries : ");

        //    for(String s : packages){
        //        System.out.println(s);
       //     }

            HashMap<String,ArrayList<Class[]>> classes = DependencyTreeMethods.listClasses();
            
            for(String s : classes.keySet()) {
                System.out.println("class : " + s);
                for (int i=0; i<classes.get(s).size(); i++) {
                    System.out.println("constructor params : ");
                    for (int j=0; j<classes.get(s).get(i).length; j++)
                        System.out.println(classes.get(s).get(i)[j].getTypeName());
                }
            }
         /*   HashSet<String> usedLibraries = JavaClassParser.testLibraryUsage();

            System.out.println("\n\n\nTest libraries used : ");

            for(String s : usedLibraries){
                System.out.println(s);
            }
            
            HashMap<String,String> internalPackagesAndClasses = JavaClassParser.getInternalPackagesAndClasses();
            
            for(String s : internalPackagesAndClasses.keySet()){
                System.out.println(s + " " + internalPackagesAndClasses.get(s));
            }*/
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    
}
