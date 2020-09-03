package ciui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public class ScriptWriter {
    
    private File file;
    private FileWriter fw;
    private StringBuilder sb;
    
    public ScriptWriter() {
        sb = new StringBuilder();
    }
    
    public void parseData() {
        /* TODO parse data to string*/
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
