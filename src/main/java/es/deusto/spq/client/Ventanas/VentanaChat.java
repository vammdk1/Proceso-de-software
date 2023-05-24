package es.deusto.spq.client.Ventanas;

import javax.swing.*;

import org.apache.commons.math3.analysis.solvers.RegulaFalsiSolver;

import es.deusto.spq.client.PictochatntClient;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VentanaChat extends JFrame{
	
	private static final long serialVersionUID = 6788025475843454961L;
	private static final int size = 512;
	int screenWidth;
    int screenHeigth;
    
    boolean erasing = false;
    double ratio;
    
    JTextField texto;
    JButton palante;
    
    JPanel pDibujo;
    
    JLabel title;
    JTextArea taTexto;
    BufferedImage bImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
    
    public static VentanaChat ventanaChat;

    public VentanaChat() {
		ventanaChat = this;
        setTitle("Chat In");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        Toolkit tk = Toolkit.getDefaultToolkit();
        screenWidth = tk.getScreenSize().width;
        screenHeigth = tk.getScreenSize().height;
        setUndecorated(true);
        

        JPanel panel = new JPanel() {
			private static final long serialVersionUID = -4560585749615976907L;
			@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image image = new ImageIcon("src/main/java/es/deusto/spq/client/Imagenes/Wallpaper.jpeg").getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        
        JPanel panel2 = new JPanel() {
			private static final long serialVersionUID = -506574610894438989L;
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
        Font z = new Font("Serif", Font.PLAIN, 25);
        
        
        pDibujo = new JPanel() {
	        /**
			 * 
			 */
			private static final long serialVersionUID = -7249652798292608268L;

			@Override
	        protected void paintComponent(Graphics g) {
	        	Rectangle r = g.getClipBounds();
	        	Graphics2D g2d = (Graphics2D) g;
	    		g2d.drawImage(bImage, 0, 0, (int) r.getWidth(), (int) r.getHeight(), Color.WHITE, null);
	    		pDibujo.repaint();
	        }
        };
        
        double w = screenWidth*0.6;
        double h = screenHeigth*0.85;
        
        double s;
        
        if (w > h) {
        	s = (int) h;
        } else {
        	s = (int) w;
        }
        
        ratio = size / s;
        pDibujo.setBounds(50 + (int) (w/2 - s/2), 100 + (int) (h/2 - s/2), (int) s, (int) s);
        pDibujo.setBackground(Color.WHITE);
        
        
        
        taTexto = new JTextArea(8,40);
        taTexto.setBounds(1000,100,100,100);
        taTexto.setLocation(1000,100);
        taTexto.setFont(z);
        taTexto.setEditable(false);
        
        
        JScrollPane pTexto = new JScrollPane(taTexto);
        pTexto.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        pTexto.setBounds((int)(screenWidth*0.65),100,(int)(screenWidth*0.3),(int)(screenHeigth*0.75));
        pTexto.setBackground(Color.WHITE);
        
        title = new JLabel("Sala XXXX");
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
				PictochatntClient.leaveRoom();
			}
		});
        
        pDibujo.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            	if (e.getButton() == 1) {
            		erasing = false;
                    // Botón izquierdo del mouse (pintar en negro)
                } else if (e.getButton() == 3) {
                    // Botón derecho del mouse (pintar en blanco)
                	erasing = true;
                }
            }
        });
        
        pDibujo.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
            	
                int x = e.getX();
                int y = e.getY();
                
                x = (int) (x * ratio);
                y = (int) (y * ratio);
                
                if (x < 0 || x >= size || y < 0 || y >= size) {
                	return;
                }
                
                System.out.println(x + " ============= " + y);
                
                PictochatntClient.paint(x, y, erasing);
            }
        });;
        
        palante.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//taTexto.append("(Time)(User) " + texto.getText() + "\n");
				
				PictochatntClient.sendMessage(texto.getText());
				texto.setText("");
			}
		});
        
        
    }
    
    /**
     * This method adds a message to the message panel
     * @param user
     * @param text
     * @param timeStamp
     */
    public void addMessage(String user, String text, long timeStamp) {
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    	Date date = new Date(timeStamp);
    	taTexto.append("" + sdf.format(date) + " (" + user + ") " + text + "\n");
    }
	
	public void setRoomName(String name) {
		title.setText(name);
	}
	
	/**
	 * This method gets the current drawing when a new user enters the room
	 * @param image
	 */
	public void getImageHistory(byte[] image) {
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				if (image[y*size+x] == 0) {
					bImage.setRGB(x, y, 0xFFFFFF);
				} else {
					bImage.setRGB(x, y, 0x000000);
				}
			}
		}
		
		pDibujo.repaint();
	}
	
	/**
	 * This method sets a pixel to a determined color given his position
	 * @param x
	 * @param y
	 * @param erase
	 */
	private void setPixel(int x, int y, boolean erase) {
		if (!erase) {
			bImage.setRGB(x, y, 0x000000);
		} else {
			bImage.setRGB(x, y, 0xFFFFFF);
		}
	}
	
	/**
	 * This method paints on the position given
	 * @param x
	 * @param y
	 * @param erase
	 */
	public void paint(int x, int y, boolean erase) {
		int startX = x - 1;
		int startY = y - 1;
		int endX = x + 1;
		int endY = y + 1;
		
		if (startX < 0) {
			startX = 0;
		}
		
		if (startY < 0) {
			startY = 0;
		}
		
		if (endX >= size) {
			endX = size - 1;
		}
		
		if (endY >= size) {
			endY = size - 1;
		}
		
		for (int pX = startX; pX <= endX; pX++) {
			for (int pY = startY; pY <= endY; pY++) {
				this.setPixel(pX, pY, erase);
			}
		}
		
		pDibujo.repaint();
	}

    public static void main(String[] args) {
        new VentanaChat();
    }
}