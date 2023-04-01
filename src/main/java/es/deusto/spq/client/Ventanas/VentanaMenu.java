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
private JPanel mainPanel, leftPanel, datos;
private JList<String> lista;
private JScrollPane scrollPane;
private JButton button1, button2, button3, button4;
private JLabel titleLabel;

public VentanaMenu() {


        this.setTitle("Menu principal");
        //this.setSize(600, 400);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        

        
        //-----------------------------------------------------------------
        //TODO contener en una función aparte para poder usar más adelante
        String[] data = {"prueba 1", "prueba 2", "prueba 3", "prueba 4", "prueba 5"};
        DefaultListModel<String> tp = new DefaultListModel<>();
        for(String i : data) {
        	tp.addElement(i);
        }
        lista = new JList<String>(data);
        lista.setModel(tp);
        lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lista.setFont(new Font("Arial", Font.PLAIN, 16));
        lista.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        //-----------------------------------------------------------------
        scrollPane = new JScrollPane(lista);
        
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setLayout(new GridLayout(2, 1));
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(5, 1));
        leftPanel.setPreferredSize(new Dimension(200, 0));
        
        titleLabel = new JLabel("Menu principal");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        leftPanel.add(titleLabel);
        
        datos = new JPanel();
        JLabel Nsala = new JLabel("Nombre de la sala");
		JTextField Ns = new JTextField(10);
		
		JButton Aceptar = new JButton("Aceptar");
		Aceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Cuando se cierre esta ventana mandar los datos de la sala al servidor 
				if(Ns.getText()!="" || Ns.getText()!=" ") {
					//TODO Conectar automáticamente
					JOptionPane.showMessageDialog(null, "Sala creada, Enviando datos al servidor", ":)", JOptionPane.ERROR_MESSAGE);
					//tp.addElement(Ns.getText());
					Ns.setText("");
					datos.setVisible(false);
					dispose();
				}else {
					JOptionPane.showMessageDialog(null, "La sala tiene que tener nombre para ser creada",">:(",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		JButton Cancelar = new JButton("cancelar");
		Cancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				datos.setVisible(false);				
			}
		});
		
		datos.add(Nsala);
		datos.add(Ns);
		datos.add(Aceptar);
		datos.add(Cancelar);
		
        
        datos.setVisible(false);
        mainPanel.add(datos, BorderLayout.SOUTH);
        

        button1 = new JButton("Crear");
        button2 = new JButton("Conectar");
        button3 = new JButton("Salir");
        button4 = new JButton("Referscar Salas");
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //System.out.println("Button 1 clicked");
            	datos.setVisible(true);
            	
            }
        });
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if(lista.getSelectedValue()!=null) {
            		JOptionPane.showMessageDialog(null, "Conectamdo a "+lista.getSelectedValue(),">:(",JOptionPane.ERROR_MESSAGE);
                	dispose();
                    /**
                    VentanaChat VChat = new VentanaChat();
                    VChat.setVisible(true);
                    **/
            	}
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
        leftPanel.add(button4);
        

        this.add(mainPanel, BorderLayout.CENTER);
        this.add(leftPanel, BorderLayout.WEST);
        
      
        this.setVisible(true);
    }
    
        
	
    public static void main(String[] args) {
        new VentanaMenu();
    }
}