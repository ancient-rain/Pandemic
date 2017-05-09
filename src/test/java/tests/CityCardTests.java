package tests;

import static org.junit.Assert.*;

import org.easymock.EasyMock;
import org.junit.Test;

import cards.CityCard;
import code.City;

public class CityCardTests {

	@Test
	public void testGetCity(){
		City testCity = EasyMock.niceMock(City.class);
		CityCard testCard = new CityCard(testCity);
		assertEquals(testCity, testCard.getCity());
	}
}
