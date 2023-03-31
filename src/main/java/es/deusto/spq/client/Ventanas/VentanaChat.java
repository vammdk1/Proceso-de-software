package es.deusto.spq.client.Ventanas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Time;
import java.util.Arrays;

public class VentanaChat extends JFrame{
	
    int screenWidth;
    int screenHeigth;
    
    JTextField texto;
    JButton palante;
    
    private int x1, y1;

public VentanaChat() {
        setTitle("Chat In");
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
        
        
        JPanel pDibujo = new JPanel();
        pDibujo.setBounds(50,100,(int)(screenWidth*0.6),(int)(screenHeigth*0.85));
        pDibujo.setBackground(Color.WHITE);
        
        
        
        JTextArea taTexto = new JTextArea(8,40);
        taTexto.setBounds(1000,100,100,100);
        taTexto.setLocation(1000,100);
        taTexto.setFont(z);
        taTexto.setEditable(false);
        
        
        JScrollPane pTexto = new JScrollPane(taTexto);
        pTexto.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        pTexto.setBounds((int)(screenWidth*0.65),100,(int)(screenWidth*0.3),(int)(screenHeigth*0.75));
        pTexto.setBackground(Color.WHITE);
        
        JLabel title = new JLabel("Sala XXXX");
        title.setBounds(200, 200, 500, 75);
        title.setForeground(Color.black);
        title.setFont(x);
        title.setLocation(screenWidth/2 - 50, 25);
        
        texto = new JTextField();
        texto.setFont(x);
        texto.setBounds((int)(screenWidth*0.65),(int)(screenHeigth*0.87),(int)(screenWidth*0.25),(int)(screenHeigth*0.05));
        palante = new JButton("Ok");
        palante.setFont(x);
        palante.setBounds((int)(screenWidth*0.90),(int)(screenHeigth*0.87),(int)(screenWidth*0.05),(int)(screenHeigth*0.05));
        
        
        
        panel.add(title);
        panel.add(panel2);
        panel.add(pDibujo);
        panel.add(pTexto);
        panel.add(texto);
        panel.add(palante);
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
        
        pDibujo.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                x1 = e.getX();
                y1 = e.getY();
            }
        });
        
        pDibujo.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int x2 = e.getX();
                int y2 = e.getY();

                Graphics g = getGraphics();
                g.drawLine(x1, y1, x2, y2);

                x1 = x2;
                y1 = y2;
            }
        });
        
        palante.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				taTexto.append("(Time)(User) " + texto.getText() + "\n");
				texto.setText("");
				
			}
		});
    }

    public static void main(String[] args) {
        new VentanaChat();
    }
}