package citiesTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.beans.DesignMode;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import city.CityController;
import city.CityModel;
import diseases.DiseaseController;
import diseases.DiseaseModel;


public class CityControllerTests {
	DiseaseController diseaseController;
	CityController controller;
	String cityName = "Chicago";
	
	@Before
	public void init(){
		this.diseaseController = new DiseaseController();
		this.controller = new CityController(diseaseController);
	}
	
	@Test
	public void testControllerInit(){
		DiseaseModel black = this.diseaseController.getBlackDisease();
		DiseaseModel yellow = this.diseaseController.getYellowDisease();
		DiseaseModel blue = this.diseaseController.getBlueDisease();
		DiseaseModel red = this.diseaseController.getRedDisease();
		for(CityModel city : this.controller.getCities()){
			assertTrue(city.getCubesByDisease(black) == 0);
			assertTrue(city.getCubesByDisease(blue) == 0);
			assertTrue(city.getCubesByDisease(yellow) == 0);
			assertTrue(city.getCubesByDisease(red) == 0);
		}
	}
	
	@Test
	public void testInfect(){
		CityModel cityToInfect = this.controller.getCityByName(cityName);
		DiseaseModel diseaseModel = this.diseaseController.getBlueDisease();
		
		int beforeInfect = cityToInfect.getCubesByDisease(diseaseModel);
		
		this.controller.infect(cityToInfect, diseaseModel);
		
		int afterInfect = cityToInfect.getCubesByDisease(diseaseModel);
		
		assertTrue(beforeInfect == afterInfect - 1);
	}
	
	@Test
	public void testInfectToOutbreak(){
		CityModel cityToInfect = this.controller.getCityByName(cityName);
		DiseaseModel diseaseModel = this.diseaseController.getBlueDisease();
		
		this.controller.infect(cityToInfect, diseaseModel);
		this.controller.infect(cityToInfect, diseaseModel);
		this.controller.infect(cityToInfect, diseaseModel);
		
		int beforeOutbreak = cityToInfect.getCubesByDisease(diseaseModel);
		for(CityModel city: cityToInfect.getNeighbors()){
			beforeOutbreak += city.getCubesByDisease(diseaseModel);
		}
		
		this.controller.infect(cityToInfect, diseaseModel);
		
		int afterOutbreak = cityToInfect.getCubesByDisease(diseaseModel);
		for(CityModel city: cityToInfect.getNeighbors()){
			afterOutbreak += city.getCubesByDisease(diseaseModel);
		}
		
		int numNeighbors = cityToInfect.getNeighbors().size();
		
		assertTrue(beforeOutbreak == afterOutbreak - numNeighbors);
	}
	
	@Test
	public void testInfectQuarentined(){
		CityModel cityToInfect = this.controller.getCityByName(cityName);
		DiseaseModel diseaseModel = this.diseaseController.getBlueDisease();
		cityToInfect.setQuarentined(true);
		
		int beforeInfect = cityToInfect.getCubesByDisease(diseaseModel);
		
		this.controller.infect(cityToInfect, diseaseModel);
		
		int afterInfect = cityToInfect.getCubesByDisease(diseaseModel);
		
		assertTrue(beforeInfect == afterInfect);
	}
	
	@Test
	public void testInfectToOutbreakQuarentinedNeighbor(){
		CityModel cityToInfect = this.controller.getCityByName(cityName);
		DiseaseModel diseaseModel = this.diseaseController.getBlueDisease();
		boolean firstNeighbor = true;
		
		
		this.controller.infect(cityToInfect, diseaseModel);
		this.controller.infect(cityToInfect, diseaseModel);
		this.controller.infect(cityToInfect, diseaseModel);
		
		int beforeOutbreak = cityToInfect.getCubesByDisease(diseaseModel);
		for(CityModel city: cityToInfect.getNeighbors()){
			if(firstNeighbor){
				city.setQuarentined(true);
				firstNeighbor = false;
			}
			beforeOutbreak += city.getCubesByDisease(diseaseModel);
		}
		
		
		this.controller.infect(cityToInfect, diseaseModel);
		
		int afterOutbreak = cityToInfect.getCubesByDisease(diseaseModel);
		for(CityModel city: cityToInfect.getNeighbors()){
			afterOutbreak += city.getCubesByDisease(diseaseModel);
		}
		
		int numNeighbors = cityToInfect.getNeighbors().size();
		int numQuarentined = 1;
		afterOutbreak -= (numNeighbors - numQuarentined);
		
		assertTrue(beforeOutbreak == afterOutbreak);
	}
	
	@Test
	public void testGetOutBrokenCities(){
		assertEquals(new HashSet<>(), this.controller.getOutbrokenCities());
	}
}
