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
        file = new File("gen.sh");
        sb = new StringBuilder(); 
    }
    
    public void parseData(Map<String, String> param_map, Commands command) {
        sb.append("#!/bin/bash\n\n");
        for(Map.Entry<String, String> entry : param_map.entrySet()) {
                sb.append("export ");
                sb.append(entry.getKey());
                sb.append("=");
                sb.append(entry.getValue());
                sb.append("\n");
        }
        sb.append("\n");
        
        String path = file.getAbsolutePath();
        String[] parts = path.split("CIUI");
        path = parts[0];
        
        //Add run command
        sb.append("cd ");
        sb.append(path);
        sb.append("CI");
        sb.append("\n");
        sb.append("./run.sh ");
        sb.append(command.getCommand());
    }
    
    public void createFile() {
        try {
            //Delete existing file
            if(file.exists())
                file.delete();
            
            //Creating the file
            file.createNewFile();
            
            //Start writing
            fw = new FileWriter(file);
            fw.write(sb.toString());
            
            //Releasing resources
            fw.close();
            
            //Set it read only and executable
            file.setReadOnly();
            file.setExecutable(true);
            
        }catch(IOException e) {
        
        }
    }       
}
