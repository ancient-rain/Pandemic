package diseaseTests;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import diseases.DiseaseController;
import diseases.DiseaseFrontEndModel;
import diseases.DiseaseModel;

public class DiseaseFrontEndModelTests {
	DiseaseController diseaseController;
	DiseaseFrontEndModel diseaseFrontEndModel;
	DiseaseModel disease;
	Color color = Color.BLUE;
	
	@Before
	public void init(){
		this.diseaseController = new DiseaseController();
		this.disease = this.diseaseController.getBlueDisease();
		this.diseaseFrontEndModel = new DiseaseFrontEndModel(this.disease, color);
	}
	
	@Test
	public void testGetDiseaseTrue(){
		assertEquals(this.disease, this.diseaseFrontEndModel.getDisease());
	}
	
	@Test
	public void testGetColorTrue(){
		assertEquals(this.color, this.diseaseFrontEndModel.getColor());
	}

}
