package clientGeneral;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;

import clientConnection.ClientConnection;
import clientUI.ScreenInitial;
import clientUI.ScreenInitial.OnMessageListener;
import events.OnWaitingScreenListener;
import javafx.application.Platform;



public class Client implements OnWaitingScreenListener{
	
	//private static Client instance;
	
	/*private Client() {}
	public static synchronized Client getInstance() {
		if(instance == null) {
			instance = new Client();
		}
		return instance;
	}*/
	public Client() {}
	
	
	
	
	public ScreenInitial sc;
	public ClientConnection cc = ClientConnection.getInstance();
	
	
	
	//public final static long TIME=1000;
	

	public void setScreenInitial(ScreenInitial sc) {
		this.sc=sc;
		sc.setWaitingScreenListener(this);
	}
	

	@Override
	public void waitingScreenListener() {
		
		System.out.println("Reparto a client");
		Platform.runLater(()->{
		cc.startConnection();
		});
		//Hilos para no bloquear interfaz
				//Generar la conexion con un run later
				//En cuanto encuentre otro jugador, hacer listener 
				//para notificar a la otra interfaz de que cargue juego
		
	}	
	
	
}
