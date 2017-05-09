package gameTests;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import cards.CardModel;
import characters.AbstractCharacterController;
import characters.CharacterControllerFactory;
import characters.CharacterModel;
import city.CityController;
import city.CityModel;
import diseases.DiseaseController;
import diseases.DiseaseModel;
import game.GameController;
import game.GameModel;

public class GameControllerTests {
	GameController controller;
	GameModel model;
	CityController cityController;
	DiseaseController diseaseController;
	AbstractCharacterController playerController;
	
	@Before
	public void init(){
		this.model = new GameModel();
		this.controller = new GameController(model);
		this.cityController = this.controller.getCityController();
		CityModel startingCity = this.cityController.getCityByName("Atlanta");
		CharacterModel playerModel = new CharacterModel("Researcher", startingCity);
		CharacterControllerFactory factory = new CharacterControllerFactory();
		this.controller.getPlayers().add(factory.createCharacterController(playerModel));
		playerModel = new CharacterModel("Medic", startingCity);
		this.controller.getPlayers().add(factory.createCharacterController(playerModel));
		this.diseaseController = this.controller.getDiseaseController();
		this.playerController = this.controller.getCurrentPlayer();
	}

	@Test
	public void testMoveCharacters() {
		CityModel currentCity = this.playerController.getCharactersCurrentCity();
		Iterator<CityModel> iter = currentCity.getNeighbors().iterator();
		CityModel nextCity = iter.next();
		
		assertTrue(this.controller.moveCharacter(playerController, nextCity));
	}
	
	@Test
	public void testMoveCharactersEndsTurn() {
		CityModel currentCity = this.playerController.getCharactersCurrentCity();
		Iterator<CityModel> iter = currentCity.getNeighbors().iterator();
		CityModel nextCity = iter.next();
		
		this.controller.moveCharacter(playerController, nextCity);
		this.controller.moveCharacter(playerController, currentCity);
		this.controller.moveCharacter(playerController, nextCity);
		this.controller.moveCharacter(playerController, currentCity);
		
		assertTrue(!this.controller.getCurrentPlayer().equals(this.playerController));
	}
	
	@Test
	public void testMedicTreat() {
		CityModel currentCity = this.playerController.getCharactersCurrentCity();
		DiseaseModel currentDisease = this.diseaseController.getBlackDisease();
		currentCity.setCubesByDisease(currentDisease, 2);
		
		this.controller.treatCity(currentDisease);
		
		int afterTreat = currentCity.getCubesByDisease(currentDisease);
		assertTrue(afterTreat == 0);
	}
	
	@Test
	public void testNormalTreat() {
		CityModel currentCity = this.playerController.getCharactersCurrentCity();
		DiseaseModel currentDisease = this.diseaseController.getBlackDisease();
		Iterator<CityModel> iter = currentCity.getNeighbors().iterator();
		CityModel nextCity = iter.next();
		
		this.controller.moveCharacter(playerController, nextCity);
		this.controller.moveCharacter(playerController, currentCity);
		this.controller.moveCharacter(playerController, nextCity);
		this.controller.moveCharacter(playerController, currentCity);
		
		currentCity = this.playerController.getCharactersCurrentCity();
		currentCity.setCubesByDisease(currentDisease, 2);
		
		int beforeTreat = currentCity.getCubesByDisease(currentDisease);
		
		this.controller.treatCity(currentDisease);
		
		int afterTreat = currentCity.getCubesByDisease(currentDisease);
		assertTrue(beforeTreat == afterTreat + 1);
	}
	
	@Test
	public void testBuildResearchCenterWithCardInHand() {
		CityModel currentCity = this.playerController.getCharactersCurrentCity();
		Iterator<CityModel> iter = currentCity.getNeighbors().iterator();
		CityModel nextCity = iter.next();
		CardModel card = new CardModel(nextCity.getName());
		this.playerController.addCardToHandOfCards(card);
		
		this.controller.moveCharacter(playerController, nextCity);
		
		assertFalse(nextCity.isHasResearchStation());
		
		this.controller.buildResearchStation();
		
		assertTrue(nextCity.isHasResearchStation());
	}
	
	@Test
	public void testBuildResearchCenterWithoutCardInHand() {
		CityModel currentCity = this.playerController.getCharactersCurrentCity();
		Iterator<CityModel> iter = currentCity.getNeighbors().iterator();
		CityModel nextCity = iter.next();
		
		this.controller.moveCharacter(playerController, nextCity);
		
		assertFalse(nextCity.isHasResearchStation());
		
		this.controller.buildResearchStation();
		
		assertFalse(nextCity.isHasResearchStation());
	}
	
	@Test
	public void testResearcherTakeShare() {
		CityModel currentCity = this.playerController.getCharactersCurrentCity();
		Iterator<CityModel> iter = currentCity.getNeighbors().iterator();
		CityModel nextCity = iter.next();
		CardModel card = new CardModel(nextCity.getName());
		this.playerController.addCardToHandOfCards(card);
		this.controller.shareKnowledge(this.controller.getPlayers().get(0), card);
		
		assertTrue(this.controller.getPlayers().get(0).getCharacterModel().isInHand(card));
		assertFalse(this.playerController.getCharacterModel().isInHand(card));
	}
	
