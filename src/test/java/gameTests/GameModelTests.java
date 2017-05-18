package gameTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.*;
import org.junit.Test;

import cards.AbstractDeckCardController;
import cards.CardModel;
import cards.PlayerDeckCardController;
import city.CityController;
import city.CityModel;
import diseases.DiseaseController;
import game.GameModel;

public class GameModelTests {

	GameModel gameModel;
	Map<CityModel, CardModel> cityToCardMap;
	List<CityModel> listOfCities;
	DiseaseController diseaseController;
	CityController cityController;
	AbstractDeckCardController playerDeckController;

	@Before
	public void init() {
		this.gameModel = new GameModel();
		DiseaseController diseaseController = new DiseaseController();
		this.cityController = new CityController(diseaseController);
		this.playerDeckController = new PlayerDeckCardController(cityController);

		this.cityToCardMap = playerDeckController.getCityToCardMap();

		Set<CityModel> setOfCities = cityController.getCities();
		this.listOfCities = new ArrayList<CityModel>(setOfCities);
	}

	@Test
	public void testSetNumberOfStartingCards() {
		int numCards = 3;
		this.gameModel.setNumberOfStartingCards(numCards);
		assertEquals(numCards, this.gameModel.getNumberOfStartingCards());
	}

	@Test
	public void testSetLost() {
		this.gameModel.setLost(true);
		assertTrue(this.gameModel.isLost());
	}

	@Test
	public void testSetForecastCardsLeft() {
		int numCards = 3;
		this.gameModel.setForecastCardsLeft(numCards);
		assertEquals(numCards, this.gameModel.getForecastCardsLeft());
	}
	
	@Test
	public void testGetForecastcards(){
		int numForecastCards = 5;
		List<CardModel> listOfCards = new ArrayList<CardModel>();
		for(int i = 0; i < numForecastCards; i++){
			listOfCards.add(this.cityToCardMap.get(this.listOfCities.get(i)));
		}
		this.gameModel.setForecastCards(listOfCards);
		assertEquals(listOfCards, this.gameModel.getForecastCards());
	}
	
	@Test
	public void testGetCardToRemoveFromInfectionDeck(){
		int numForecastCards = 5;
		CardModel cardToRemove = this.cityToCardMap.get(this.listOfCities.get(0));
		this.gameModel.setCardToRemoveFromInfectionDeck(cardToRemove);
		assertEquals(cardToRemove, this.gameModel.getCardToRemoveFromInfectionDeck());
	}

}
