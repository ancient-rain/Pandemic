package main;

import static constants.Game.STARTING_CARD_HELPER;
import static constants.Game.PANDEMIC;

import java.util.ArrayList;
import java.util.List;

import cards.AbstractDeckCardController;
import cards.InfectionDeckCardController;
import cards.PlayerDeckCardController;
import characters.CharacterModel;
import city.CityController;
import diseases.DiseaseController;
import game.GameController;
import game.GameModel;
import game.GameView;
import menus.CharacterMenu;
import menus.LanguageMenu;
import menus.StartMenu;

public class Main {
	
	public static void main(String [] args) {
		LanguageMenu languageMenu = new LanguageMenu();
		StartMenu startMenu = new StartMenu();
		GameModel gameModel = new GameModel();
		DiseaseController diseaseController = new DiseaseController();
		CityController cityController = new CityController(diseaseController);
		AbstractDeckCardController playerDeckController = new PlayerDeckCardController(cityController);
		AbstractDeckCardController infectionDeckController = new InfectionDeckCardController(cityController);
		List<CharacterModel> characters = new ArrayList<>();
		
		languageMenu.selectLocale();
		
		startMenu.view();
		
		gameModel.setNumberOfStartingCards(STARTING_CARD_HELPER - startMenu.getNumPlayers());
		gameModel.setDifficulty(startMenu.getDifficulty());
		
		CharacterMenu characterMenu = new CharacterMenu(startMenu.getNumPlayers(), cityController);

		characterMenu.view();
		
		characters = characterMenu.getCharacters();
		
		gameModel.setCharacters(characters);
		
		GameController controller = new GameController(gameModel, 
				diseaseController, cityController, playerDeckController, infectionDeckController);
		GameView view = new GameView(controller);
		
		view.setTitle(PANDEMIC);
		view.viewGame();
		view.repaint();
	}
}