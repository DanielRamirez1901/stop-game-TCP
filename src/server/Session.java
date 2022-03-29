package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Random;
import java.util.UUID;

import com.google.gson.Gson;

import server.Session.OnMessageListener;

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
		Gson gson = new Gson();
		
		try {
			System.out.println("Ya cree una sesion de juego");
			bwriter1 = new BufferedWriter(new OutputStreamWriter(socket1.getOutputStream()));
			breader1 = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
			bwriter2 = new BufferedWriter(new OutputStreamWriter(socket2.getOutputStream()));
			breader2 = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
			//Aqui les aviso a los dos jugadores que deben jugar
			String a = assignRandomLetter();
			String toSendC1 = gson.toJson(a);
			bwriter1.write(toSendC1+"\n");
			bwriter1.flush();
			bwriter2.write(toSendC1+"\n");
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
					System.out.println("Esperando rpta cliente 1");
					msgClient1 = breader1.readLine();//Respuestas cliente 1
					String msgToSend = gson.fromJson(msgClient1, String.class);
					System.out.println("Mensaje recibido cliente 1: "+msgToSend);
					String msgToResend = gson.toJson(msgToSend);
					String snd = "YouLose";
					String msgToLost = gson.toJson(snd);
					bwriter2.write(msgToLost+"\n");
					bwriter2.flush();
					bwriter2.write(msgToResend+"\n");//Le envio a cliente 2
					bwriter2.flush();
					System.out.println("Respuesta de cliente 1 enviada");
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}).start();
			
			new Thread(()-> {
				//Hilo espera cliente 2
				String msgClient2 = "";
				try {
					System.out.println("Esperando rpta cliente 2");
					msgClient2 = breader2.readLine();//Respuestas cliente 2
					String msgToSend = gson.fromJson(msgClient2, String.class);
					System.out.println("Mensaje recibido cliente 2:"+msgToSend);
					String msgToResend = gson.toJson(msgToSend);
					String snd = "YouLose";
					String msgToLost = gson.toJson(snd);
					bwriter1.write(msgToLost+"\n");
					bwriter1.flush();
					bwriter1.write(msgToResend+"\n");//Le envio a cliente 1
					bwriter1.flush();
					System.out.println("Respuesta de cliente 2 enviada");
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}).start();
			System.out.println("Ya envie las respuestas a los dos");
			
		}catch(IOException ex) {
			ex.printStackTrace();
		}
		
	}
	
	
	public void setListener(OnMessageListener listener) {this.listener = listener;}

	private String assignRandomLetter() {
		 Random random = new Random();
		 char randomizedCharacter = (char) (random.nextInt(26) + 'a');
		 String randomLetter = String.valueOf(randomizedCharacter);
		 return randomLetter;
	}

	//Interface
	public interface OnMessageListener {void onMessage(String line);}

}
