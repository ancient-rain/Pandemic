package characterTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
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
import characters.CharacterModel.CharacterRole;
import characters.OperationsExpertCharacterController;
import city.CityController;
import city.CityModel;
import diseases.DiseaseController;
import diseases.DiseaseModel;
import game.GameController;
import game.GameModel;

public class OperationExpertCharacterControllerTests {

	DiseaseController diseaseController;
	DiseaseModel blueDisease;
	String cityName = "Chicago";
	GameController gameController;
	OperationsExpertCharacterController operSpecialist;
	Map<CityModel, CardModel> cityToCardMap;
	AbstractDeckCardController playerDeckController;
	AbstractDeckCardController infectionDeckController;
	CityController cityController;
	List<CityModel> listOfCities;
	CharacterModel characterModel;

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

		Set<CityModel> setOfCities = cityController.getCities();
		this.listOfCities = new ArrayList<CityModel>(setOfCities);
		
		String characterName = "CharacterName";
		DiseaseModel diseaseModel = new DiseaseModel();
		CityModel cityModel = this.listOfCities.get(0);
		this.characterModel = new CharacterModel(characterName, cityModel);
		
		this.operSpecialist = new OperationsExpertCharacterController(characterModel);
		this.blueDisease = diseaseController.getBlueDisease();

