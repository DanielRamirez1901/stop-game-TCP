package server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import server.Session;

public class Main implements Session.OnMessageListener{

	
	public static void main(String[] args) throws IOException{
		new Main();
	}
	
	private ArrayList<Session> sessions;
	
	public Main() throws IOException {
		
		sessions = new ArrayList<>();
		ServerSocket server = new ServerSocket(6100);
		
		while(true) {
			System.out.println("Esperando cliente...");
			Socket socket1 = server.accept();
			System.out.println("Nuevo cliente conectado!");
			System.out.println("Entró en el puerto: " + socket1.getPort());
			System.out.println("Esperando el segundo cliente...");
			Socket socket2 = server.accept();
			System.out.println("El otro cliente entro al puerto:" + socket2.getPort());
			new Thread(()-> {
				startSession(socket1, socket2);
			}).start();			
		}
		
	}
	
	
	
	
	@Override
	public void onMessage(String line) {
		
		System.out.println("Conexión Multicliente creada");
		
	}
	
	public void startSession(Socket socket1, Socket socket2) {
		System.out.println("Empiezo una sesion de juego");
		Session session = new Session(socket1, socket2);
		session.setListener(this);
		session.start();
		sessions.add(session);
	}
	
	

}
