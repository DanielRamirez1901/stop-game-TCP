package clientUI;

import java.awt.TextField;

import clientConnection.ClientConnection;
import clientGeneral.Client;
import events.OnPlayerFoundListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ScreenGame implements OnPlayerFoundListener{
	
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
	    

	    
	    public void setScreenGame(Client c) {
			this.c=c;
			c.setPlayListener(this);
		}
	    
		@Override
		public void showGamePlayer() {
			
			System.out.println("A cargar interfaz");
			
		}
}
