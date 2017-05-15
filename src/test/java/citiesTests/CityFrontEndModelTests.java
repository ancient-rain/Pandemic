package citiesTests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import city.CityController;
import city.CityFrontEndModel;
import city.CityModel;
import diseases.DiseaseController;

public class CityFrontEndModelTests {
	List<CityModel> listOfCities;
	CityController cityController;
	CityFrontEndModel cityFrontEndModel;

	@Before
	public void init() {
		DiseaseController diseaseController = new DiseaseController();
		this.cityController = new CityController(diseaseController);
		
		Set<CityModel> setOfCities = cityController.getCities();
		this.listOfCities = new ArrayList<CityModel>(setOfCities);
		
		cityFrontEndModel = new CityFrontEndModel(listOfCities.get(2));
	}
	
	@Test
	public void testSetAndGetColor(){
		cityFrontEndModel.setColor(Color.BLUE);
		assertEquals(Color.BLUE, cityFrontEndModel.getColor());
	}
	
	@Test
	public void testCityFrontEndModelPoint(){
		CityFrontEndModel testCity = new CityFrontEndModel(0,0);
		assertEquals(null, testCity.getColor());
		assertEquals(null, testCity.getCityModel());
	}
	
	@Test
	public void testSetLocationZeros(){
		cityFrontEndModel.setLocation(0, 0);
		assertEquals(0, cityFrontEndModel.getX());
		assertEquals(0, cityFrontEndModel.getY());
	}
	
	/*@Test
	public void testGetCityModel(){
		assertEquals(this.listOfCities.get(2), cityFrontEndModel.getCityModel());
	}*/

}
