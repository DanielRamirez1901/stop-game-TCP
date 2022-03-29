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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
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
	
	public ScreenGame() {
		
		/*
		cc = ClientConnection.getInstance();
		cc.setPlayListener(this);
		*/
	}
	
   public static synchronized ScreenGame getInstance() {
   	if(instance == null) {
			instance = new ScreenGame();
		}
		return instance;
   }
   
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
	    	if(!nametxt.getText().isEmpty()&&!animaltxt.getText().isEmpty()&&!citytxt.getText().isEmpty()&&!ittxt.getText().isEmpty()) {
	    		String msgToSend = "";
	    		msgToSend = nametxt.getText()+":"+animaltxt.getText()+":"+citytxt.getText()+":"+ittxt.getText();
	    		cc.setMsgWinner(msgToSend);
	    		Platform.runLater(()-> {
		    		cc.startConnection(1);
		    	});
	    		
	    	}else {
	    		completeTheText(event);
	    	}
	    	//msgToSend = nametxt.getText()+":"+animaltxt.getText()+":"+citytxt.getText();
	    	//msgToSend = ":"+ittxt.getText();
	    	//cc.setMsgToSend(msgToSend);

	    }


	    
		@Override
		public void showGamePlayer(String letter) {
			
			Platform.runLater(()-> {
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
					randomLetterlb.setText(letter);
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
				
				
				Platform.runLater(()-> {
		    		cc.startConnection(2);
				});
		    	
				
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
	    	//cc.setMsgToSend(msgToSend);
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
		
		
		 @FXML
		    public void completeTheText(ActionEvent event) {
		        Alert alert = new Alert(AlertType.INFORMATION);
		        alert.setTitle("Error");
		        alert.setHeaderText(null);
		        alert.setContentText("Porfavor, no deje campos de texto vacios :D");
		        alert.showAndWait();
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



		@Override
		public void saveLetters() {
			
   		String msgToSend = "";
   		if(nametxt.getText()==null||nametxt.getText().equals("")||nametxt.getText().isEmpty()) {
   			nametxt.setText("-");
   		}
   		if(animaltxt.getText()==null||animaltxt.getText().equals("")||animaltxt.getText().isEmpty()) {
   			animaltxt.setText("-");
   		}
   		if(citytxt.getText()==null||citytxt.getText().equals("")||citytxt.getText().isEmpty()) {
   			citytxt.setText("-");
   		}
   		if(ittxt.getText()==null||ittxt.getText().equals("")||ittxt.getText().isEmpty()) {
   			ittxt.setText("-");
   		}


   		msgToSend = nametxt.getText()+":"+animaltxt.getText()+":"+citytxt.getText()+":"+ittxt.getText();
   		//System.out.println("Nombre: "+nametxt.getText());
   		cc.setMsgLosser(msgToSend);
			
			
		}
		
		public void saveLetters2() {
			System.out.println("KHa");
			
			String msgToSend = "";
   		//msgToSend = nametxt.getText()+":"+animaltxt.getText()+":"+citytxt.getText()+":"+ittxt.getText();
   		System.out.println("Nombre: "+nametxt.getText());
   		//cc.setMsgLosser(msgToSend);
			
		}
}
