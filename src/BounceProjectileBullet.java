import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.Timer;

public class BounceProjectileBullet extends Bullet implements Runnable{
	private int delayCount=0;
	MainFrame main;
	Player player;
	private Timer time;
	private Image bullet;
	private Image bomb;
	private Image image;
	public BounceProjectileBullet(MainFrame main,int angle,int speed){
		bullet = new ImageIcon("Image/BounceBullet.png").getImage();
		bomb = new ImageIcon("Image/Bomb2.gif").getImage();
		this.main=main;
		this.player=main.stage.player;
		dead=false;
		lifetime=7;
		width=20;
		height=20;
		this.speed=speed;
		x=player.getX()+50;
		y=player.getY()+player.getHeight()/2-height/2+20;
		this.angle=angle;
		image=bullet;
		thread=new Thread(this);
		thread.start();
		
		ax=(int) (speed*Math.cos(Math.toRadians(angle)));
		ay=(int) (speed*Math.sin(Math.toRadians(angle)));
		
		time = new Timer(80,new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				delayCount+=time.getDelay();
				if(delayCount>=1000*lifetime){
					remove();
				}
				if(inAir())
					ay-=1;
				else
					if(ax!=0&&delayCount%640==0){
						if(ax>=1)
							ax--;
						else
							ax++;
						
				}		
			}
		});
		
		time.start();
		
	}

	public void run() {
		while(!dead){
			this.x+=ax;
			this.y-=ay;
			
			for(int i=0;i<Stage.arrayEnemy.size();i++){
				if(Stage.arrayEnemy.get(i).getBounds().intersects(getBounds())){
					Stage.arrayEnemy.get(i).die();
					image=bomb;
					width=120;
					height=100;
					x-=width/2;
					y-=height/2;
					dead=true;
					bomb.flush();
					try {
						thread.sleep(300);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					remove();
					break;
				}
			}
			if(Math.abs(ax)>Math.abs(ay)){
				for(int i=0;i<Stage.arrayObstacle.size();i++){
					if(isRightCollision()){
							ax*=-0.9;
							x=Stage.arrayObstacle.get(i).getX()-width;
	
					}else if(isLeftCollision()){
							ax*=-0.9;
							x=Stage.arrayObstacle.get(i).getX()+Stage.arrayObstacle.get(i).getWidth();
					}else  if(isBottomCollision()){
						y=Stage.arrayObstacle.get(i).getY()-height;
						ay*=-0.7;
					
					}else if(isTopCollision()){
						y=Stage.arrayObstacle.get(i).getY()+Stage.arrayObstacle.get(i).getHeight();
						ay*=-0.7;
					
					}
				}
			}else{
				for(int i=0;i<Stage.arrayObstacle.size();i++){
					if(getBoundsDown().intersects(Stage.arrayObstacle.get(i).getBounds())){
						y=Stage.arrayObstacle.get(i).getY()-height;
						ay*=-0.7;				
					}else if(getBoundsUp().intersects(Stage.arrayObstacle.get(i).getBounds())){
						y=Stage.arrayObstacle.get(i).getY();
						ay*=-0.7;
					
					}else if(getBoundsLeft().intersects(Stage.arrayObstacle.get(i).getBounds())){
							ax*=-0.9;
							x=Stage.arrayObstacle.get(i).getX()+Stage.arrayObstacle.get(i).getWidth();
					}else if(getBoundsRight().intersects(Stage.arrayObstacle.get(i).getBounds())){
							ax*=-0.9;
							x=Stage.arrayObstacle.get(i).getX()-width;

				}
				}
			}
			try {
				thread.sleep(16);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public Rectangle getBounds(){
		return new Rectangle(x+1,y+1,width-1,height-1);
	}
	
	public Rectangle getBoundsUp(){
		return new Rectangle(x+(width/2),y,1,1);
	}
	
	public Rectangle getBoundsDown(){
		return new Rectangle(x+(width/2)-2,y+height-1,4,1);
	}
	
	public Rectangle getBoundsLeft(){
		return new Rectangle(x-1, y+(height/2), 1, 1);
	}
	
	public Rectangle getBoundsRight(){
		return new Rectangle(x+width, y+(height/2), 1, 1);
	}
	
	public void remove(){
		time.stop();
		Stage.arrayBullet.remove(this);
		thread.stop();
		
	}
	
	private boolean inAir(){
		for(int i=0;i<Stage.arrayObstacle.size();i++)
			if(new Rectangle(x+15,y+height-1,1,2).intersects(Stage.arrayObstacle.get(i).getBounds())){
				return false;
			}
		return true;
	}
	
	private boolean isLeftCollision(){
		return getBoundsLeft().intersects(Stage.arrayObstacle.get(i).getBounds());
	}
	private boolean isRightCollision(){
		return getBoundsRight().intersects(Stage.arrayObstacle.get(i).getBounds());
	}
	private boolean isTopCollision(){
		return getBoundsTop().intersects(Stage.arrayObstacle.get(i).getBounds());
	}
	private boolean isBottomCollision(){
		return getBoundsBottom().intersects(Stage.arrayObstacle.get(i).getBounds());
	}
	
	public Image getImage(){
		return image;
	}
	
	public void stopThread(){
		this.thread.stop();
	}
}

	
