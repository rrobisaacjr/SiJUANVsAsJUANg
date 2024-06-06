package miniprojtemplate;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/*
 * The GameTimer is a subclass of the AnimationTimer class. It must override the handle method.
 */

public class GameTimer extends AnimationTimer{

	private GraphicsContext gc;
	private Scene theScene;
	private Ship myShip;
	private ArrayList<Fish> fishes;
	private ArrayList<Salt> salts;
	private ArrayList<Bible> bibles;
	public static final int MAX_NUM_FISHES = 3;
	public static final int INITIAL_NUM_FISHES = 7;
	public static final int MAX_NUM_POWERUPS = 1;

	private Image background = new Image("images/bgplz2.jpg", 800, 500, false, false);
	private double backgroundX;

	private long startSpawnFish;
	private long startSpawnItem;
	private long stopSpawnItem;
	private long stopImmortality;
	private long timer=0;

	private int minutes = 0;
	private int tenseconds= -1;
	private int timerseconds;

	public final static double SPAWN_FISH_SEC = 5;
	public final static double SPAWN_POWERUP_SEC = 10;
	public final static double STOP_POWERUP_SEC = 5;
	public final static double STOP_IMMORTALITY_SEC = 5;




	GameTimer(GraphicsContext gc, Scene theScene){
		this.gc = gc;
		this.theScene = theScene;
		this.myShip = new Ship("JUAN",100,100);
		//instantiate the ArrayList of Fish
		this.fishes = new ArrayList<Fish>();
		this.salts = new ArrayList<Salt>();
		this.bibles = new ArrayList<Bible>();

		//call the spawnFishes method


		this.spawninitFishes();
		//call method to handle mouse click event
		this.handleKeyPressEvent();
	}

