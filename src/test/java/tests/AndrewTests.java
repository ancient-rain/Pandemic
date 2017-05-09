package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import code.City;
import code.ListOfCities;

public class AndrewTests {
	
	@Test
	public void testGetListOfCities() {
		ListOfCities listOfCities = new ListOfCities();
		ArrayList<City> arrayOfCities = listOfCities.getListOfCities();
		assertEquals(48, arrayOfCities.size());
	}

}
