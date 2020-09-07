package ciui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;


public class PanelMain extends JPanel{
    
    private final int FIELD_SIZE=15;
    private final String[] options = {"Specific Pull Request", "All Pull Requests"};
    private JLabel msg_label;
    private JButton start;
    private JRadioButton rb_pr, rb_all;
    private JTextField at_field, program_field;
    private JTextArea output_log;
    
    public PanelMain(){
        setLayout(new FlowLayout(FlowLayout.LEADING, 10, 20));
        
        JPanel left_panel = new JPanel();
        left_panel.setLayout(new BoxLayout(left_panel, BoxLayout.Y_AXIS));
        
        JPanel label_panel = new JPanel();
        label_panel.setLayout(new BorderLayout());
        msg_label = new JLabel();
        msg_label.setFont(new Font("Helvetica", Font.PLAIN, 14));
        label_panel.add(msg_label);
        left_panel.add(label_panel);
         
        JPanel radio_panel = new JPanel();
        
        rb_pr = new JRadioButton(options[0]);
        rb_all = new JRadioButton(options[1]);    
        rb_pr.setBounds(75,50,100,30);    
        rb_all.setBounds(75,100,100,30);   
        rb_all.setSelected(true);
        
        ButtonGroup bg = new ButtonGroup();    
        bg.add(rb_pr);
        bg.add(rb_all);    
        
        radio_panel.add(rb_pr);
        radio_panel.add(rb_all);
        
        left_panel.add(radio_panel);
        
        JPanel inputs = new JPanel();
        CardLayout cl = new CardLayout();
        inputs.setLayout(cl);
        
        JPanel inputs_pr = new JPanel();
        inputs_pr.setLayout(new BoxLayout(inputs_pr, BoxLayout.Y_AXIS));
      
        JPanel group_1 = new JPanel();
        group_1.setLayout(new BorderLayout());
        group_1.add(new JLabel("PROGRAM"), BorderLayout.WEST);
        
        group_1.add(new JTextField(FIELD_SIZE), BorderLayout.EAST);
        inputs_pr.add(group_1);
        inputs_pr.add(Box.createRigidArea(new Dimension(0, 5)));
         
        JPanel group_2 = new JPanel();
        group_2.setLayout(new BorderLayout());
        group_2.add(new JLabel("PROGRAM_PR"), BorderLayout.WEST);
        group_2.add(new JTextField(FIELD_SIZE), BorderLayout.EAST);
        inputs_pr.add(group_2);
        inputs_pr.add(Box.createRigidArea(new Dimension(0, 5)));
        
        JPanel group_3 = new JPanel();
        group_3.setLayout(new BorderLayout());
        group_3.add(new JLabel("PROGRAM_BRANCH"), BorderLayout.WEST);
        group_3.add(new JTextField(FIELD_SIZE), BorderLayout.EAST);
        inputs_pr.add(group_3);
        inputs_pr.add(Box.createRigidArea(new Dimension(0, 5)));
        
        JPanel group_4 = new JPanel();
        group_4.setLayout(new BorderLayout());
        group_4.add(new JLabel("AT"), BorderLayout.WEST);
        group_4.add(new JTextField(FIELD_SIZE), BorderLayout.EAST);
        inputs_pr.add(group_4);
        inputs_pr.add(Box.createRigidArea(new Dimension(0, 5)));
        
        JPanel group_5 = new JPanel();
        group_5.setLayout(new BorderLayout());
        group_5.add(new JLabel("AT_PR"), BorderLayout.WEST);
        group_5.add(new JTextField(FIELD_SIZE), BorderLayout.EAST);
        inputs_pr.add(group_5);
        inputs_pr.add(Box.createRigidArea(new Dimension(0, 5)));
        
        JPanel group_6 = new JPanel();
        group_6.setLayout(new BorderLayout());
        group_6.add(new JLabel("AT_BRANCH"), BorderLayout.WEST);
        group_6.add(new JTextField(FIELD_SIZE), BorderLayout.EAST);
        inputs_pr.add(group_6);
        inputs_pr.add(Box.createRigidArea(new Dimension(0, 5)));
        
        JPanel group_7 = new JPanel();
        group_7.setLayout(new BorderLayout());
        group_7.add(new JLabel("TEST_CATEGORY"), BorderLayout.WEST);
        group_7.add(new JTextField(FIELD_SIZE), BorderLayout.EAST);
        inputs_pr.add(group_7);
        inputs_pr.add(Box.createRigidArea(new Dimension(0, 5)));
        
        JPanel group_8 = new JPanel();
        group_8.setLayout(new BorderLayout());
        group_8.add(new JLabel("PROGRAM_BUILD"), BorderLayout.WEST);
        group_8.add(new JTextField(FIELD_SIZE), BorderLayout.EAST);
        inputs_pr.add(group_8);
       
        
        inputs.add(inputs_pr, "pr");

        JPanel inputs_all = new JPanel();
        inputs_all.setLayout(new BoxLayout(inputs_all, BoxLayout.Y_AXIS));
      
        JPanel group_9 = new JPanel();
        group_9.setLayout(new BorderLayout());
        group_9.add(new JLabel("AT"), BorderLayout.WEST);
        at_field = new JTextField(FIELD_SIZE);
        group_9.add(at_field, BorderLayout.EAST);
        inputs_all.add(group_9);
        inputs_all.add(Box.createRigidArea(new Dimension(0, 5)));
         
        JPanel group_10 = new JPanel();
        group_10.setLayout(new BorderLayout());
        group_10.add(new JLabel("PROGRAM"), BorderLayout.WEST);
        program_field = new JTextField(FIELD_SIZE);
        group_10.add(program_field, BorderLayout.EAST);
        inputs_all.add(group_10);
        
        inputs_all.add(Box.createRigidArea(new Dimension(0, group_1.getPreferredSize().height*8)));
        inputs.add(inputs_all, "all");
        
        //Show PR card
        cl.show(inputs, "all");
        
        left_panel.add(inputs);
        left_panel.add(Box.createRigidArea(new Dimension(0, 32)));
        
        add(left_panel);

        start = new JButton("Start");
        start.setMinimumSize(new Dimension(300, 30));
        start.setMaximumSize(new Dimension(300, 30));
        start.setAlignmentX(Component.CENTER_ALIGNMENT);
        left_panel.add(start);
        
        JPanel right_panel = new JPanel();
        right_panel.setLayout(new BoxLayout(right_panel, BoxLayout.Y_AXIS));
        right_panel.setPreferredSize(new Dimension(300, 300));
            
        output_log = new JTextArea(5, 10);
        output_log.setLineWrap(true);
        output_log.setEditable(false);
        JScrollPane scroll = new JScrollPane(output_log);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        right_panel.add(scroll);

        add(right_panel);
        
        //Listeners
        rb_pr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(inputs, "pr");
                
            }
        });
        
        rb_all.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(inputs, "all");
            }
        });
    }
    
    public void addActionListener(ActionListener lis) {
        start.addActionListener(lis);
    }
    
    public Map getParameters() {
        Map<String, String> map = new HashMap<>();
        
        if(rb_pr.isSelected()) {
        
        }else{
            map.put("AT", at_field.getText());
            map.put("PROGRAM", program_field.getText());
        }
        return map;
    }
    
    public Commands getCommand() {
        Commands command = null;
        
        if(rb_pr.isSelected()) {
            
        }else{
            command = Commands.ALL;
        }
        return command;
    }
    
    public void successMessage(String text) {
        msg_label.setForeground(new Color(13,169,13));
        msg_label.setText(text);
    }
    
    public void failureMessage(String text) {
        msg_label.setForeground(Color.RED);
        msg_label.setText(text);
    }
    
    public void appendOutputLog(String text) {
        output_log.setText(output_log.getText()+text);
    }
}