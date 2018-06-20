package org.jboss.dependencytreeparser;

import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author panos
 */
public class DependencyTree {

    public static void main(String[] args) throws Exception {
        try {
            HashSet<String> packages = DependencyTreeMethods.listPackages();

            System.out.println("Available libraries : ");

            for(String s : packages){
                System.out.println(s);
            }

            HashSet<String> usedLibraries = JavaClassParser.testLibraryUsage();

            System.out.println("\n\n\nTest libraries used : ");

            for(String s : usedLibraries){
                System.out.println(s);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    
}
