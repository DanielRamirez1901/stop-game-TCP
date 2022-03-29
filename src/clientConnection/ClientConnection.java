package clientConnection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

import com.google.gson.Gson;

import clientGeneral.Client;
import clientUI.ScreenGame;
import clientUI.ScreenInitial;
import clientUI.ScreenResult;
import events.OnFinalScreenListener;
import events.OnPlayerFoundListener;
import javafx.application.Platform;
import javafx.stage.Stage;

public class ClientConnection {
	
	//*************************
	OnPlayerFoundListener playListener;
	OnFinalScreenListener finalListener;
	public ScreenGame sg ;
	public ScreenResult sr;
	public ScreenInitial si;
	Client cl;
	Stage stage;
	String msgWinner = "";
	String msgLosser = "";
	int contAccess = 0;
	//Unica instancia************************
	private static ClientConnection instance;
	
	private ClientConnection() {
	}
	public static synchronized ClientConnection getInstance() {
		if(instance == null) {
			instance = new ClientConnection();
		}
		return instance;
	}
	//Connection Variables****
	private BufferedReader breader;
	private BufferedWriter bwriter;
	private final static int PORT = 6100;
	private final static String IP = "127.0.0.1";
	private Socket socket;
	

	
	public void startConnection(int cont) {

		new Thread(()-> {
			try {
				Gson gson = new Gson();
				sg = ScreenGame.getInstance();
				sg.setScreenGame(this);
				sr = ScreenResult.getInstance();
				sr.setScreenResult(this);
				if(cont==0) {
					//Fase de conexion y espera de rival
					socket = new Socket(IP, PORT);
					bwriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
					breader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					String msg = "";
					msg = breader.readLine();
					String msgToPrint = gson.fromJson(msg, String.class);
					playListener.showGamePlayer(msgToPrint);
				}else if(cont==1){
					String json = gson.toJson(msgWinner);
					bwriter.write(json+"\n");
					bwriter.flush();
					String rivalScore = "";
					rivalScore = breader.readLine();
					String msgToPrint = gson.fromJson(rivalScore, String.class);
					msgLosser = msgToPrint;		
					Platform.runLater(()-> {
						finalListener.showFinalScreen(msgWinner,msgLosser);
					});	
				}else if(cont==2) {
					//Caso en el que es el jugador perdedor
					String rivalScore = "";
					String lostMsg = "";
					lostMsg = breader.readLine();
					String msgTo = gson.fromJson(lostMsg, String.class);
					if(msgTo.contains("YouLose")) {
						rivalScore = breader.readLine();
						String msgToPrint = gson.fromJson(rivalScore, String.class);
						playListener.saveLetters();
						String toSend = gson.toJson(msgLosser);
						bwriter.write(toSend+"\n");
						bwriter.flush();
						msgWinner= msgToPrint;
						Platform.runLater(()-> {
							finalListener.showFinalScreen(msgLosser,msgWinner);
						});
					}

				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();
	}
	
	//Listener para invocar pestaña Stop Game
	public void setPlayListener(OnPlayerFoundListener playListener) {
		this.playListener = playListener;
	}
	public void setMsgWinner(String msgToSend) {
		this.msgWinner = msgToSend;
	}
	public void setMsgLosser(String msgLosser) {
		this.msgLosser = msgLosser;
	}
	public void setFinalListener(OnFinalScreenListener finalListener) {
		this.finalListener = finalListener;
	}
	public Stage getStage() {
		return stage;
	}
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	
}
