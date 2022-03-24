package clientUI;

import java.awt.TextField;

import clientGeneral.Client;
import events.OnPlayerFoundListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ScreenGame implements OnPlayerFoundListener{
	
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
	    
	    public void setScreenGame(Client c) {
			this.c=c;
			c.setPlayListener(this);
		}
	    
		@Override
		public void showGamePlayer() {
			System.out.println("a cargar interfaz");
			
		}
}