	@Override
	public void handle(long currentNanoTime) {
		this.gc.clearRect(0, 0, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.backGround();

		this.myShip.move();

		this.moveBullets();
		this.moveFishes();
		this.checkPowerups();

		this.myShip.render(this.gc);

		this.renderBullets();
		this.renderFishes();
		this.renderPowerups();


		double spawnFishElapsedTime = (currentNanoTime - this.startSpawnFish) / 1000000000.0;
		double spawnItemElapsedTime = (currentNanoTime - this.startSpawnItem) / 1000000000.0;
		double stopItemElapsedTime = (currentNanoTime - this.stopSpawnItem) / 1000000000.0;



		if(spawnFishElapsedTime > GameTimer.SPAWN_FISH_SEC) {
			this.spawnFishes();
        	this.startSpawnFish = System.nanoTime();
		}

		if (spawnItemElapsedTime > GameTimer.SPAWN_POWERUP_SEC){
			this.spawnPowerup();
			System.out.println("\tA new powerup appeared!");
			this.startSpawnItem = System.nanoTime();
		}

		if (stopItemElapsedTime > GameTimer.STOP_POWERUP_SEC + (GameTimer.STOP_POWERUP_SEC*2)){
			this.timesupPowerup();
			System.out.println("\tTimes up Powerup!");
			this.stopSpawnItem = System.nanoTime();
		}

		if (myShip.getHittability()==false){

			long currentSec = TimeUnit.NANOSECONDS.toSeconds(currentNanoTime);
			long startSecImmortality = TimeUnit.NANOSECONDS.toSeconds(this.stopImmortality);

			//System.out.println("\t(currentSec-startSecImmortality)");

			if ((currentSec-startSecImmortality)% GameTimer.STOP_IMMORTALITY_SEC==0){
				myShip.setHittability(true);
				System.out.println("\tTimes up Immortality!");
				myShip.loadImage(Ship.ORDINARY_SHIP_IMAGE);
				this.stopImmortality = System.nanoTime();
			}
		}

		getTime(currentNanoTime);

		this.drawScoreStrength();

		if (this.myShip.isAlive()==false){
			this.stop();
			this.drawGameOver();
		}

		if (this.minutes==1){
			System.out.println("\tYOU WON!");
			this.drawGameWinner();
			this.stop();
		}
	}

	public void getTime(long currentNanoTime) {
		double seconds = (currentNanoTime - this.timer) / 1000000000.0;

		 if ((int) seconds >= 9){
			 tenseconds++;
			 this.timer = System.nanoTime();
		 }

		 if (tenseconds>= 6){
			 tenseconds=0;
			 minutes++;
		 }

		 this.timerseconds = (int) seconds;
	}


	void backGround(){
		// clear the canvas
        this.gc.clearRect(0, 0, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);

        // redraw background image (moving effect)
        this.backgroundX += 3;

        this.gc.drawImage( background, this.backgroundX-this.background.getWidth(), 0 );
        this.gc.drawImage( background, this.backgroundX, 0 );

        if(this.backgroundX >=GameStage.WINDOW_WIDTH)
        	this.backgroundX = GameStage.WINDOW_WIDTH-this.background.getWidth();
	}

	//method that will render/draw the fishes to the canvas
	private void renderFishes() {
		for (Fish f : this.fishes){
			f.render(this.gc);
		}
	}

	private void renderPowerups() {
		for (Powerup s : this.salts){
			s.render(this.gc);
		}

		for (Powerup b : this.bibles){
			b.render(this.gc);
		}
	}


	//method that will render/draw the bullets to the canvas
	private void renderBullets() {
		/*
		 *TODO: Loop through the bullets arraylist of myShip
		 *				and render each bullet to the canvas
		 */
		for (Bullet b: this.myShip.getBullets()){
			b.render(this.gc);
		}
	}

	//method that will spawn/instantiate three fishes at a random x,y location
	private void spawnFishes(){

		int yPos;
		Random r = new Random();
		for(int i=0;i<GameTimer.MAX_NUM_FISHES;i++){
			int x = r.nextInt(GameStage.WINDOW_WIDTH - (GameStage.WINDOW_WIDTH/2)) +(GameStage.WINDOW_WIDTH/2);
			//int y = r.nextInt(GameStage.WINDOW_WIDTH);
			/*
			 *TODO: Add a new object Fish to the fishes arraylist
			 */
			yPos = (i+1)*Fish.FISH_WIDTH;

			this.fishes.add(new Fish(x,yPos));
		}

	}

	private void spawninitFishes(){

		int yPos;
		Random r = new Random();
		for(int i=0;i<INITIAL_NUM_FISHES-1;i++){
			int x = r.nextInt(GameStage.WINDOW_WIDTH - (GameStage.WINDOW_WIDTH/2)) +(GameStage.WINDOW_WIDTH/2);
			//int y = r.nextInt(GameStage.WINDOW_HEIGHT);
			/*
			 *TODO: Add a new object Fish to the fishes arraylist
			 */
			yPos = ((i+1)*Fish.FISH_WIDTH);

			this.fishes.add(new Fish(x,yPos));
		}

	}

	private void spawnPowerup(){

		int yPos;
		Random r = new Random();
		for(int i=0;i<GameTimer.MAX_NUM_POWERUPS;i++){
			int x = r.nextInt((GameStage.WINDOW_WIDTH/2) - 0) + 0;
			int y = r.nextInt(MAX_NUM_FISHES);
			int type = r.nextInt(2);
			/*
			 *TODO: Add a new object Fish to the fishes arraylist
			 */
			yPos = (y+1)*Fish.FISH_WIDTH;

			switch(type){
				case 0: this.salts.add(new Salt(x,yPos)); break;
				case 1: this.bibles.add(new Bible(x,yPos)); break;
			}
		}
	}

	private void timesupPowerup(){

		if(this.salts.isEmpty()){
		} else {
			this.salts.clear();
		}

		if(this.bibles.isEmpty()){
		} else {
			this.bibles.clear();
		}

	}

	//method that will move the bullets shot by a ship
	private void moveBullets(){
		//create a local arraylist of Bullets for the bullets 'shot' by the ship
		ArrayList<Bullet> bList = this.myShip.getBullets();

		//Loop through the bullet list and check whether a bullet is still visible.
		for(int i = 0; i < bList.size(); i++){
			Bullet b = bList.get(i);
			/*
			 * TODO:  If a bullet is visible, move the bullet, else, remove the bullet from the bullet array list.
			 */
			if (b.isVisible()){
				b.move();
			} else {
				bList.remove(i);
			}
		}
	}

	//method that will move the fishes
	private void moveFishes(){
		//Loop through the fishes arraylist
		for(int i = 0; i < this.fishes.size(); i++){
			Fish f = this.fishes.get(i);
			/*
			 * TODO:  *If a fish is alive, move the fish. Else, remove the fish from the fishes arraylist.
			 */
			if(f.isVisible()){
				f.move();
				f.checkCollision(this.myShip);
			}
			else this.fishes.remove(i);
		}
	}

	private void checkPowerups(){
		for(int i = 0; i < this.salts.size(); i++){
			Salt s = this.salts.get(i);

			if(s.isVisible()){
				s.checkCollision(this.myShip);
			}
			else {
				this.salts.remove(i);
			}
		}

		for(int i = 0; i < this.bibles.size(); i++){
			Bible b = this.bibles.get(i);

			if(b.isVisible()){
				b.checkCollision(this.myShip);
			}
			else {
				this.bibles.remove(i);
			}
		}
	}


	//method that will listen and handle the key press events
	private void handleKeyPressEvent() {
		this.theScene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent e){
            	KeyCode code = e.getCode();
                moveMyShip(code);
			}
		});

		this.theScene.setOnKeyReleased(new EventHandler<KeyEvent>(){
		            public void handle(KeyEvent e){
		            	KeyCode code = e.getCode();
		                stopMyShip(code);
		            }
		        });
    }

	//method that will move the ship depending on the key pressed
	private void moveMyShip(KeyCode ke) {
		if(ke==KeyCode.UP || ke==KeyCode.W) this.myShip.setDY(-10);

		if(ke==KeyCode.LEFT || ke==KeyCode.A) this.myShip.setDX(-10);

		if(ke==KeyCode.DOWN || ke==KeyCode.S) this.myShip.setDY(10);

		if(ke==KeyCode.RIGHT || ke==KeyCode.D) this.myShip.setDX(10);

		if(ke==KeyCode.SPACE) this.myShip.shoot();

		System.out.println(ke+" key pressed.");
   	}

	//method that will stop the ship's movement; set the ship's DX and DY to 0
	private void stopMyShip(KeyCode ke){
		this.myShip.setDX(0);
		this.myShip.setDY(0);
	}

	private void drawScoreStrength(){
		this.gc.setFont(Font.font("Franklin Gothic", FontWeight.EXTRA_BOLD, 25));
		this.gc.setFill(Color.YELLOW);
		this.gc.fillText("PUNTOS:", 450, 40);
		this.gc.setFont(Font.font("Franklin Gothic", FontWeight.BOLD, 30));
		this.gc.setFill(Color.WHITE);
		this.gc.fillText(myShip.getScore()+"", 580, 40);
		this.gc.setFont(Font.font("Franklin Gothic", FontWeight.EXTRA_BOLD, 25));
		this.gc.setFill(Color.YELLOW);
		this.gc.fillText("LAKAS:", 630, 40);
		this.gc.setFont(Font.font("Franklin Gothic", FontWeight.BOLD, 30));
		this.gc.setFill(Color.WHITE);
		this.gc.fillText(myShip.getStrength()+"", 730, 40);

		this.gc.setFont(Font.font("Franklin Gothic", FontWeight.BOLD, 30));
		this.gc.setFill(Color.WHITE);
		this.gc.fillText(minutes + ":" + tenseconds+ timerseconds, 350, 40);
	}

	private void drawGameOver(){
		this.gc.setFont(Font.font("Verdana", FontWeight.BOLD, 100));
		this.gc.setFill(Color.WHITE);
		this.gc.fillText("GAME OVER!", 50, (GameStage.WINDOW_HEIGHT/2)+50);
	}

	private void drawGameWinner(){
		this.gc.setFont(Font.font("Verdana", FontWeight.BOLD, 100));
		this.gc.setFill(Color.WHITE);
		this.gc.fillText("YOU WON!", 50, (GameStage.WINDOW_HEIGHT/2)+50);
	}





}