	@Test
	public void testResearcherGiveShare() {
		CityModel currentCity = this.playerController.getCharactersCurrentCity();
		Iterator<CityModel> iter = currentCity.getNeighbors().iterator();
		CityModel nextCity = iter.next();
		CardModel card = new CardModel(nextCity.getName());
		this.playerController.addCardToHandOfCards(card);
		
		this.controller.moveCharacter(playerController, nextCity);
		this.controller.moveCharacter(playerController, currentCity);
		this.controller.moveCharacter(playerController, nextCity);
		this.controller.moveCharacter(playerController, currentCity);
		
		AbstractCharacterController newPlayer = this.controller.getCurrentPlayer();
		this.controller.shareKnowledge(this.playerController, card);
		
		assertTrue(newPlayer.getCharacterModel().isInHand(card));
		assertFalse(this.playerController.getCharacterModel().isInHand(card));
	}
	
	@Test
	public void testNormalTakeShare() {
		this.controller.getPlayers().clear();
		CityModel startingCity = this.cityController.getCityByName("Atlanta");
		CharacterModel playerModel = new CharacterModel("Dispatcher", startingCity);
		CharacterControllerFactory factory = new CharacterControllerFactory();
		this.controller.getPlayers().add(factory.createCharacterController(playerModel));
		playerModel = new CharacterModel("Medic", startingCity);
		this.controller.getPlayers().add(factory.createCharacterController(playerModel));
		this.playerController = this.controller.getCurrentPlayer();
		
		CityModel currentCity = this.playerController.getCharactersCurrentCity();
		CardModel card = new CardModel(currentCity.getName());
		this.playerController.addCardToHandOfCards(card);
		this.controller.shareKnowledge(this.controller.getPlayers().get(0), card);
		
		assertTrue(this.controller.getPlayers().get(0).getCharacterModel().isInHand(card));
		assertFalse(this.playerController.getCharacterModel().isInHand(card));
	}
	
	@Test
	public void testNormalGiveShare() {
		this.controller.getPlayers().clear();
		CityModel startingCity = this.cityController.getCityByName("Atlanta");
		CharacterModel playerModel = new CharacterModel("Dispatcher", startingCity);
		CharacterControllerFactory factory = new CharacterControllerFactory();
		this.controller.getPlayers().add(factory.createCharacterController(playerModel));
		playerModel = new CharacterModel("Medic", startingCity);
		this.controller.getPlayers().add(factory.createCharacterController(playerModel));
		this.playerController = this.controller.getCurrentPlayer();
		
		CityModel currentCity = this.playerController.getCharactersCurrentCity();
		Iterator<CityModel> iter = currentCity.getNeighbors().iterator();
		CityModel nextCity = iter.next();
		CardModel card = new CardModel(currentCity.getName());
		this.playerController.addCardToHandOfCards(card);
		
		this.controller.moveCharacter(playerController, nextCity);
		this.controller.moveCharacter(playerController, currentCity);
		this.controller.moveCharacter(playerController, nextCity);
		this.controller.moveCharacter(playerController, currentCity);
		
		AbstractCharacterController newPlayer = this.controller.getCurrentPlayer();
		this.controller.shareKnowledge(this.playerController, card);
		
		assertTrue(newPlayer.getCharacterModel().isInHand(card));
		assertFalse(this.playerController.getCharacterModel().isInHand(card));
	}

	@Test
	public void testFailShare() {
		this.controller.getPlayers().clear();
		CityModel startingCity = this.cityController.getCityByName("Atlanta");
		CharacterModel playerModel = new CharacterModel("Dispatcher", startingCity);
		CharacterControllerFactory factory = new CharacterControllerFactory();
		this.controller.getPlayers().add(factory.createCharacterController(playerModel));
		playerModel = new CharacterModel("Medic", startingCity);
		this.controller.getPlayers().add(factory.createCharacterController(playerModel));
		this.playerController = this.controller.getCurrentPlayer();
		
		CityModel currentCity = this.playerController.getCharactersCurrentCity();
		Iterator<CityModel> iter = currentCity.getNeighbors().iterator();
		CityModel nextCity = iter.next();
		CardModel card = new CardModel(nextCity.getName());
		this.playerController.addCardToHandOfCards(card);
		
		this.controller.moveCharacter(playerController, nextCity);
		this.controller.moveCharacter(playerController, currentCity);
		this.controller.moveCharacter(playerController, nextCity);
		this.controller.moveCharacter(playerController, currentCity);
		
		AbstractCharacterController newPlayer = this.controller.getCurrentPlayer();
		this.controller.shareKnowledge(this.playerController, card);
		
		assertFalse(newPlayer.getCharacterModel().isInHand(card));
		assertTrue(this.playerController.getCharacterModel().isInHand(card));
	}
	
}
