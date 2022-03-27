package clientUI;

import java.awt.TextField;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ScreenGame implements OnPlayerFoundListener{
	
	 Stage stage;
     ScreenInitial si;
	//Instancia unica
	private static ScreenGame instance;
	private ScreenGame() {	
		cc = ClientConnection.getInstance();
		cc.setPlayListener(this);
		
	}
    public static synchronized ScreenGame getInstance() {
    	if(instance == null) {
			instance = new ScreenGame();
		}
		return instance;
    }
	
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

	    Client c;
	    ClientConnection cc;
	    
		@Override
		public void showGamePlayer(int cont) {
			//Descomentar metodo cuando cargue bien el screen
			/*
				try {
					FXMLLoader loader = new FXMLLoader(Main.class.getResource("gameScreen.fxml"));
					Parent p;
					p = (Parent) loader.load();
					Scene scene = new Scene(p);
				    stage = (Stage) anchorGame.getScene().getWindow();
					stage.setScene(scene);
					randomLetterlb.setText(assignRandomLetter());
					stage.show();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				System.out.println("Aqui muestro Stop game");
				//Aqui invoco hilo que entre en modo lectura(cont 2)
				//Si detecto mensaje, detengo el juego
				Platform.runLater(()->{
		    		cc.startConnection(2);
		    	});
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

}