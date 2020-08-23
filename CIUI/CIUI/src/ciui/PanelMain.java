package ciui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class PanelMain extends JPanel{
    
    private final int FIELD_SIZE=15;
    private String[] options = {"Specific Pull Request", "All Pull Requests"};
    private JButton start;
    
    public PanelMain(){
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
        
        JPanel left_panel = new JPanel();
        left_panel.setLayout(new BoxLayout(left_panel, BoxLayout.Y_AXIS));
        
        
        JPanel radio_panel = new JPanel();
        
        JRadioButton r1 = new JRadioButton(options[0]);
        JRadioButton r2 = new JRadioButton(options[1]);    
        r1.setBounds(75,50,100,30);    
        r2.setBounds(75,100,100,30);   
        r1.setSelected(true);
        
        ButtonGroup bg = new ButtonGroup();    
        bg.add(r1);
        bg.add(r2);    
        
        radio_panel.add(r1);
        radio_panel.add(r2);
        
        left_panel.add(radio_panel);
        
        JPanel inputs = new JPanel();
        inputs.setLayout(new BoxLayout(inputs, BoxLayout.Y_AXIS));
      
        JPanel group_1 = new JPanel();
        group_1.setLayout(new BorderLayout());
        group_1.add(new JLabel("SERVER"), BorderLayout.WEST);
        group_1.add(new JTextField(FIELD_SIZE), BorderLayout.EAST);
        inputs.add(group_1);
        inputs.add(Box.createRigidArea(new Dimension(0, 5)));
         
        JPanel group_2 = new JPanel();
        group_2.setLayout(new BorderLayout());
        group_2.add(new JLabel("SERVER_PR"), BorderLayout.WEST);
        group_2.add(new JTextField(FIELD_SIZE), BorderLayout.EAST);
        inputs.add(group_2);
        inputs.add(Box.createRigidArea(new Dimension(0, 5)));
        
        JPanel group_3 = new JPanel();
        group_3.setLayout(new BorderLayout());
        group_3.add(new JLabel("SERVER_BRANCH"), BorderLayout.WEST);
        group_3.add(new JTextField(FIELD_SIZE), BorderLayout.EAST);
        inputs.add(group_3);
        inputs.add(Box.createRigidArea(new Dimension(0, 5)));
        
        JPanel group_4 = new JPanel();
        group_4.setLayout(new BorderLayout());
        group_4.add(new JLabel("EAT"), BorderLayout.WEST);
        group_4.add(new JTextField(FIELD_SIZE), BorderLayout.EAST);
        inputs.add(group_4);
        inputs.add(Box.createRigidArea(new Dimension(0, 5)));
        
        JPanel group_5 = new JPanel();
        group_5.setLayout(new BorderLayout());
        group_5.add(new JLabel("EAT_PR"), BorderLayout.WEST);
        group_5.add(new JTextField(FIELD_SIZE), BorderLayout.EAST);
        inputs.add(group_5);
        inputs.add(Box.createRigidArea(new Dimension(0, 5)));
        
        JPanel group_6 = new JPanel();
        group_6.setLayout(new BorderLayout());
        group_6.add(new JLabel("EAT_BRANCH"), BorderLayout.WEST);
        group_6.add(new JTextField(FIELD_SIZE), BorderLayout.EAST);
        inputs.add(group_6);
        inputs.add(Box.createRigidArea(new Dimension(0, 5)));
        
        JPanel group_7 = new JPanel();
        group_7.setLayout(new BorderLayout());
        group_7.add(new JLabel("TEST_CATEGORY"), BorderLayout.WEST);
        group_7.add(new JTextField(FIELD_SIZE), BorderLayout.EAST);
        inputs.add(group_7);
        inputs.add(Box.createRigidArea(new Dimension(0, 5)));
        
        JPanel group_8 = new JPanel();
        group_8.setLayout(new BorderLayout());
        group_8.add(new JLabel("SERVER_BUILD"), BorderLayout.WEST);
        group_8.add(new JTextField(FIELD_SIZE), BorderLayout.EAST);
        inputs.add(group_8);
        
        left_panel.add(inputs);

        JPanel right_panel = new JPanel();
        right_panel.setLayout(new BoxLayout(right_panel, BoxLayout.PAGE_AXIS));
        right_panel.setPreferredSize(new Dimension(300, 300));
        
        start = new JButton("Start");
        start.setMinimumSize(new Dimension(300, 30));
        start.setMaximumSize(new Dimension(300, 30));
        start.setAlignmentX(Component.CENTER_ALIGNMENT);
        right_panel.add(start);
        
        right_panel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        JTextArea output_log = new JTextArea(5, 10);
        output_log.setLineWrap(true);
        right_panel.add(output_log);

        add(left_panel);
        add(right_panel);
    }
}