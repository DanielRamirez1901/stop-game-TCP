package clientUI;

import java.io.BufferedReader;



public class Client extends Thread  implements ScreenInitial.OnMessageListener{
	
	private static Client instance;
	
	//Con esto hago que todo se apunte a un unico cliente
	public static synchronized Client getInstance() {
		if(instance == null) {
			instance = new Client();
		}
		return instance;
	}
	
//	@FXML
    //private AnchorPane anchorPane;
	
	private static BufferedReader breader;
	
	public ScreenInitial sc;
	
	
	//public final static long TIME=1000;
	
	private Client() {
		//sc.setListener(this);
	}
	
	public void setScreenInitial() {
		//this.sc=sc;
		sc.setListener(this);
	}
	
	
	
	



	@Override
	public void run()  {
		//showLoading();
			//try {
			/*
			Scanner scanner = new Scanner(System.in);
			String line = scanner.nextLine()+"\n";
			Socket socket = new Socket("127.0.0.1", 6000);
			
			breader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			boolean already = false;*/
			//openWindow();
		
			
				//main.setAnchorPane();
				
					
					
					
				
				
			
				
			
			
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
			
			//} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			//}
			//LLAMA A LA CONTROLADORA DE LA SEGUNDA VENTANA Y ABRE LA SEGUNDA VENTANA
			
		
		//Hacer el .start en cliente
		
	}
	/*
	public void showLoading() {
		
		
			
			try {
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("gameScreen.fxml"));
				Parent p;
				p = (Parent) loader.load();
				Scene scene = new Scene(p);
				Stage stage = (Stage) anchorPane.getScene().getWindow();
				stage.setScene(scene);
				stage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			});
		
	}*/







	@Override
	public void onMessage() {
		System.out.println("Genero la conexion");
		//Hilos para no bloquear interfaz
		//Generar la conexion con un run later
		//En cuanto encuentre otro jugador, hacer listener 
		//para notificar a la otra interfaz de que cargue juego
	}
	
	/*public void openWindow() throws IOException {
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("gameScreen.fxml"));
		Parent p = (Parent) loader.load();
		
		Scene scene = new Scene(p);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
	}*/
	
	
	
	
	
}
