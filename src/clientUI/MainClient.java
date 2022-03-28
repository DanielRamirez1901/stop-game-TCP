package clientUI;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainClient extends Application{
	
	Stage stage2;
	ScreenInitial si = new ScreenInitial();
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(MainClient.class.getResource("initialScreen.fxml"));
		Parent p = (Parent) loader.load();
		
		Scene scene = new Scene(p);
		Stage stage = new Stage();
		stage2 = stage;
		
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
		si.setStage(stage2);
	}


	public Stage getStage2() {
		return stage2;
	}
	
	
	
	
	
	

}
