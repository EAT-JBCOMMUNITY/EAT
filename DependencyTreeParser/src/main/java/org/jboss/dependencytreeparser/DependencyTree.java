package org.jboss.dependencytreeparser;

import java.util.ArrayList;

/**
 *
 * @author panos
 */
public class DependencyTree {

    public static void main(String[] args) throws Exception {
        ArrayList<String> jarClasses = DependencyTreeMethods.listClasses();
        
        for(String s : jarClasses){
            System.out.println(s);
        }
    }

    
}
