package main;

import static constants.City.ATLANTA;

import java.util.ArrayList;
import java.util.List;

import cards.AbstractDeckCardController;
import cards.InfectionDeckCardController;
import cards.PlayerDeckCardController;
import characters.CharacterModel;
import city.CityController;
import city.CityModel;
import diseases.DiseaseController;
import game.GameController;
import game.GameModel;
import game.GameView;
import menus.StartMenu;

public class Main {
	
	public static void main(String [] args) {
		StartMenu startMenu = new StartMenu();
		GameModel gameModel = new GameModel();
		DiseaseController diseaseController = new DiseaseController();
		CityController cityController = new CityController(diseaseController);
		CityModel atlanta = cityController.getCityByName(ATLANTA);
		AbstractDeckCardController playerDeckController = new PlayerDeckCardController(cityController);
		AbstractDeckCardController infectionDeckController = new InfectionDeckCardController(cityController);
		
		
		startMenu.view();
		
		gameModel.setNumberOfStartingCards(6 - startMenu.getNumPlayers());
		gameModel.setDifficulty(startMenu.getDifficulty());
		
		
		
		CharacterModel medic = new CharacterModel("Medic", atlanta);
		CharacterModel operations = new CharacterModel("Operations Expert", atlanta);
		CharacterModel scientist = new CharacterModel("Scientist", atlanta);
		List<CharacterModel> characters = new ArrayList<>();
		
		medic.setName("Ralph");
		scientist.setName("Ted");
		operations.setName("Bob the Builder");
				
		characters.add(medic);
		characters.add(operations);
		characters.add(scientist);
		
		
		gameModel.setCharacters(characters);
		
		GameController controller = new GameController(gameModel, 
				diseaseController, cityController, playerDeckController, infectionDeckController);
		GameView view = new GameView(controller);
		
		view.setTitle("Pandemic at Noob Difficulty with 2 Players");
		view.viewGame();
		view.repaint();
	}
}
