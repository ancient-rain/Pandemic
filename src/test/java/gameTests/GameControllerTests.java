package gameTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.easymock.EasyMock;
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

import static constants.City.*;
import static constants.Game.*;
import static constants.Card.*;

public class GameControllerTests {
	GameController controller;
	GameModel model;
	CityController cityController;
	DiseaseController diseaseController;
	AbstractCharacterController playerController;
	GameModel gameModel;

	ArrayList<CityModel> listOfCities;
	AbstractDeckCardController playerDeckController;
	AbstractDeckCardController infectionDeckController;
	CityModel startingCity;

	@Before
	public void init() {
		this.gameModel = new GameModel();
		this.diseaseController = new DiseaseController();
	    this.cityController = new CityController(diseaseController);
		this.playerDeckController = new PlayerDeckCardController(cityController);
		this.infectionDeckController = new InfectionDeckCardController(cityController);

		this.controller = new GameController(gameModel, diseaseController, cityController, playerDeckController,
				infectionDeckController);

		this.cityController = this.controller.getCityController();
		this.startingCity = this.cityController.getCityByName("Atlanta");
		CharacterModel playerModel = new CharacterModel("Medic", startingCity);
		CharacterControllerFactory factory = new CharacterControllerFactory();
		this.controller.getPlayers().add(factory.createCharacterController(playerModel));
		playerModel = new CharacterModel("Researcher", startingCity);
		this.controller.getPlayers().add(factory.createCharacterController(playerModel));
		this.diseaseController = this.controller.getDiseaseController();
		this.playerController = this.controller.getCurrentPlayer();

		Set<CityModel> setOfCities = cityController.getCities();
		this.listOfCities = new ArrayList<CityModel>(setOfCities);
	}

