package characterTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
import characters.AbstractCharacterController;
import characters.CharacterModel;
import characters.ContingencyPlannerCharacterController;
import characters.DispatcherCharacterController;
import city.CityController;
import city.CityModel;
import diseases.DiseaseController;
import diseases.DiseaseModel;
import game.GameController;
import game.GameModel;

public class AbstractCharacterControllerTests {
	DiseaseController diseaseController;
	String cityName = "Chicago";
	GameController gameController;
	AbstractCharacterController contPlanner;
	CharacterModel character, secondCharacter;
	AbstractCharacterController characterController;
	CityModel cityModel, testCity;
	CityModel neighborCityModel;
	DiseaseModel blueDisease;
	DiseaseModel redDisease;
	CityController cityController;
	List<CityModel> listOfCities;
	AbstractDeckCardController playerDeckController;
	AbstractDeckCardController infectionDeckController;
	Map<CityModel, CardModel> cityToCardMap;

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
		this.blueDisease = diseaseController.getBlueDisease();
		this.redDisease = diseaseController.getRedDisease();
		
		this.cityToCardMap = playerDeckController.getCityToCardMap();
		
		Set<CityModel> setOfCities = cityController.getCities();
		this.listOfCities = new ArrayList<CityModel>(setOfCities);
		
		this.cityModel = listOfCities.get(0);
		this.testCity = listOfCities.get(30);
		this.character = new CharacterModel(characterName, this.cityModel);
		this.secondCharacter = new CharacterModel("otherName", this.testCity);
		this.characterController = new DispatcherCharacterController(this.character);
		CharacterModel contingencyCharacterModel = new CharacterModel(characterName, this.testCity);
		this.contPlanner = new ContingencyPlannerCharacterController(contingencyCharacterModel);
		
	}
	
	@Test
	public void testGetCharacterModel(){
		assertEquals(this.character, this.characterController.getCharacterModel());
	}
	
	@Test
	public void testGetCharactersCurrentCity(){
		assertEquals(this.character.getCurrentCity(), this.characterController.getCharactersCurrentCity());
	}
	
	@Test
	public void testverifyMoveWithoutCardIsANeighbor(){
		Set<CityModel> setOfNeighbors = this.cityModel.getNeighbors();
		List<CityModel> list = new ArrayList<CityModel>(setOfNeighbors);
		this.neighborCityModel = list.get(0);
		
		assertTrue(this.characterController.verifyMoveWithoutCard(this.neighborCityModel));
	}
	
	@Test
	public void testverifyMoveWithoutCardIsANeighborAndHasResearchStation(){
		Set<CityModel> setOfNeighbors = this.cityModel.getNeighbors();
		List<CityModel> list = new ArrayList<CityModel>(setOfNeighbors);
		this.neighborCityModel = list.get(0);
		this.neighborCityModel.setHasResearchStation(true);
		
		assertTrue(this.characterController.verifyMoveWithoutCard(this.neighborCityModel));
	}
	
	@Test
	public void testverifyMoveWithoutCardIsNotANeighbor(){
		Set<CityModel> setOfNeighbors = this.cityModel.getNeighbors();
		List<CityModel> list = new ArrayList<CityModel>(setOfNeighbors);
		this.neighborCityModel = list.get(0);
		
		CityModel fakeCityModel = new CityModel(this.cityName, new DiseaseModel());
		
		assertFalse(this.characterController.verifyMoveWithoutCard(fakeCityModel));
	}
	
	@Test
	public void testverifyMoveWithoutCardFalse(){
		Set<CityModel> setOfNeighbors = this.cityModel.getNeighbors();
		List<CityModel> list = new ArrayList<CityModel>(setOfNeighbors);
		this.neighborCityModel = list.get(0);
		
		CityModel fakeCityModel = new CityModel(this.cityName, new DiseaseModel());
		
		assertFalse(this.characterController.verifyMoveWithoutCard(fakeCityModel));
	}
	
	@Test
	public void testverifyMoveWithoutCardBothCitiesHaveStations(){
		CityModel testCity = listOfCities.get(10);
		testCity.setHasResearchStation(true);
		this.characterController.getCharactersCurrentCity().setHasResearchStation(true);
		
		assertTrue(this.characterController.verifyMoveWithoutCard(testCity));
	}
	
	@Test
	public void testverifyMoveWithoutCurrentCityNoStation(){
		CityModel testCity = listOfCities.get(10);
		testCity.setHasResearchStation(true);
		this.characterController.getCharactersCurrentCity().setHasResearchStation(false);
		
		assertFalse(this.characterController.verifyMoveWithoutCard(testCity));
	}
	
	@Test
	public void testMoveWithoutCard(){		
		CityModel fakeCityModel = new CityModel(this.cityName, new DiseaseModel());
		this.characterController.moveWithoutCard(fakeCityModel);
		
		assertEquals(fakeCityModel, this.character.getCurrentCity());
	}
	
	@Test
	public void testVerifyBuildFalse(){
		CityController currentCityController = this.gameController.getCityController();
		currentCityController.setResearchStationCounter(6);
		this.characterController.addCardToHandOfCards(this.playerDeckController.getCityToCardMap()
				.get(this.character.getCurrentCity()));
		assertFalse(this.characterController.verifyBuild(currentCityController));
	}
	
	@Test
	public void testVerifyBuildNoCard(){
		CityController currentCityController = this.gameController.getCityController();
		currentCityController.setResearchStationCounter(3);
		assertFalse(this.characterController.verifyBuild(currentCityController));
	}
	
	@Test
	public void testVerifyBuildIsResearchStation(){
		this.cityController.setResearchStationCounter(3);
		characterController.getCharactersCurrentCity().setHasResearchStation(true);
		this.characterController.addCardToHandOfCards(this.playerDeckController.getCityToCardMap()
				.get(this.character.getCurrentCity()));
		assertFalse(characterController.verifyBuild(this.cityController));
	}
	
	@Test
	public void testVerifyBuildTrue(){
		//CityController currentCityController = this.gameController.getCityController();
		//currentCityController.setResearchStationCounter(3);
		this.cityController.setResearchStationCounter(3);
		characterController.getCharactersCurrentCity().setHasResearchStation(false);
		this.characterController.addCardToHandOfCards(this.playerDeckController.getCityToCardMap()
				.get(this.character.getCurrentCity()));
		assertTrue(characterController.verifyBuild(this.cityController));
	}
	
	@Test
	public void testHasCardForCurrentCityFalse(){
		this.characterController.addCardToHandOfCards(this.playerDeckController.getCityToCardMap()
				.get(this.testCity));
		assertFalse(this.characterController.hasCardForCurrentCity());
	}
	
	@Test
	public void testMoveWithCard(){
		CardModel testCityCardModel = this.playerDeckController.getCityToCardMap().get(this.testCity);
		this.characterController.addCardToHandOfCards(testCityCardModel);
		assertEquals(1, this.character.getHandSize());
		this.characterController.moveWithCard(testCity, testCityCardModel);
		assertEquals(testCity, this.character.getCurrentCity());
		assertEquals(0, this.character.getHandSize());
	}
	
	@Test
	public void testVerifyMoveWithCardHasCard(){
		CardModel testCityCardModel = this.playerDeckController.getCityToCardMap().get(this.testCity);
		this.characterController.addCardToHandOfCards(this.playerDeckController.getCityToCardMap()
				.get(this.testCity));
		this.character.setCurrentCity(this.testCity);
		assertTrue(this.characterController.verifyMoveWithCard(testCity, testCityCardModel));
	}
	
	@Test
	public void testVerifyMoveWithCardNoHasCard(){
		CardModel testCityCardModel = this.playerDeckController.getCityToCardMap().get(this.testCity);
		assertTrue(this.characterController.verifyMoveWithCard(testCity, testCityCardModel));
	}
	
	@Test
	public void testVerifyMoveWithCardFalse(){
		CardModel testCityCardModel = this.playerDeckController.getCityToCardMap().get(this.testCity);
		assertFalse(this.characterController.verifyMoveWithCard(this.character.getCurrentCity()
				, testCityCardModel));
	}
	
	@Test
	public void testShareKnowledgeIsInHand(){
		CardModel testCityCardModel = this.playerDeckController.getCityToCardMap().get(this.testCity);
		this.secondCharacter = new CharacterModel("otherCharacter", this.testCity);
		this.characterController.addCardToHandOfCards(this.playerDeckController.getCityToCardMap()
				.get(this.testCity));
		assertEquals(1, this.characterController.getCharacterModel().getHandSize());
		this.characterController.shareKnowledge(this.secondCharacter, testCityCardModel);
		assertEquals(0, this.characterController.getCharacterModel().getHandSize());
		assertEquals(1, secondCharacter.getHandSize());
	}
	
	@Test
	public void testShareKnowledgeNotInHand(){
		CardModel testCityCardModel = this.playerDeckController.getCityToCardMap().get(this.testCity);
		this.secondCharacter = new CharacterModel("otherCharacter", this.testCity);
		this.characterController.shareKnowledge(this.secondCharacter, testCityCardModel);
	}
	
	@Test
	public void testVerifyShareKnowledgeIsInHand(){
		this.contPlanner.addCardToHandOfCards(this.playerDeckController.getCityToCardMap()
				.get(this.testCity));
		assertFalse(this.contPlanner.verifyShareKnowledge(characterController, true));
	}
	
	@Test
	public void testBuildCityTrue(){
		//CityController currentCityController = this.gameController.getCityController();
		this.characterController.addCardToHandOfCards(this.playerDeckController.getCityToCardMap()
				.get(this.testCity));
		assertEquals(1, this.character.getHandSize());
		this.character.setCurrentCity(this.testCity);
		this.characterController.build(this.cityController);
		assertEquals(0, this.character.getHandSize());
		assertTrue(this.testCity.hasResearchStation());
		assertEquals(2, this.cityController.getResearchStationCounter());
	}
	
	@Test
	public void testBuildCityFalse(){
		CityController currentCityController = this.gameController.getCityController();
		this.characterController.addCardToHandOfCards(this.playerDeckController.getCityToCardMap()
				.get(this.testCity));
		this.characterController.build(currentCityController);
		assertFalse(this.testCity.hasResearchStation());
	}
	
	@Test
	public void testCureTrue(){
		Set<CardModel> cardsToCure = developCardSet();
		List<CardModel> listOfCardsToCure = new ArrayList<CardModel>(cardsToCure);
		for(int i = 0; i < listOfCardsToCure.size();i++){
			this.characterController.addCardToHandOfCards(listOfCardsToCure.get(i));
		}
		assertEquals(5, this.character.getHandSize());
		
		this.blueDisease.setCubesLeft(24);
		
		assertFalse(this.blueDisease.isCured());
		assertFalse(this.blueDisease.isEradicated());
		
		this.characterController.cure(cardsToCure, this.blueDisease);
		
		assertEquals(0, this.character.getHandSize());
		
		assertTrue(this.blueDisease.isCured());
		assertTrue(this.blueDisease.isEradicated());
	}
	
	@Test
	public void testCureFalse(){		
		Set<CardModel> cardsToCure = developCardSet();
		
		this.blueDisease.setCubesLeft(0);
		
		this.characterController.cure(cardsToCure, this.blueDisease);
		
		assertTrue(this.blueDisease.isCured());
		assertFalse(this.blueDisease.isEradicated());
	}
	
	private Set<CardModel> developCardSet() {
		Set<CityModel> setOfCities = cityController.getCities();
		this.listOfCities = new ArrayList<CityModel>(setOfCities);
		
		CityModel firstCureCity = listOfCities.get(0);
		CityModel secondCureCity = listOfCities.get(1);
		CityModel thirdCureCity = listOfCities.get(2);
		CityModel fourthCureCity = listOfCities.get(3);
		CityModel fifthCureCity = listOfCities.get(4);
		CardModel firstCureCard = this.playerDeckController.getCityToCardMap().get(firstCureCity);
		CardModel secondCureCard = this.playerDeckController.getCityToCardMap().get(secondCureCity);
		CardModel thirdCureCard = this.playerDeckController.getCityToCardMap().get(thirdCureCity);
		CardModel fourthCureCard = this.playerDeckController.getCityToCardMap().get(fourthCureCity);
		CardModel fifthCureCard = this.playerDeckController.getCityToCardMap().get(fifthCureCity);
		this.characterController.addCardToHandOfCards(firstCureCard);
		this.characterController.addCardToHandOfCards(secondCureCard);
		this.characterController.addCardToHandOfCards(thirdCureCard);
		this.characterController.addCardToHandOfCards(fourthCureCard);
		this.characterController.addCardToHandOfCards(fifthCureCard);
		
		Set<CardModel> cardsToCure = new HashSet<CardModel>();
		cardsToCure.add(firstCureCard);
		cardsToCure.add(secondCureCard);
		cardsToCure.add(thirdCureCard);
		cardsToCure.add(fourthCureCard);
		cardsToCure.add(fifthCureCard);
		
		return cardsToCure;
	}

	@Test
	public void testTreatNotCured(){
		this.blueDisease.setCured(false);
		this.blueDisease.setCubesLeft(0);
		this.characterController.treat(this.blueDisease);
		
		this.character.getCurrentCity().setCubesByDisease(this.blueDisease, 2);
		
		assertEquals(1, this.blueDisease.getCubesLeft());
	}
	
	@Test
	public void testTreatIsCuredWith24Cubes(){
		this.blueDisease.setCured(true);
		this.blueDisease.setCubesLeft(24);
		this.characterController.treat(this.blueDisease);
		
		//this.character.getCurrentCity().setCubesByDisease(this.blueDisease, 2);
		assertTrue(this.blueDisease.isEradicated());
		assertEquals(0, this.character.getCurrentCity().getCubesByDisease(this.blueDisease));
	}
	
	@Test
	public void testTreatIsCuredWith0Cubes(){
		this.blueDisease.setCured(true);
		this.blueDisease.setCubesLeft(0);
		this.character.setCubesByDiseaseOnCurrentCity(this.blueDisease, 2);
		assertEquals(0, this.blueDisease.getCubesLeft());
		this.characterController.treat(this.blueDisease);
		assertEquals(2, this.blueDisease.getCubesLeft());
		//this.character.getCurrentCity().setCubesByDisease(this.blueDisease, 2);
		assertFalse(this.blueDisease.isEradicated());
		assertEquals(0, this.character.getCurrentCity().getCubesByDisease(this.blueDisease));
	}
	
	@Test
	public void testVerifyCureFiveCardsTrue(){
		Set<CardModel> cardsToCure = developCardSet(5);
		assertTrue(characterController.verifyCure(cardsToCure, this.blueDisease));
	}
	
	@Test
	public void testVerifyCureFiveCardsFalse(){
		Set<CardModel> cardsToCure = developCardSetNoHandAdd(5);
		assertFalse(characterController.verifyCure(cardsToCure, this.blueDisease));
	}
	
	@Test
	public void testVerifyCureFiveCardsTwoCards(){
		Set<CardModel> cardsToCure = developCardSetNoHandAdd(2);
		assertFalse(characterController.verifyCure(cardsToCure, this.blueDisease));
	}
	
	@Test
	public void testVerifyCureFiveCardsAlreadyCured(){
		Set<CardModel> cardsToCure = developCardSetNoHandAdd(5);
		this.blueDisease.setCured(true);
		assertFalse(characterController.verifyCure(cardsToCure, this.blueDisease));
	}
	
	@Test
	public void testVerifyDiseaseCanBeTreatedFalse(){
		this.characterController.getCharactersCurrentCity().setCubesByDisease(this.blueDisease, 0);
		assertFalse(this.characterController.verifyDiseaseCanBeTreated(this.blueDisease));
	}
	
	//this.characterController.getCharactersCurrentCity().setCubesByDisease(this.blueDisease, 0);
	//assertFalse(this.characterController.verifyDiseaseCanBeTreated(this.blueDisease));

	
	private Set<CardModel> developCardSet(int numCards) {
		Set<CityModel> setOfCities = cityController.getCities();
		this.listOfCities = new ArrayList<CityModel>(setOfCities);
		
		Set<CardModel> cardsToCure = new HashSet<CardModel>();
		for(int i = 0; i < numCards; i++){
			CityModel cityToAdd = listOfCities.get(i);
			this.characterController.addCardToHandOfCards(this.cityToCardMap.get(cityToAdd));
			cardsToCure.add(this.cityToCardMap.get(cityToAdd));
		}
		
		return cardsToCure;
	}
	
	private Set<CardModel> developCardSetNoHandAdd(int numCards) {
		Set<CityModel> setOfCities = cityController.getCities();
		this.listOfCities = new ArrayList<CityModel>(setOfCities);
		
		Set<CardModel> cardsToCure = new HashSet<CardModel>();
		for(int i = 0; i < numCards; i++){
			CityModel cityToAdd = listOfCities.get(i);
			cardsToCure.add(this.cityToCardMap.get(cityToAdd));
		}
		
		return cardsToCure;
	}
}
