
import java.util.ArrayList;
import java.util.List;

import characters.CharacterModel;
import city.CityModel;
import diseases.DiseaseModel;
import game.GameController;
import game.GameModel;
import game.GameView;

public class main {
	
	public static void main(String [] args) {
		GameModel model = new GameModel();
		
		// This will be retrieved from our other menus
		DiseaseModel blue = new DiseaseModel();
		CityModel atlanta = new CityModel("Atlanta", blue);
		CharacterModel medic = new CharacterModel("Medic", atlanta);
		CharacterModel scientist = new CharacterModel("Scientist", atlanta);
		List<CharacterModel> characters = new ArrayList<>();
		
		characters.add(medic);
		characters.add(scientist);
		
		model.setCharacters(characters);
		
		GameController controller = new GameController(model);
		GameView view = new GameView(controller);
		
		view.viewGame();
		view.repaint();
	}
}
