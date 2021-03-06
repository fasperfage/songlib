/*
 * Written by Calvin Lee and Bartosz Kidacki
 */

package songlib.app;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;


public class SongLib extends Application {
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/songlib/view/Layout.fxml"));
		
		Parent root;
		
		try {
			root = loader.load();
		} catch (IOException e) {
			return;
		}
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Song Library");
		primaryStage.setResizable(false);  
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
