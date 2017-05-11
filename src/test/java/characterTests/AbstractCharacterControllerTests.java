package characterTests;

import static org.junit.Assert.*;

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
		this.blueDisease = new DiseaseModel();
		this.redDisease = new DiseaseModel();
		this.cityModel = new CityModel(cityName, this.blueDisease);
		this.character = new CharacterModel(characterName, this.cityModel);
		this.characterController = new DispatcherCharacterController(this.character);
		
		//Set<CityModel> setOfNeighbors = this.cityModel.getNeighbors();
		//this.neighborCityModel = (CityModel) setOfNeighbors.toArray()[0];
		
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
		assertTrue(this.characterController.verifyMoveWithoutCard(this.neighborCityModel));
	}

}
