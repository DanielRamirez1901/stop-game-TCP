package comm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.UUID;

public class Session extends Thread{
	
	//Conection
	private Socket socket;
	
	//Writer & Reader
	private BufferedWriter bwriter;
	private BufferedReader breader;
	
	private OnMessageListener listener;
	
	//Constructor
	public Session(Socket socket) {this.socket = socket;}
	
	@Override
	public void run() {
		
		try {
			
			bwriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			breader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			while(true) {
				String line = breader.readLine();
				listener.onMessage(line);
			}
			
		}catch(IOException ex) {
			ex.printStackTrace();
		}
		
	}
	
	
	public void setListener(OnMessageListener listener) {this.listener = listener;}


	//Interface
	public interface OnMessageListener {void onMessage(String line);}

}
