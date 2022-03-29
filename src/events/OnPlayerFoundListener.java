package events;

import javafx.stage.Stage;

public interface OnPlayerFoundListener {
	void showGamePlayer(String letter);
	void saveLetters();
}
