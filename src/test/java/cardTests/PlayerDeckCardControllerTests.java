package cardTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.List;

import org.easymock.EasyMock;
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
	
	public void testSetupShuffleXDifficultyXPlayers(int difficulty, int numberOfCardsToDrawBefore){
		DiseaseModel diseaseModelMock = EasyMock.createNiceMock(DiseaseModel.class);
		DiseaseController diseaseMock = EasyMock.createNiceMock(DiseaseController.class);
		EasyMock.expect(diseaseMock.getBlueDisease()).andStubReturn(diseaseModelMock);
		EasyMock.expect(diseaseMock.getBlackDisease()).andStubReturn(diseaseModelMock);
		EasyMock.expect(diseaseMock.getRedDisease()).andStubReturn(diseaseModelMock);
		EasyMock.expect(diseaseMock.getYellowDisease()).andStubReturn(diseaseModelMock);
		EasyMock.replay(diseaseMock, diseaseModelMock);
		CityController cityController = new CityController(diseaseMock);
		AbstractDeckCardController playerDeck = new PlayerDeckCardController(cityController);
		
		playerDeck.drawNumberOfCards(numberOfCardsToDrawBefore);
		int originalSize = playerDeck.getNumberOfCardsInDeck();
		int partitionSize = playerDeck.getNumberOfCardsInDeck()/difficulty;
		playerDeck.specialShuffle(difficulty);
		boolean twoInARow = false;
		int cardsSinceLastEpidemic = 0;
		int epidemics = 0;
		List<CardModel> cards = playerDeck.drawNumberOfCards(playerDeck.getNumberOfCardsInDeck());
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
