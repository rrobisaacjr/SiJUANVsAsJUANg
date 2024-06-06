package miniprojtemplate;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GameStage {
	public static final int WINDOW_HEIGHT = 500;
	public static final int WINDOW_WIDTH = 800;
	private Scene gameScene;
	private Scene splashScene;
	private Stage stage;
	private Group root;
	private Canvas canvas;

	Image bg = new Image("images/TITLENAMAYBG.jpg", 800, 500, false, false);
	Image bg2 = new Image("images/INSTRUCTION.jpg", 800, 500, false, false);
	Image bg3 = new Image("images/ABOUT.jpg", 800, 500, false, false);


	//the class constructor
	public GameStage() {
		this.root = new Group();
		this.gameScene = new Scene(root, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
	}

	//method to add the stage elements
	public void setStage(Stage stage) {
		this.stage = stage;
		this.stage.setTitle("Juan vs. asJUANg");

		this.initMainMenu(stage, bg);

		stage.setScene(this.splashScene);
		stage.setResizable(false);

		this.stage.show();
	}


	private void initMainMenu(Stage stage, Image bg){
		StackPane root = new StackPane();
		root.getChildren().addAll(this.createCanvas(bg),this.createHBox());
		this.splashScene = new Scene(root);
	}

	private Canvas createCanvas(Image bg){
		Canvas canvas = new Canvas(GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		GraphicsContext gc = canvas.getGraphicsContext2D();

		gc.drawImage(bg, 0, 0);
		return canvas;
	}

	private HBox createHBox(){
		HBox hbox = new HBox();
		hbox.setAlignment(Pos.BOTTOM_CENTER);
		hbox.setPadding(new Insets(30));
		hbox.setSpacing(20);

		Button b1 = new Button("New Game");
		Button b2 = new Button("Instructions");
		Button b3 = new Button("About");

		hbox.getChildren().addAll(b1,b2,b3);

		b1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e){
				setGame(stage);
			}
		});

		b2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e){
				initMainMenu(stage, bg2);
				stage.setScene(splashScene);
			}
		});

		b3.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e){
				initMainMenu(stage, bg3);
				stage.setScene(splashScene);
			}
		});

		return hbox;
	}

	void setGame(Stage stage){
		stage.setScene(this.gameScene);

		GraphicsContext gc = this.canvas.getGraphicsContext2D();

		GameTimer gameTimer = new GameTimer(gc, gameScene);
		this.root.getChildren().add(canvas);
		gameTimer.start();
	}



}

