package comm;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	public static void main(String [] args) throws IOException{
		

			while(true) {
				Scanner scanner = new Scanner(System.in);
				String line = scanner.nextLine()+"\n";
				
				Socket socket = new Socket("127.0.0.1", 6000);
				
			}
			
		
		
	}
	
}
