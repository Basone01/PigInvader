import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class WeaponIcon extends JLabel {
	private int weapon;
	private ImageIcon activated,deactivated;
	public WeaponIcon(ImageIcon Deactivated,ImageIcon Activated){
		super(Deactivated);
		this.activated=Activated;
		this.deactivated=Deactivated;
		super.setBounds(0, 0, 64, 64);
	}
	
	public WeaponIcon(ImageIcon Deactivated,ImageIcon Activated,int x,int y,int width ,int height){
		this.activated=Activated;
		this.deactivated=Deactivated;
		super.setIcon(Deactivated);
		super.setBounds(x, y, width, height);
	}
	
	public void activate(){
		super.setIcon(activated);
	}
	public void deactivate(){
		super.setIcon(deactivated);
	}
	public int getWeapon(){
		return weapon;
	}
	
	public void setWeapon(int weaponNumber){
		weapon=weaponNumber;
	}
}
