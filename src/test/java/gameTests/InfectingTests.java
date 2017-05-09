package gameTests;

import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;

import actions.Action;
import cards.Card;
import cards.CityCard;
import code.City;
import code.Game;
import code.PlayerCharacter;
import deck.InfectionDeck;
import deck.PlayerDeck;

public class InfectingTests {
	int infectNegative = -1;
	int infectNoCities = 0;
	int infectOneCity = 1;
	int infectTwoCities = 2;
	int infectThreeCities = 3;
	int infectFourCities = 4;
	int infectFiveCities = 5;
	int infectionsToOutbreak = 4;
	int minNumberOfNeighbors = 1;
	
	@Test
	public void testInfectOneCity(){
		Game newGame = new Game();
		int cubesRemaining = newGame.getCubesRemaining();
		newGame.resetInfectionDeck();

		newGame.drawAndInfect(infectOneCity);
		int newCubesRemaining = newGame.getCubesRemaining();
		
		assertTrue(newCubesRemaining == cubesRemaining - infectOneCity);
	}
	
	@Test
	public void testInfectTwoCities(){
		Game newGame = new Game();
		int cubesRemaining = newGame.getCubesRemaining();
		newGame.resetInfectionDeck();

		newGame.drawAndInfect(infectTwoCities);
		int newCubesRemaining = newGame.getCubesRemaining();
		
		assertTrue(newCubesRemaining == cubesRemaining - infectTwoCities);
	}
	
	@Test
	public void testInfectThreeCities(){
		Game newGame = new Game();
		int cubesRemaining = newGame.getCubesRemaining();
		newGame.resetInfectionDeck();

		newGame.drawAndInfect(infectThreeCities);
		int newCubesRemaining = newGame.getCubesRemaining();
		
		assertTrue(newCubesRemaining == cubesRemaining - infectThreeCities);
	}
	
	@Test
	public void testInfectFourCities(){
		Game newGame = new Game();
		int cubesRemaining = newGame.getCubesRemaining();
		newGame.resetInfectionDeck();

		newGame.drawAndInfect(infectFourCities);
		int newCubesRemaining = newGame.getCubesRemaining();
		
		assertTrue(newCubesRemaining == cubesRemaining - infectFourCities);
	}
	
	@Test
	public void testInfectFiveCities(){
		Game newGame = new Game();
		int cubesRemaining = newGame.getCubesRemaining();
		newGame.resetInfectionDeck();

		newGame.drawAndInfect(infectFiveCities);
		int newCubesRemaining = newGame.getCubesRemaining();
		
		assertTrue(newCubesRemaining == cubesRemaining - infectFiveCities);
	}
	
	@Test
	public void testTryInfectNegative(){
		Game newGame = new Game();
		int cubesRemaining = newGame.getCubesRemaining();
		newGame.resetInfectionDeck();

		newGame.drawAndInfect(infectNegative);
		int newCubesRemaining = newGame.getCubesRemaining();
		
		assertTrue(newCubesRemaining == cubesRemaining);
	}
	
	@Test
	public void testInfectNoCities(){
		Game newGame = new Game();
		int cubesRemaining = newGame.getCubesRemaining();
		

		newGame.drawAndInfect(infectNoCities);
		int newCubesRemaining = newGame.getCubesRemaining();
		
		assertTrue(newCubesRemaining == cubesRemaining - infectNoCities);
	}
	
	@Test
	public void testInfectOneCityAndOutbreak(){
		Game newGame = new Game();
		int cubesRemaining = newGame.getCubesRemaining();
		
		newGame.resetInfectionDeck();
		
		for(int i = 0; i < infectionsToOutbreak; i++){
			newGame.drawAndInfect(infectOneCity);
			newGame.infectionDiscardBackOnTop();
		}
		int newCubesRemaining = newGame.getCubesRemaining();

		assertTrue(newCubesRemaining < cubesRemaining - (infectThreeCities + minNumberOfNeighbors));
	}
}
