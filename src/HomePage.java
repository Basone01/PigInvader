import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HomePage extends JPanel{
	private Image background,pig,name,mainPig;
	private JLabel startButton;
	MainFrame main;
	public HomePage(MainFrame main){
		//Take Image Form File;
		background = Toolkit.getDefaultToolkit().getImage("Image/Stage2Background.png");
		pig=Toolkit.getDefaultToolkit().getImage("Image/Pig.png");
		name=Toolkit.getDefaultToolkit().getImage("Image/GameName.png");
		mainPig = Toolkit.getDefaultToolkit().getImage("Image/Player.gif");
		//Pointer of JFrame
		this.main=main;
		setSize(1024,600);
		setLayout(null);
		startButton = new JLabel(new ImageIcon("Image/PLAY.png"));
		startButton.setBounds(700,60,231,138);
		startButton.addMouseListener(new MouseAdapter(){
			
			public void mouseReleased(MouseEvent e){
				main.remove(main.homePage);
				main.stage=new Stage(main);
				main.add(main.stage);
				main.stage.repaint();
				main.stage.setFocusable(true);
				main.stage.requestFocusInWindow();
			}
		});
		
		add(startButton);
		
		
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(background, 0, 0, this.getWidth(),this.getHeight(),this);
		g.drawImage(pig, 400, 320, 350,200,this);
		g.drawImage(name, 100, 30, 400,200,this);
		g.drawImage(mainPig, 0, 260, 120, 130, this);
		
		

		
	}

	
}
