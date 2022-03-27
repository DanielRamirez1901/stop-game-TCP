package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerPrueba {

	public static void main(String[] args) throws IOException{
		while(true) {
		ServerSocket server = new ServerSocket(6100);
		System.out.println("Esperando cliente...");
		Socket socket = server.accept();
		System.out.println("Nuevo cliente conectado!");
		System.out.println("Entró en el puerto: " + socket.getPort());
		InputStream is = socket.getInputStream();
		OutputStream os = socket.getOutputStream();	
		BufferedWriter bwriter = new BufferedWriter(new OutputStreamWriter(os));
		BufferedReader breader = new BufferedReader(new InputStreamReader(is));
		bwriter.write("Holiwiris"+"\n");
		bwriter.flush();
		bwriter.write("A"+"\n");
		bwriter.flush();
		server.close();
		}
	}
	

}
