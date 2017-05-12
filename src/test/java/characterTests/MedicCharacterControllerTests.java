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
import characters.MedicCharacterController;
import characters.ResearcherCharacterController;
import city.CityController;
import city.CityModel;
import diseases.DiseaseController;
import diseases.DiseaseModel;
import game.GameController;
import game.GameModel;

public class MedicCharacterControllerTests {
	DiseaseController diseaseController;
	String cityName = "Chicago";
	GameController gameController;
	MedicCharacterController medic;
	CityModel cityModel;
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
		DiseaseModel diseaseModel = new DiseaseModel();
		this.cityModel = new CityModel(cityName, diseaseModel);
		CharacterModel characterModel = new CharacterModel(characterName, cityModel);
		this.medic = new MedicCharacterController(characterModel);
		
		Set<CityModel> setOfCities = cityController.getCities();
		this.listOfCities = new ArrayList<CityModel>(setOfCities);
		
	}
	
	@Test
	public void testVerifyAbilityFalse(){
		assertFalse(this.medic.verifyAbility(this.gameController));
	}
	
	@Test
	public void testEndTurn(){
		this.medic.endTurn();
	}
	
	@Test
	public void testMoveWithoutCard(){
		CityModel randomCity = this.listOfCities.get(20);

		Set<DiseaseModel> setOfDiseases = randomCity.getDiseases();
		List<DiseaseModel> listOfDiseases = new ArrayList<DiseaseModel>(setOfDiseases);
		
		DiseaseModel disease = listOfDiseases.get(0);
		disease.setCured(true);
		disease.setCubesLeft(24);
		
		this.medic.moveWithoutCard(randomCity);
		
		assertEquals(0, randomCity.getCubesByDisease(disease));
		assertTrue(disease.isEradicated());
	}
	
	@Test
	public void testMoveWithoutCardLessCubes(){
		CityModel randomCity = this.listOfCities.get(20);

		Set<DiseaseModel> setOfDiseases = randomCity.getDiseases();
		List<DiseaseModel> listOfDiseases = new ArrayList<DiseaseModel>(setOfDiseases);
		
		DiseaseModel disease = listOfDiseases.get(0);
		disease.setCured(true);
		disease.setCubesLeft(5);
		
		this.medic.moveWithoutCard(randomCity);
		assertFalse(disease.isEradicated());
	}
	
	@Test
	public void testAbility(){
		this.medic.ability(this.gameController);
	}

}
