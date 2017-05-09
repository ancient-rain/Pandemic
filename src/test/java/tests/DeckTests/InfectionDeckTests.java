package tests.DeckTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import cards.Card;
import code.ListOfCities;
import deck.AbstractDeck;
import deck.InfectionDeck;

public class InfectionDeckTests {
	@Test
	public void testSpecialShuffle() {
		ListOfCities listOfCities = new ListOfCities();
		listOfCities.initListOfCities();
		AbstractDeck testDeck = new InfectionDeck(listOfCities);
		List<Card> discardedCards = new ArrayList<>();
		discardedCards.add(testDeck.draw());
		testDeck.discard(discardedCards.get(0));
		discardedCards.add(testDeck.draw());
		testDeck.discard(discardedCards.get(1));
		discardedCards.add(testDeck.draw());
		testDeck.discard(discardedCards.get(2));
		testDeck.specialShuffle(0);
		for(int i = 0; i < discardedCards.size(); i++){
			assertTrue(discardedCards.contains(testDeck.draw()));
		}
	}
	
	@Test
	public void testDrawBottomCard() {
		ListOfCities listOfCities = new ListOfCities();
		listOfCities.initListOfCities();
		AbstractDeck testDeck = new InfectionDeck(listOfCities);
		List<Card> allButBottomCard = testDeck.drawNumberOfCards(testDeck.getNumberOfCardsInDeck() - 1);
		for(Card c : allButBottomCard){
			testDeck.discard(c);
		}
		testDeck.specialShuffle(0);
		Card bottom = ((InfectionDeck) testDeck).drawBottomCard();
		assertFalse(allButBottomCard.contains(bottom));
	}
}
