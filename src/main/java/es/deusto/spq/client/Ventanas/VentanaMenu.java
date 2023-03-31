package es.deusto.spq.client.Ventanas;

import javax.swing.*;

import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter.Red;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VentanaMenu extends JFrame{

private JTextField usernameField;
private JPanel mainPanel, leftPanel;
private JList<String> list;
private JScrollPane scrollPane;
private JButton button1, button2, button3;
private JLabel titleLabel;

public VentanaMenu() {


        this.setTitle("Menu principal");
        //this.setSize(600, 400);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        

        String[] data = {"prueba 1", "prueba 2", "prueba 3", "prueba 4", "prueba 5"};
        list = new JList<String>(data);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setFont(new Font("Arial", Font.PLAIN, 16));
        list.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        scrollPane = new JScrollPane(list);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        

        leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(4, 1));
        leftPanel.setPreferredSize(new Dimension(150, 0));
        

        titleLabel = new JLabel("Menu principal");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        leftPanel.add(titleLabel);

        button1 = new JButton("Crear");
        button2 = new JButton("Conectar");
        button3 = new JButton("Salir");
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //System.out.println("Button 1 clicked");
            }
        });
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                VentanaChat VChat = new VentanaChat();
                VChat.setVisible(true);
            }
        });
        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	System.exit(0);
            }
        });
        leftPanel.add(button1);
        leftPanel.add(button2);
        leftPanel.add(button3);
        

        this.add(mainPanel, BorderLayout.CENTER);
        this.add(leftPanel, BorderLayout.WEST);
        
      
        this.setVisible(true);
    }
    
        
	
    public static void main(String[] args) {
        new VentanaMenu();
    }
}