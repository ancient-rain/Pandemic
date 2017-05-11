package characterTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import cards.AbstractDeckCardController;
import cards.InfectionDeckCardController;
import cards.PlayerDeckCardController;
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
	ContingencyPlannerCharacterController contPlanner;
	CharacterModel character;
	DispatcherCharacterController characterController;
	CityModel cityModel;
	CityModel neighborCityModel;
	DiseaseModel blueDisease;
	DiseaseModel redDisease;
	List<CityModel> listOfCities;

	@Before
	public void init() {
		GameModel gameModel = new GameModel();
		DiseaseController diseaseController = new DiseaseController();
		CityController cityController = new CityController(diseaseController);
		AbstractDeckCardController playerDeckController = new PlayerDeckCardController(cityController);
		AbstractDeckCardController infectionDeckController = new InfectionDeckCardController(cityController);
		
		this.gameController = new GameController(gameModel,
											diseaseController,
											cityController,
											playerDeckController,
											infectionDeckController);

		
		String characterName = "CharacterName";
		this.blueDisease = diseaseController.getBlueDisease();
		this.redDisease = diseaseController.getRedDisease();
		
		Set<CityModel> setOfCities = cityController.getCities();
		this.listOfCities = new ArrayList<CityModel>(setOfCities);
		
		this.cityModel = listOfCities.get(0);
		this.character = new CharacterModel(characterName, this.cityModel);
		this.characterController = new DispatcherCharacterController(this.character);
		
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
	public void testverifyMoveWithoutCardIsNotANeighbor(){
		Set<CityModel> setOfNeighbors = this.cityModel.getNeighbors();
		List<CityModel> list = new ArrayList<CityModel>(setOfNeighbors);
		this.neighborCityModel = list.get(0);
		
		CityModel fakeCityModel = new CityModel(this.cityName, new DiseaseModel());
		
		assertFalse(this.characterController.verifyMoveWithoutCard(fakeCityModel));
	}
	
	// can anyone move to any research station if they are on a current research station
	
	@Test
	public void testMoveWithoutCard(){		
		CityModel fakeCityModel = new CityModel(this.cityName, new DiseaseModel());
		this.characterController.moveWithoutCard(fakeCityModel);
		
		assertEquals(fakeCityModel, this.character.getCurrentCity());
	}
	
	/*@Test
	public void testVerifyDiseaseCanBeTreatedTrue(){		
		CityModel fakeCityModel = new CityModel(this.cityName, new DiseaseModel());
		this.characterController.moveWithoutCard(fakeCityModel);
		
		this.cityModel.setCubesByDisease(this.blueDisease, 2);
		
		assertTrue(this.characterController.verifyDiseaseCanBeTreated(this.blueDisease));
	}*/
	
	@Test
	public void testVerifyBuildFalse(){
		CityController currentCityController = this.gameController.getCityController();
		currentCityController.setResearchStationCounter(6);
		assertFalse(this.characterController.verifyBuild(currentCityController));
	}
	
	@Test
	public void testVerifyBuildNoCard(){
		CityController currentCityController = this.gameController.getCityController();
		currentCityController.setResearchStationCounter(3);
		assertFalse(this.characterController.verifyBuild(currentCityController));
	}
	
	@Test
	public void testVerifyBuildTrue(){
		CityController currentCityController = this.gameController.getCityController();
		currentCityController.setResearchStationCounter(3);
		this.characterController.addCardToHandOfCards(null);
		assertTrue(this.characterController.verifyBuild(currentCityController));
	}
}
