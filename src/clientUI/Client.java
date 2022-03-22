package clientUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

import javafx.application.Platform;


public class Client{
	
	private static BufferedReader breader;


	public static void main(String [] args) throws IOException{

		try {
			
		Scanner scanner = new Scanner(System.in);
		String line = scanner.nextLine()+"\n";
		Socket socket = new Socket("127.0.0.1", 6000);
		
		breader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		boolean already = false;
		
		/*
		while(!already) {
			String playerTwo = breader.readLine();
			if(playerTwo == "Jugador encontrado") {
				already = true;
			}
		}
		*/
		
		//Ejecutar interfaz conexion inicial
		//Lanzar interfaz de stop
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}

	

	
}
