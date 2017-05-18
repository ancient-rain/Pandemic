package gameTests;

import static constants.Character.*;
import static constants.Card.*;
import static constants.Disease.*;
import static constants.Game.*;
import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import cards.AbstractDeckCardController;
import cards.InfectionDeckCardController;
import cards.PlayerDeckCardController;
import characters.AbstractCharacterController;
import characters.CharacterModel;
import city.CityController;
import city.CityModel;
import diseases.DiseaseController;
import game.GameController;
import game.GameModel;
import game.GameView;

public class GameViewTests {
	GameController controller;
	GameModel model;
	CityController cityController;
	DiseaseController diseaseController;
	AbstractCharacterController playerController;
	GameView gameViewFourPlayer, gameViewTwoPlayer, gameViewThreePlayer;
	GameModel gameModel;
	CharacterModel playerModel1;
	CharacterModel playerModel2;
	CharacterModel playerModel3;
	CharacterModel playerModel4;
	
	@Before
	public void init(){
		this.gameModel = new GameModel();
		DiseaseController diseaseController = new DiseaseController();
		CityController cityController = new CityController(diseaseController);
		AbstractDeckCardController playerDeckController = new PlayerDeckCardController(cityController);
		AbstractDeckCardController infectionDeckController = new InfectionDeckCardController(cityController);
		
		this.controller = new GameController(gameModel,
											diseaseController,
											cityController,
											playerDeckController,
											infectionDeckController);
		
		this.cityController = this.controller.getCityController();
		CityModel startingCity = this.cityController.getCityByName("Atlanta");

		playerModel1 = new CharacterModel("one", startingCity);
		ArrayList<CharacterModel> twoPlayerList = new ArrayList<CharacterModel>();
		twoPlayerList.add(playerModel1);
		twoPlayerList.add(playerModel1);
		ArrayList<CharacterModel> threePlayerList = new ArrayList<CharacterModel>();
		threePlayerList.add(playerModel1);
		threePlayerList.add(playerModel1);
		threePlayerList.add(playerModel1);
		ArrayList<CharacterModel> fourPlayerList = new ArrayList<CharacterModel>();
		fourPlayerList.add(playerModel1);
		fourPlayerList.add(playerModel1);
		fourPlayerList.add(playerModel1);
		fourPlayerList.add(playerModel1);
		
		this.gameModel.setCharacters(twoPlayerList);
		this.gameViewTwoPlayer = new GameView(this.controller);
		
		this.gameModel.setCharacters(threePlayerList);
		this.gameViewThreePlayer = new GameView(this.controller);
		
		this.gameModel.setCharacters(fourPlayerList);
		this.gameViewFourPlayer = new GameView(this.controller);
	}
	
	@Test
	public void testFindPlayerYCoordFirstPlayer(){
		assertEquals(PLAYER_ONE_Y, this.gameViewFourPlayer.findPlayerYCoord(0));
	}
	
	@Test
	public void testFindPlayerYCoordSecondPlayerFourPlayerGame(){
		assertEquals(PLAYER_TWO_FOUR_PLAYERS_Y, this.gameViewFourPlayer.findPlayerYCoord(1));
	}
	
	@Test
	public void testFindPlayerYCoordThirdPlayerFourPlayerGame(){
		assertEquals(PLAYER_THREE_FOUR_PLAYERS_Y, this.gameViewFourPlayer.findPlayerYCoord(2));
	}
	
	@Test
	public void testFindPlayerYCoordFourthPlayerFourPlayerGame(){
		assertEquals(PLAYER_FOUR_PLAYERS_Y, this.gameViewFourPlayer.findPlayerYCoord(3));
	}
	
	@Test
	public void testFindPlayerYCoordSecondPlayerThreePlayerGame(){
		assertEquals(PLAYER_TWO_THREE_PLAYERS_Y, this.gameViewThreePlayer.findPlayerYCoord(1));
	}
	
	@Test
	public void testFindPlayerYCoordThirdPlayerThreePlayerGame(){
		assertEquals(PLAYER_THREE_THREE_PLAYERS_Y, this.gameViewThreePlayer.findPlayerYCoord(2));
	}
	
	@Test
	public void testFindPlayerYCoordSecondPlayerTwoPlayerGame(){
		assertEquals(PLAYER_TWO_TWO_PLAYERS_Y, this.gameViewTwoPlayer.findPlayerYCoord(1));
	}
	
	@Test
	public void testCheckLowCountRed(){
		assertEquals(Color.RED, this.gameViewTwoPlayer.checkLowCount(4));
	}
	
	@Test
	public void testCheckLowCountGray(){
		assertEquals(CUSTOM_GRAY_2, this.gameViewTwoPlayer.checkLowCount(11));
	}

}
