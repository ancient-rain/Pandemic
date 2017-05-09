package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import actions.Action;
import cards.Card;
import cards.CityCard;
import code.City;
import code.PlayerCharacter;
import roles.Medic;

public class PlayerCharacterTests {
	
	PlayerCharacter testPlayerCharacter;
	HashSet<Card> testPlayerHandOfCards;
	HashSet<Action> testCapableActions;
	City testCity;
	String testRole;
	
	
	@Before
	public void init(){
		testPlayerHandOfCards = new HashSet<>();
		testCapableActions = new HashSet<>();
		testCity = EasyMock.createNiceMock(City.class);
		testRole = "Test Character";
		Medic medic = new Medic(null);
		testPlayerCharacter = new PlayerCharacter(testCapableActions, testCity, medic);
	}
	
	@Test
	public void testGetRole(){
		Medic medic = new Medic(null);
		testPlayerCharacter = new PlayerCharacter(testCapableActions, testCity, medic);
		assertEquals(medic, testPlayerCharacter.getRole());
	}
	
	@Test
	public void testGetHand(){
		assertEquals(testPlayerHandOfCards, testPlayerCharacter.getCards());
	}
	
	@Test (expected=UnsupportedOperationException.class)
	public void testModifyGetHand(){
		testPlayerCharacter.getCards().add(EasyMock.createMock(Card.class));
		assertEquals(testPlayerHandOfCards, testPlayerCharacter.getCards());
	}
	
	@Test
	public void testAddCard(){
		Card c = EasyMock.createMock(Card.class);
		testPlayerHandOfCards.add(c);
		testPlayerCharacter.addCard(c);
		assertEquals(testPlayerHandOfCards, testPlayerCharacter.getCards());
	}
	
	@Test
	public void testAddMoreThanHandLimit(){
		for(int i = 0; i < testPlayerCharacter.getCardLimit(); i++){
			testPlayerCharacter.addCard(EasyMock.createMock(Card.class));
		}
		assertFalse(testPlayerCharacter.addCard(EasyMock.createMock(Card.class)));
	}
	
	@Test
	public void testGetHandSize(){
		testPlayerCharacter.addCard(EasyMock.createMock(Card.class));
		assertEquals(1, testPlayerCharacter.getHandSize());
	}
	

	@Test
	public void testEmptyHandSize(){
		assertEquals(0, testPlayerCharacter.getHandSize());
	}

	@Test
	public void testHandLimitSize(){
		for(int i = 0; i < testPlayerCharacter.getCardLimit(); i++){
			testPlayerCharacter.addCard(EasyMock.createMock(Card.class));
		}
		assertEquals(testPlayerCharacter.getCardLimit(), testPlayerCharacter.getHandSize());
	}
	
	@Test
	public void testRemoveCard(){
		Card c = EasyMock.createMock(Card.class);
		testPlayerCharacter.addCard(c);
		testPlayerCharacter.addCard(EasyMock.createMock(Card.class));
		testPlayerCharacter.removeCard(c);
		assertEquals(1, testPlayerCharacter.getHandSize());
	}
	
	@Test
	public void testRemoveCardNotInHand(){
		testPlayerCharacter.addCard(EasyMock.createMock(Card.class));
		assertFalse(testPlayerCharacter.removeCard(EasyMock.createMock(Card.class)));
	}
	
	@Test
	public void testRemoveCardFromEmptyHand(){
		assertFalse(testPlayerCharacter.removeCard(EasyMock.createMock(Card.class)));
	}
	
	@Test
	public void testCardIsInHand(){
		Card c = EasyMock.createMock(Card.class);
		testPlayerCharacter.addCard(c);
		assertTrue(testPlayerCharacter.inHand(c));
	}
	
	@Test
	public void testCardIsNotInHand(){
		testPlayerCharacter.addCard(EasyMock.createMock(Card.class));
		assertFalse(testPlayerCharacter.inHand(EasyMock.createMock(Card.class)));
	}
	
	@Test
	public void testGetActions(){
		assertEquals(testCapableActions, testPlayerCharacter.getActions());
	}
	
	@Test (expected=UnsupportedOperationException.class)
	public void testModifyGetActions(){
		testPlayerCharacter.getActions().add(EasyMock.createMock(Action.class));
		assertEquals(testPlayerHandOfCards, testPlayerCharacter.getActions());
	}
	
	@Test
	public void testAddAction(){
		Action a = EasyMock.createNiceMock(Action.class);
		testCapableActions.add(a);
		testPlayerCharacter.addAction(a);
		assertEquals(testCapableActions, testPlayerCharacter.getActions());
	}
	
	@Test
	public void testRemoveAction(){
		Action a = EasyMock.createNiceMock(Action.class);
		testPlayerCharacter.addAction(a);
		testPlayerCharacter.addAction(EasyMock.createNiceMock(Action.class));
		testPlayerCharacter.removeAction(a);
		assertEquals(1, testPlayerCharacter.getActions().size());
	}
	
