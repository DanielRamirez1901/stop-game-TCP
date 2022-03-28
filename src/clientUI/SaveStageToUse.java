package clientUI;

import clientConnection.ClientConnection;
import javafx.stage.Stage;

public class SaveStageToUse {
	
	
	Stage stage;

	private static SaveStageToUse instance;
	
	private SaveStageToUse() {
		// TODO Auto-generated constructor stub
	}
	public static synchronized SaveStageToUse getInstance() {
		if(instance == null) {
			instance = new SaveStageToUse();
		}
		return instance;
	}
	
	public Stage getStage() {
		return stage;
	}
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	
}
