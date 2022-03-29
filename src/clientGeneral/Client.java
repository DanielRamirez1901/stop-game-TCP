package clientGeneral;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;

import clientConnection.ClientConnection;
import clientUI.ScreenGame;
import clientUI.ScreenInitial;
import events.OnPlayerFoundListener;
import events.OnWaitingScreenListener;
import javafx.application.Platform;
import javafx.stage.Stage;



public class Client implements OnWaitingScreenListener{
	

	Stage stage;
	public Client(Stage stage) {this.stage = stage;}
	
	public ScreenInitial sc;
	public ClientConnection cc = ClientConnection.getInstance();
	OnPlayerFoundListener playListener;
	ScreenGame sG;
	

	

	public void setScreenInitial(ScreenInitial sc) {
		this.sc=sc;
		sc.setWaitingScreenListener(this);
	}
	
	

	@Override
	public void waitingScreenListener() {
		
		Platform.runLater(()->{
			cc.setStage(stage);
			cc.startConnection(0);
		});
		
	}



	public void setPlayListener(OnPlayerFoundListener playListener) {
		this.playListener = playListener;
	}



	public void setSc(ScreenInitial sc) {
		this.sc = sc;
	}


	public Stage getStage() {
		return stage;
	}

	
	
	
	
}
