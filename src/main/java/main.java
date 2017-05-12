
import java.util.ArrayList;
import java.util.List;

import cards.AbstractDeckCardController;
import cards.InfectionDeckCardController;
import cards.PlayerDeckCardController;
import characters.CharacterModel;
import city.CityController;
import city.CityModel;
import diseases.DiseaseController;
import diseases.DiseaseModel;
import game.GameController;
import game.GameModel;
import game.GameView;

public class main {
	
	public static void main(String [] args) {
		GameModel gameModel = new GameModel();
		DiseaseController diseaseController = new DiseaseController();
		CityController cityController = new CityController(diseaseController);
		AbstractDeckCardController playerDeckController = new PlayerDeckCardController(cityController);
		AbstractDeckCardController infectionDeckController = new InfectionDeckCardController(cityController);
		
		// This will be retrieved from our other menus
		DiseaseModel blue = new DiseaseModel();
		CityModel atlanta = new CityModel("Atlanta", blue);
		CharacterModel medic = new CharacterModel("Medic", atlanta);
		CharacterModel scientist = new CharacterModel("Scientist", atlanta);
		List<CharacterModel> characters = new ArrayList<>();
		
		medic.setName("Ralph");
		scientist.setName("Bob");
		
		characters.add(medic);
		characters.add(scientist);
		characters.add(medic);
		characters.add(scientist);
		
		gameModel.setCharacters(characters);
		
		GameController controller = new GameController(gameModel, diseaseController, cityController, playerDeckController, infectionDeckController);
		GameView view = new GameView(controller);
		
		view.setTitle("Pandemic at Noob Difficulty with 2 Players");
		view.viewGame();
		view.repaint();

	}
}