	@Test
	public void testRemoveActionNotInActions(){
		testPlayerCharacter.addAction(EasyMock.createNiceMock(Action.class));
		assertFalse(testPlayerCharacter.removeAction(EasyMock.createNiceMock(Action.class)));
	}
	
	@Test
	public void testRemoveActionFromEmptyActions(){
		assertFalse(testPlayerCharacter.removeAction(EasyMock.createNiceMock(Action.class)));
	}

	@Test
	public void testGetLocation(){
		assertEquals(testCity, testPlayerCharacter.getLocation());
	}
	
	@Test
	public void testSetLocation(){
		City city = EasyMock.createNiceMock(City.class);
		testCity = city;
		testPlayerCharacter.setLocation(city);
		assertEquals(testCity, testPlayerCharacter.getLocation());
	}
	
	@Test
	public void testIsAtResearchCenter(){
		City city = EasyMock.createNiceMock(City.class);
		EasyMock.expect(city.getHasResearchCenter()).andReturn(true);
		
		EasyMock.replay(city);
		testPlayerCharacter.setLocation(city);
		boolean test = testPlayerCharacter.isAtResearchCenter();
		EasyMock.verify(city);
		assertTrue(test);
	}
	
	@Test
	public void testIsNotAtResearchCenter(){
		City city = EasyMock.createNiceMock(City.class);
		EasyMock.expect(city.getHasResearchCenter()).andReturn(false);
		
		EasyMock.replay(city);
		testPlayerCharacter.setLocation(city);
		boolean test = testPlayerCharacter.isAtResearchCenter();
		EasyMock.verify(city);
		assertFalse(test);
	}
	
	@Test
	public void testHasCardForCurrentLocation(){
		City city = EasyMock.createNiceMock(City.class);
		CityCard card = EasyMock.createNiceMock(CityCard.class);
		EasyMock.expect(card.getCity()).andReturn(city);
		
		EasyMock.replay(card);
		testPlayerCharacter.setLocation(city);
		testPlayerCharacter.addCard(card);
		boolean hasCard = testPlayerCharacter.hasCardForCurrentLocation();
		EasyMock.verify(card);
		assertTrue(hasCard);
	}
	
	@Test
	public void testDoesntHaveCardForCurrentLocation(){
		City city = EasyMock.createNiceMock(City.class);
		CityCard card = EasyMock.createNiceMock(CityCard.class);
		EasyMock.expect(card.getCity()).andReturn(city);
		
		EasyMock.replay(card);
		testPlayerCharacter.setLocation(EasyMock.createNiceMock(City.class));
		testPlayerCharacter.addCard(card);
		boolean hasCard = testPlayerCharacter.hasCardForCurrentLocation();
		EasyMock.verify(card);
		assertFalse(hasCard);
	}
	
	@Test
	public void testGetConnectedLocations(){
		City neighbor1 = EasyMock.createNiceMock(City.class);
		City neighbor2 = EasyMock.createNiceMock(City.class);
		City neighbor3 = EasyMock.createNiceMock(City.class);
		City flight1 = EasyMock.createNiceMock(City.class);
		City flight2 = EasyMock.createNiceMock(City.class);
		City flight3 = EasyMock.createNiceMock(City.class);
		
		HashSet<City> neighbors = new HashSet<>();
		
		neighbors.add(neighbor1);
		neighbors.add(neighbor2);
		neighbors.add(neighbor3);
		
		City start = EasyMock.partialMockBuilder(City.class)
				.addMockedMethod("getNeighbors")
				.withConstructor(String.class, Set.class, Color.class)
				.withArgs("start", neighbors, Color.BLACK)
				.createNiceMock();
		EasyMock.expect(start.getNeighbors()).andReturn(neighbors);
		
		CityCard card1 = EasyMock.partialMockBuilder(CityCard.class)
				.addMockedMethod("getCity")
				.withConstructor(City.class)
				.withArgs(flight1)
				.createNiceMock();
		CityCard card2 = EasyMock.partialMockBuilder(CityCard.class)
				.addMockedMethod("getCity")
				.withConstructor(City.class)
				.withArgs(flight2)
				.createNiceMock();
		CityCard card3 = EasyMock.partialMockBuilder(CityCard.class)
				.addMockedMethod("getCity")
				.withConstructor(City.class)
				.withArgs(flight3)
				.createNiceMock();
		
		EasyMock.expect(card1.getCity()).andReturn(flight1);
		EasyMock.expect(card2.getCity()).andReturn(flight2);
		EasyMock.expect(card3.getCity()).andReturn(flight3);
		
		EasyMock.replay(start, card1, card2, card3);
		Set<City> connections = new HashSet<>();
		connections.addAll(neighbors);
		connections.add(flight1);
		connections.add(flight2);
		connections.add(flight3);
		
		testPlayerCharacter.setLocation(start);
		testPlayerCharacter.addCard(card1);
		testPlayerCharacter.addCard(card2);
		testPlayerCharacter.addCard(card3);
		Set<City> actual = testPlayerCharacter.getConnectedCities();
		EasyMock.verify(start, card1, card2, card3);
		assertEquals(connections, actual);
	}
}