		this.cityToCardMap = playerDeckController.getCityToCardMap();
		
	}
	
	@Test
	public void testVerifyAbilityFalse(){
		assertFalse(operSpecialist.verifyAbility(this.gameController));
	}
	
	@Test
	public void testAbility(){
		operSpecialist.ability(this.gameController);
	}
	
	@Test
	public void testBuild(){
		operSpecialist.build(this.cityController);
		assertEquals(2, this.cityController.getResearchStationCounter());
		assertTrue(operSpecialist.getCharactersCurrentCity().hasResearchStation());
	}
	
	@Test
	public void testVerifyBuildTrue(){
		this.cityController.setResearchStationCounter(1);
		operSpecialist.getCharactersCurrentCity().setHasResearchStation(false);
		assertTrue(operSpecialist.verifyBuild(this.cityController));
	}
	
	@Test
	public void testVerifyBuildAtResearchStation(){
		this.cityController.setResearchStationCounter(1);
		operSpecialist.getCharactersCurrentCity().setHasResearchStation(true);
		assertFalse(operSpecialist.verifyBuild(this.cityController));
	}
	
	@Test
	public void testVerifyBuildFalse(){
		this.cityController.setResearchStationCounter(6);
		assertFalse(operSpecialist.verifyBuild(this.cityController));
	}
	
	@Test
	public void testVerifyBuildFalseTooLarge(){
		operSpecialist.build(this.cityController);
		this.cityController.setResearchStationCounter(7);
		assertFalse(operSpecialist.verifyBuild(this.cityController));
	}
	
	@Test
	public void testVerifyBuildFalseTooLargeOfNumber(){
		this.cityController.setResearchStationCounter(7);
		operSpecialist.getCharactersCurrentCity().setHasResearchStation(false);
		assertFalse(operSpecialist.verifyBuild(this.cityController));
	}
	
	// ask how to test this method to double check
	@Test
	public void testMoveWithCardIsAtResearchStation(){
		operSpecialist.getCharactersCurrentCity().setHasResearchStation(true);
		CityModel cityToMoveTo = listOfCities.get(1);
		CardModel cardToMoveWith = this.cityToCardMap.get(cityToMoveTo);
		this.operSpecialist.moveWithCard(cityToMoveTo, cardToMoveWith);
		assertFalse(this.operSpecialist.getMovedFromResearchStationWithcard());
	}
	
	@Test
	public void testMoveWithCardIsAtResearchStationNoShares(){
		operSpecialist.getCharactersCurrentCity().setHasResearchStation(true);
		CityModel cityToMoveTo = listOfCities.get(0);
		CityModel cityToMoveToNotCharacter = listOfCities.get(1);
		CardModel cardToMoveWith = this.cityToCardMap.get(cityToMoveTo);
		this.operSpecialist.moveWithCard(cityToMoveToNotCharacter, cardToMoveWith);
		assertTrue(this.operSpecialist.getMovedFromResearchStationWithcard());
		assertTrue(operSpecialist.verifyMoveWithCard(cityToMoveTo, cardToMoveWith));
	}
	
	@Test
	public void testMoveWithCardIsAtResearchStationFirstShareFalseSecondShareFalse(){
		CityModel cityModel = this.listOfCities.get(3);
		this.characterModel = new CharacterModel("", cityModel);
		
		this.operSpecialist = new OperationsExpertCharacterController(characterModel);
		
		operSpecialist.getCharactersCurrentCity().setHasResearchStation(true);
		CityModel cityToMoveTo = listOfCities.get(0);
		CityModel cityToMoveToNotCharacter = listOfCities.get(1);
		CardModel cardToMoveWith = this.cityToCardMap.get(cityToMoveTo);
		this.operSpecialist.moveWithCard(cityToMoveToNotCharacter, cardToMoveWith);
		assertFalse(this.operSpecialist.getMovedFromResearchStationWithcard());
		assertTrue(operSpecialist.getCharactersCurrentCity().equals(cityToMoveToNotCharacter));
	}
	
	@Test
	public void testVerifyMoveWithCardAllIfsFalse(){
		operSpecialist.getCharactersCurrentCity().setHasResearchStation(true);
		CityModel cityToMoveTo = listOfCities.get(0);
		CityModel cityToMoveToNotCharacter = listOfCities.get(1);
		CardModel cardToMoveWith = this.cityToCardMap.get(cityToMoveTo);
		this.operSpecialist.addCardToHandOfCards(cardToMoveWith);
		assertEquals(1, this.operSpecialist.getCharacterModel().getHandSize());
		this.operSpecialist.moveWithCard(cityToMoveToNotCharacter, cardToMoveWith);
		assertEquals(0, this.operSpecialist.getCharacterModel().getHandSize());
		assertTrue(this.operSpecialist.getMovedFromResearchStationWithcard());
		assertFalse(operSpecialist.verifyMoveWithCard(cityToMoveToNotCharacter, cardToMoveWith));
	}
	
	@Test
	public void testMoveWithCardAllFalse(){
		operSpecialist.getCharactersCurrentCity().setHasResearchStation(false);
		CityModel cityToMoveTo = listOfCities.get(0);
		CityModel cityToMoveToNotCharacter = listOfCities.get(1);
		operSpecialist.moveWithCard(listOfCities.get(10), this.cityToCardMap.get(cityToMoveTo));
		CardModel cardToMoveWith = this.cityToCardMap.get(cityToMoveTo);
		this.operSpecialist.moveWithCard(cityToMoveTo, cardToMoveWith);
		assertFalse(this.operSpecialist.getMovedFromResearchStationWithcard());
	}
	
	// verify move with card ask how to check again
	@Test
	public void testVerifyMoveWithCardHasCurrentCityCard(){
		operSpecialist.getCharactersCurrentCity().setHasResearchStation(false);
		CardModel cardToAdd = this.cityToCardMap.get(operSpecialist.getCharactersCurrentCity());
		operSpecialist.addCardToHandOfCards(cardToAdd);
		assertTrue(operSpecialist.verifyMoveWithCard(operSpecialist.getCharactersCurrentCity(), cardToAdd));
	}
	
	@Test
	public void testVerifyMoveWithCardSharesName(){
		operSpecialist.getCharactersCurrentCity().setHasResearchStation(false);
		CityModel cityToMoveTo = operSpecialist.getCharactersCurrentCity();
		CardModel cardToAdd = this.cityToCardMap.get(operSpecialist.getCharactersCurrentCity());
		assertTrue(operSpecialist.verifyMoveWithCard(cityToMoveTo, cardToAdd));
	}
	
	@Test
	public void testVerifyMoveWithCardMovedFromResearch(){
		CityModel cityToMoveTo = operSpecialist.getCharactersCurrentCity();
		CardModel cardToAdd = this.cityToCardMap.get(listOfCities.get(12));
		operSpecialist.getCharactersCurrentCity().setHasResearchStation(true);
		assertTrue(operSpecialist.verifyMoveWithCard(cityToMoveTo, cardToAdd));
	}
	
	/*@Test
	public void testVerifyMoveWithCardMovedFromResearchWithCard(){
		operSpecialist.getCharactersCurrentCity().setHasResearchStation(true);
		CityModel cityToMoveTo = listOfCities.get(0);
		CityModel cityToMoveToNotCharacter = listOfCities.get(1);
		CardModel cardToMoveWith = this.cityToCardMap.get(cityToMoveTo);
		this.operSpecialist.moveWithCard(cityToMoveToNotCharacter, cardToMoveWith);
		assertTrue(operSpecialist.verifyMoveWithCard(cityToMoveTo, cardToMoveWith));
	}*/
	
	@Test
	public void testVerifyMoveWithCardAllFalse(){
		CityModel cityToMoveTo = listOfCities.get(1);
		CardModel cardToAdd = this.cityToCardMap.get(listOfCities.get(20));
		operSpecialist.getCharactersCurrentCity().setHasResearchStation(false);
		assertFalse(operSpecialist.verifyMoveWithCard(cityToMoveTo, cardToAdd));
	}

}
