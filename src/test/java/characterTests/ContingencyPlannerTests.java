package characterTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import cards.AbstractDeckCardController;
import cards.InfectionDeckCardController;
import cards.PlayerDeckCardController;
import characters.CharacterModel;
import characters.ContingencyPlannerCharacterController;
import city.CityController;
import city.CityModel;
import diseases.DiseaseController;
import diseases.DiseaseModel;
import game.GameController;
import game.GameModel;

public class ContingencyPlannerTests {
	DiseaseController diseaseController;
	String cityName = "Chicago";
	GameController gameController;
	ContingencyPlannerCharacterController contPlanner;

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
		CityModel cityModel = new CityModel(cityName, diseaseModel);
		CharacterModel characterModel = new CharacterModel(characterName, cityModel);
		contPlanner = new ContingencyPlannerCharacterController(characterModel);
		
		//this.gameController = new GameController(null, diseaseController, controller, null, null);
	}
	
	/*@Test
	public void testVerifyAbilityTrue(){
		assertFalse(contPlanner.verifyAbility(null));
		
		// when it gets implemented
	}*/
	
	@Test
	public void testVerifyAbilityFalse(){
		assertFalse(contPlanner.verifyAbility(null));
	}
	
	@Test
	public void testEndTurn(){
		assertTrue(true);
		
		// fix when it gets implemented
	}
	
	@Test
	public void testAbilityEmpty(){
		this.contPlanner.ability(this.gameController);
	}

}
