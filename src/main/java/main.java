import game.GameController;
import game.GameModel;
import game.GameView;

public class main {
	
	public static void main(String [] args) {
		GameModel model = new GameModel();
		GameView view = new GameView(model);
		
		view.viewGame();
	}
}
