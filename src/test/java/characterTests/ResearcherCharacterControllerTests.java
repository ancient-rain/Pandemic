package characterTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import cards.AbstractDeckCardController;
import cards.InfectionDeckCardController;
import cards.PlayerDeckCardController;
import characters.CharacterModel;
import characters.ResearcherCharacterController;
import city.CityController;
import city.CityModel;
import diseases.DiseaseController;
import diseases.DiseaseModel;
import game.GameController;
import game.GameModel;

public class ResearcherCharacterControllerTests {
	DiseaseController diseaseController;
	String cityName = "Chicago";
	GameController gameController;
	ResearcherCharacterController researcher;

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
		this.researcher = new ResearcherCharacterController(characterModel);
		
	}
	
	@Test
	public void testVerifyAbilityFalse(){
		assertFalse(researcher.verifyAbility(this.gameController));
	}
	
	@Test
	public void testAbilityNameEqual(){
		researcher.ability(this.gameController);
	}

}
