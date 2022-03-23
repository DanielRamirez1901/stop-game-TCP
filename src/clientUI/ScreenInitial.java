package clientUI;

import java.io.IOException;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ScreenInitial {
	
	@FXML
    private AnchorPane anchorPaneInit;
	
	public Client cl = new Client();
	
	private OnMessageListener listener;
	
	 @FXML
	 void showWaiting(ActionEvent event) {
		 
			try {
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("loadingScreen.fxml"));
				Parent p;
				p = (Parent) loader.load();
				Scene scene = new Scene(p);
				Stage stage = (Stage) anchorPaneInit.getScene().getWindow();
				stage.setScene(scene);
				stage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			cl.setScreenInitial(this);
			listener.onMessage();
			
	 }
	 
	 
	 
	 public void setListener(OnMessageListener listener) {this.listener = listener;}
	 
	 public interface OnMessageListener {void onMessage();}
		

}
