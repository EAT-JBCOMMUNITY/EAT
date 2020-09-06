package ciui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;


public class ScriptWriter {
    
    private File file;
    private FileWriter fw;
    private StringBuilder sb;
    
    public ScriptWriter() {
        sb = new StringBuilder();
        sb.append("#!/bin/bash\n\n");
    }
    
    public void parseData(Map<String, String> param_map, Commands command) {
        for(Map.Entry<String, String> entry : param_map.entrySet()) {
                sb.append("export ");
                sb.append(entry.getKey());
                sb.append("=");
                sb.append(entry.getValue());
                sb.append("\n");
        }
        sb.append("\n");
        
        //Add run command
        sb.append("./run.sh ");
        sb.append(command.getCommand());
    }
    
    public void createFile() {
        try {
            //Creating the file
            file = new File("gen.sh");
            file.createNewFile();
            
            //Start writing
            fw = new FileWriter(file);
            fw.write(sb.toString());
            
            //Releasing resources
            fw.close();
            
            //Set it read only
            file.setReadOnly();
            
        }catch(IOException e) {
        
        }
    }       
}
