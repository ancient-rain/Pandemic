package gameTests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;

import org.easymock.EasyMock;
import org.junit.Test;

import actions.Action;
import cards.Card;
import code.City;
import code.Game;
import code.ListOfCities;
import code.PlayerCharacter;
import deck.PlayerDeck;
import roles.Medic;

public class DrawingTests {
	City startingCity = new City("Terre Haute", new HashSet<City>(), Color.RED);
	String role = "Scientist";
	Medic medic = new Medic(null);
	PlayerCharacter player = new PlayerCharacter(new HashSet<Action>(), startingCity, medic);
	ListOfCities listOfCities = new ListOfCities();
	int tryDrawNegative = -1;
	int drawNoCards = 0;
	int drawOneCard = 1;
	int drawTwoCards = 2;
	int drawThreeCards = 3;
	int drawFourCards = 4;
	int noPlayers = 0;
	int twoPlayers = 2;
	int threePlayers = 3;
	int fourPlayers = 4;
	int fivePlayers = 5;
	
	@Test
	public void testDrawOneCard(){
		Game newGame = new Game();
		
		newGame.resetPlayerDeck();

		newGame.drawAndGiveCardToPlayer(player, drawOneCard);
		List<String> namesOfCardsInHand = new ArrayList<String>();
		
		for(Card card : player.getCards()){
			namesOfCardsInHand.add(card.getName());
		}
		
		assertTrue(namesOfCardsInHand.size() == drawOneCard);
	}
	
	@Test
	public void testDrawFourCard(){
		Game newGame = new Game();
		List<String> namesOfCardsInHand = new ArrayList<String>();
		
		newGame.resetPlayerDeck();

		
		newGame.drawAndGiveCardToPlayer(player, drawFourCards);
		
		for(Card card : player.getCards()){
			namesOfCardsInHand.add(card.getName());
		}

		
		assertTrue(namesOfCardsInHand.size() == drawFourCards);
	}
	
	@Test
	public void testNoCardsDrawn(){
		Game newGame = new Game();
		newGame.resetPlayerDeck();
		newGame.drawAndGiveCardToPlayer(player, drawNoCards);
		
		assertTrue(player.getCards().isEmpty());
	}
	
	@Test
	public void testDrawNegative(){
		Game newGame = new Game();
		newGame.resetPlayerDeck();
		newGame.drawAndGiveCardToPlayer(player, tryDrawNegative);
		
		assertTrue(player.getCards().isEmpty());
	}
	
	@Test
	public void testDealStartingCardsTwoPlayers() {
		Game newGame = new Game();
		assertEquals(drawFourCards, newGame.dealStartingCards(twoPlayers));
	}
	
	@Test
	public void testDealStartingCardsThreePlayers() {
		Game newGame = new Game();
		assertEquals(drawThreeCards, newGame.dealStartingCards(threePlayers));
	}
	
	@Test
	public void testDealStartingCardsFourPlayers() {
		Game newGame = new Game();
		assertEquals(drawTwoCards, newGame.dealStartingCards(fourPlayers));
	}
	
	@Test
	public void testDealStartingCardsFivePlayers() {
		Game newGame = new Game();
		assertEquals(drawNoCards, newGame.dealStartingCards(fivePlayers));
	}
	
	@Test
	public void testDealStartingCardsNoPlayers() {
		Game newGame = new Game();
		assertEquals(drawNoCards, newGame.dealStartingCards(noPlayers));
	}
	
	@Test
	public void testDrawCardsAtEndOfTurn() {
		Game newGame = new Game();
		int startingCubes = newGame.getCubesRemaining();
		newGame.endOfTurn(player);
		int remainingCubes = newGame.getCubesRemaining();
		boolean hadEpidemic = remainingCubes < startingCubes - 4;
		boolean correctAmountOfCards = player.getHandSize() == 2;
		assertTrue(hadEpidemic || correctAmountOfCards);
	}
}
