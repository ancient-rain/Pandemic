package diseaseTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import city.CityController;
import diseases.DiseaseController;
import diseases.DiseaseModel;

public class DiseaseControllerTests {

	DiseaseController diseaseController;
	DiseaseModel black;
	DiseaseModel yellow;
	DiseaseModel blue;
	DiseaseModel red;
	
	@Before
	public void init(){
		this.diseaseController = new DiseaseController();
		black = this.diseaseController.getBlackDisease();
		yellow = this.diseaseController.getYellowDisease();
		blue = this.diseaseController.getBlueDisease();
		red = this.diseaseController.getRedDisease();
	}
	
	@Test
	public void testAreAllDiseasesCuredTrue(){
		black.setCured(true);
		yellow.setCured(true);
		blue.setCured(true);
		red.setCured(true);
		
		boolean areAllDiseasesCured = this.diseaseController.areAllDiseasesCured();
		
		assertTrue(areAllDiseasesCured);
	}
	
	@Test
	public void testAreAllDiseasesCuredBlackFalse(){
		black.setCured(false);
		yellow.setCured(true);
		blue.setCured(true);
		red.setCured(true);
		
		boolean areAllDiseasesCured = this.diseaseController.areAllDiseasesCured();
		
		assertFalse(areAllDiseasesCured);
	}
	
	@Test
	public void testAreAllDiseasesCuredYellowFalse(){
		black.setCured(true);
		yellow.setCured(false);
		blue.setCured(true);
		red.setCured(true);
		
		boolean areAllDiseasesCured = this.diseaseController.areAllDiseasesCured();
		
		assertFalse(areAllDiseasesCured);
	}
	
	@Test
	public void testAreAllDiseasesCuredBlueFalse(){
		black.setCured(true);
		yellow.setCured(true);
		blue.setCured(false);
		red.setCured(true);
		
		boolean areAllDiseasesCured = this.diseaseController.areAllDiseasesCured();
		
		assertFalse(areAllDiseasesCured);
	}
	
	@Test
	public void testAreAllDiseasesCuredRedFalse(){
		black.setCured(true);
		yellow.setCured(true);
		blue.setCured(true);
		red.setCured(false);
		
		boolean areAllDiseasesCured = this.diseaseController.areAllDiseasesCured();
		
		assertFalse(areAllDiseasesCured);
	}

}
