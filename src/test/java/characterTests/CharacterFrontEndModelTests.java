package characterTests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import cards.AbstractDeckCardController;
import cards.CardModel;
import characters.CharacterFrontEndModel;
import characters.CharacterModel;
import characters.ScientistCharacterController;
import city.CityController;
import city.CityModel;
import diseases.DiseaseController;
import diseases.DiseaseModel;
import game.GameController;
import game.GameModel;

import static constants.Constants.*;

public class CharacterFrontEndModelTests {
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
	CityModel cityModel;
	DiseaseModel diseaseModel;

	@Before
	public void init() {
		GameModel gameModel = new GameModel();
		DiseaseController diseaseController = new DiseaseController();
		this.cityController = new CityController(diseaseController);
		
		Set<CityModel> setOfCities = cityController.getCities();
		this.listOfCities = new ArrayList<CityModel>(setOfCities);
		
		cityModel = this.listOfCities.get(0);
		this.diseaseModel = new DiseaseModel();
	}
	
	@Test
	public void testGetColorEmpty(){
		String characterName = "";
		CharacterModel characterModel = new CharacterModel(characterName, cityModel);
		CharacterFrontEndModel frontEndModel = new CharacterFrontEndModel(characterModel);
	
		assertEquals(SCIENTIST_COLOR, frontEndModel.getColor());
	}
	
	@Test
	public void testGetColorContingency(){
		String characterName = "Contingency Planner";
		CharacterModel characterModel = new CharacterModel(characterName, cityModel);
		CharacterFrontEndModel frontEndModel = new CharacterFrontEndModel(characterModel);
	
		assertEquals(CONTINGENCY_PLANNER_COLOR, frontEndModel.getColor());
	}
	
	@Test
	public void testGetColorDispatcher(){
		String characterName = "Dispatcher";
		CharacterModel characterModel = new CharacterModel(characterName, cityModel);
		CharacterFrontEndModel frontEndModel = new CharacterFrontEndModel(characterModel);
	
		assertEquals(DISPATCHER_COLOR, frontEndModel.getColor());
	}
	
	@Test
	public void testGetColorMedic(){
		String characterName = "Medic";
		CharacterModel characterModel = new CharacterModel(characterName, cityModel);
		CharacterFrontEndModel frontEndModel = new CharacterFrontEndModel(characterModel);
	
		assertEquals(MEDIC_COLOR, frontEndModel.getColor());
	}
	
	@Test
	public void testGetColorOperations(){
		String characterName = "Operations Expert";
		CharacterModel characterModel = new CharacterModel(characterName, cityModel);
		CharacterFrontEndModel frontEndModel = new CharacterFrontEndModel(characterModel);
	
		assertEquals(OPERATIONS_EXPERT_COLOR, frontEndModel.getColor());
	}
	
	@Test
	public void testGetColorResearcher(){
		String characterName = "Researcher";
		CharacterModel characterModel = new CharacterModel(characterName, cityModel);
		CharacterFrontEndModel frontEndModel = new CharacterFrontEndModel(characterModel);
	
		assertEquals(RESEARCHER_COLOR, frontEndModel.getColor());
	}
	
	@Test
	public void testGetColorQuarentine(){
		String characterName = "Quarentine Specialist";
		CharacterModel characterModel = new CharacterModel(characterName, cityModel);
		CharacterFrontEndModel frontEndModel = new CharacterFrontEndModel(characterModel);
	
		assertEquals(QUARANTINE_SPECIALIST_COLOR, frontEndModel.getColor());
	}
	
	@Test
	public void testGetImgQuarentine(){
		String characterName = "Quarentine Specialist";
		CharacterModel characterModel = new CharacterModel(characterName, cityModel);
		CharacterFrontEndModel frontEndModel = new CharacterFrontEndModel(characterModel);
	
		assertEquals(QUARANTINE_SPECIALIST_ICON, frontEndModel.getImgPath());
	}
	
	// how do you test all of these elements of the switch statement

}
