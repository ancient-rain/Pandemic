package tests.DeckTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import cards.Card;
import cards.CityCard;
import cards.EpidemicCard;
import cards.EventCard;
import code.ListOfCities;
import deck.AbstractDeck;
import deck.PlayerDeck;

public class PlayerDeckTests {
	
	private int introDifficulty = 4;
	private int standDifficulty = 5;
	private int heroicDifficulty = 6;
	private int players2or4 = 8;
	private int players3 = 9;
	
	public void testSetupShuffleXDifficultyXPlayers(int difficulty, int numberOfCardsToDrawBefore){
		ListOfCities listOfCities = new ListOfCities();
		listOfCities.initListOfCities();
		AbstractDeck pDeck = new PlayerDeck(listOfCities);
		//Draw a number to simulate players taking cards before this shuffle
		//So the distribution is correct
		pDeck.drawNumberOfCards(numberOfCardsToDrawBefore);
		int originalSize = pDeck.getNumberOfCardsInDeck();
		int partitionSize = pDeck.getNumberOfCardsInDeck()/difficulty;
		pDeck.specialShuffle(difficulty);
		boolean twoInARow = false;
		int cardsSinceLastEpidemic = 0;
		int epidemics = 0;
		List<Card> cards = pDeck.drawNumberOfCards(pDeck.getNumberOfCardsInDeck());
		for(int i = 0; i < cards.size(); i++){
			if(cards.get(i).getClass() == EpidemicCard.class){
				if(cardsSinceLastEpidemic == 0 && i != 0){
					assertFalse(twoInARow);
					twoInARow = true;
				} else {
					twoInARow = false;
				}
				cardsSinceLastEpidemic = 0;
				epidemics++;
			} else {
				assertFalse(++cardsSinceLastEpidemic > (partitionSize * 2)+1);
			}
		}
		assertEquals(difficulty, epidemics);
		assertEquals(originalSize+difficulty, cards.size());
	}
	
	@Test
	public void testSetupShuffleIntroductoryDifficulty2Or4Players(){
		testSetupShuffleXDifficultyXPlayers(introDifficulty, players2or4);
	}
	
	@Test
	public void testSetupShuffleStandardDifficulty2Or4Players(){
		testSetupShuffleXDifficultyXPlayers(standDifficulty, players2or4);
	}
	
	@Test
	public void testSetupShuffleHeroicDifficulty2Or4Players(){
		testSetupShuffleXDifficultyXPlayers(heroicDifficulty, players2or4);
	}
	
	@Test
	public void testSetupShuffleIntroductoryDifficulty3Players(){
		testSetupShuffleXDifficultyXPlayers(introDifficulty, players3);
	}
	
	@Test
	public void testSetupShuffleStandardDifficulty3Players(){
		testSetupShuffleXDifficultyXPlayers(standDifficulty, players3);
	}
	
	@Test
	public void testSetupShuffleHeroicDifficulty3Players(){
		testSetupShuffleXDifficultyXPlayers(heroicDifficulty, players3);
	}
}
