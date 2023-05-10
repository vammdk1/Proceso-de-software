package es.deusto.spq.client.Ventanas;

import javax.swing.*; 
import java.awt.*;
import java.awt.event.*;

public class VentanaPerfil extends JFrame{
	
    int screenWidth;
    int screenHeigth;
    
    JLabel nombre;
    JLabel Contraseña;
    JLabel username;
    JLabel password;
    JTable friends;
    JButton cerrarSesion;
    
    private int x1, y1;
    
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
        Font y = new Font("Serif", Font.BOLD, 100);
        Font x = new Font("Serif", Font.PLAIN, 35);
        Font z = new Font("Serif", Font.PLAIN, 25);
        
        
        JPanel pDatos = new JPanel();
        pDatos.setBounds(50,100,(int)(screenWidth*0.5),(int)(screenHeigth*0.85));
        pDatos.setBackground(Color.WHITE);
        
        
        JScrollPane pFriends = new JScrollPane(friends);
        pFriends.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        pFriends.setBounds((int)(screenWidth*0.55),100,(int)(screenWidth*0.4),(int)(screenHeigth*0.75));
        pFriends.setBackground(Color.WHITE);
        
        title = new JLabel("Perfil");
        title.setBounds(200, 200, 500, 75);
        title.setForeground(Color.black);
        title.setFont(x);
        title.setLocation(screenWidth/2 - 50, 25);
        
        panel.add(title);
        panel.add(panel2);
        panel.add(pDatos);
        panel.add(pFriends);
        setContentPane(panel);
        setLayout(null);
        setVisible(true);
        
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
        
    }
	
	public void setRoomName(String name) {
		title.setText(name);
	}

    public static void main(String[] args) {
        new VentanaPerfil();
    }
}