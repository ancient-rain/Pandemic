package characterTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import cards.AbstractDeckCardController;
import cards.CardModel;
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
	AbstractDeckCardController playerDeckController;
	AbstractDeckCardController infectionDeckController;
	CityController cityController;

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
		int numCubesLeft = 22;
		disease.setCubesLeft(numCubesLeft);
		randomCity.setCubesByDisease(disease, 24-numCubesLeft);
		
		assertEquals(2, randomCity.getCubesByDisease(disease));
		
		this.medic.moveWithoutCard(randomCity);
		
		assertEquals(0, randomCity.getCubesByDisease(disease));
		assertEquals(24, disease.getCubesLeft());
		assertTrue(disease.isEradicated());
	}
	
	@Test
	public void testMedicTreat(){
		CityModel randomCity = this.listOfCities.get(20);

		Set<DiseaseModel> setOfDiseases = randomCity.getDiseases();
		List<DiseaseModel> listOfDiseases = new ArrayList<DiseaseModel>(setOfDiseases);
		
		DiseaseModel disease = listOfDiseases.get(0);
		int numCubesLeft = 22;
		disease.setCubesLeft(numCubesLeft);
		
		
		this.medic.getCharacterModel().setCubesByDiseaseOnCurrentCity(disease, 2);
		assertEquals(22, disease.getCubesLeft());
		
		this.medic.treat(disease);
		
		assertEquals(24, disease.getCubesLeft());
		assertEquals(0, this.medic.getCharacterModel().getCubesByDiseaseOnCurrentCity(disease));
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
		assertEquals(randomCity, this.medic.getCharactersCurrentCity());
		assertFalse(disease.isEradicated());
	}
	
	@Test
	public void testAbility(){
		this.medic.ability(this.gameController);
	}

}
