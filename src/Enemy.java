import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Enemy implements Runnable{
	private Image image;
	private int x,y,width,height;
	private Thread thread;
	Stage stage;
	boolean dead=false;
	MainFrame main;
	public Enemy(MainFrame main,int x,int y){
		image= new ImageIcon("Image/NormalPig.gif").getImage();
		this.main=main;
		width=50;
		height=50;
		this.x=x;
		this.y=y;
		this.thread=new Thread(this);
		
	}
	@Override
	public void run() {
		while(width>0){
			x+=2;
			y+=2;
			width-=4;
			height-=4;
			try {
				Thread.sleep(16);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Stage.arrayEnemy.remove(this);
		if(Stage.arrayEnemy.size()==0){
			main.stage.win();
		}
		
	}
	public void die(){
		if(!dead){
			dead=true;
			thread.start();
		}
		
	}
	public void setPosition(int x,int y){
		this.x=x;
		this.y=y;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}
	public void setWidth(int width){
		this.width=width;
	}
	public void setHeight(int height){
		this.height=height;
	}
	public Rectangle getBounds(){
		return new Rectangle(x,y,width,height);
	}
	
	public Image getImage(){
		return image;
	}
	
	public void stopThread(){
		this.thread.stop();
	}
}
