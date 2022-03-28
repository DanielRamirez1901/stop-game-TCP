package clientUI;


import events.OnStartScreenListener;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainClient extends Application implements OnStartScreenListener{
	
	Stage stageToSend;
	ScreenInitial si ;
	SaveStageToUse save;
	//ScreenGame sg = ScreenGame.getInstance();
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(MainClient.class.getResource("initialScreen.fxml"));
		Parent p = (Parent) loader.load();
		
		Scene scene = new Scene(p);
		Stage stage = new Stage();
		stageToSend = stage;
		
        primaryStage.setScene(scene);
        primaryStage.show();
		//System.out.println("Stage desde MainClient:" +stageToSend);
		//save = SaveStageToUse.getInstance();
		//save.setStage(stageToSend);
		//System.out.println("Stage desde MainClient: "+stageToSend);
		//si = new ScreenInitial();
		//si.setStage(stageToSend);
	}


	public Stage getStage2() {
		
		return stageToSend;
	}

	public void setScreenInitial(ScreenInitial si) {
		this.si = si;
		si.setStartListener(this);
	}

	@Override
	public Stage getStageToUse() {
		System.out.println("Stage desde MainClient: "+stageToSend);
		return stageToSend;
	}
	
	
	
	
	
	

}
