package es.deusto.spq.client.Ventanas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class VentanaLogin extends JFrame{

private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField repPasswordField;
    private JButton next;
    private JButton createAccount;

public VentanaLogin() {
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
        Font x = new Font("Serif", Font.PLAIN, 35);
       
        JLabel title = new JLabel("Pictochatnt");
        title.setBounds(200, 200, 500, 75);
        title.setForeground(Color.black);
        title.setFont(y);
        title.setLocation(525, 50);
       
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(200, 200, 300, 75);
        usernameLabel.setForeground(Color.black);
        usernameLabel.setFont(x);
        usernameLabel.setLocation(550, 250);
        usernameField = new JTextField();
        usernameField.setFont(x);
        
        usernameField.setBounds(55, 105, 400, 40);
        usernameField.setLocation(550, 325);
       
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(55, 145, 300, 75);
        passwordLabel.setForeground(Color.black);
        passwordLabel.setFont(x);
        passwordLabel.setLocation(550, 350);
        passwordField = new JPasswordField();
        passwordField.setBounds(55, 165, 400, 40);
        passwordField.setLocation(550, 425);
        
        next = new JButton("Next");
        next.setBounds(55, 215, 75, 50);
        next.setLocation(900, 600);
       
        createAccount = new JButton("Create account");
        createAccount.setBounds(55, 215, 200, 50);
        createAccount.setLocation(550, 600);
               
        panel.add(passwordLabel);
        panel.add(usernameLabel);
        panel.add(title);
        panel.add(usernameField);
        panel.add(passwordField);
        panel.add(next);
        panel.add(createAccount);
        panel.add(panel2);
       
        setContentPane(panel);
        setLayout(null);
        setVisible(true);
        
        next.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(passwordField.getPassword().length==0) {
					return;
				}
				if(Arrays.equals(passwordField.getPassword(),repPasswordField.getPassword())) {
					System.out.println("Contraseña Aceptada");
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
        new VentanaLogin();
    }
}