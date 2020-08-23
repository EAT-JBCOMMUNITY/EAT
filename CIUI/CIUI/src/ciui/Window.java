package ciui;

import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.JFrame;


public class Window extends JFrame{
    
    private CardLayout cl = new CardLayout();
    
    public Window() {
        
        setTitle("CIUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(1000, 700));
        setMinimumSize(new Dimension(700, 500));
        setLayout(cl);
        
        //Close button event
//        addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowClosing(WindowEvent we) {
//                Client.close();
//                System.exit(0);
//            }
//        });
    }
    
    public void setPanel(String name) {
        cl.show(this.getContentPane(), name);
        this.setVisible(true);
    }
}
