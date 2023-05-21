package es.deusto.spq.client.Ventanas;

import javax.swing.*;

import es.deusto.spq.client.PictochatntClient;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class VentanaPerfil extends JFrame{
	
    int screenWidth;
    int screenHeigth;
    
    JLabel nombre;
    JLabel Contraseña;
    JLabel username;
    JLabel password;
    JList<String> friends;
    JButton cerrarSesion;
    ArrayList<String> friendList;
    JLabel lUser;
    JLabel title;

public VentanaPerfil() {
        setTitle("Profile");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        Toolkit tk = Toolkit.getDefaultToolkit();
        screenWidth = tk.getScreenSize().width;
        screenHeigth = tk.getScreenSize().height;
        setUndecorated(true);
        

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image image = new ImageIcon("src/main/java/es/deusto/spq/client/Imagenes/Wallpaper.jpeg").getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        
        JPanel panel2 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image image = new ImageIcon("src/main/java/es/deusto/spq/client/Imagenes/closeImage.png").getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel2.setOpaque(false);
        panel2.setBounds(20,20,50,50);
        // Font y = new Font("Serif", Font.BOLD, 100);
        Font x = new Font("Serif", Font.PLAIN, 35);
        // Font z = new Font("Serif", Font.PLAIN, 25);
        
        
        JLabel lNombre = new JLabel("Nickname");
        lNombre.setBounds((int)(screenWidth*0.1),(int)(screenHeigth*0.5),500,50);
        lNombre.setFont(x);
        
        lUser = new JLabel("aaaa");
        //TODO lUser.setText(PictochatntClient.getUser());
        lUser.setBounds((int)(screenWidth*0.3),(int)(screenHeigth*0.5),500,50);
        lUser.setFont(x);
        
        JLabel lAmigos = new JLabel("Friend List");
        lAmigos.setBounds((int)(screenWidth*0.55),(int)(screenHeigth*0.1),500,50);
        lAmigos.setFont(x);
        
        JButton bAnadirAmigo = new JButton("Add");
        bAnadirAmigo.setBounds((int)(screenWidth*0.65),(int)(screenHeigth*0.85),(int)(screenWidth*0.2),(int)(screenHeigth*0.07));
        bAnadirAmigo.setFont(x);
        
        friends = new JList<String>();
        DefaultListModel<String> tp = new DefaultListModel<>();
        friends.setModel(tp);
        JScrollPane pFriends = new JScrollPane(friends);
        pFriends.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        pFriends.setBounds((int)(screenWidth*0.55),(int)(screenHeigth*0.2),(int)(screenWidth*0.4),(int)(screenHeigth*0.65));
        pFriends.setBackground(Color.WHITE);
        
        title = new JLabel("Perfil");
        title.setBounds(200, 200, 500, 75);
        title.setForeground(Color.black);
        title.setFont(x);
        title.setLocation(screenWidth/2 - 50, 25);
        
        panel.add(title);
        panel.add(panel2);
        panel.add(lNombre);
        panel.add(bAnadirAmigo);
        panel.add(lUser);
        panel.add(lAmigos);
        panel.add(pFriends);
        setContentPane(panel);
        setLayout(null);
        setVisible(true);
        
        bAnadirAmigo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String s = JOptionPane.showInputDialog("Nombre del amigo");
				System.out.println(s);
				
				if (PictochatntClient.addFriend(s)) {
					refrescar();
				}else {
					JOptionPane.showMessageDialog(null, "El amigo no esta registrado", "Error", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
        
        panel2.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				VentanaMenu vMenu = new VentanaMenu();
				vMenu.setVisible(true);
				
			}
		});
        
        refrescar();
        
    }

	public void refrescar() {
		lUser.setText(PictochatntClient.getUsername());
		DefaultListModel<String> model = (DefaultListModel<String>) friends.getModel();
		model.removeAllElements();
		friendList = PictochatntClient.getFriendList();
		if (friendList == null) {
			return;
		}
		for (String s : friendList) {
			model.addElement(s);
		}
		
	}

    public static void main(String[] args) {
        new VentanaPerfil();
    }
}