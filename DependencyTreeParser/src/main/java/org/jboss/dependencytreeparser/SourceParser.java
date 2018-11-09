package org.jboss.dependencytreeparser;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;

/**
 *
 * @author panos
 */
public class SourceParser {
    static HashMap<String,String> fields = new HashMap<>();
    static HashSet<String> imports = new HashSet<>();
    static HashSet<String> methods = new HashSet<>();

    public static HashMap<String,String> getFields() {
        return fields;
    }

    public static void setFields(HashMap<String,String> fields) {
        SourceParser.fields = fields;
    }

    public static HashSet<String> getImports() {
        return imports;
    }

    public static void setImports(HashSet<String> imports) {
        SourceParser.imports = imports;
    }

    public static void parse(String str) throws IOException {

        fields.clear();
        methods.clear();
        imports.clear();
   //     System.out.println("parse");
        ASTParser parser = ASTParser.newParser(AST.JLS3);
  //      System.out.println("parse");
        parser.setSource(readFileToString(str).toCharArray());
        parser.setKind(ASTParser.K_COMPILATION_UNIT);

   //     System.out.println(readFileToString(str).toCharArray());
        
        final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
        

        cu.accept(new ASTVisitor() {

            Set names = new HashSet();
            int blockCount = 0;

            public boolean visit(FieldDeclaration node) {
                String name = node.fragments().get(0).toString().split("=")[0].trim();
                String type = node.getType().toString();
                
             //   System.out.println("Declaration of field '" + name + " of type " + type + "' at line"
             //                   + cu.getLineNumber(node.getStartPosition()) + " " + node.modifiers().toString()); 
                
                if(!node.modifiers().toString().contains("private")){
                    fields.put(name, type);
                }
                
                return true;
            }

            public boolean visit(MethodDeclaration node) {
                if (node.getName().getIdentifier() != null) {
                //    System.out.println("Declaration of method '" + node.getName() + "' at line"
               //                 + cu.getLineNumber(node.getStartPosition())); 
               
                    methods.add(node.getName().toString());
                }
                return true;
            }
        
            public boolean visit(ImportDeclaration node) {
            //    System.out.println("Declaration of import '" + node.getName() + "' at line"
            //                    + cu.getLineNumber(node.getStartPosition())); 
                   
                imports.add(node.getName().toString());
 
                return true;
            }
        });

    }
    
    //read file content into a string
    private static String readFileToString(String filePath) throws IOException {
        StringBuilder fileData = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        char[] buf = new char[1024];
        int numRead = 0;
        while ((numRead = reader.read(buf)) != -1) {
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
        }

        reader.close();

        return fileData.toString();
    }
    
}

