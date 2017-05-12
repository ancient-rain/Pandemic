package characterTests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import characters.CharacterControllerFactory;
import characters.CharacterModel;
import characters.ContingencyPlannerCharacterController;
import characters.OperationsExpertCharacterController;
import characters.QuarentineSpecialistCharacterController;
import characters.ResearcherCharacterController;
import city.CityModel;
import diseases.DiseaseController;
import diseases.DiseaseModel;

public class CharacterControllerFactoryTests {
	DiseaseController diseaseController;
	String cityName = "Chicago";
	ResearcherCharacterController researcher;
	CharacterModel characterModel;
	CityModel cityModel;
	CharacterControllerFactory characterFactory;

	@Before
	public void init() {
		DiseaseModel diseaseModel = new DiseaseModel();
		this.cityModel = new CityModel(cityName, diseaseModel);
		this.characterFactory = new CharacterControllerFactory();
	}
	
	@Test
	public void testCreateContingencyPlanner(){
		String characterName = "Contingency Planner";
		this.characterModel = new CharacterModel(characterName, cityModel);
		assertEquals(ContingencyPlannerCharacterController.class, this.characterFactory.createCharacterController(this.characterModel).getClass());
	}
	
	@Test
	public void testCreateOperationsExpert(){
		String characterName = "Operations Expert";
		this.characterModel = new CharacterModel(characterName, cityModel);
		assertEquals(OperationsExpertCharacterController.class, this.characterFactory.createCharacterController(this.characterModel).getClass());
	}
	
	@Test
	public void testCreateQuarentineSpecialist(){
		String characterName = "Quarentine Specialist";
		this.characterModel = new CharacterModel(characterName, cityModel);
		assertEquals(QuarentineSpecialistCharacterController.class, this.characterFactory.createCharacterController(this.characterModel).getClass());
	}

}
