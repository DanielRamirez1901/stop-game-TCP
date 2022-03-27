package clientConnection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

import clientGeneral.Client;
import clientUI.ScreenGame;
import clientUI.ScreenInitial;
import events.OnPlayerFoundListener;
import javafx.application.Platform;
import javafx.stage.Stage;

public class ClientConnection {

private static ClientConnection instance;
	
	private ClientConnection() {}
	public static synchronized ClientConnection getInstance() {
		if(instance == null) {
			instance = new ClientConnection();
		}
		return instance;
	}
	
	private BufferedReader breader;
	private BufferedWriter bwriter;
	private final static int PORT = 6100;
	private final static String IP = "127.0.0.1";
	private Socket socket;
	OnPlayerFoundListener playListener;
	public ScreenGame sg ;
	ScreenInitial si;
	Client cl;
	Stage stage;
	String msgToSend;
	
	public void startConnection(int cont) {

		
		System.out.println("Comienzo conexion");
		
		new Thread(()-> {
			try {
				if(cont==0) {
					//Fase de conexion y espera de rival
					System.out.println("Entro al hilo");
					socket = new Socket(IP, PORT);
					bwriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
					breader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					System.out.println("Espero mensaje de otro jugador");
					String msg = "";
					msg = breader.readLine();
					System.out.println(msg);
					//Crear listener para invocar el metodo ventanaB
					sg = ScreenGame.getInstance();
					if(si.getStage()!=null) {
						sg.setStage(si.getStage());
					}
					playListener.showGamePlayer(0);
				}else if(cont==1){
					//Caso en el que el jugador presiona Stop
					bwriter.write(msgToSend);
					bwriter.flush();
					String rivalScore = "";
					rivalScore = breader.readLine();
					System.out.println(rivalScore);
					//Invoco listener para que se cambie de pantalla
					//Pantalla de resultados
				}else if(cont==2) {
					//Caso en el que es el jugador perdedor
					String rivalScore = "";
					rivalScore = breader.readLine();
					sg = ScreenGame.getInstance();
					sg.sendAlert();
				}else if(cont==3) {
					bwriter.write(msgToSend);
					bwriter.flush();
					//Aqui se invoca la interfaz de resultado caso perdedor
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}).start();
		
		
		
	}
	
	public void setPlayListener(OnPlayerFoundListener playListener) {
		this.playListener = playListener;
	}
	public void setMsgToSend(String msgToSend) {
		this.msgToSend = msgToSend;
	}
	
	
	
	
}
