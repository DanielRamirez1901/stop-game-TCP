package clientUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application{
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("gameScreen.fxml"));
		Parent p = (Parent) loader.load();
		
		Scene scene = new Scene(p);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
		//Hacer el .start en cliente
	}

}
