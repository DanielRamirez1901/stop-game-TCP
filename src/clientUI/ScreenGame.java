package clientUI;

import java.awt.TextField;
import java.io.IOException;

import clientConnection.ClientConnection;
import clientGeneral.Client;
import events.OnPlayerFoundListener;
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
	private ScreenGame(Stage stage) {	
		cc = ClientConnection.getInstance();
		cc.setPlayListener(this);
		this.stage = stage;
	}
    public static synchronized ScreenGame getInstance(Stage stage) {
    	if(instance == null) {
			instance = new ScreenGame(stage);
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
        
	    @FXML
	    void stopbutt(ActionEvent event) {

	    }

	    Client c;
	    ClientConnection cc;
	    
		@Override
		public void showGamePlayer() {
			
				try {
					FXMLLoader loader = new FXMLLoader(Main.class.getResource("gameScreen.fxml"));
					Parent p;
					p = (Parent) loader.load();
					Scene scene = new Scene(p);
				    stage = (Stage) anchorGame.getScene().getWindow();
					stage.setScene(scene);
					stage.show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("f");
		}


		
	    //*********************Setters&Getters*********************

		public void setStage(Stage stage) {
			this.stage = stage;
		}

}
