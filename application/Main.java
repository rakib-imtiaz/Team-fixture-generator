package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			// Initialize team file if it doesn't exist
			TeamFileInitializer.initializeTeamFile();

			Parent root = FXMLLoader.load(getClass().getResource("loginpage.fxml"));
			Scene scene = new Scene(root, 800, 600);
			primaryStage.setTitle("UCL Team Match Maker");
			primaryStage.setScene(scene);
			primaryStage.setMinWidth(600);
			primaryStage.setMinHeight(500);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
