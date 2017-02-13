import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

public class MainFrame extends JFrame {
	HomePage homePage;
	Stage stage;
	MainFrame main;
	public MainFrame(){
		main=this;
		setTitle("Pig Invader");
		setLayout(null);
		setSize(1024,600);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		homePage=new HomePage(this);
		add(homePage);
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e){
				if((e.getKeyCode()==KeyEvent.VK_ENTER||e.getKeyCode()==KeyEvent.VK_SPACE)&&stage==null){
					remove(homePage);
					stage=new Stage(main);
					add(stage);
					stage.repaint();
					stage.setFocusable(true);
					stage.requestFocusInWindow();
					
				}
			}
		});
		setVisible(true);
		setFocusable(true);
		
		
	
	}
	
	public static void main(String[] args){
		MainFrame main = new MainFrame();
		
	}
	
}
