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
    private String output_line;
    
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
            
            Thread live_output = new Thread() {
                public void run() {
                    try {
                        while((output_line = reader.readLine()) != null) {
                            panel_main.appendOutputLog(output_line+System.getProperty("line.separator"));
                        } 
                    }catch(IOException e) {
                    
                    }
                }  
            };
            live_output.start();
            
        }catch(IOException e) {
            
        }
    }
}
