import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Stage extends JPanel implements MouseListener,MouseMotionListener,KeyListener{
	MainFrame main;
	Player player;
	
	private boolean playing;
	private Image background;
	private WeaponIcon weapon1,weapon2;
	private JLabel stageClearButton,nextStageButton,restartButton,restartInGame;
	boolean hold;
	private int stage,speed,dspeed,maxSpeed=20;
	private Timer speedrun;
	private ImageIcon stoneBlock;
	static ArrayList<Bullet> arrayBullet = new ArrayList<Bullet>();
	static ArrayList<Enemy> arrayEnemy = new ArrayList<Enemy>();
	static ArrayList<Obstacle> arrayObstacle = new ArrayList<Obstacle>();
	static ArrayList<WeaponIcon> arrayWeapon = new ArrayList<WeaponIcon>();
	
	Stage(MainFrame main){
		stoneBlock=new ImageIcon("Image/StoneBlock.png");
		this.main=main;
		setSize(1024,600);
		setLayout(null);
		addMouseMotionListener(this);
		addMouseListener(this);
		addKeyListener(this);
		stage=1;
		stageClearButton=new JLabel(new ImageIcon("Image/CLEAR.png"));
		stageClearButton.addMouseListener(this);
		stageClearButton.setBounds(362,175,300,250);
		restartButton=new JLabel(new ImageIcon("Image/Restart.png"));
		restartButton.addMouseListener(this);
		restartButton.setBounds(410,330,64,64);
		
		nextStageButton=new JLabel(new ImageIcon("Image/Next.png"));
		nextStageButton.addMouseListener(this);
		nextStageButton.setBounds(550,330,64,64);
		restartInGame=new JLabel(new ImageIcon("Image/Restart.png"));
		restartInGame.addMouseListener(this);
		restartInGame.setBounds(940,55,64,64);
		player=new Player(main);
		stage=1;
		weapon1 = new WeaponIcon(new ImageIcon("Image/Normal.png"),new ImageIcon("Image/NormalSelected.png"),15,15,64,64);
		weapon1.setWeapon(1);
		weapon1.addMouseListener(this);
		weapon1.activate();
		weapon2 = new WeaponIcon(new ImageIcon("Image/Bounce.png"),new ImageIcon("Image/BounceSelected.png"),94,15,64,64);
		weapon2.setWeapon(2);
		weapon2.addMouseListener(this);
		
		arrayWeapon.add(weapon1);
		arrayWeapon.add(weapon2);
		add(weapon1);
		add(weapon2);
		
		speedrun=new Timer(33,new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				speed+=dspeed;
				if(speed>=maxSpeed||speed<=0){
					dspeed*=-1;
				}
			}
		});
		
		nextStage();
	}
	
	public void paintComponent(Graphics g){
		g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
		for(int i = 0;i<arrayObstacle.size();i++){
			if(arrayObstacle.get(i).getImage()!=null){
				g.drawImage(arrayObstacle.get(i).getImage(), arrayObstacle.get(i).getX()-1, arrayObstacle.get(i).getY()-1, arrayObstacle.get(i).getWidth()+1, arrayObstacle.get(i).getHeight()+1, this);
			}
		}
		if(playing){
			if(player.isShooting()){
				//RotatePlayerImage
				AffineTransform at = AffineTransform.getTranslateInstance(player.getX(), player.getY());
				at.scale(0.65, 0.65);//setScaleOFRotatedImage
				at.rotate(Math.toRadians(-player.getImageAngle()),player.getAttackImage().getWidth(null)/2,player.getAttackImage().getHeight(null)/2);
				Graphics2D g2d = (Graphics2D)g;
				g2d.drawImage(player.getAttackImage(), at,this);
			}
			else g.drawImage(player.getImage(),player.getX(),player.getY(),player.getWidth(),player.getHeight(),this);
			g.fillArc(player.getX()-30,player.getY()-30,200,200,player.getAngle(),1);
			g.setColor(Color.black);
			g.fillRect(930, 10, 80, 30);
			g.setColor(Color.white);
			g.drawString("AMMO : "+player.getAmmo(), 945, 30);
		}
		g.setColor(Color.blue);
		for(int i = 0;i<arrayBullet.size();i++){
			g.drawImage(arrayBullet.get(i).getImage(),arrayBullet.get(i).getX(),arrayBullet.get(i).getY(),arrayBullet.get(i).getWidth(),arrayBullet.get(i).getHeight(),this);
		}

		g.setColor(Color.red);
		for(int i = 0;i<arrayEnemy.size();i++){
			g.drawImage(arrayEnemy.get(i).getImage(),arrayEnemy.get(i).getX(),arrayEnemy.get(i).getY(),arrayEnemy.get(i).getWidth(),arrayEnemy.get(i).getHeight(),this);
		}
		
		if(hold){
			g.setColor(Color.black);
			g.fillRect(player.getX()+10, player.getY()-54, maxSpeed*4+6,18 );
			g.setColor(Color.red);
			g.fillRect(player.getX()+13, player.getY()-50, speed*4,10 );
		}
		repaint();
	}

	private void shoot(){
		if(player.getAmmo()>0){
			player.setShootImage();
			player.setAmmo(player.getAmmo()-1);
			if(player.getWeapon()==1){
				arrayBullet.add(new NormalBullet(main,player.getAngle()));
			}
			else if(player.getWeapon()==2){
				arrayBullet.add(new BounceProjectileBullet(main,player.getAngle(),speed));
				
			}
		}
	}
	
	
	
	private void clearStage(){
		for(int i=0;i<arrayEnemy.size();i++){
			arrayEnemy.get(i).stopThread();
		}
		for(int i=0;i<arrayBullet.size();i++){
			arrayBullet.get(i).thread.stop();
		}
		arrayEnemy.removeAll(arrayEnemy);
		arrayObstacle.removeAll(arrayObstacle);
		arrayBullet.removeAll(arrayBullet);
		for(int i = 0;i<arrayWeapon.size();i++){
			arrayWeapon.get(i).hide();
		}
		restartInGame.hide();
		player.hide();
		if(speedrun.isRunning()){
			speedrun.stop();
			dspeed=0;
		}
		if(hold){
			hold=false;
		}
	}
	
	private void setupStage1(){
		background=new ImageIcon("Image/Stage1Background.png").getImage();
		player.setPostion(15, 325);
		player.show();
		player.setAmmo(30);
		player.setWeapon(1);
		restartInGame.show();
		for(int i = 0;i<arrayWeapon.size();i++){
			arrayWeapon.get(i).show();
		}
		
		arrayObstacle.add(new Obstacle(0, 490, 1024, 110));//Bounds for Player Hill for Stand
		arrayObstacle.add(new Obstacle(0,430,120,70));//Bounds for Lowest Floor
		//add Stone
		arrayObstacle.add(new Obstacle(stoneBlock,619, 445, 45,45));
		arrayObstacle.add(new Obstacle(stoneBlock,450, 100, 45,45));
		arrayObstacle.add(new Obstacle(stoneBlock,495, 100, 45,45));
		for(int i = 0;i<9;i++){
			arrayObstacle.add(new Obstacle(stoneBlock,664+(i*45), 400, 45,45));
			arrayObstacle.add(new Obstacle(stoneBlock,664+(i*45), 445, 45,45));
		}
		//add Enemy
		arrayEnemy.add(new Enemy(main,900,350));
		arrayEnemy.add(new Enemy(main,800,350));
		arrayEnemy.add(new Enemy(main,175,445));
		playing=true;
		
		
	}
	private void setupStage2(){
		background=new ImageIcon("Image/Stage2Background.png").getImage();
		for(int i = 0;i<arrayWeapon.size();i++){
			arrayWeapon.get(i).show();
		}
		
		arrayObstacle.add(new Obstacle(0, 390, 125, 210));
		arrayObstacle.add(new Obstacle(0, 515, 1024, 85));
		
		arrayObstacle.add(new Obstacle(stoneBlock,820,90,45,45));
		arrayObstacle.add(new Obstacle(stoneBlock,875,467,45,45));
		arrayObstacle.add(new Obstacle(stoneBlock,875,422,45,45));
		for(int i=0;i<7;i++){
			for(int j=0;j<i;j++){
				arrayObstacle.add(new Obstacle(stoneBlock,320+(i*45),467-(j*45),45,45));
			}
			
		}
		
		
		
		arrayEnemy.add(new Enemy(main,130,460));
		arrayEnemy.add(new Enemy(main,590,190));
		arrayEnemy.add(new Enemy(main,640,460));
		
		restartInGame.show();
		player.setPostion(15, 290);
		player.setAmmo(player.getRemainingAmmo());
		player.show();
		playing=true;
	}
	private void setupStage3(){
		background=new ImageIcon("Image/Stage3Background.png").getImage();
		for(int i = 0;i<arrayWeapon.size();i++){
			arrayWeapon.get(i).show();
		}
		
		arrayObstacle.add(new Obstacle(0, 440, 90, 210));
		arrayObstacle.add(new Obstacle(0, 515, 1024, 85));
		
		arrayObstacle.add(new Obstacle(stoneBlock,820,90,45,45));
		arrayObstacle.add(new Obstacle(stoneBlock,905,287,45,45));
		arrayObstacle.add(new Obstacle(stoneBlock,950,242,45,45));
		for(int i=0;i<13;i++){
			for(int j=0;j<i;j++){
				if(i!=7&&j!=6)
					arrayObstacle.add(new Obstacle(stoneBlock,320+(i*45),467-(j*45),45,45));
			}
			
		}
		
		
		
		arrayEnemy.add(new Enemy(main,130,460));
		arrayEnemy.add(new Enemy(main,590,190));
		arrayEnemy.add(new Enemy(main,633,460));
		arrayEnemy.add(new Enemy(main,950,192));
		restartInGame.show();
		player.setPostion(10, 355);
		player.show();
		player.setAmmo(player.getRemainingAmmo());
		playing=true;
	}
	private void nextStage(){
		weapon1.activate();
		weapon2.deactivate();
		player.setWeapon(1);
		if(stage==1){
			setupStage1();
			add(restartInGame);
		}else if(stage==2){
			setupStage2();
		}else if(stage==3){
			setupStage3();
		}else{
			background=new ImageIcon("Image/TheEnds.gif").getImage();
		}
		remove(stageClearButton);
		remove(nextStageButton);
		remove(restartButton);
	}
	
	void win(){
		playing=false;
		player.setRemainingAmmo(player.getAmmo());
		clearStage();
		//background=null;
		add(restartButton);
		add(nextStageButton);
		add(stageClearButton);
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(playing){
			if(e.getSource()==weapon1){
				arrayWeapon.get(player.getWeapon()-1).deactivate();
				player.setWeapon(weapon1.getWeapon());
				weapon1.activate();
			}else if(e.getSource()==weapon2){
				arrayWeapon.get(player.getWeapon()-1).deactivate();
				player.setWeapon(weapon2.getWeapon());
				weapon2.activate();
			}else if(e.getSource()==restartInGame){
				clearStage();
				nextStage();
			}
		}else if(e.getSource()==nextStageButton){
			stage+=1;
			nextStage();
		}else if(e.getSource()==restartButton){
			nextStage();
		}
		
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {	
	}

	@Override
	public void mouseExited(MouseEvent e) {	
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(playing&&e.getSource()==this&&player.getWeapon()==2){
			System.out.println("press");
			hold=true;
			speed=0;
			dspeed=1;
			speedrun.start();
		
		}
		
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(playing&&e.getSource()==this){
			if(hold){
				dspeed=0;
				hold=false;
				if(speedrun.isRunning())speedrun.stop();
			}
				
			shoot();
			
			
			
		}
			
	}

	@Override
	public void mouseDragged(MouseEvent e) {

		
	}

	@Override
	public void mouseMoved(MouseEvent e){
		player.setAngle(e.getX(), e.getY());
		System.out.println(e.getX()+" "+e.getY());
	}

	@Override
	public void keyPressed(KeyEvent e){
		if(e.getKeyCode()==KeyEvent.VK_1){
			arrayWeapon.get(player.getWeapon()-1).deactivate();
			player.setWeapon(1);
			System.out.println("Weapon Selected : 1");
			weapon1.activate();
			
		}
		else if(e.getKeyCode()==KeyEvent.VK_2){
			arrayWeapon.get(player.getWeapon()-1).deactivate();
			player.setWeapon(2);
			System.out.println("Weapon Selected : 2");
			weapon2.activate();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

		
	}

	@Override
	public void keyTyped(KeyEvent e) {

		
	}
}
