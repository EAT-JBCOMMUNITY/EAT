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
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;


public class PanelMain extends JPanel{
    
    private final int FIELD_SIZE=15;
    private final String[] options = {"Specific Pull Request", "All Pull Requests"};
    private JLabel msg_label;
    private JButton start;
    private JRadioButton rb_pr, rb_all;
    private JTextField program_field, program_pr_field, program_branch_field, at_field, at_pr_field, at_branch_field, at_test_category_field;
    private JTextField program_all_field, at_all_field;
    private JTextArea output_log;
    private JComboBox program_build_combo;
    
    public PanelMain(){
        setLayout(new FlowLayout(FlowLayout.LEADING, 10, 10));
        
        JPanel left_panel = new JPanel();
        left_panel.setLayout(new FlowLayout(FlowLayout.LEADING, 10, 20));
        DefaultListModel list_model = new DefaultListModel<>();
        for(String option : options){
            list_model.addElement(option);
        }

        JList list = new JList<>(list_model);
        list.setVisibleRowCount(15);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane list_scrollpane = new JScrollPane(list);
        left_panel.add(list_scrollpane);
        add(left_panel);
        
        JPanel middle_panel = new JPanel();
        middle_panel.setLayout(new BoxLayout(middle_panel, BoxLayout.Y_AXIS));
        
        JPanel label_panel = new JPanel();
        label_panel.setLayout(new BorderLayout());
        msg_label = new JLabel();
        msg_label.setFont(new Font("Helvetica", Font.PLAIN, 14));
        label_panel.add(msg_label);
        middle_panel.add(label_panel);

        JPanel inputs = new JPanel();
        CardLayout cl = new CardLayout();
        inputs.setLayout(cl);
        
        JPanel inputs_pr = new JPanel();
        inputs_pr.setLayout(new BoxLayout(inputs_pr, BoxLayout.Y_AXIS));
      
        JPanel group_1 = new JPanel();
        group_1.setLayout(new BorderLayout());
        group_1.add(new JLabel("PROGRAM"), BorderLayout.WEST);
        program_field = new JTextField(FIELD_SIZE);
        group_1.add(program_field, BorderLayout.EAST);
        inputs_pr.add(group_1);
        inputs_pr.add(Box.createRigidArea(new Dimension(0, 5)));
         
        JPanel group_2 = new JPanel();
        group_2.setLayout(new BorderLayout());
        group_2.add(new JLabel("PROGRAM_PR"), BorderLayout.WEST);
        program_pr_field = new JTextField(FIELD_SIZE);
        group_2.add(program_pr_field, BorderLayout.EAST);
        inputs_pr.add(group_2);
        inputs_pr.add(Box.createRigidArea(new Dimension(0, 5)));
        
        JPanel group_3 = new JPanel();
        group_3.setLayout(new BorderLayout());
        group_3.add(new JLabel("PROGRAM_BRANCH "), BorderLayout.WEST);
        program_branch_field = new JTextField(FIELD_SIZE);
        group_3.add(program_branch_field, BorderLayout.EAST);
        inputs_pr.add(group_3);
        inputs_pr.add(Box.createRigidArea(new Dimension(0, 5)));
        
        JPanel group_4 = new JPanel();
        group_4.setLayout(new BorderLayout());
        group_4.add(new JLabel("AT"), BorderLayout.WEST);
        at_field = new JTextField(FIELD_SIZE);
        group_4.add(at_field, BorderLayout.EAST);
        inputs_pr.add(group_4);
        inputs_pr.add(Box.createRigidArea(new Dimension(0, 5)));
        
        JPanel group_5 = new JPanel();
        group_5.setLayout(new BorderLayout());
        group_5.add(new JLabel("AT_PR"), BorderLayout.WEST);
        at_pr_field = new JTextField(FIELD_SIZE);
        group_5.add(at_pr_field, BorderLayout.EAST);
        inputs_pr.add(group_5);
        inputs_pr.add(Box.createRigidArea(new Dimension(0, 5)));
        
        JPanel group_6 = new JPanel();
        group_6.setLayout(new BorderLayout());
        group_6.add(new JLabel("AT_BRANCH"), BorderLayout.WEST);
        at_branch_field = new JTextField(FIELD_SIZE);
        group_6.add(at_branch_field, BorderLayout.EAST);
        inputs_pr.add(group_6);
        inputs_pr.add(Box.createRigidArea(new Dimension(0, 5)));
        
        JPanel group_7 = new JPanel();
        group_7.setLayout(new BorderLayout());
        group_7.add(new JLabel("TEST_CATEGORY"), BorderLayout.WEST);
        at_test_category_field = new JTextField(FIELD_SIZE);
        group_7.add(at_test_category_field, BorderLayout.EAST);
        inputs_pr.add(group_7);
        inputs_pr.add(Box.createRigidArea(new Dimension(0, 5)));
        
        JPanel group_8 = new JPanel();
        group_8.setLayout(new BorderLayout());
        group_8.add(new JLabel("PROGRAM_BUILD"), BorderLayout.WEST);
        program_build_combo = new JComboBox();
        program_build_combo.addItem("true");
        program_build_combo.addItem("false");
        program_build_combo.setPreferredSize(program_field.getPreferredSize());
        group_8.add(program_build_combo, BorderLayout.EAST);
        inputs_pr.add(group_8);
       
        
        inputs.add(inputs_pr, "pr");

        JPanel inputs_all = new JPanel();
        inputs_all.setLayout(new BoxLayout(inputs_all, BoxLayout.Y_AXIS));
         
        JPanel group_9 = new JPanel();
        group_9.setLayout(new BorderLayout());
        group_9.add(new JLabel("PROGRAM"), BorderLayout.WEST);
        program_all_field = new JTextField(FIELD_SIZE);
        group_9.add(program_all_field, BorderLayout.EAST);
        inputs_all.add(group_9);
        inputs_all.add(Box.createRigidArea(new Dimension(0, 5)));
        
        JPanel group_10 = new JPanel();
        group_10.setLayout(new BorderLayout());
        group_10.add(new JLabel("AT"), BorderLayout.WEST);
        at_all_field = new JTextField(FIELD_SIZE);
        group_10.add(at_all_field, BorderLayout.EAST);
        inputs_all.add(group_10);
        inputs_all.add(Box.createRigidArea(new Dimension(0, 5)));
        
        inputs_all.add(Box.createRigidArea(new Dimension(0, group_1.getPreferredSize().height*8)));
        inputs.add(inputs_all, "all");
        
        //Show PR card
        cl.show(inputs, "pr");
        
        middle_panel.add(inputs);
        middle_panel.add(Box.createRigidArea(new Dimension(0, 32)));
        
        add(middle_panel);

        start = new JButton("Start");
        start.setMinimumSize(new Dimension(300, 30));
        start.setMaximumSize(new Dimension(300, 30));
        start.setAlignmentX(Component.CENTER_ALIGNMENT);
        middle_panel.add(start);
        
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
    }
    
    public void addActionListener(ActionListener lis) {
        start.addActionListener(lis);
    }
    
    public Map getParameters() {
        Map<String, String> map = new HashMap<>();
        
        if(rb_pr.isSelected()) {
            map.put("PROGRAM", program_field.getText());
            map.put("PROGRAM_PR", program_pr_field.getText());
            map.put("PROGRAM_BRANCH", program_branch_field.getText());
            map.put("AT", at_field.getText());
            map.put("AT_PR", at_pr_field.getText());
            map.put("AT_BRANCH", at_branch_field.getText());
            map.put("TEST_CATEGORY", at_test_category_field.getText());
            map.put("PROGRAM_BUILD", program_build_combo.getSelectedItem().toString());
        }else{
            map.put("PROGRAM", program_all_field.getText());
            map.put("AT", at_all_field.getText());
        }
        return map;
    }
    
    public Commands getCommand() {
        Commands command = null;
        
        if(rb_pr.isSelected()) {
            command = Commands.AT;
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