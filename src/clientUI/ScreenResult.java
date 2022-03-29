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
	String myMsg = "";
	String yourMsg = "";
	int myScore = 0;
	int youScore = 0;
	
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
	   	stage.close();
	   	
   }
   
   
   
	
	@Override
	public void showFinalScreen(String myMessage, String yourMessage) {
		this.myMsg = myMessage;
		this.yourMsg = yourMessage;
		System.out.println("La clase final recibe:");
		System.out.println(myMsg);
		System.out.println(yourMsg);
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
			setMyLettersInInterface();
			setYourLettersInInterface();
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
	
	public void setMyLettersInInterface() {
		System.out.println("Entro a setMyLetters");
		String [] myLetters = myMsg.split(":");
		//System.out.println("Esto esta en array"+myLetters[2]);
		lblMyName.setText(myLetters[0]);
		lblMyAnimal.setText(myLetters[1]);
		lblMyCity.setText(myLetters[2]);
		lblMyThing.setText(myLetters[3]);
		
	}
	
	public void setYourLettersInInterface() {
		
		System.out.println("Entro a SetYouLetter(metodo final)");
		String [] yourLetters = yourMsg.split(":");
		lblYourName.setText(yourLetters[0]);
		lblYourAnimal.setText(yourLetters[1]);
		lblYourCity.setText(yourLetters[2]);
		lblYourIt.setText(yourLetters[3]);
		calculateScore(lblMyName.getText(), lblYourName.getText());
		calculateScore(lblMyAnimal.getText(), lblYourAnimal.getText());
		calculateScore(lblMyCity.getText(), lblYourCity.getText());
		calculateScore(lblMyThing.getText(), lblYourIt.getText());
		String myScoreString="";
		String yourScoreString="";
		myScoreString = String.valueOf(myScore);
		yourScoreString = String.valueOf(youScore);
		lblMyScore.setText(myScoreString);
		lblYourScore.setText(yourScoreString);
		
	}
	
	public void calculateScore(String myScoremsg, String yourScoremsg) {
		


		if(yourScoremsg == "-") {
			youScore+=youScore+0;
		}
		if(myScoremsg == "-") {
			myScore+=myScore+0;
		}
		if(myScoremsg != "-" && myScoremsg.equals(yourScoremsg)) {
			myScore+=myScore+50;
			youScore+=youScore+50;
		}
		if(myScoremsg != "-" && yourScoremsg == "-") {
			myScore+=myScore+100;
		}
		if(yourScoremsg != "-" && myScoremsg == "-") {
			youScore+=youScore+100;
		}

		
	}
	
	public void setScreenResult(ClientConnection cc) {
		this.cc=cc;
		cc.setFinalListener(this);
	}

	public void setMyMsg(String myMsg) {
		this.myMsg = myMsg;
	}

	public void setYourMsg(String yourMsg) {
		this.yourMsg = yourMsg;
	}
	
	

}
