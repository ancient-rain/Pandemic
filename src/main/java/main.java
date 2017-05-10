
import game.GameController;
import game.GameModel;
import game.GameView;

public class main {
	
	public static void main(String [] args) {
		GameModel model = new GameModel();
		GameController controller = new GameController(model);
		GameView view = new GameView(controller);
		
		view.viewGame();
		view.repaint();
	}
}
