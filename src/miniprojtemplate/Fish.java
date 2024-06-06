package miniprojtemplate;

import java.util.Random;

import javafx.scene.image.Image;

public class Fish extends Sprite {
	public static final int MAX_FISH_SPEED = 5;
	public static final int MIN_FISH_SPEED = 1;
	public static final int MAX_FISH_DAMAGE = 40;
	public static final int MIN_FISH_DAMAGE = 30;
	public final static Image FISH_IMAGE = new Image("images/Bat.png",Fish.FISH_WIDTH,Fish.FISH_HEIGHT,false,false);
	public final static int FISH_WIDTH = 83;
	public final static int FISH_HEIGHT = 83;
	private boolean alive;

	//attribute that will determine if a fish will initially move to the right
	private boolean moveRight;
	private int speed;
	private int damage;

	Fish(int x, int y){
		super(x,y);
		this.alive = true;
		this.loadImage(Fish.FISH_IMAGE);
		/*
		 *TODO: Randomize speed of fish and moveRight's initial value
		 */
		Random r = new Random();
		this.speed = r.nextInt(MAX_FISH_SPEED - MIN_FISH_SPEED + 1) + MIN_FISH_SPEED;

		this.moveRight = r.nextBoolean();

		this.damage = r.nextInt(MAX_FISH_DAMAGE - MIN_FISH_DAMAGE + 1) + MIN_FISH_DAMAGE;
	}

	//method that changes the x position of the fish
	void move(){
		/*
		 * TODO: 		If moveRight is true and if the fish hasn't reached the right boundary yet,
		 * 					move the fish to the right by changing the x position of the fish depending on its speed
		 * 				else if it has reached the boundary, change the moveRight value / move to the left
		 * 			 Else, if moveRight is false and if the fish hasn't reached the left boundary yet,
		 * 					move the fish to the left by changing the x position of the fish depending on its speed.
		 *				else if it has reached the boundary, change the moveRight value / move to the right
		 */
		if (this.moveRight==true && this.x+this.dx < GameStage.WINDOW_WIDTH-this.width){

			this.x += (this.speed);
			//this.moveRight = false;
		} else if (this.moveRight==true && this.x+this.dx >= GameStage.WINDOW_WIDTH-this.width){

			this.x -= (this.speed);
			this.moveRight = false;
		} else if (this.moveRight==false && this.x+this.dx > 0){

			this.x -= (this.speed);
			//this.moveRight = true;
		} else if (this.moveRight==false && this.x+this.dx <= 0){

			this.x += (this.speed);
			this.moveRight = true;
		}
	}

	void checkCollision(Ship myShip){
		for (int i=0; i < myShip.getBullets().size();i++){
			if (this.collidesWith(myShip.getBullets().get(i))){
				this.vanish();
				myShip.getBullets().get(i).vanish();
				myShip.gainScore(1);
			}
		}

		if (this.collidesWith(myShip) && myShip.getHittability()==true){
			myShip.getHit(this.damage);
			this.vanish();
		}
	}

//	private void getHit(){
//		this.vanish();
//	}


	//getter
	public boolean isAlive() {
		return this.alive;
	}

	public void die(){
    	this.alive = false;
    }

}
