package ciui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;


public class Controller {
   
    private Window window;
    private PanelMain panel_main;
    
    public Controller() {
        window = new Window();
        panel_main = new PanelMain();
        
        window.add(panel_main, "Main");
    }
    
    public void open_window() {
        window.setPanel("Menu");
        
        panel_main.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                run_ci(panel_main.getParameters());
            }
        });
    }
    
    public void run_ci(Map<String, String> param_map) {
        try {
            String command;
            command = "./run.sh -all";
            
            ProcessBuilder pb = new ProcessBuilder("/bin/bash", "-c", command);
            
            //Export parameters to process
            for(Map.Entry<String, String> entry : param_map.entrySet()) {
                pb.environment().put(entry.getKey(), entry.getValue());
            }
            
            Process p = pb.start();
       
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line = null;
            while((line = reader.readLine()) != null) {
               
               builder.append(line);
               //builder.append(System.getProperty("line.separator"));
            }
            
            String result = builder.toString();
            System.out.println(result);
            
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}
