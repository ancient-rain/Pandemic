package characterTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import cards.AbstractDeckCardController;
import cards.CardModel;
import cards.InfectionDeckCardController;
import cards.PlayerDeckCardController;
import characters.CharacterModel;
import characters.ScientistCharacterController;
import city.CityController;
import city.CityModel;
import diseases.DiseaseController;
import diseases.DiseaseModel;
import game.GameController;
import game.GameModel;

public class ScientistCharacterControllerTests {
	DiseaseController diseaseController;
	DiseaseModel blueDisease;
	String cityName = "Chicago";
	GameController gameController;
	ScientistCharacterController scientist;
	Map<CityModel, CardModel> cityToCardMap;
	AbstractDeckCardController playerDeckController;
	AbstractDeckCardController infectionDeckController;
	CityController cityController;
	List<CityModel> listOfCities;

	@Before
	public void init() {
		GameModel gameModel = new GameModel();
		DiseaseController diseaseController = new DiseaseController();
		this.cityController = new CityController(diseaseController);
		this.playerDeckController = new PlayerDeckCardController(cityController);
		this.infectionDeckController = new InfectionDeckCardController(cityController);
		
		this.gameController = new GameController(gameModel,
											diseaseController,
											cityController,
											playerDeckController,
											infectionDeckController);

		
		String characterName = "CharacterName";
		DiseaseModel diseaseModel = new DiseaseModel();
		CityModel cityModel = new CityModel(cityName, diseaseModel);
		CharacterModel characterModel = new CharacterModel(characterName, cityModel);
		this.scientist = new ScientistCharacterController(characterModel);
		this.blueDisease = diseaseController.getBlueDisease();

		this.cityToCardMap = playerDeckController.getCityToCardMap();
		
		Set<CityModel> setOfCities = cityController.getCities();
		this.listOfCities = new ArrayList<CityModel>(setOfCities);
		
	}
	
	@Test
	public void testVerifyAbilityFalse(){
		assertFalse(scientist.verifyAbility(this.gameController));
	}
	
	@Test
	public void testEndTurn(){
		this.scientist.endTurn();
	}
	
	@Test
	public void testAbility(){
		scientist.ability(this.gameController);
	}
	
	@Test
	public void testVerifyCureFiveCardsTrue(){
		Set<CardModel> cardsToCure = developCardSet(5);
		assertTrue(scientist.verifyCure(cardsToCure, this.blueDisease));
	}
	
	@Test
	public void testVerifyCureFiveCardsFalse(){
		Set<CardModel> cardsToCure = developCardSetNoHandAdd(5);
		assertFalse(scientist.verifyCure(cardsToCure, this.blueDisease));
	}
	
	@Test
	public void testVerifyCureFiveCardsTwoCards(){
		Set<CardModel> cardsToCure = developCardSetNoHandAdd(2);
		assertFalse(scientist.verifyCure(cardsToCure, this.blueDisease));
	}
	
	@Test
	public void testVerifyCureFiveCardsAlreadyCured(){
		Set<CardModel> cardsToCure = developCardSetNoHandAdd(5);
		this.blueDisease.setCured(true);
		assertFalse(scientist.verifyCure(cardsToCure, this.blueDisease));
	}
	
	private Set<CardModel> developCardSet(int n) {
		Set<CityModel> setOfCities = cityController.getCities();
		this.listOfCities = new ArrayList<CityModel>(setOfCities);
		
		Set<CardModel> cardsToCure = new HashSet<CardModel>();
		for(int i = 0; i < n; i++){
			CityModel cityToAdd = listOfCities.get(i);
			this.scientist.addCardToHandOfCards(this.cityToCardMap.get(cityToAdd));
			cardsToCure.add(this.cityToCardMap.get(cityToAdd));
		}
		
		return cardsToCure;
	}
	
	private Set<CardModel> developCardSetNoHandAdd(int n) {
		Set<CityModel> setOfCities = cityController.getCities();
		this.listOfCities = new ArrayList<CityModel>(setOfCities);
		
		Set<CardModel> cardsToCure = new HashSet<CardModel>();
		for(int i = 0; i < n; i++){
			CityModel cityToAdd = listOfCities.get(i);
			cardsToCure.add(this.cityToCardMap.get(cityToAdd));
		}
		
		return cardsToCure;
	}
}
