package gameTests;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import cards.AbstractDeckCardController;
import cards.CardModel;
import cards.InfectionDeckCardController;
import cards.PlayerDeckCardController;
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
		GameModel gameModel = new GameModel();
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
		CharacterModel playerModel = new CharacterModel("Medic", startingCity);
		CharacterControllerFactory factory = new CharacterControllerFactory();
		this.controller.getPlayers().add(factory.createCharacterController(playerModel));
		playerModel = new CharacterModel("Researcher", startingCity);
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
		CardModel card = new CardModel(nextCity.getName(), CardModel.CardType.PLAYER);
		this.playerController.addCardToHandOfCards(card);
		
		this.controller.moveCharacter(playerController, nextCity);
		
		assertFalse(nextCity.hasResearchStation());
		
		this.controller.buildResearchStation();
		
		assertTrue(nextCity.hasResearchStation());
	}
	
	@Test
	public void testBuildResearchCenterWithoutCardInHand() {
		CityModel currentCity = this.playerController.getCharactersCurrentCity();
		Iterator<CityModel> iter = currentCity.getNeighbors().iterator();
		CityModel nextCity = iter.next();
		
		this.controller.moveCharacter(playerController, nextCity);
		
		assertFalse(nextCity.hasResearchStation());
		
		this.controller.buildResearchStation();
		
		assertFalse(nextCity.hasResearchStation());
	}
	
	@Test
	public void testResearcherTakeShare() {
		CityModel currentCity = this.playerController.getCharactersCurrentCity();
		Iterator<CityModel> iter = currentCity.getNeighbors().iterator();
		CityModel nextCity = iter.next();
		CardModel card = new CardModel(nextCity.getName(), CardModel.CardType.PLAYER);
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
		CardModel card = new CardModel(nextCity.getName(), CardModel.CardType.PLAYER);
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
		CardModel card = new CardModel(currentCity.getName(), CardModel.CardType.PLAYER);
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
		CardModel card = new CardModel(currentCity.getName(), CardModel.CardType.PLAYER);
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
		CardModel card = new CardModel(nextCity.getName(), CardModel.CardType.PLAYER);
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
	
	@Test
	public void testNormalCure(){
		CityModel currentCity = this.playerController.getCharactersCurrentCity();
		DiseaseModel diseaseToCure = currentCity.getPrimaryDisease();
		CardModel card1 = new CardModel(currentCity.getName(), CardModel.CardType.PLAYER);
		CityModel city1 = this.cityController.getCityByName("Chicago");
		CityModel city2 = this.cityController.getCityByName("Paris");
		CityModel city3 = this.cityController.getCityByName("Montreal");
		CityModel city4 = this.cityController.getCityByName("Washington");
		CardModel card2 = new CardModel(city1.getName(), CardModel.CardType.PLAYER);
		CardModel card3 = new CardModel(city2.getName(), CardModel.CardType.PLAYER);
		CardModel card4 = new CardModel(city3.getName(), CardModel.CardType.PLAYER);
		CardModel card5 = new CardModel(city4.getName(), CardModel.CardType.PLAYER);
		this.playerController.addCardToHandOfCards(card1);
		this.playerController.addCardToHandOfCards(card2);
		this.playerController.addCardToHandOfCards(card3);
		this.playerController.addCardToHandOfCards(card4);
		this.playerController.addCardToHandOfCards(card5);
		
		Set<CardModel> toCureWithSet = new HashSet<CardModel>();
		toCureWithSet.add(card1);
		toCureWithSet.add(card2);
		toCureWithSet.add(card3);
		toCureWithSet.add(card4);
		toCureWithSet.add(card5);
		
		assertFalse(this.diseaseController.getBlueDisease().isCured());
		
		this.controller.cureDisease(toCureWithSet, diseaseToCure);
		
		assertTrue(this.diseaseController.getBlueDisease().isCured());
	}
	
	@Test
	public void testScientistCure(){
		this.controller.getPlayers().clear();
		CityModel startingCity = this.cityController.getCityByName("Atlanta");
		CharacterModel playerModel = new CharacterModel("Dispatcher", startingCity);
		CharacterControllerFactory factory = new CharacterControllerFactory();
		this.controller.getPlayers().add(factory.createCharacterController(playerModel));
		playerModel = new CharacterModel("Scientist", startingCity);
		this.controller.getPlayers().add(factory.createCharacterController(playerModel));
		this.playerController = this.controller.getCurrentPlayer();
		
		CityModel currentCity = this.playerController.getCharactersCurrentCity();
		DiseaseModel diseaseToCure = currentCity.getPrimaryDisease();
		CardModel card1 = new CardModel(currentCity.getName(), CardModel.CardType.PLAYER);
		CityModel city1 = this.cityController.getCityByName("Chicago");
		CityModel city2 = this.cityController.getCityByName("Paris");
		CityModel city3 = this.cityController.getCityByName("Montreal");
		CardModel card2 = new CardModel(city1.getName(), CardModel.CardType.PLAYER);
		CardModel card3 = new CardModel(city2.getName(), CardModel.CardType.PLAYER);
		CardModel card4 = new CardModel(city3.getName(), CardModel.CardType.PLAYER);
		this.playerController.addCardToHandOfCards(card1);
		this.playerController.addCardToHandOfCards(card2);
		this.playerController.addCardToHandOfCards(card3);
		this.playerController.addCardToHandOfCards(card4);
		
		Set<CardModel> toCureWithSet = new HashSet<CardModel>();
		toCureWithSet.add(card1);
		toCureWithSet.add(card2);
		toCureWithSet.add(card3);
		toCureWithSet.add(card4);
		
		assertFalse(this.diseaseController.getBlueDisease().isCured());
		
		this.controller.cureDisease(toCureWithSet, diseaseToCure);
		
		assertTrue(this.diseaseController.getBlueDisease().isCured());
	}
	
	@Test
	public void testNormalCureFourCards(){
		CityModel currentCity = this.playerController.getCharactersCurrentCity();
		DiseaseModel diseaseToCure = currentCity.getPrimaryDisease();
		CardModel card1 = new CardModel(currentCity.getName(), CardModel.CardType.PLAYER);
		CityModel city1 = this.cityController.getCityByName("Chicago");
		CityModel city2 = this.cityController.getCityByName("Paris");
		CityModel city3 = this.cityController.getCityByName("Montreal");
		CardModel card2 = new CardModel(city1.getName(), CardModel.CardType.PLAYER);
		CardModel card3 = new CardModel(city2.getName(), CardModel.CardType.PLAYER);
		CardModel card4 = new CardModel(city3.getName(), CardModel.CardType.PLAYER);
		this.playerController.addCardToHandOfCards(card1);
		this.playerController.addCardToHandOfCards(card2);
		this.playerController.addCardToHandOfCards(card3);
		this.playerController.addCardToHandOfCards(card4);
		
		Set<CardModel> toCureWithSet = new HashSet<CardModel>();
		toCureWithSet.add(card1);
		toCureWithSet.add(card2);
		toCureWithSet.add(card3);
		toCureWithSet.add(card4);
		
		assertFalse(this.diseaseController.getBlueDisease().isCured());
		
		this.controller.cureDisease(toCureWithSet, diseaseToCure);
		
		assertFalse(this.diseaseController.getBlueDisease().isCured());
	}
}