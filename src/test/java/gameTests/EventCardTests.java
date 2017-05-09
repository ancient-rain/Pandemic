package gameTests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Test;

import actions.Action;
import cards.Card;
import cards.CityCard;
import code.City;
import code.Game;
import code.PlayerCharacter;
import roles.Medic;

public class EventCardTests {
	int infectOneCity = 1;

	@Test
	public void testResilientPopulationSuccess(){
		Game newGame = new Game();
		newGame.resetInfectionDeck();
		
		newGame.drawAndInfect(infectOneCity);
		List<Card> currentDiscard = newGame.showInfectionDiscard();
		CityCard toRemove = (CityCard) currentDiscard.get(0);
		
		newGame.playResilientPopulation(toRemove);
		currentDiscard = newGame.showInfectionDiscard();
		
		assertFalse(currentDiscard.contains(toRemove));
	}
	
	@Test
	public void testResilientPopulationFailure(){
		Game newGame = new Game();
		newGame.resetInfectionDeck();
		
		newGame.drawAndInfect(infectOneCity);
		List<Card> currentDiscard = newGame.showInfectionDiscard();
		CityCard cardToStay = (CityCard) currentDiscard.get(0);
		CityCard toRemove = new CityCard(new City("Terre Haute", Color.BLACK));
		
		newGame.playResilientPopulation(toRemove);
		currentDiscard = newGame.showInfectionDiscard();
		
		assertTrue(currentDiscard.contains(cardToStay));
	}
	
	@Test
	public void testAirliftSuccess(){
		Game newGame = new Game();
		Medic medic = new Medic(null);
		PlayerCharacter newPlayer = new PlayerCharacter(new HashSet<Action>(), 
				new City("Terre Haute", 
						Color.BLACK), 
				medic);
		City newCity = new City("Brazil", Color.RED);
		
		newGame.playAirlift(newPlayer, newCity);
		
		assertEquals(newCity, newPlayer.getLocation());
	}
	
	@Test
	public void testGovernmentGrantSuccess(){
		Game newGame = new Game();
		City newCity = new City("Brazil", Color.RED);
		
		assertTrue(newGame.playGovernmentGrant(newCity));
		assertTrue(newCity.getHasResearchCenter());
	}
	
	@Test
	public void testGovernmentGrantFailure(){
		Game newGame = new Game();
		City newCity = new City("Brazil", Color.RED);
		newCity.setHasResearchCenter(true);
		
		assertFalse(newGame.playGovernmentGrant(newCity));
	}
	
	@Test
	public void testForecast(){
		Game newGame = new Game();
		int forecastAmmount = 6;
		newGame.resetInfectionDeck();
		
		List<Card> list = newGame.playForecast();
		List<Card> checkList = new ArrayList<Card>();
		for(int i = 0; i < forecastAmmount; i++){
			checkList.add(list.get(0));
			assertTrue(newGame.forecastReturnCard(list.remove(0)));
		}
	}
	
}
