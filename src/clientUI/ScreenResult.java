package clientUI;

import java.io.IOException;

import clientConnection.ClientConnection;
import events.OnFinalScreenListener;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ScreenResult implements OnFinalScreenListener{

	ClientConnection cc = ClientConnection.getInstance();
	SaveStageToUse save;
	Stage stage;
	
	private static ScreenResult instance;
	
	public ScreenResult() {
		
		/*
		cc = ClientConnection.getInstance();
		cc.setPlayListener(this);
		*/
	}
	
   public static synchronized ScreenResult getInstance() {
   	if(instance == null) {
			instance = new ScreenResult();
		}
		return instance;
   }
   
   
   @FXML
   private AnchorPane anchor;

   @FXML
   private Label lblYourName;

   @FXML
   private Label lblMyName;

   @FXML
   private Label lblMyAnimal;

   @FXML
   private Label lblYourAnimal;

   @FXML
   private Label lblMyCity;

   @FXML
   private Label lblYourCity;

   @FXML
   private Label lblMyThing;

   @FXML
   private Label lblYourIt;

   @FXML
   private Label lblMyScore;

   @FXML
   private Label lblYourScore;

   @FXML
   void finishConnection(ActionEvent event) {

   }
   
   
   
	
	@Override
	public void showFinalScreen() {

		Platform.runLater(()-> {
		 	FXMLLoader loader = new FXMLLoader(getClass().getResource("answersScreen.fxml"));
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
		
	}
	
	public void setScreenResult(ClientConnection cc) {
		this.cc=cc;
		cc.setFinalListener(this);
	}

}
