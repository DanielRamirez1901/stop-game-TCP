package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.UUID;

import com.google.gson.Gson;

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
			String a = "Ya llego tu rival 1";
			String toSendC1 = gson.toJson(a);
			bwriter1.write(toSendC1+"\n");
			bwriter1.flush();
			String b = "Ya llego tu rival 2";
			String toSendC2 = gson.toJson(b);
			bwriter2.write(toSendC2+"\n");
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
					if(msgToSend == "Stop") {
						//Caso en el que el cliente 1 es ganador
						bwriter1.write("StopWinner"+"\n");
						bwriter1.flush();
						bwriter2.write("StopLosser"+"\n");
						bwriter2.flush();
						String msgToSendLosser = "";
						msgToSendLosser = breader1.readLine();
						bwriter2.write(msgToSendLosser+"/n");
						bwriter2.flush();
						String msgToSendWinner = "";
						msgToSendWinner = breader2.readLine();
						bwriter1.write(msgToSendWinner+"\n");
						bwriter1.flush();
					}else {
						//Caso en el que cliente 1 es perdedor
						
					}
					/*
					String msgToResend = gson.toJson(msgToSend);
					bwriter2.write(msgToResend+"\n");//Le envio a cliente 2
					bwriter2.flush();
					System.out.println("Respuesta de cliente 1 enviada");
					*/
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
					msgClient2 = breader2.readLine();//Respuestas cliente 1
					String msgToSend = gson.fromJson(msgClient2, String.class);
					System.out.println("Mensaje recibido cliente 2: "+msgToSend);
					if(msgToSend == "Stop") {
						//Caso en el que el cliente 2 es ganador
						bwriter1.write("StopWinner"+"\n");
						bwriter1.flush();
						bwriter2.write("StopLosser"+"\n");
						bwriter2.flush();
						String msgToSendLosser = "";
						msgToSendLosser = breader2.readLine();
						bwriter1.write(msgToSendLosser+"/n");
						bwriter1.flush();
						String msgToSendWinner = "";
						msgToSendWinner = breader1.readLine();
						bwriter2.write(msgToSendWinner+"\n");
						bwriter2.flush();
					}
					/*
					System.out.println("Esperando rpta cliente 2");
					msgClient2 = breader2.readLine();//Respuestas cliente 2
					String msgToSend = gson.fromJson(msgClient2, String.class);
					System.out.println("Mensaje recibido cliente 2:"+msgToSend);
					String msgToResend = gson.toJson(msgToSend);
					bwriter1.write(msgToResend+"\n");//Le envio a cliente 1
					bwriter1.flush();
					System.out.println("Respuesta de cliente 2 enviada");
					*/
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


	//Interface
	public interface OnMessageListener {void onMessage(String line);}

}
