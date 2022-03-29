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
import events.OnFinalScreenListener;
import events.OnPlayerFoundListener;
import javafx.application.Platform;
import javafx.stage.Stage;

public class ClientConnection {
	
	//*************************
	OnPlayerFoundListener playListener;
	OnFinalScreenListener finalListener;
	public ScreenGame sg ;
	public ScreenFinal sf;
	public ScreenInitial si;
	Client cl;
	Stage stage;
	String msgWinner = "";
	String msgLosser = "";

	
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
				sg = new ScreenGame(stage);
				if(cont==0) {
					//Fase de conexion y espera de rival
					System.out.println("Entro al hilo");
					socket = new Socket(IP, PORT);
					bwriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
					breader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					System.out.println("Espero mensaje de otro jugador");
					String msg = "";
					msg = breader.readLine();
					String msgToPrint = gson.fromJson(msg, String.class);
					System.out.println(msgToPrint);
					sg.setScreenGame(this);
					playListener.showGamePlayer(0);
				}else if(cont==1){
					//Caso en el que el jugador presiona Stop
					System.out.println("A:Soy el ganador, presione Stop");
					//bwriter.write(msgMyLetters+"\n");
					String a = "A: Mensaje del ganador";
					String json = gson.toJson(a);
					bwriter.write(json+"\n");
					bwriter.flush();
					msgWinner = json;
					String rivalScore = "";
					System.out.println("A: Aqui espero al perdedor");
					rivalScore = breader.readLine();
					String msgToPrint = gson.fromJson(rivalScore, String.class);
					msgLosser = msgToPrint;
					System.out.println("A: Cliente ganador recibio: "+msgToPrint);
					System.out.println("A: Invoco interfaz resultado :D");
					System.out.println("A:Mi respuesta: "+msgWinner);
					System.out.println("A:Respuesta del perdedor: "+msgLosser);
					//Invoco listener para que se cambie de pantalla
					//Pantalla de resultados
					/*
					sf = ScreenFinal.getInstance(msgMyLetters,msgYourLetters);
					finalListener.showFinalScreen();
					*/
					msgWinner="";
					msgLosser="";
				}else if(cont==2) {
					//Caso en el que es el jugador perdedor
					String rivalScore = "";
					System.out.println("B:Perdedor esperando");
					rivalScore = breader.readLine();
					String msgToPrint = gson.fromJson(rivalScore, String.class);
					System.out.println("B:Cliente recibio: "+msgToPrint);
					msgWinner= msgToPrint;
					//sg = ScreenGame.getInstance();
					sg.sendAlert();
				}else if(cont==3) {
					System.out.println("B: Aqui envio mensaje");
					//bwriter.write(msgMyLetters+"\n");
					String b = "B: Mensaje enviado desde el perdedor ";
					String toSend = gson.toJson(b);
					bwriter.write(toSend+"\n");
					bwriter.flush();
					msgLosser = toSend;
					System.out.println("Invoco interfaz resultado :D");
					System.out.println("B:Mi respuesta: "+msgLosser);
					System.out.println("B:Respuesta del ganador: "+msgWinner);
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
	public void setMsgToSend(String msgToSend) {
		this.msgWinner = msgToSend;
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
