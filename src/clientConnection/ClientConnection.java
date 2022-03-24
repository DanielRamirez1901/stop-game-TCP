package clientConnection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

import clientGeneral.Client;
import javafx.application.Platform;

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
	
	public void startConnection() {
		
		
		System.out.println("Comienzo conexion");
		
		new Thread(()-> {
		try {
			
			System.out.println("Entro al hilo");
			socket = new Socket(IP, PORT);
			bwriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			breader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println("Espero mensaje de otro jugador");
			String msg = "";
			msg = breader.readLine();
			System.out.println(msg);
			//Crear listener para invocar el metodo ventanaB
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}).start();
		
		
		
	}
}
