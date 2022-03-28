package clientUI;

import java.io.IOException;
import java.util.Random;

import clientConnection.ClientConnection;
import clientGeneral.Client;
import events.OnPlayerFoundListener;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ScreenGame implements OnPlayerFoundListener{
	
	 Stage stage;
     ScreenInitial si;
     MainClient m;
	 Client c;
	 ClientConnection cc = ClientConnection.getInstance();
	 SaveStageToUse save;
	 Stage stageToShare;
     
	//Instancia unica***************************
	private static ScreenGame instance;
	
	public ScreenGame(Stage stage) {
		this.stage = stage;
		/*
		cc = ClientConnection.getInstance();
		cc.setPlayListener(this);
		*/
	}
	/*
    public static synchronized ScreenGame getInstance() {
    	if(instance == null) {
			instance = new ScreenGame();
		}
		return instance;
    }
    */
	//*******************************************
    
    //FXML things
        
        @FXML
        private AnchorPane anchorGame;

        @FXML
        private TextField nametxt;

        @FXML
        private TextField animaltxt;

        @FXML
        private TextField citytxt;

        @FXML
        private TextField ittxt;

        @FXML
        private Label randomLetterlb;
        
        String letters[] = new String[3];
        
	    @FXML
	    void stopbutt(ActionEvent event) {
	    	String msgToSend = "";
	    	msgToSend = nametxt.getText()+":"+animaltxt.getText()+":"+citytxt.getText();
	    	msgToSend = ":"+ittxt.getText();
	    	cc.setMsgToSend(msgToSend);
	    	Platform.runLater(()->{
	    		cc.startConnection(1);
	    	});
	    }


	    
		@Override
		public void showGamePlayer(int cont) {
			
			 Platform.runLater(()->{
				 	FXMLLoader loader = new FXMLLoader(getClass().getResource("gameScreen.fxml"));
					Parent p;
				try {
					loader.setController(this);
					p = (Parent) loader.load();
					Scene scene = new Scene(p);
					
					
					
					save = SaveStageToUse.getInstance();
					stage = save.getStage();
					System.out.println("Esta es la escena de ScreenGame: "+stage);
					//stage =  (Stage) anchorGame.getScene().getWindow();
					stage.setScene(scene);
					
					stage.show();
					
					
					
					//stage.setResizable(false);
					//stage.setScene(scene);
					//randomLetterlb.setText(assignRandomLetter());
					
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 });
			
				System.out.println("Aqui muestro Stop game");
				//Aqui invoco hilo que entre en modo lectura(cont 2)
				//Si detecto mensaje, detengo el juego
				
				//Borrar luego*****************************************
				/*
				Platform.runLater(()->{
		    		cc.startConnection(1);
		    	});
				*/
				//
				/*
				Platform.runLater(()->{
		    		cc.startConnection(2);
		    	});
		    	*/
		}
		
		public void sendAlert() {
			System.out.println("Aqui entro en sendAlert");
			String msgToSend = "";
	    	//msgToSend = nametxt.getText()+":"+animaltxt.getText()+":"+citytxt.getText();
	    	//msgToSend = ":"+ittxt.getText();
	    	cc.setMsgToSend(msgToSend);
	    	Platform.runLater(()->{
	    		cc.startConnection(3);
	    	});
		}

		private void assignLetters() {
			letters[0] = nametxt.getText();
			letters[1] = animaltxt.getText();
			letters[2] = citytxt.getText();
			letters[3] = ittxt.getText();
		}
		
		private String assignRandomLetter() {
			 Random random = new Random();
			 char randomizedCharacter = (char) (random.nextInt(26) + 'a');
			 String randomLetter = String.valueOf(randomizedCharacter);
			 return randomLetter;
		}

		
	    //*********************Setters&Getters*********************

		public void setStage(Stage stage) {
			this.stage = stage;
		}

		public void setScreenGame(ClientConnection cc) {
			this.cc=cc;
			
			cc.setPlayListener(this);
			//this.stage = stage;
		}
}
