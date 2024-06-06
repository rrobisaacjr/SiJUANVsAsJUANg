package miniprojtemplate;

import java.io.File;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage stage){
		GameStage theGameStage = new GameStage();
		theGameStage.setStage(stage);

		Media sound = new Media(new File("src/images/bgmusic.mp3").toURI().toString());
        AudioClip mediaPlayer = new AudioClip(sound.getSource());
        mediaPlayer.play();
	}
}