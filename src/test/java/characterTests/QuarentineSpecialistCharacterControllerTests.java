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
import characters.QuarentineSpecialistCharacterController;
import characters.ScientistCharacterController;
import city.CityController;
import city.CityModel;
import diseases.DiseaseController;
import diseases.DiseaseModel;
import game.GameController;
import game.GameModel;

public class QuarentineSpecialistCharacterControllerTests {
	DiseaseController diseaseController;
	DiseaseModel blueDisease;
	String cityName = "Chicago";
	GameController gameController;
	QuarentineSpecialistCharacterController quarentineSpec;
	Map<CityModel, CardModel> cityToCardMap;
	AbstractDeckCardController playerDeckController;
	AbstractDeckCardController infectionDeckController;
	CityController cityController;
	List<CityModel> listOfCities;
	DiseaseModel diseaseModel;
	String characterName;	

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

		
		this.characterName = "CharacterName";
		this.diseaseModel = new DiseaseModel();
		
		Set<CityModel> setOfCities = cityController.getCities();
		this.listOfCities = new ArrayList<CityModel>(setOfCities);
		
		CityModel testCity = listOfCities.get(0);
		CharacterModel characterModel = new CharacterModel("random", testCity);
		this.quarentineSpec = new QuarentineSpecialistCharacterController(characterModel);
		
		this.blueDisease = diseaseController.getBlueDisease();

		this.cityToCardMap = playerDeckController.getCityToCardMap();
		
	}
	
	@Test
	public void testInitialCityQuarentined(){
		assertTrue(this.quarentineSpec.getCharactersCurrentCity().isQuarentined());
	}
	
	@Test
	public void testNeighboringCitiesQuarentined(){
		CityModel currentCity = this.quarentineSpec.getCharactersCurrentCity();
		Set<CityModel> neighboringCities = currentCity.getNeighbors();
		ArrayList<CityModel> neighboringCitiesList = new ArrayList<CityModel>(neighboringCities);
		for(int i = 0; i < neighboringCitiesList.size(); i++){
			assertTrue(neighboringCitiesList.get(i).isQuarentined());
		}
	}
	
	@Test
	public void testVerifyAbilityFalse(){
		assertFalse(quarentineSpec.verifyAbility(this.gameController));
	}
	
	@Test
	public void testEndTurn(){
		this.quarentineSpec.endTurn();
	}
	
	@Test
	public void testAbility(){
		quarentineSpec.ability(this.gameController);
	}
	
	@Test
	public void testMoveWithoutCard(){	
		CityModel previousCity = this.quarentineSpec.getCharactersCurrentCity();
		CityModel cityToMoveTo = listOfCities.get(1);
		quarentineSpec.moveWithoutCard(cityToMoveTo);
		
		Set<CityModel> previousNeighboringCities = previousCity.getNeighbors();
		assertFalse(previousCity.isQuarentined());
		ArrayList<CityModel> previousNeighboringCitiesList = new ArrayList<CityModel>(previousNeighboringCities);
		for(int i = 0; i < previousNeighboringCitiesList.size(); i++){
			assertFalse(previousNeighboringCitiesList.get(i).isQuarentined());
		}
		
		CityModel currentCity = this.quarentineSpec.getCharactersCurrentCity();
		Set<CityModel> neighboringCities = currentCity.getNeighbors();
		ArrayList<CityModel> neighboringCitiesList = new ArrayList<CityModel>(neighboringCities);
		for(int i = 0; i < neighboringCitiesList.size(); i++){
			assertTrue(neighboringCitiesList.get(i).isQuarentined());
		}
	}
}
