package es.deusto.spq.client.Ventanas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class VentanaRegistro extends JFrame{

private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField repPasswordField;
    private JButton loginButton;
    private JButton back;

public VentanaRegistro() {
        setTitle("Sign In");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
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
        Font y = new Font("Serif", Font.BOLD, 100);
        Font z = new Font("Serif", Font.BOLD, 60);
        Font x = new Font("Serif", Font.PLAIN, 35);      
       
        JLabel title = new JLabel("Pictochatnt");
        title.setBounds(200, 200, 500, 75);
        title.setForeground(Color.black);
        title.setFont(y);
        title.setLocation(525, 50);
        
        JLabel lCreate = new JLabel("Create Account");
        lCreate.setBounds(200, 200, 700, 75);
        lCreate.setForeground(Color.black);
        lCreate.setFont(z);
        lCreate.setLocation(550, 130);
       
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(200, 200, 300, 75);
        usernameLabel.setForeground(Color.black);
        usernameLabel.setFont(x);
        usernameLabel.setLocation(550, 200);
        usernameField = new JTextField();
        usernameField.setFont(x);
        usernameField.setBounds(55, 105, 400, 40);
        usernameField.setLocation(550, 275);
       
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(55, 145, 300, 75);
        passwordLabel.setForeground(Color.black);
        passwordLabel.setFont(x);
        passwordLabel.setLocation(550, 300);
        passwordField = new JPasswordField();
        passwordField.setBounds(55, 165, 400, 40);
        passwordField.setLocation(550, 375);
        
        JLabel repPasswordLabel = new JLabel("Repeat Password");
        repPasswordLabel.setBounds(55, 145, 300, 75);
        repPasswordLabel.setForeground(Color.black);
        repPasswordLabel.setFont(x);
        repPasswordLabel.setLocation(550, 425);
        repPasswordField = new JPasswordField();
        repPasswordField.setBounds(55, 165, 400, 40);
        repPasswordField.setLocation(550, 500);
       
        loginButton = new JButton("Create Account");
        loginButton.setBounds(55, 215, 200, 50);
        loginButton.setLocation(750, 600);
       
        back = new JButton("Back");
        back.setBounds(55, 215, 75, 50);
        back.setLocation(550, 600);
               
        panel.add(passwordLabel);
        panel.add(usernameLabel);
        panel.add(title);
        panel.add(lCreate);
        panel.add(usernameField);
        panel.add(passwordField);
        panel.add(repPasswordLabel);
        panel.add(repPasswordField);
        panel.add(loginButton);
        panel.add(back);
        panel.add(panel2);
       
        setContentPane(panel);
        setLayout(null);
        setVisible(true);
       
        back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				VentanaLogin VLogin= new VentanaLogin();
				VLogin.setVisible(true);				
			}
		});
        
        loginButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(passwordField.getPassword().length==0) {
					return;
				}
				if(Arrays.equals(passwordField.getPassword(),repPasswordField.getPassword())) {
					System.out.println("Contraseña Aceptada");
					dispose();
					VentanaMenu VMenu = new VentanaMenu();
					VMenu.setVisible(true);
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
				System.exit(0);
				
			}
		});
    }

    public static void main(String[] args) {
        new VentanaRegistro();
    }
}