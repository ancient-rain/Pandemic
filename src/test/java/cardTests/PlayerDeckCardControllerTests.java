package cardTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.List;
import java.util.Random;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import cards.AbstractDeckCardController;
import cards.CardModel;
import cards.PlayerDeckCardController;
import city.CityController;
import diseases.DiseaseController;
import diseases.DiseaseModel;

public class PlayerDeckCardControllerTests {

	private int introDifficulty = 4;
	private int standDifficulty = 5;
	private int heroicDifficulty = 6;
	private int players2or4 = 8;
	private int players3 = 9;
	
	private PlayerDeckCardController playerDeck1;
	private PlayerDeckCardController playerDeck2;
	
	@Before
	public void init(){
		DiseaseModel diseaseModelMock = EasyMock.createNiceMock(DiseaseModel.class);
		DiseaseController diseaseMock = EasyMock.createNiceMock(DiseaseController.class);
		EasyMock.expect(diseaseMock.getBlueDisease()).andStubReturn(diseaseModelMock);
		EasyMock.expect(diseaseMock.getBlackDisease()).andStubReturn(diseaseModelMock);
		EasyMock.expect(diseaseMock.getRedDisease()).andStubReturn(diseaseModelMock);
		EasyMock.expect(diseaseMock.getYellowDisease()).andStubReturn(diseaseModelMock);
		EasyMock.replay(diseaseMock, diseaseModelMock);
		CityController cityController = new CityController(diseaseMock);
		playerDeck1 = new PlayerDeckCardController(cityController);
		playerDeck2 = new PlayerDeckCardController(cityController);
	}
	
	@Test
	public void testShufflesPartitions(){
		playerDeck1.specialShuffle(introDifficulty, new Random(0l));
		playerDeck2.specialShuffle(introDifficulty, new Random(1l));
		assertFalse(playerDeck1.draw().getName().equals(playerDeck2.draw().getName()));
	}
	
	@Test
	public void testDeckSameSize(){
		int initCards = playerDeck1.getNumberOfCardsInDeck();
		playerDeck1.specialShuffle(introDifficulty, new Random(0l));
		assertEquals(initCards+introDifficulty, playerDeck1.getNumberOfCardsInDeck());
	}
	
	@Test
	public void testSpecialShuffleNoSeed(){
		playerDeck1.specialShuffle(introDifficulty);
		playerDeck2.specialShuffle(introDifficulty);
		assertFalse(playerDeck1.draw().getName().equals(playerDeck2.draw().getName()));
	}
	
	public void testSetupShuffleXDifficultyXPlayers(int difficulty, int numberOfCardsToDrawBefore){
		Random seed = new Random(0l);
		playerDeck1.drawNumberOfCards(numberOfCardsToDrawBefore);
		int originalSize = playerDeck1.getNumberOfCardsInDeck();
		int partitionSize = playerDeck1.getNumberOfCardsInDeck()/difficulty;
		playerDeck1.specialShuffle(difficulty, seed);
		boolean twoInARow = false;
		int cardsSinceLastEpidemic = 0;
		int epidemics = 0;
		List<CardModel> cards = playerDeck1.drawNumberOfCards(playerDeck1.getNumberOfCardsInDeck());
		for(int i = 0; i < cards.size(); i++){
			if(cards.get(i).getType().equals(CardModel.CardType.EPIDEMIC)){
				if(cardsSinceLastEpidemic == 0 && i != 0){
					assertFalse(twoInARow);
					twoInARow = true;
				} else {
					twoInARow = false;
				}
				cardsSinceLastEpidemic = 0;
				epidemics++;
			} else {
				assertFalse(cardsSinceLastEpidemic++ > (partitionSize * 2)+1);
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
