package clientUI;

import java.io.IOException;

import clientGeneral.Client;
import events.OnStartScreenListener;
import events.OnWaitingScreenListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ScreenInitial {
	
	@FXML
    private AnchorPane anchorPaneInit;
	
	public Client cl ;
	public SaveStageToUse save;
	private OnWaitingScreenListener waitingList;
	private OnStartScreenListener startListener;
	
	Stage stage;
	Stage stageToSend;
	MainClient mc;
	
	public ScreenInitial() {
		
	}
	

	 @FXML
	 void showWaiting(ActionEvent event) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("loadingScreen.fxml"));
				Parent p;
				loader.setController(this);
				p = (Parent) loader.load();
				Scene scene = new Scene(p);
				
			    Stage stage = (Stage) anchorPaneInit.getScene().getWindow();
				stage.setScene(scene);
				stageToSend = stage;
				stage.show();
				save = SaveStageToUse.getInstance();
				save.setStage(stageToSend);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cl = new Client(stageToSend);
			cl.setScreenInitial(this);
			waitingList.waitingScreenListener();
	 }
	 
	 
	 
	 public void setWaitingScreenListener(OnWaitingScreenListener waitingList) {this.waitingList = waitingList;}
	 public void setStartListener(OnStartScreenListener startListener) {this.startListener = startListener;}


	public Stage getStage() {
		return stage;
	}
	public void setStage(Stage stage) {
		this.stage = stage;
		stageToSend = stage;
	}
	
	
		

	 
}
