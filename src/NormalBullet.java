import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class NormalBullet extends Bullet implements Runnable{
	MainFrame main;
	Player player;
	private Image image = Toolkit.getDefaultToolkit().getImage("Image/BounceBullet.png");;
	public NormalBullet(){}
	public NormalBullet(MainFrame main,int angle){
		
		this.main=main;
		this.player=main.stage.player;
		lifetime=3;
		width=10;
		height=10;
		speed=15;
		this.angle=angle;
		x=player.getX()+width/2+40;
		y=player.getY()+player.getHeight()/2-height/2+20;
		ax=(int) (speed*Math.cos(Math.toRadians(angle)));
		ay=(int) (speed*Math.sin(Math.toRadians(angle)));
		thread=new Thread(this);
		thread.start();

	}
	@Override
	public void run() {
		while(!dead){
			this.x+=ax;
			this.y-=ay;
			if(x>1024||y<-30)remove();
			for(int i = 0;i<Stage.arrayEnemy.size();i++){
				if(getBounds().intersects(Stage.arrayEnemy.get(i).getBounds())){
					Stage.arrayEnemy.get(i).die();
					
					
				}
			}
			for(int i = 0;i<Stage.arrayObstacle.size();i++){
				if(getBounds().intersects(Stage.arrayObstacle.get(i).getBounds())){
					remove();
					
				}
			}
			try {
				thread.sleep(16);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
		
	}
	public void remove(){
		try {
			thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dead=true;
		Stage.arrayBullet.remove(this);
		
	}
	
	public Image getImage(){
		return image;
		
	}
	
	public void stopThread(){
		this.thread.stop();
	}
	
}
