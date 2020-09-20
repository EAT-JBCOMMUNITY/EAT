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
                Map param_map = panel_main.getParameters();
                Commands command = panel_main.getCommand();
                ScriptWriter sw = new ScriptWriter();
                sw.parseData(param_map, command);
                sw.createFile();
                run_ci();
            }
        });
    }
    
    private void run_ci() {
        try {
            String command = "./gen.sh";
            
            panel_main.appendOutputLog("Build has started ..." + System.getProperty("line.separator"));
            ProcessBuilder pb = new ProcessBuilder("/bin/bash", "-c", command);
            Process p = pb.start();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            
            Thread live_output = new Thread() {
                public void run() {
                    try {
                        while((output_line = reader.readLine()) != null || p.isAlive()) {
                            panel_main.appendOutputLog(output_line+System.getProperty("line.separator"));
                        } 
                        panel_main.appendOutputLog("Build has finished ... Check log for output details ..." + System.getProperty("line.separator"));
            
                        p.destroy();
                    }catch(IOException e) {
                    
                    }
                }  
            };
            live_output.start();
            
        }catch(IOException e) {
            
        }
    }
}
