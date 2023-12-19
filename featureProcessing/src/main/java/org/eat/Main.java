package org.eat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;


public class Main {

    private static HashMap<String, String> processFiles = new HashMap();
    private static String featureFile;
    private static String eatHomeDir;

    public static void main(String[] args) throws IOException, InterruptedException {
        featureFile = System.getenv("FEATURE_FILE");
        eatHomeDir = System.getenv("EAT_HOME");
        Main.processFiles();
    }

    private static void processFiles() throws IOException {

	try (BufferedReader br = new BufferedReader(new FileReader(new File(featureFile)))) {
	    String line;
	    while ((line = br.readLine()) != null) {
	    System.out.println(line);
	       String[] parts = line.split(">>");
	       if(parts.length==2) {
	      	 	processFiles.put(parts[0].trim(),parts[1].trim());
	       		System.out.println("+++ " + parts[0].trim() + " " + parts[1].trim());
	       }
	    }
	}
	
	for(String key : processFiles.keySet()) {
	    if(processFiles.get(key).compareTo("delete")!=0) {
		List<String> fileContent = new ArrayList<>(Files.readAllLines(Paths.get(eatHomeDir + "/" + key), StandardCharsets.UTF_8));
		for (int i = 0; i < fileContent.size(); i++) {
		    if (fileContent.get(i).contains("@EAT")) {
			fileContent.set(i, processFiles.get(key));
			break;
		    }
		}

		Files.write(Paths.get(eatHomeDir + "/" + key), fileContent, StandardCharsets.UTF_8);
	    }else {
	        File toDelete = new File(eatHomeDir + "/" + key);
	        toDelete.delete();
	    }
	        
	}

    }
}
