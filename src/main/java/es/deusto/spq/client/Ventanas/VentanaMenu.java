package es.deusto.spq.client.Ventanas;

import javax.swing.*;

import es.deusto.spq.client.PictochatntClient;
import es.deusto.spq.pojo.GetRoomData;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class VentanaMenu extends JFrame{


	private static final long serialVersionUID = -3703712434748598804L;
	public JPanel mainPanel, leftPanel, datos;
	public JList<String> lista;
	private JScrollPane scrollPane;
	public JButton bCrear;
	public JButton bConectar;
	public JButton bSalir, Aceptar, Cancelar;
	public JButton bRefrescar;
	private JLabel titleLabel;
	private JTextField password;
	private ArrayList<GetRoomData> activeRooms;
	private JButton bPerfil;
	
	public static VentanaMenu ventanaMenu;
	
	public VentanaMenu() {
		ventanaMenu = this;

        this.setTitle("Menu principal");
        //this.setSize(600, 400);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        

        
        //-----------------------------------------------------------------
        //TODO contener en una función aparte para poder usar más adelante
        DefaultListModel<String> tp = new DefaultListModel<>();

        lista = new JList<String>();
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
        leftPanel.setLayout(new GridLayout(6, 1));
        leftPanel.setPreferredSize(new Dimension(200, 0));
        
        titleLabel = new JLabel("Menu principal");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        leftPanel.add(titleLabel);
        
        datos = new JPanel();
        JLabel Nsala = new JLabel("Nombre de la sala");
		JTextField Ns = new JTextField(10);
		
		
		
		
		refreshRooms();
		
		
		Aceptar = new JButton("Aceptar");
		Aceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Cuando se cierre esta ventana mandar los datos de la sala al servidor 
				if(Ns.getText().length()>0) {
					//TODO Conectar automáticamente
					datos.setVisible(false);
					if (PictochatntClient.createRoom(Ns.getText(), password.getText())) {
						VentanaChat VChat = new VentanaChat();
						VChat.setVisible(true);
						VChat.setRoomName(Ns.getText());
						dispose();
					}else {
						JOptionPane.showMessageDialog(null, "Error, sala no creada",">:(",JOptionPane.ERROR_MESSAGE);
					}
					Ns.setText("");
					
				}else {
					JOptionPane.showMessageDialog(null, "La sala tiene que tener nombre para ser creada",">:(",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		Cancelar = new JButton("cancelar");
		Cancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				datos.setVisible(false);				
			}
		});
		
		JLabel passwordL = new JLabel("Contraseña");
		password = new JTextField(10);
		datos.add(Nsala);
		datos.add(Ns);
		datos.add(passwordL);
		datos.add(password);
		datos.add(Aceptar);
		datos.add(Cancelar);		
		        
        datos.setVisible(false);
        mainPanel.add(datos, BorderLayout.SOUTH);
        

        bCrear = new JButton("Crear");
        bConectar = new JButton("Conectar");
        bSalir = new JButton("Salir");
        bRefrescar = new JButton("Refrescar Salas");
        bCrear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //System.out.println("Button 1 clicked");
            	datos.setVisible(true);
            	
            }
        });
        bPerfil = new JButton("Perfil");
        bPerfil.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //System.out.println("Button 1 clicked");
            	dispose();
                VentanaPerfil VPerfil = new VentanaPerfil();
                VPerfil.setVisible(true);
            	
            }
        });
        bConectar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if(lista.getSelectedValue()!=null) {
            		if (activeRooms.get(lista.getSelectedIndex()).isPrivate()) {
            			// String pass = JOptionPane.showInputDialog(null, "Contraseña");
                    	//TODO
            			/*
            			if (!PictochatntClient.conectar()) {
            				return;
            			}
            			*/
                    	
                    }
            		//dispose();
                    //VentanaChat VChat = new VentanaChat();
                    //VChat.setVisible(true);
            	}
            }
        });
        bSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if (PictochatntClient.logout()) {
            		dispose();
                    VentanaLogin VLogin = new VentanaLogin();
                    VLogin.setVisible(true);
            	}else {
            		JOptionPane.showMessageDialog(null, "no se ha podido desconectar", "Error", JOptionPane.ERROR_MESSAGE);
            	}

            }
        });
        
        bRefrescar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	refreshRooms();
            }
        });
        leftPanel.add(bCrear);
        
        
        leftPanel.add(bPerfil);
        leftPanel.add(bConectar);
        leftPanel.add(bSalir);
        leftPanel.add(bRefrescar);
        

        getContentPane().add(mainPanel, BorderLayout.CENTER);
        getContentPane().add(leftPanel, BorderLayout.WEST);
        
      
        this.setVisible(true);
    }
    
    private void refreshRooms(){
    	
    	DefaultListModel<String> model = (DefaultListModel<String>) lista.getModel();
		model.removeAllElements();
		activeRooms = PictochatntClient.getActiveRooms();
		for (GetRoomData room : activeRooms) {
			model.addElement(room.getNameRoom());
		}
		
    }
	
    public static void main(String[] args) {
        new VentanaMenu();
        //v.bCrear.doClick();
    }
}