	@Test
	public void testInitialCityLocations() {
		for (AbstractCharacterController c : this.controller.getPlayers()) {
			assertTrue(c.getCharactersCurrentCity().getName().equals("Atlanta"));
			;
		}
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
	public void testMoveCharactersToInvalidLocation() {
		CityModel currentCity = this.playerController.getCharactersCurrentCity();
		CityModel nextCity = this.cityController.getCityByName("Madrid");

		assertTrue(!currentCity.getNeighbors().contains(nextCity));

		assertFalse(this.controller.moveCharacter(playerController, nextCity));
	}

	@Test
	public void testCharterFlight() {
		CityModel currentCity = this.playerController.getCharactersCurrentCity();
		CityModel nextCity = this.cityController.getCityByName("Madrid");
		CardModel card = new CardModel(currentCity.getName(), CardModel.CardType.PLAYER);

		this.playerController.getCharacterModel().getHandOfCards().add(card);

		assertTrue(!currentCity.getNeighbors().contains(nextCity));

		assertTrue(this.controller.moveCharacter(playerController, nextCity));
	}

	@Test
	public void testDirectFlight() {
		CityModel currentCity = this.playerController.getCharactersCurrentCity();
		CityModel nextCity = this.cityController.getCityByName("Madrid");
		CardModel card = new CardModel(nextCity.getName(), CardModel.CardType.PLAYER);

		this.playerController.getCharacterModel().getHandOfCards().add(card);

		assertTrue(!currentCity.getNeighbors().contains(nextCity));

		assertTrue(this.controller.moveCharacter(playerController, nextCity));
		assertEquals(nextCity, playerController.getCharactersCurrentCity());
		assertEquals(3, this.controller.getGameModel().getActionsLeft());
	}

	@Test
	public void testInvalidFlight() {
		CityModel currentCity = this.playerController.getCharactersCurrentCity();
		CityModel nextCity = this.cityController.getCityByName("Madrid");
		CardModel card = new CardModel("Washington", CardModel.CardType.PLAYER);

		this.playerController.getCharacterModel().getHandOfCards().add(card);

		assertTrue(!currentCity.getNeighbors().contains(nextCity));

		assertTrue(!this.controller.moveCharacter(playerController, nextCity));
	}

	@Test
	public void testMedicTreat() {
		CityModel currentCity = this.playerController.getCharactersCurrentCity();
		DiseaseModel currentDisease = this.diseaseController.getBlackDisease();
		currentCity.setCubesByDisease(currentDisease, 2);
		int actionsBefore = this.controller.getGameModel().getActionsLeft();

		this.controller.treatCity(currentDisease);

		int actionsAfter = this.controller.getGameModel().getActionsLeft();
		assertTrue(actionsBefore == actionsAfter + 1);

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

		assertTrue(this.controller.treatCity(currentDisease));

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
		int actionsBefore = this.controller.getGameModel().getActionsLeft();

		assertFalse(nextCity.hasResearchStation());

		assertTrue(this.controller.buildResearchStation());

		int actionsAfter = this.controller.getGameModel().getActionsLeft();
		assertTrue(actionsBefore == actionsAfter + 1);

		assertTrue(nextCity.hasResearchStation());
	}

	@Test
	public void testBuildResearchCenterWithoutCardInHand() {
		CityModel currentCity = this.playerController.getCharactersCurrentCity();
		Iterator<CityModel> iter = currentCity.getNeighbors().iterator();
		CityModel nextCity = iter.next();

		this.controller.moveCharacter(playerController, nextCity);

		assertFalse(nextCity.hasResearchStation());

		assertFalse(this.controller.buildResearchStation());

		assertFalse(nextCity.hasResearchStation());
	}

	@Test
	public void testResearcherTakeShare() {
		CityModel currentCity = this.playerController.getCharactersCurrentCity();
		Iterator<CityModel> iter = currentCity.getNeighbors().iterator();
		CityModel nextCity = iter.next();
		CardModel card = new CardModel(nextCity.getName(), CardModel.CardType.PLAYER);
		this.playerController.addCardToHandOfCards(card);

		int actionsBefore = this.controller.getGameModel().getActionsLeft();

		assertTrue(this.controller.shareKnowledge(this.controller.getPlayers().get(1), card));

		int actionsAfter = this.controller.getGameModel().getActionsLeft();
		assertTrue(actionsBefore == actionsAfter + 1);

		assertTrue(this.controller.getPlayers().get(1).getCharacterModel().isInHand(card));
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
		assertTrue(this.controller.shareKnowledge(this.playerController, card));

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
		this.controller.shareKnowledge(this.controller.getPlayers().get(1), card);

		assertTrue(this.controller.getPlayers().get(1).getCharacterModel().isInHand(card));
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
		assertFalse(this.controller.shareKnowledge(this.playerController, card));

		assertFalse(newPlayer.getCharacterModel().isInHand(card));
		assertTrue(this.playerController.getCharacterModel().isInHand(card));
	}

	@Test
	public void testNormalCure() {
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

		int actionsBefore = this.controller.getGameModel().getActionsLeft();

		assertFalse(this.diseaseController.getBlueDisease().isCured());

		this.controller.cureDisease(toCureWithSet, diseaseToCure);

		int actionsAfter = this.controller.getGameModel().getActionsLeft();

		assertTrue(actionsBefore == actionsAfter + 1);

		assertTrue(this.diseaseController.getBlueDisease().isCured());
	}

	@Test
	public void testScientistCure() {
		this.controller.getPlayers().clear();
		CityModel startingCity = this.cityController.getCityByName("Atlanta");
		CharacterModel playerModel = new CharacterModel("Scientist", startingCity);
		CharacterControllerFactory factory = new CharacterControllerFactory();
		this.controller.getPlayers().add(factory.createCharacterController(playerModel));
		playerModel = new CharacterModel("Dispatcher", startingCity);
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
	public void testNormalCureFourCards() {
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

		assertFalse(this.controller.cureDisease(toCureWithSet, diseaseToCure));

		assertFalse(this.diseaseController.getBlueDisease().isCured());
	}

	@Test
	public void testCureWins() {
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

		this.diseaseController.getBlackDisease().setCured(true);
		this.diseaseController.getRedDisease().setCured(true);
		this.diseaseController.getYellowDisease().setCured(true);

		assertFalse(this.diseaseController.getBlueDisease().isCured());

		assertTrue(this.controller.cureDisease(toCureWithSet, diseaseToCure));

		assertTrue(this.diseaseController.getBlueDisease().isCured());

		assertTrue(this.controller.checkForWin());
	}

	@Test
	public void testAirLift() {
		CityModel currentCity = this.playerController.getCharactersCurrentCity();
		Iterator<CityModel> iter = currentCity.getNeighbors().iterator();
		CityModel nextCity = iter.next();
		CardModel card = new CardModel("Airlift", CardModel.CardType.EVENT);
		
		this.controller.getGameModel().setSelectedCharacter(playerController);
		this.controller.getGameModel().setSelectedCity(nextCity);
		
		assertTrue(this.controller.playEventCard(card));

		assertTrue(this.playerController.getCharactersCurrentCity().equals(nextCity));
	}

	@Test
	public void testGovernmentGrant() {
		CityModel currentCity = this.playerController.getCharactersCurrentCity();
		Iterator<CityModel> iter = currentCity.getNeighbors().iterator();
		CityModel nextCity = iter.next();
		CardModel card = new CardModel("Government Grant", CardModel.CardType.EVENT);
		
		this.controller.getGameModel().setSelectedCity(nextCity);
		
		assertFalse(this.controller.getCityController().getCityByName(nextCity.getName()).hasResearchStation());

		assertTrue(this.controller.playEventCard(card));

		assertTrue(this.controller.getCityController().getCityByName(nextCity.getName()).hasResearchStation());
	}

	@Test
	public void testEndOfTurn() {
		CityModel currentCity = this.playerController.getCharactersCurrentCity();
		Iterator<CityModel> iter = currentCity.getNeighbors().iterator();
		CityModel nextCity = iter.next();
		DiseaseModel disease = this.controller.getDiseaseController().getBlackDisease();

		nextCity.setCubesByDisease(disease, 3);

		this.controller.moveCharacter(playerController, nextCity);
		assertEquals(ACTION_COUNT - 1, this.controller.getGameModel().getActionsLeft());
		this.controller.moveCharacter(playerController, currentCity);
		this.controller.moveCharacter(playerController, nextCity);
		this.controller.treatCity(disease);
		assertEquals(ACTION_COUNT, this.controller.getGameModel().getActionsLeft());

		assertTrue(!this.playerController.equals(this.controller.getCurrentPlayer()));
	}

	@Test
	public void testEndOfTurnNoCardsInDeck() {
		int cardsInDeck = this.controller.getPlayerDeckController().getNumberOfCardsInDeck();
		this.controller.getPlayerDeckController().drawNumberOfCards(cardsInDeck);

		this.controller.endOfTurn();

		assertTrue(this.controller.getGameModel().isLost());
	}

	@Test
	public void testEndOfTurnOneCardInDeck() {
		int cardsInDeck = this.controller.getPlayerDeckController().getNumberOfCardsInDeck();
		this.controller.getPlayerDeckController().drawNumberOfCards(cardsInDeck - 1);

		this.controller.endOfTurn();

		assertTrue(this.controller.getGameModel().isLost());
	}

	@Test
	public void testEndOfTurnEpidemic() {
		PlayerDeckCardController deckController = EasyMock.createNiceMock(PlayerDeckCardController.class);
		InfectionDeckCardController infDeckController = EasyMock.createNiceMock(InfectionDeckCardController.class);
		this.controller.setPlayerDeck(deckController);
		this.controller.setInfectionDeck(infDeckController);
		CityModel infecting = this.controller.getCityController().getCityByName("Atlanta");

		CardModel epidemicCard = EasyMock.createNiceMock(CardModel.class);
		CardModel cityToEpidemic = EasyMock.createNiceMock(CardModel.class);
		CardModel cardOne = EasyMock.createNiceMock(CardModel.class);
		CardModel cardTwo = EasyMock.createNiceMock(CardModel.class);

		EasyMock.expect(deckController.getNumberOfCardsInDeck()).andStubReturn(3);
		EasyMock.expect(epidemicCard.getName()).andStubReturn("Epidemic");
		EasyMock.expect(deckController.draw()).andStubReturn(epidemicCard);
		EasyMock.expect(cityToEpidemic.getName()).andStubReturn(infecting.getName());
		EasyMock.expect(infDeckController.drawBottomCard()).andStubReturn(cityToEpidemic);
		EasyMock.expect(cardOne.getName()).andStubReturn("Chicago");
		EasyMock.expect(infDeckController.draw()).andReturn(cardOne);
		infDeckController.discard(cardOne);
		EasyMock.expectLastCall();
		EasyMock.expect(cardTwo.getName()).andStubReturn("Washington");
		EasyMock.expect(infDeckController.draw()).andReturn(cardTwo);
		infDeckController.discard(cardTwo);
		EasyMock.expectLastCall();

		EasyMock.replay(deckController, epidemicCard, cityToEpidemic, infDeckController, cardOne, cardTwo);

		this.controller.endOfTurn();

		EasyMock.verify(deckController, epidemicCard, cityToEpidemic, infDeckController, cardOne, cardTwo);
	}

	@Test
	public void testEndOfTurnEpidemicWithMaxInfectionRate() {
		PlayerDeckCardController deckController = EasyMock.createNiceMock(PlayerDeckCardController.class);
		InfectionDeckCardController infDeckController = EasyMock.createNiceMock(InfectionDeckCardController.class);
		this.controller.setPlayerDeck(deckController);
		this.controller.setInfectionDeck(infDeckController);
		CityModel infecting = this.controller.getCityController().getCityByName("Atlanta");
		this.controller.getGameModel().setInfectionRateIndex(7);

		CardModel epidemicCard = EasyMock.createNiceMock(CardModel.class);
		CardModel cityToEpidemic = EasyMock.createNiceMock(CardModel.class);
		CardModel cardOne = EasyMock.createNiceMock(CardModel.class);
		CardModel cardTwo = EasyMock.createNiceMock(CardModel.class);

		EasyMock.expect(deckController.getNumberOfCardsInDeck()).andStubReturn(3);
		EasyMock.expect(epidemicCard.getName()).andStubReturn("Epidemic");
		EasyMock.expect(deckController.draw()).andStubReturn(epidemicCard);
		EasyMock.expect(cityToEpidemic.getName()).andStubReturn(infecting.getName());
		EasyMock.expect(infDeckController.drawBottomCard()).andStubReturn(cityToEpidemic);
		EasyMock.expect(cardOne.getName()).andStubReturn("Chicago");
		EasyMock.expect(infDeckController.draw()).andReturn(cardOne);
		infDeckController.discard(cardOne);
		EasyMock.expectLastCall();
		EasyMock.expect(cardTwo.getName()).andStubReturn("Washington");
		EasyMock.expect(infDeckController.draw()).andReturn(cardTwo);
		infDeckController.discard(cardTwo);
		EasyMock.expectLastCall();
		EasyMock.expect(infDeckController.draw()).andReturn(cardOne);
		EasyMock.expect(infDeckController.draw()).andReturn(cardOne);

		EasyMock.replay(deckController, epidemicCard, cityToEpidemic, infDeckController, cardOne, cardTwo);

		this.controller.endOfTurn();

		EasyMock.verify(deckController, epidemicCard, cityToEpidemic, infDeckController, cardOne, cardTwo);
	}

	@Test
	public void testEndOfTurnEpidemicWithQuietNight() {
		PlayerDeckCardController deckController = EasyMock.createNiceMock(PlayerDeckCardController.class);
		InfectionDeckCardController infDeckController = EasyMock.createNiceMock(InfectionDeckCardController.class);
		this.controller.setPlayerDeck(deckController);
		this.controller.setInfectionDeck(infDeckController);
		CityModel infecting = this.controller.getCityController().getCityByName("Atlanta");
		this.controller.getGameModel().setQuietNightsLeft(2);
		this.controller.getGameModel().setInfectionRateIndex(7);

		CardModel epidemicCard = EasyMock.createNiceMock(CardModel.class);
		CardModel cityToEpidemic = EasyMock.createNiceMock(CardModel.class);
		CardModel cardOne = EasyMock.createNiceMock(CardModel.class);
		CardModel cardTwo = EasyMock.createNiceMock(CardModel.class);

		EasyMock.expect(deckController.getNumberOfCardsInDeck()).andStubReturn(3);
		EasyMock.expect(epidemicCard.getName()).andStubReturn("Epidemic");
		EasyMock.expect(deckController.draw()).andStubReturn(epidemicCard);
		EasyMock.expect(cityToEpidemic.getName()).andStubReturn(infecting.getName());
		EasyMock.expect(infDeckController.drawBottomCard()).andStubReturn(cityToEpidemic);
		EasyMock.expect(cardOne.getName()).andStubReturn("Chicago");
		EasyMock.expect(infDeckController.draw()).andReturn(cardOne);
		infDeckController.discard(cardOne);
		EasyMock.expectLastCall();

		EasyMock.replay(deckController, epidemicCard, cityToEpidemic, infDeckController, cardOne, cardTwo);

		this.controller.endOfTurn();

		EasyMock.verify(deckController, epidemicCard, cityToEpidemic, infDeckController, cardOne, cardTwo);
	}

	@Test
	public void testNormalTreatFails() {
		CityModel currentCity = this.playerController.getCharactersCurrentCity();
		DiseaseModel currentDisease = this.diseaseController.getBlackDisease();
		Iterator<CityModel> iter = currentCity.getNeighbors().iterator();
		CityModel nextCity = iter.next();

		this.controller.moveCharacter(playerController, nextCity);
		this.controller.moveCharacter(playerController, currentCity);
		this.controller.moveCharacter(playerController, nextCity);
		this.controller.moveCharacter(playerController, currentCity);

		currentCity = this.playerController.getCharactersCurrentCity();

		int beforeTreat = currentCity.getCubesByDisease(currentDisease);

		assertFalse(this.controller.treatCity(currentDisease));

		int afterTreat = currentCity.getCubesByDisease(currentDisease);
		assertTrue(beforeTreat == afterTreat);
	}

	@Test
	public void testInitWithACharacterList() {
		GameModel gameModel = new GameModel();
		CityModel city = this.controller.getCurrentPlayer().getCharacterModel().getCurrentCity();
		CharacterModel playerModel1 = new CharacterModel("Medic", city);
		CharacterModel playerModel2 = new CharacterModel("Scientist", city);
		ArrayList<CharacterModel> twoPlayerList = new ArrayList<CharacterModel>();
		twoPlayerList.add(playerModel1);
		twoPlayerList.add(playerModel2);
		gameModel.setCharacters(twoPlayerList);
		gameModel.setNumberOfStartingCards(4);

		DiseaseController diseaseController = new DiseaseController();
		CityController cityController = new CityController(diseaseController);
		AbstractDeckCardController playerDeckController = new PlayerDeckCardController(cityController);
		AbstractDeckCardController infectionDeckController = new InfectionDeckCardController(cityController);

		this.controller = new GameController(gameModel, diseaseController, cityController, playerDeckController,
				infectionDeckController);

		int cardsInHand = 0;
		for (AbstractCharacterController character : this.controller.getPlayers()) {
			cardsInHand += character.getCharacterModel().getHandOfCards().size();
		}
		int cardsPer = this.controller.getGameModel().getNumberOfStartingCards();

		assertTrue(cardsInHand == this.controller.getPlayers().size() * cardsPer);
		for (AbstractCharacterController c : this.controller.getPlayers()) {
			assertTrue(c.getCharactersCurrentCity().getName().equals("Atlanta"));
			;
		}
	}

	@Test
	public void testInfectQuarentined() {
		DiseaseModel red = this.controller.getDiseaseController().getRedDisease();
		DiseaseModel yellow = this.controller.getDiseaseController().getYellowDisease();
		DiseaseModel black = this.controller.getDiseaseController().getBlackDisease();
		DiseaseModel blue = this.controller.getDiseaseController().getBlueDisease();

		int beforeInfect = 0;
		for (CityModel city : this.controller.getCityController().getCities()) {
			city.setQuarentined(true);
			beforeInfect += city.getCubesByDisease(red);
			beforeInfect += city.getCubesByDisease(black);
			beforeInfect += city.getCubesByDisease(blue);
			beforeInfect += city.getCubesByDisease(yellow);
		}

		this.controller.endOfTurn();

		int afterInfect = 0;
		for (CityModel city : this.controller.getCityController().getCities()) {
			afterInfect += city.getCubesByDisease(red);
			afterInfect += city.getCubesByDisease(black);
			afterInfect += city.getCubesByDisease(blue);
			afterInfect += city.getCubesByDisease(yellow);
		}

		assertTrue(beforeInfect == afterInfect);
	}

	@Test
	public void testInfectEradicated() {
		DiseaseModel red = this.controller.getDiseaseController().getRedDisease();
		DiseaseModel yellow = this.controller.getDiseaseController().getYellowDisease();
		DiseaseModel black = this.controller.getDiseaseController().getBlackDisease();
		DiseaseModel blue = this.controller.getDiseaseController().getBlueDisease();
		this.controller.getDiseaseController().getBlackDisease().setEradicated(true);
		this.controller.getDiseaseController().getBlueDisease().setEradicated(true);
		this.controller.getDiseaseController().getRedDisease().setEradicated(true);
		this.controller.getDiseaseController().getYellowDisease().setEradicated(true);

		int beforeInfect = 0;
		for (CityModel city : this.controller.getCityController().getCities()) {
			beforeInfect += city.getCubesByDisease(red);
			beforeInfect += city.getCubesByDisease(black);
			beforeInfect += city.getCubesByDisease(blue);
			beforeInfect += city.getCubesByDisease(yellow);
		}

		this.controller.endOfTurn();

		int afterInfect = 0;
		for (CityModel city : this.controller.getCityController().getCities()) {
			afterInfect += city.getCubesByDisease(red);
			afterInfect += city.getCubesByDisease(black);
			afterInfect += city.getCubesByDisease(blue);
			afterInfect += city.getCubesByDisease(yellow);
		}

		assertTrue(beforeInfect == afterInfect);
	}

	@Test
	public void testEndOfTurnUntilGameLoss() {
		for (CityModel city : this.controller.getCityController().getCities()) {
			city.setCubesByDisease(city.getPrimaryDisease(), 3);
		}
		this.controller.getCityController().setOutbreakCounter(7);
		while (!this.controller.getGameModel().isLost()) {
			this.controller.endOfTurn();
		}

		assertTrue(this.controller.getCityController().getOutbreakCoutner() > 7);
	}
	
	@Test
	public void testEndOfTurnUntilGameLossCubes() {
		this.controller.getDiseaseController().getBlackDisease().setCubesLeft(0);
		this.controller.getDiseaseController().getBlueDisease().setCubesLeft(0);
		this.controller.getDiseaseController().getYellowDisease().setCubesLeft(0);
		this.controller.getDiseaseController().getRedDisease().setCubesLeft(0);
		
		this.controller.endOfTurn();

		assertTrue(this.controller.getGameModel().isLost());
	}
	
	@Test
	public void testEndOfTurnUntilGameLossCubesPositive() {
		this.controller.getDiseaseController().getBlackDisease().setCubesLeft(1);
		this.controller.getDiseaseController().getBlueDisease().setCubesLeft(1);
		this.controller.getDiseaseController().getYellowDisease().setCubesLeft(1);
		this.controller.getDiseaseController().getRedDisease().setCubesLeft(1);
		this.controller.getCityController().setOutbreakCounter(MAX_NUM_OUTBREAKS-1);
		
		this.controller.endOfTurn();

		assertFalse(this.controller.getGameModel().isLost());
	}
	
	@Test
	public void testEndOfTurnUntilGameLossCubesNegative() {
		this.controller.getDiseaseController().getBlackDisease().setCubesLeft(-1);
		this.controller.getDiseaseController().getBlueDisease().setCubesLeft(-1);
		this.controller.getDiseaseController().getYellowDisease().setCubesLeft(-1);
		this.controller.getDiseaseController().getRedDisease().setCubesLeft(-1);
		this.controller.getCityController().setOutbreakCounter(MAX_NUM_OUTBREAKS-1);
		
		this.controller.endOfTurn();

		assertTrue(this.controller.getGameModel().isLost());
	}

	@Test
	public void testOneQuietNight() {
		CardModel card = new CardModel("One Quiet Night", CardModel.CardType.EVENT);
		this.controller.getCurrentPlayer().getCharacterModel().addCardToHandOfCards(card);
		int cardsInHand = this.controller.getCurrentPlayer().getCharacterModel().getHandOfCards().size();
		
		int inDiscard = this.controller.getPlayerDeckController().getNumberOfCardsInDiscard();
		
		assertTrue(this.controller.playEventCard(card));
		
		int afterDiscard = this.controller.getPlayerDeckController().getNumberOfCardsInDiscard();
		int after = this.controller.getCurrentPlayer().getCharacterModel().getHandOfCards().size();
		
		assertTrue(inDiscard == afterDiscard - 1);
		assertTrue(cardsInHand == after + 1);
		assertTrue(this.controller.getPlayers().size() == this.controller.getGameModel().getQuietNightsLeft());
	}
	
	@Test
	public void testPlayEventNonEventCard() {
		int cardsInDiscard = this.controller.getPlayerDeckController().getNumberOfCardsInDiscard();
		CardModel card = new CardModel("", CardModel.CardType.EVENT);

		assertFalse(this.controller.playEventCard(card));
		int after = this.controller.getPlayerDeckController().getNumberOfCardsInDiscard();
		
		assertTrue(cardsInDiscard == after);
	}

	@Test
	public void testResilientPopulation() {
		CardModel card = new CardModel("Resilient Population", CardModel.CardType.EVENT);
		this.controller.endOfTurn();

		CardModel cardToRemove = this.controller.getInfectionDeckController().getDiscardedCards().get(0);
		this.controller.getGameModel().setCardToRemoveFromInfectionDeck(cardToRemove);

		assertTrue(this.controller.playEventCard(card));

		assertTrue(!this.controller.getInfectionDeckController().getDiscardedCards().contains(cardToRemove));
	}

	@Test
	public void testForecast() {
		CardModel card = new CardModel("Forecast", CardModel.CardType.EVENT);
		ArrayList<CardModel> cardsToReturn = new ArrayList<CardModel>();

		assertTrue(this.controller.playEventCard(card));

		for (CardModel newCard : this.controller.getGameModel().getForecastCards()) {
			cardsToReturn.add(newCard);
		}

		for (CardModel newCard : cardsToReturn) {
			card = newCard;
			assertTrue(this.controller.forecastReturnCard(newCard));
		}

		assertTrue(card.equals(this.controller.getInfectionDeckController().draw()));

	}

	@Test
	public void testForecastFail() {
		CardModel card = new CardModel("Forecast", CardModel.CardType.EVENT);
		ArrayList<CardModel> cardsToReturn = new ArrayList<CardModel>();

		assertTrue(this.controller.playEventCard(card));

		for (CardModel newCard : this.controller.getGameModel().getForecastCards()) {
			cardsToReturn.add(newCard);
		}

		for (CardModel newCard : cardsToReturn) {
			card = newCard;
			assertTrue(this.controller.forecastReturnCard(newCard));
		}

		assertFalse(this.controller.forecastReturnCard(card));
	}

	@Test
	public void testPlayFakeEventCard() {
		CardModel card = new CardModel("Fake", CardModel.CardType.EVENT);

		assertFalse(this.controller.playEventCard(card));
	}

	@Test
	public void testIsLost() {
		assertFalse(this.controller.checkForLoss());
	}

	@Test
	public void testGovernmentGrantFailsCount() {
		CityModel currentCity = this.playerController.getCharactersCurrentCity();
		Iterator<CityModel> iter = currentCity.getNeighbors().iterator();
		CityModel nextCity = iter.next();
		this.cityController.setResearchStationCounter(6);
		CardModel card = new CardModel("Government Grant", CardModel.CardType.EVENT);
		
		this.controller.getGameModel().setSelectedCity(nextCity);
		
		assertFalse(this.controller.getCityController().getCityByName(nextCity.getName()).hasResearchStation());

		assertFalse(this.controller.playEventCard(card));

		assertFalse(this.controller.getCityController().getCityByName(nextCity.getName()).hasResearchStation());
	}

	@Test
	public void testGovernmentGrantFailsHasStation() {
		CityModel currentCity = this.playerController.getCharactersCurrentCity();
		Iterator<CityModel> iter = currentCity.getNeighbors().iterator();
		CityModel nextCity = iter.next();
		nextCity.setHasResearchStation(true);
		CardModel card = new CardModel("Government Grant", CardModel.CardType.EVENT);
		
		this.controller.getGameModel().setSelectedCity(nextCity);
		
		assertTrue(this.controller.getCityController().getCityByName(nextCity.getName()).hasResearchStation());

		this.controller.playEventCard(card);

		assertTrue(this.controller.getCityController().getCityByName(nextCity.getName()).hasResearchStation());
	}

	@Test
	public void testInitInfect() {
		int totalCubes = 24 * 4;

		int threeInfected = 3 * 3;
		int twoInfected = 2 * 3;
		int oneInfected = 1 * 3;

		int redRemaining = this.controller.getDiseaseController().getRedDisease().getCubesLeft();
		int yellowRemaining = this.controller.getDiseaseController().getYellowDisease().getCubesLeft();
		int blueRemaining = this.controller.getDiseaseController().getBlueDisease().getCubesLeft();
		int blackRemaining = this.controller.getDiseaseController().getBlackDisease().getCubesLeft();

		int totalInfected = threeInfected + twoInfected + oneInfected;
		int totalRemaining = redRemaining + yellowRemaining + blackRemaining + blueRemaining;

		assertTrue(totalInfected + totalRemaining == totalCubes);
	}

	@Test
	public void testDoesNotHaveSpecialAbility() {
		this.controller.getPlayers().clear();
		CityModel startingCity = this.cityController.getCityByName("Atlanta");
		CharacterModel playerModel = new CharacterModel("Scientist", startingCity);
		CharacterControllerFactory factory = new CharacterControllerFactory();
		this.controller.getPlayers().add(factory.createCharacterController(playerModel));
		playerModel = new CharacterModel("Dispatcher", startingCity);
		this.controller.getPlayers().add(factory.createCharacterController(playerModel));
		this.playerController = this.controller.getCurrentPlayer();

		assertFalse(this.controller.specialAbility());
	}

	@Test
	public void testHasSpecialAbility() {
		this.controller.getPlayers().clear();
		CityModel startingCity = this.cityController.getCityByName("Atlanta");
		CharacterModel playerModel = new CharacterModel("Scientist", startingCity);
		CharacterControllerFactory factory = new CharacterControllerFactory();
		this.controller.getPlayers().add(factory.createCharacterController(playerModel));
		playerModel = new CharacterModel("Contingency Planner", startingCity);
		this.controller.getPlayers().add(factory.createCharacterController(playerModel));
		this.playerController = this.controller.getCurrentPlayer();


		int nightsBefore = this.controller.getGameModel().getQuietNightsLeft();
		this.controller.endOfTurn();
		int nightsAfter = this.controller.getGameModel().getQuietNightsLeft();

		assertTrue(nightsBefore == nightsAfter + 1);

		CardModel card = new CardModel("Government Grant", CardModel.CardType.EVENT);
		this.controller.getGameModel().setSelectedCard(card);

		int actionsBefore = this.controller.getGameModel().getActionsLeft();

		assertTrue(this.controller.specialAbility());

		int actionsAfter = this.controller.getGameModel().getActionsLeft();
		assertTrue(actionsBefore == actionsAfter + 1);

		assertTrue(!this.controller.getGameModel().getSelectedContingencyPlan().getName().equals(""));
	}

	@Test
	public void testCardNameToCardFindsCard() {
		String cardName = this.listOfCities.get(0).getName();
		ArrayList<CardModel> listOfTopCards = new ArrayList<CardModel>();
		CardModel testCity1 = new CardModel(cardName, CardModel.CardType.PLAYER);
		CardModel testCity2 = new CardModel("Hello", CardModel.CardType.PLAYER);
		CardModel testCity3 = new CardModel("myname", CardModel.CardType.PLAYER);
		CardModel testCity4 = new CardModel("isbob", CardModel.CardType.PLAYER);
		listOfTopCards.add(testCity1);
		listOfTopCards.add(testCity2);
		listOfTopCards.add(testCity3);
		listOfTopCards.add(testCity4);

		assertEquals(testCity1, this.controller.cardNameToCard(cardName, listOfTopCards));

	}

	@Test
	public void testCardNameToCardDoesntFindCard() {
		String cardName = this.listOfCities.get(0).getName();
		ArrayList<CardModel> listOfTopCards = new ArrayList<CardModel>();
		CardModel testCity1 = new CardModel("notBob", CardModel.CardType.PLAYER);
		CardModel testCity2 = new CardModel("Hello", CardModel.CardType.PLAYER);
		CardModel testCity3 = new CardModel("myname", CardModel.CardType.PLAYER);
		CardModel testCity4 = new CardModel("isbob", CardModel.CardType.PLAYER);
		listOfTopCards.add(testCity1);
		listOfTopCards.add(testCity2);
		listOfTopCards.add(testCity3);
		listOfTopCards.add(testCity4);

		assertEquals(null, this.controller.cardNameToCard(cardName, listOfTopCards));

	}

	@Test
	public void testRemoveEventCardFromHand() {
		CardModel cardToAdd1 = new CardModel("notBob", CardModel.CardType.PLAYER);
		CardModel cardToAdd2 = new CardModel("Hello", CardModel.CardType.PLAYER);
		this.controller.getPlayers().get(0).addCardToHandOfCards(cardToAdd1);
		this.controller.getPlayers().get(0).addCardToHandOfCards(cardToAdd2);
		assertEquals(2, this.controller.getPlayers().get(0).getCharacterModel().getHandSize());

		this.controller.removeEventCardFromHand(cardToAdd1);
		assertEquals(1, this.controller.getPlayers().get(0).getCharacterModel().getHandSize());
	}

	@Test
	public void testInitInfectionNumberOfCities() {
		int citiesInfected = this.controller.getInfectionDeckController().getNumberOfCardsInDiscard();
		int shouldBeInfected = 9;

		assertEquals(citiesInfected, shouldBeInfected);
	}

	@Test
	public void testInitAllOnAtlanta() {
		for (AbstractCharacterController character : this.controller.getPlayers()) {
			assertEquals(character.getCharactersCurrentCity(), this.startingCity);
		}
	}

	@Test
	public void testHandleEpidemic() {
		int infectionIndex = this.controller.getGameModel().getInfectionRateIndex();
		
		CardModel bottom = ((InfectionDeckCardController) 
				this.controller.getInfectionDeckController()).drawBottomCard();
		
		InfectionDeckCardController infectController = 
				((InfectionDeckCardController) this.controller.getInfectionDeckController());
		
		infectController.addToBottom(bottom);
		this.controller.handleEpidemic();
		
		CityModel bottomCity = this.controller.getCityController().getCityByName(bottom.getName());
		assertEquals(3, bottomCity.getCubesByDisease(bottomCity.getPrimaryDisease()));
		assertEquals(10, infectController.getDiscardedCards().size());

		int indexAfter = this.controller.getGameModel().getInfectionRateIndex();

		assertTrue(infectionIndex < indexAfter);
	}
	
	@Test
	public void testHandleEpidemicInfectionRateIndexMinusOne() {
		int currentInfectionRateIndex = 7;
		this.controller.getGameModel().setInfectionRateIndex(currentInfectionRateIndex);
		this.controller.handleEpidemic();
		assertEquals(7, this.controller.getGameModel().getInfectionRateIndex());
	}
	@Test
	public void testHandleEpidemicInfectionRateIndexChangeBoundary() {
		int currentInfectionRateIndex = 8;
		this.controller.getGameModel().setInfectionRateIndex(currentInfectionRateIndex);
		this.controller.handleEpidemic();
		assertEquals(8, this.controller.getGameModel().getInfectionRateIndex());
	}
	
	@Test
	public void testHandleEpidemicInfectionRateIndexChangeBoundaryNine() {
		int currentInfectionRateIndex = 9;
		this.controller.getGameModel().setInfectionRateIndex(currentInfectionRateIndex);
		this.controller.handleEpidemic();
		assertEquals(9, this.controller.getGameModel().getInfectionRateIndex());
	}
	
	@Test
	public void testHandleEpidemicInfectionRateIndexChangeBoundarySix() {
		int currentInfectionRateIndex = 6;
		this.controller.getGameModel().setInfectionRateIndex(currentInfectionRateIndex);
		this.controller.handleEpidemic();
		assertEquals(6, this.controller.getGameModel().getInfectionRateIndex());
	}
	
	@Test
	public void testDrawDoubleEpidemic(){
		CardModel epidemicOne = new CardModel(EPIDEMIC, CardModel.CardType.EPIDEMIC);
		CardModel epidemicTwo = new CardModel(EPIDEMIC, CardModel.CardType.EPIDEMIC);
		this.playerDeckController.addCard(epidemicTwo);
		this.playerDeckController.addCard(epidemicOne);
		int infectionIndexBefore = this.controller.getGameModel().getInfectionRateIndex();
		this.controller.endOfTurn();
		int infectionIndexAfter = this.controller.getGameModel().getInfectionRateIndex();
		assertEquals(infectionIndexAfter, infectionIndexBefore + 2);
		assertEquals(infectionIndexAfter, this.playerDeckController.getDiscardedCards().size());
	}
	
	@Test
	public void testDrawTwoPlayer() {
		CardModel epidemicOne = new CardModel("player", CardModel.CardType.PLAYER);
		CardModel epidemicTwo = new CardModel("player", CardModel.CardType.PLAYER);
		this.playerDeckController.addCard(epidemicTwo);
		this.playerDeckController.addCard(epidemicOne);
		
		AbstractCharacterController player = this.controller.getCurrentPlayer();
		int numPlayerCardsBefore = player.getCharacterModel().getHandSize();
		int infectionIndexBefore = this.controller.getGameModel().getInfectionRateIndex();
		this.cityController.getOutbrokenCities().add(new CityModel("meow", this.controller.getDiseaseController().getBlueDisease()));
		this.controller.endOfTurn();
		
		int infectionIndexAfter = this.controller.getGameModel().getInfectionRateIndex();
		int numPlayerCardsAfter = player.getCharacterModel().getHandSize();
		
		assertEquals(0,this.controller.getCityController().getOutbrokenCities().size());
		assertEquals(infectionIndexAfter, infectionIndexBefore + 0);
		assertEquals(numPlayerCardsAfter, numPlayerCardsBefore + 2);
		assertEquals(infectionIndexAfter, this.playerDeckController.getDiscardedCards().size());
	}
}