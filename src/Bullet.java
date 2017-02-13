import java.awt.Image;
import java.awt.Rectangle;

abstract class Bullet{
	protected int x,y,width,height,speed,ax,ay,angle,lifetime;
	protected Thread thread;
	protected boolean dead;
	
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
	
	public void setSpeed(int speed){
		this.speed=speed;
	}
	
	public Rectangle getBounds(){
		return new Rectangle(x,y,width,height);
	}
	public abstract Image getImage();
}