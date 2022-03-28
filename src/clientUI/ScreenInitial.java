package clientUI;

import java.io.IOException;

import clientGeneral.Client;
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
	
	public Client cl =  new Client();
	
	private OnMessageListener listener;
	private OnWaitingScreenListener waitingList;
	//ScreenGame sg= ScreenGame.getInstance();
	
	Stage stage;
	Stage stage2;
	

	 @FXML
	 void showWaiting(ActionEvent event) {
		 //Pasar stages
			try {
				FXMLLoader loader = new FXMLLoader(MainClient.class.getResource("loadingScreen.fxml"));
				Parent p;
				p = (Parent) loader.load();
				Scene scene = new Scene(p);
			    stage = (Stage) anchorPaneInit.getScene().getWindow();
				stage.setScene(scene);
				stage.show();
				//sg.setStage(stage);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//cl = Client.getInstance();
			cl.setScreenInitial(this);
			waitingList.waitingScreenListener();
	 }
	 
	 
	 
	 public void setListener(OnMessageListener listener) {this.listener = listener;}
	 public void setWaitingScreenListener(OnWaitingScreenListener waitingList) {this.waitingList = waitingList;}
	 
	 public interface OnMessageListener {void onMessage();}

	public Stage getStage() {
		return stage;
	}
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	
		

	 
}
