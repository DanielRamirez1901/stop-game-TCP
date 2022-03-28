package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.UUID;

public class Session extends Thread{
	
	//Conection
	private Socket socket1;
	private Socket socket2;
	
	//Writer & Reader
	private BufferedWriter bwriter1;
	private BufferedReader breader1;
	private BufferedWriter bwriter2;
	private BufferedReader breader2;
	
	private OnMessageListener listener;
	
	//Constructor
	public Session(Socket socket1, Socket socket2) {
		this.socket1 = socket1;
		this.socket2 = socket2;
	}
	
	@Override
	public void run() {
		
		try {
			System.out.println("Ya cree una sesion de juego");
			bwriter1 = new BufferedWriter(new OutputStreamWriter(socket1.getOutputStream()));
			breader1 = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
			bwriter2 = new BufferedWriter(new OutputStreamWriter(socket2.getOutputStream()));
			breader2 = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
			//Aqui les aviso a los dos jugadores que deben jugar
			bwriter1.write("Ya llego tu rival 1"+"\n");
			bwriter1.flush();
			bwriter2.write("Ya llego tu rival 2"+"\n");
			bwriter2.flush();
			System.out.println("Ya envie el mensaje a los dos jugadores");
			
			//Aqui espero a ambos jugadores por sus respuestas
			//Si el socket1 me envia algo, le envio esa respuesta al socket2
			//Si el socket2 me envia algo, le envio esa respuesta al socket1
			//Debo hacer ambos procesos en hilos
			new Thread(()-> {
				//Hilo espera cliente 1
				String msgClient1 = "";
				try {
					msgClient1 = breader1.readLine();//Respuestas cliente 1
					bwriter2.write(msgClient1);//Le envio a cliente 2
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}).start();
			
			new Thread(()-> {
				//Hilo espera cliente 2
				String msgClient2 = "";
				try {
					msgClient2 = breader1.readLine();//Respuestas cliente 2
					bwriter1.write(msgClient2);//Le envio a cliente 1
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}).start();
			
			breader1.readLine();//Espero mensaje de finalizar cliente 1
			breader2.readLine();//Espero mensaje de finalizar cliente 2
		}catch(IOException ex) {
			ex.printStackTrace();
		}
		
	}
	
	
	public void setListener(OnMessageListener listener) {this.listener = listener;}


	//Interface
	public interface OnMessageListener {void onMessage(String line);}

}
