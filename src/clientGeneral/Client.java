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
	
	//private static Client instance;
	
	/*private Client() {}
	public static synchronized Client getInstance() {
		if(instance == null) {
			instance = new Client();
		}
		return instance;
	}*/
	Stage stage;
	public Client(Stage stage) {this.stage = stage;}
	
	
	
	
	
	public ScreenInitial sc;
	public ClientConnection cc = ClientConnection.getInstance();
	OnPlayerFoundListener playListener;
	ScreenGame sG;
	
	//public final static long TIME=1000;
	

	public void setScreenInitial(ScreenInitial sc) {
		this.sc=sc;
		sc.setWaitingScreenListener(this);
		//this.stage = stage;
	}
	

	/*
	public void startScreenPlayer() {
		System.out.println("A jugar");
		sG.setScreenGame(this);
		playListener.showGamePlayer();
	}
	*/
	

	@Override
	public void waitingScreenListener() {
		
		System.out.println("Reparto a client");
		Platform.runLater(()->{
			cc.setStage(stage);
			cc.startConnection(0);
		});
		//Hilos para no bloquear interfaz
				//Generar la conexion con un run later
				//En cuanto encuentre otro jugador, hacer listener 
				//para notificar a la otra interfaz de que cargue juego
		
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
