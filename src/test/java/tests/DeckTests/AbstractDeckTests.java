package tests.DeckTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import cards.Card;
import cards.CityCard;
import code.City;
import code.ListOfCities;
import deck.AbstractDeck;

public class AbstractDeckTests {

	ListOfCities mockListOfCities;
	ArrayList<City> stubCities;
	AbstractDeck testDeck;
	
	@Before
	public void init(){
		this.stubCities = new ArrayList<>();
		this.stubCities.add(EasyMock.createMock(City.class));
		this.stubCities.add(EasyMock.createMock(City.class));
		this.stubCities.add(EasyMock.createMock(City.class));
		this.mockListOfCities = EasyMock.createMock(ListOfCities.class);
		EasyMock.expect(mockListOfCities.getListOfCities()).andReturn(stubCities);
		EasyMock.replay(mockListOfCities);
		this.testDeck = new AbstractDeck(mockListOfCities){

			@Override
			public void specialShuffle(int numberToUseInShuffle) {
			}};
	}
	
	@Test
	public void testDeckConstructor() {
		assertEquals(stubCities.size(), testDeck.getNumberOfCardsInDeck());
	}
	
	@Test
	public void testGetNumCardsLeftInEmptyDeck(){
		this.mockListOfCities = EasyMock.createMock(ListOfCities.class);
		EasyMock.expect(mockListOfCities.getListOfCities()).andReturn(new ArrayList<>());
		EasyMock.replay(mockListOfCities);
		AbstractDeck testDeck = new AbstractDeck(mockListOfCities){

			@Override
			public void specialShuffle(int numberToUseInShuffle) {
			}};
		assertEquals(0, testDeck.getNumberOfCardsInDeck());
	}
	
	@Test
	public void testGetNumCardsLeftInNonEmptyDeck(){
		assertEquals(stubCities.size(), testDeck.getNumberOfCardsInDeck());
	}
	
	@Test
	public void testDrawFromNonEmptyDeck(){
		Card drawnCard = testDeck.draw();
		assertEquals(stubCities.size() - 1, testDeck.getNumberOfCardsInDeck());
	}
	
	@Test
	public void testDrawZeroCards(){
		testDeck.drawNumberOfCards(0);
		assertEquals(stubCities.size(), testDeck.getNumberOfCardsInDeck());
	}
	
	@Test
	public void testDrawOneCard(){
		testDeck.drawNumberOfCards(1);
		assertEquals(stubCities.size() - 1, testDeck.getNumberOfCardsInDeck());
	}
	
	@Test
	public void testDrawTwoCards(){
		testDeck.drawNumberOfCards(2);
		assertEquals(stubCities.size() - 2, testDeck.getNumberOfCardsInDeck());
	}
	
	@Test
	public void testDiscard(){
		Card drawnCard = testDeck.draw();
		testDeck.discard(drawnCard);
		assertEquals(1, testDeck.getNumberOfCardsInDiscard());
	}
	
	@Test
	public void testGetEmptyDiscard(){
		assertTrue(testDeck.getDiscardedCards().isEmpty());
	}
	
	@Test
	public void testGetNonEmptyDiscard(){
		Card drawnCard = testDeck.draw();
		testDeck.discard(drawnCard);
		assertFalse(testDeck.getDiscardedCards().isEmpty());
	}

}
