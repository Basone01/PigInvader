import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Obstacle{
	private int x,y,width,height;
	private Image image;
	public Obstacle(int x,int y,int width,int height){
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		image = null;
	}
	public Obstacle(ImageIcon icon,int x,int y,int width,int height){
		this(x,y,width,height);
		image = icon.getImage();
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
	public Rectangle getBounds(){
		return new Rectangle(x, y, width, height);
	}
	public Image getImage(){
		return image;
	}
	
	
	
	
}
