import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Player {
	private int x,y,width=100,height=100,angle,imageAngle,remainingAmmo,ammo,weapon;
	private MainFrame main;
	private Image stand,shoot,image;
	private boolean shooting;
	public Player(MainFrame main){
		stand = Toolkit.getDefaultToolkit().getImage("Image/Player.gif");
		shoot = Toolkit.getDefaultToolkit().getImage("Image/PlayerAttack.png");
		image=stand;
		this.main=main;
		x=50;
		y=250;
		weapon=1;
		shooting=false;
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
	public int getAngle(){
		return angle;
	}
	public void setAngle(int x,int y){
		double a=(this.y+height/2)-y;
		double b=Math.sqrt(Math.pow((this.x+(width/2))-x, 2)+Math.pow((this.y+height/2)-y, 2));
		angle=(int)Math.toDegrees(Math.asin(a/b));
	}
	public void setAmmo(int n){
		ammo=n;
		
	}
	public int getAmmo(){
		return ammo;
	}
	
	public int getWeapon(){
		return weapon;
	}
	public void setWeapon(int weapon){
		this.weapon=weapon;
	}
	public void hide(){
		width=0;
		height=0;
		x=0;
		y=0;
	}
	public void show(){
		width=100;
		height=100;
	}
	public void setPostion(int x,int y){
		this.x=x;
		this.y=y;
	}
	
	
	public void setRemainingAmmo(int ammo){
		this.remainingAmmo=ammo;
	}
	
	public int getRemainingAmmo(){
		return remainingAmmo;
	}
	
	public Image getImage(){
		return image;
	}
	public Image getAttackImage(){
		return shoot;
	}
	
	public int getImageAngle(){
		return imageAngle;
	}
	
	public void setShootImage(){
		imageAngle=angle;
		image=shoot;
		shooting=true;
		Timer t = new Timer(200, new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
            	shooting=false;
        		image=stand;
            }
    });
		t.setRepeats(false);
		t.start();               
	}
	
	public boolean isShooting(){
		return shooting;
	}
}
