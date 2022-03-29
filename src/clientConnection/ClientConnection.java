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
import clientUI.ScreenFinal;
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
		
		System.out.println("Ingreso a startConnection");
		
		new Thread(()-> {
			try {
				Gson gson = new Gson();
				sg = ScreenGame.getInstance();
				sg.setScreenGame(this);
				sr = ScreenResult.getInstance();
				sr.setScreenResult(this);
				if(cont==0) {
					//Fase de conexion y espera de rival
					System.out.println("Entro al hilo");
					socket = new Socket(IP, PORT);
					bwriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
					breader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					System.out.println("Espero mensaje de otro jugador");
					String msg = "";
					msg = breader.readLine();
					//String msgToPrint = gson.fromJson(msg, String.class);
					System.out.println(msg);
					playListener.showGamePlayer(msg);
				}else if(cont==1){
					//Caso en el que el jugador presiona Stop
					contAccess = 1;
					
					System.out.println("Aqui envio mensaje(ganador)");
					//bwriter.write(msgMyLetters+"\n");
					//String a = "Mensaje enviado desde ganador";
					String json = gson.toJson(msgWinner);
					bwriter.write(json+"\n");
					bwriter.flush();
					String rivalScore = "";
					System.out.println("Aqui espero (ganador)");
					rivalScore = breader.readLine();
					String msgToPrint = gson.fromJson(rivalScore, String.class);
					msgLosser = msgToPrint;
					//msgWinner = json;
					System.out.println("");
					System.out.println("Cliente ganador recibio: "+msgLosser);
					System.out.println("Cliente ganador tiene: "+msgWinner);
					System.out.println("Invoco interfaz resultado Ganador:D");
					
					
					
					Platform.runLater(()-> {
					finalListener.showFinalScreen(msgWinner,msgLosser);
					});

					//Invoco listener para que se cambie de pantalla
					//Pantalla de resultados
					/*
					sf = ScreenFinal.getInstance(msgMyLetters,msgYourLetters);
					finalListener.showFinalScreen();
					*/

					contAccess++;
					
				}else if(cont==2) {
					//Caso en el que es el jugador perdedor
					
					String rivalScore = "";
					System.out.println("Aqui espero (perdedor)");
					
					String lostMsg = "";
					lostMsg = breader.readLine();
					String msgTo = gson.fromJson(lostMsg, String.class);
					System.out.println("Mensaje de cliente perdedor: "+msgTo);
					if(msgTo.contains("YouLose")) {

						rivalScore = breader.readLine();
						String msgToPrint = gson.fromJson(rivalScore, String.class);
						System.out.println("Cliente perdedor recibe: "+msgToPrint);
						//Invocar obtener resultados
						
						playListener.saveLetters();
						//sg.saveLetters2();
						
						//String b = "Mensaje enviado perdedor";
						String toSend = gson.toJson(msgLosser);
						bwriter.write(toSend+"\n");
						bwriter.flush();
						msgWinner= msgToPrint;
						//msgLosser=toSend;
						System.out.println("Cliente perdedor tiene: "+msgLosser);
						System.out.println("Invoco interfaz resultado Perdedor:D");
					
						
						
						Platform.runLater(()-> {
							finalListener.showFinalScreen(msgLosser,msgWinner);
						});

						//sg = ScreenGame.getInstance();
						//sg.sendAlert();
					}
					
				}else if(cont==3) {
					System.out.println("Aqui envio mensaje(perdedor)");
					//bwriter.write(msgMyLetters+"\n");
					//Aqui se invoca la interfaz de resultado caso perdedor
					/*
					sf = ScreenFinal.getInstance(msgMyLetters,msgYourLetters);
					finalListener.showFinalScreen();
					*/
					msgWinner="";
					msgLosser="";
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
