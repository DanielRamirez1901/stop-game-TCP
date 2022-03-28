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
	
	//Unica instancia************************
	private static ClientConnection instance;
	private ClientConnection() {}
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
	
	//*************************
	OnPlayerFoundListener playListener;
	OnFinalScreenListener finalListener;
	public ScreenGame sg ;
	public ScreenFinal sf;
	public ScreenInitial si;
	Client cl;
	Stage stage;
	String msgMyLetters="";
	String msgYourLetters="";

	
	public void startConnection(int cont) {
		
		System.out.println("Ingreso a startConnection");
		
		new Thread(()-> {
			try {
				Gson gson = new Gson();
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
					sg = ScreenGame.getInstance();
					playListener.showGamePlayer(0);
				}else if(cont==1){
					//Caso en el que el jugador presiona Stop
					System.out.println("Aqui envio mensaje(ganador)");
					//bwriter.write(msgMyLetters+"\n");
					String a = "Mensaje enviado desde cliente 1";
					String json = gson.toJson(a);
					bwriter.write(json+"\n");
					bwriter.flush();
					String rivalScore = "";
					System.out.println("Aqui espero (ganador)");
					rivalScore = breader.readLine();
					String msgToPrint = gson.fromJson(rivalScore, String.class);
					msgYourLetters = msgToPrint;
					System.out.println("Cliente 1 recibio: "+msgToPrint);
					System.out.println("Invoco interfaz resultado :D");
					//Invoco listener para que se cambie de pantalla
					//Pantalla de resultados
					sf = ScreenFinal.getInstance(msgMyLetters,msgYourLetters);
					finalListener.showFinalScreen();
					msgMyLetters="";
					msgYourLetters="";
				}else if(cont==2) {
					//Caso en el que es el jugador perdedor
					String rivalScore = "";
					System.out.println("Aqui espero (perdedor)");
					rivalScore = breader.readLine();
					String msgToPrint = gson.fromJson(rivalScore, String.class);
					System.out.println("Cliente 2 recibio: "+msgToPrint);
					msgYourLetters= msgToPrint;
					sg = ScreenGame.getInstance();
					sg.sendAlert();
				}else if(cont==3) {
					System.out.println("Aqui envio mensaje(perdedor)");
					//bwriter.write(msgMyLetters+"\n");
					String b = "Mensaje enviado desde cliente 2";
					String toSend = gson.toJson(b);
					bwriter.write(toSend+"\n");
					bwriter.flush();
					System.out.println("Invoco interfaz resultado :D");
					//Aqui se invoca la interfaz de resultado caso perdedor
					sf = ScreenFinal.getInstance(msgMyLetters,msgYourLetters);
					finalListener.showFinalScreen();
					msgMyLetters="";
					msgYourLetters="";
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
		this.msgMyLetters = msgToSend;
	}
	public void setFinalListener(OnFinalScreenListener finalListener) {
		this.finalListener = finalListener;
	}
	
	
	
	
}
