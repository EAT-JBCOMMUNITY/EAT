package ciui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.JPanel;


public class Controller {
   
    Window window;
    JPanel panel_main;
    
    public Controller() {
        window = new Window();
        panel_main = new PanelMain();
        
        window.add(panel_main, "Main");
    }
    
    public void open_window() {
        window.setPanel("Menu"); 
        System.out.println("Running");
    }
    
    public void run_ci() {
        try {
            Process p = new ProcessBuilder("bash", "-c", "echo $pwd").start();
       
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
