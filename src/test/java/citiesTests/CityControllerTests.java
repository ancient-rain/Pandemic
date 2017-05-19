package citiesTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
	List<CityModel> listOfCities;
	
	@Before
	public void init(){
		this.diseaseController = new DiseaseController();
		this.controller = new CityController(diseaseController);
		
		Set<CityModel> setOfCities = controller.getCities();
		this.listOfCities = new ArrayList<CityModel>(setOfCities);
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
		
		int beforeInfectCubesLeft = diseaseModel.getCubesLeft();
		int beforeInfect = cityToInfect.getCubesByDisease(diseaseModel);
		
		this.controller.infect(cityToInfect, diseaseModel);
		
		int afterInfect = cityToInfect.getCubesByDisease(diseaseModel);
		int afterInfectCubesLeft = diseaseModel.getCubesLeft();
		
		boolean comparison = (beforeInfect == afterInfect - 1);
		boolean cubesLeftComparison = (beforeInfectCubesLeft == afterInfectCubesLeft + 1);
		
		assertTrue(comparison);
		assertTrue(cubesLeftComparison);
	}
	
	@Test
	public void testInfectOneCurrentCube(){
		CityModel cityToInfect = this.controller.getCityByName(cityName);
		DiseaseModel diseaseModel = this.diseaseController.getBlueDisease();
		cityToInfect.setCubesByDisease(diseaseModel, 1);
		int beforeInfectCubesLeft = diseaseModel.getCubesLeft();
		int beforeInfect = cityToInfect.getCubesByDisease(diseaseModel);
		
		this.controller.infect(cityToInfect, diseaseModel);
		
		int afterInfect = cityToInfect.getCubesByDisease(diseaseModel);
		int afterInfectCubesLeft = diseaseModel.getCubesLeft();
		
		boolean comparison = (beforeInfect == afterInfect - 1);
		boolean cubesLeftComparison = (beforeInfectCubesLeft == afterInfectCubesLeft + 1);
		
		assertEquals(1, this.controller.getInfectedCities().size());
		assertEquals(0, this.controller.getOutbrokenCities().size());
	}
	
	@Test
	public void testInfectTwoCurrentCubes(){
		CityModel cityToInfect = this.controller.getCityByName(cityName);
		DiseaseModel diseaseModel = this.diseaseController.getBlueDisease();
		cityToInfect.setCubesByDisease(diseaseModel, 2);
		int beforeInfectCubesLeft = diseaseModel.getCubesLeft();
		int beforeInfect = cityToInfect.getCubesByDisease(diseaseModel);
		
		this.controller.infect(cityToInfect, diseaseModel);
		
		int afterInfect = cityToInfect.getCubesByDisease(diseaseModel);
		int afterInfectCubesLeft = diseaseModel.getCubesLeft();
		
		boolean comparison = (beforeInfect == afterInfect - 1);
		boolean cubesLeftComparison = (beforeInfectCubesLeft == afterInfectCubesLeft + 1);

		assertEquals(1, this.controller.getInfectedCities().size());
		assertEquals(0, this.controller.getOutbrokenCities().size());
	}
	
	@Test
	public void testInfectThreeCurrentCubes(){
		CityModel cityToInfect = this.controller.getCityByName(cityName);
		DiseaseModel diseaseModel = this.diseaseController.getBlueDisease();
		cityToInfect.setCubesByDisease(diseaseModel, 3);
		int beforeInfectCubesLeft = diseaseModel.getCubesLeft();
		int beforeInfect = cityToInfect.getCubesByDisease(diseaseModel);
		
		assertEquals(0, this.controller.getOutbreakCoutner());
		this.controller.infect(cityToInfect, diseaseModel);

		assertEquals(cityToInfect.getNeighbors().size(), this.controller.getInfectedCities().size());
		assertEquals(1, this.controller.getOutbrokenCities().size());
		
		assertEquals(1, this.controller.getOutbreakCoutner());
		
		
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
		
		boolean comparison = (beforeOutbreak == afterOutbreak - numNeighbors);
		
		assertTrue(comparison);
	}
	
	@Test
	public void testInfectQuarentined(){
		CityModel cityToInfect = this.controller.getCityByName(cityName);
		DiseaseModel diseaseModel = this.diseaseController.getBlueDisease();
		cityToInfect.setQuarentined(true);
		
		int beforeInfect = cityToInfect.getCubesByDisease(diseaseModel);
		
		this.controller.infect(cityToInfect, diseaseModel);
		
		int afterInfect = cityToInfect.getCubesByDisease(diseaseModel);
		
		boolean comparison = (beforeInfect == afterInfect);
		
		assertTrue(comparison);
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
		
		boolean comparison = (beforeOutbreak == afterOutbreak);
		
		assertTrue(comparison);
	}
	
	@Test
	public void testRemoveInfectedCity(){
		CityModel cityToInfect = this.controller.getCityByName(cityName);
		DiseaseModel diseaseModel = this.diseaseController.getBlueDisease();
		this.controller.infect(cityToInfect, diseaseModel);
		
		Set<CityModel> infectedCities = this.controller.getInfectedCities();
		List<CityModel> listOfInfectedCities = new ArrayList<CityModel>(infectedCities);
		assertEquals(1, infectedCities.size());
		
		assertTrue(infectedCities.contains(cityToInfect));
		this.controller.removeInfectedCity(cityToInfect);
		assertFalse(infectedCities.contains(cityToInfect));
	}
	
	@Test
	public void testGetOutBrokenCities(){
		assertEquals(new HashSet<>(), this.controller.getOutbrokenCities());
	}
	
	@Test
	public void testCityNeighborSizes(){
		Map<String, Integer> cityNamesSizeMap = this.controller.getCityNameToNeighborsSize();
		for(int i = 0; i < this.listOfCities.size(); i++){
			CityModel currentCity = this.listOfCities.get(i);
			int expected = cityNamesSizeMap.get(currentCity.getName());
			int actual = currentCity.getNeighbors().size();
			assertEquals(expected, actual);
		}
	}
	
	@Test
	public void testClearOutbrokenCities(){
		CityModel cityToInfect = this.controller.getCityByName(cityName);
		DiseaseModel diseaseModel = this.diseaseController.getBlueDisease();
		cityToInfect.setCubesByDisease(diseaseModel, 4);

		this.controller.infect(cityToInfect, diseaseModel);
		
		assertEquals(3, cityToInfect.getCubesByDisease(diseaseModel));
		assertEquals(1, this.controller.getOutbrokenCities().size());
		this.controller.clearOutbrokenCities();
		assertEquals(0, this.controller.getOutbrokenCities().size());
	}
	
	@Test
	public void buildResearchStation(){
		CityModel cityToBuildOn = this.listOfCities.get(15);
		this.controller.buildResearchStation(cityToBuildOn);
		assertTrue(cityToBuildOn.hasResearchStation());
	}
}
