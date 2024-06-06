package miniprojtemplate;

import java.util.Random;

import javafx.scene.image.Image;

abstract class Powerup extends Sprite {
	int speed;
	protected final static int POWERUP_WIDTH = 50 ;

	Powerup(int x, int y) {
		super(x,y);
	}

	abstract void checkCollision(Ship myShip);

	public static int getPowerupWidth() {
		return POWERUP_WIDTH;
	}

}
