package clientUI;

import clientConnection.ClientConnection;
import events.OnFinalScreenListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ScreenFinal implements OnFinalScreenListener{
	
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
	    Stage stage;
	    String myMsg="";
	    String yourMsg="";
	    int myScore=0;
	    int yourScore=0;
	    
	    
	    
	    //Instancia unica******************************************
	    
	    ClientConnection cc;
	    private static ScreenFinal instance;
		private ScreenFinal(String myMsg, String yourMsg) {	
			cc = ClientConnection.getInstance();
			cc.setFinalListener(this);;
			this.myMsg = myMsg;
			this.yourMsg = yourMsg;
		}
	    public static synchronized ScreenFinal getInstance(String myMsg,String yourMsg) {
	    	if(instance == null) {
				instance = new ScreenFinal(myMsg,yourMsg);
			}
			return instance;
	    }
	    
	    
	    //*********************************************************
	    
	    void finishConnection(ActionEvent event) {
	    	System.out.println("Aqui acabo la conexion");
	    }
	    
	    
		@Override
		public void showFinalScreen() {
			System.out.println("Aqui entro a showFinalScreen");
			//Aqui invoco la interfaz
			/*
			try {
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("answersScreen.fxml"));
				Parent p;
				p = (Parent) loader.load();
				Scene scene = new Scene(p);
			    stage = (Stage) anchor.getScene().getWindow();
				stage.setScene(scene);
				randomLetterlb.setText(assignRandomLetter());
				setMyLettersInInterface()
				setYourLettersInInterface()
				stage.show();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			//Luego de invocarla, hago split para obtener mensajes
			//Invoco metodo de obtener puntaje
			
			System.out.println("Acabo el cliente :D");
		}
		public void setStage(Stage stage) {
			this.stage = stage;
		}
		
		public void setMyLettersInInterface() {
			System.out.println("Entro a setMyLetters");
			
			String [] myLetters = myMsg.split(":");
			lblMyName.setText(myLetters[0]);
			lblMyAnimal.setText(myLetters[1]);
			lblMyCity.setText(myLetters[2]);
			lblMyThing.setText(myLetters[3]);
			
		}
		
		public void setYourLettersInInterface() {
			System.out.println("Entro a SetYouLetter(metodo final)");
			
			String [] yourLetters = yourMsg.split(":");
			lblYourName.setText(yourLetters[0]);
			calculateScore(lblMyName.getText(), lblYourName.getText());
			lblYourAnimal.setText(yourLetters[1]);
			calculateScore(lblMyAnimal.getText(), lblYourAnimal.getText());
			lblYourCity.setText(yourLetters[2]);
			calculateScore(lblMyCity.getText(), lblYourCity.getText());
			lblYourIt.setText(yourLetters[3]);
			calculateScore(lblMyThing.getText(), lblYourIt.getText());
			String myScoreString="";
			String yourScoreString="";
			myScoreString = String.valueOf(myScore);
			yourScoreString = String.valueOf(yourScore);
			lblMyScore.setText(myScoreString);
			lblYourScore.setText(yourScoreString);
			
		}
		
		public void calculateScore(String myScoremsg, String yourScoremsg) {
			if(yourScoremsg.isEmpty()) {
				yourScore+=0;
			}
			if(myScoremsg.isEmpty()) {
				myScore+=0;
			}
			if(!myScoremsg.isEmpty() && myScoremsg.equals(yourScoremsg)) {
				myScore+=50;
				yourScore+=50;
			}
			if(!myScoremsg.isEmpty()) {
				myScore+=100;
			}
			if(!yourScoremsg.isEmpty()) {
				yourScore+=100;
			}
			
		}
		
		
}
