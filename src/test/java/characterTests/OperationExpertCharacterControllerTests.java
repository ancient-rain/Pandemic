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
		//CityModel cityModel = new CityModel(cityName, diseaseModel);
		CityModel cityModel = this.listOfCities.get(0);
		CharacterModel characterModel = new CharacterModel(characterName, cityModel);
		this.operSpecialist = new OperationsExpertCharacterController(characterModel);
		this.blueDisease = diseaseController.getBlueDisease();

		this.cityToCardMap = playerDeckController.getCityToCardMap();
		
	}
	
	@Test
	public void testVerifyAbilityFalse(){
		assertFalse(operSpecialist.verifyAbility(this.gameController));
	}
	
	@Test
	public void testEndTurn(){
		this.operSpecialist.endTurn();
	}
	
	@Test
	public void testAbility(){
		operSpecialist.ability(this.gameController);
	}
	
	@Test
	public void testBuild(){
		operSpecialist.build(this.cityController);
		assertEquals(2, this.cityController.getResearchStationCounter());
	}
	
	@Test
	public void testVerifyBuildTrue(){
		operSpecialist.build(this.cityController);
		this.cityController.setResearchStationCounter(1);
		assertTrue(operSpecialist.verifyBuild(this.cityController));
	}
	
	@Test
	public void testVerifyBuildFalse(){
		operSpecialist.build(this.cityController);
		this.cityController.setResearchStationCounter(6);
		assertFalse(operSpecialist.verifyBuild(this.cityController));
	}

}
