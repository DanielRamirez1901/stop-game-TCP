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
	//ScreenGame sg= ScreenGame.getInstance();
	
	Stage stage;
	Stage stageToSend;
	MainClient mc;
	
	public ScreenInitial() {
		
	}
	

	 @FXML
	 void showWaiting(ActionEvent event) {
		save = SaveStageToUse.getInstance();
		stage = save.getStage();
		System.out.println("Con esta stage inicio ScreeInitial: "+stage);
		 /*
		 mc = new MainClient();
		 mc.setScreenInitial(this);
		 stageToSend = startListener.getStageToUse();
		 */
		
		 //La funcion del boton sera avisarle al main client que le entregue
		 //el stage para asi usar en su iniciacion
		 //System.out.println(event);
		 //Pasar stages
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("loadingScreen.fxml"));
				Parent p;
				loader.setController(this);
				p = (Parent) loader.load();
				Scene scene = new Scene(p);
				//stage2 = stage;
				stageToSend = stage;
			    stage = (Stage) anchorPaneInit.getScene().getWindow();
				stage.setScene(scene);
				stage.show();
				
				//sg.setStage(stage);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//cl = Client.getInstance();
			System.out.println("Esta es la stage desde ScreenInitial: "+stageToSend);
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
