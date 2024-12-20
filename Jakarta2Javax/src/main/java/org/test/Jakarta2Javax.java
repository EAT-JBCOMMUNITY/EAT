package org.test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

public class Jakarta2Javax {
    // This set of map entries could/should be extended ...
    final static Map<String, String> transformations = Map.ofEntries(
            Map.entry("javax.servlet.RequestDispatcher", "jakarta.servlet.RequestDispatcher"),
            Map.entry("javax.servlet.ServletException", "jakarta.servlet.ServletException"),
            Map.entry("javax.servlet.ServletInputStream", "jakarta.servlet.ServletInputStreamr"),
            Map.entry("javax.servlet.annotation.WebServlet", "jakarta.servlet.annotation.WebServlet"),
            Map.entry("javax.servlet.http.HttpServlet", "jakarta.servlet.http.HttpServlet"),
            Map.entry("javax.servlet.http.HttpServletRequest", "jakarta.servlet.http.HttpServletRequest"),
            Map.entry("javax.servlet.http.HttpServletResponse", "jakarta.servlet.http.HttpServletResponse"),
            Map.entry("javax.servlet.AsyncContext", "jakarta.servlet.AsyncContext"),
            Map.entry("javax.servlet.ServletOutputStream", "jakarta.servlet.ServletOutputStream"),
            Map.entry("javax.servlet.WriteListener", "jakarta.servlet.WriteListener"),
            Map.entry("javax.servlet.ReadListener", "jakarta.servlet.ReadListener"),
            Map.entry("javax.servlet.ServletConfig", "jakarta.servlet.ServletConfig"),
            Map.entry("javax.servlet.Servlet", "jakarta.servlet.Servlet"),
            Map.entry("javax.servlet.annotation.WebServlet", "jakarta.servlet.annotation.WebServlet")
    );

    public static List<CtClass> getClassesFromWar(String warFilePath) throws IOException, NotFoundException {
        List<CtClass> classes = new ArrayList<>();
        try (JarFile warFile = new JarFile(warFilePath)) {
            Enumeration<JarEntry> entries = warFile.entries();
            ClassPool pool = ClassPool.getDefault();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String entryName = entry.getName();
                if (entryName.endsWith(".class")) {
                    // Extract class bytes  
                    byte[] classBytes = extractClassBytes(warFile, entry);
                    // Create CtClass object 
                    CtClass ctClass = pool.makeClass(new ByteArrayInputStream(classBytes));
                    classes.add(ctClass);
                }
            }
        }
        return classes;
    }

    private static byte[] extractClassBytes(JarFile warFile, JarEntry entry) throws IOException {
        byte[] classBytes;
        try (var inputStream = warFile.getInputStream(entry)) {
            classBytes = inputStream.readAllBytes();
        }
        return classBytes;
    }

    public static void main(String[] args) throws Exception {
        // Set with your WAR file path 
        String warFilePath = System.getProperty("jar");      
        List<CtClass> classes = getClassesFromWar(warFilePath);
        System.out.println("Extracted classes:");
        for (CtClass ctClass : classes) {
            for (String key : transformations.keySet()) {
                ctClass.replaceClassName(transformations.get(key),key);
            }
            ctClass.writeFile();
            System.out.println(ctClass.getName());
        }
    }
}
