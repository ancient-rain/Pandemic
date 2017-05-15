package cardTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import cards.AbstractDeckCardController;
import cards.CardModel;
import cards.InfectionDeckCardController;
import cards.PlayerDeckCardController;
import characters.CharacterModel;
import characters.OperationsExpertCharacterController;
import city.CityController;
import city.CityModel;
import diseases.DiseaseController;
import diseases.DiseaseModel;
import game.GameController;
import game.GameModel;

public class InfectionDeckCardControllerTests {
	DiseaseController diseaseController;
	DiseaseModel blueDisease;
	String cityName = "Chicago";
	GameController gameController;
	OperationsExpertCharacterController operSpecialist;
	Map<CityModel, CardModel> cityToCardMap;
	PlayerDeckCardController playerDeckController;
	InfectionDeckCardController infectionDeckController;
	CityController cityController;
	List<CityModel> listOfCities;
	CharacterModel characterModel;

	@Before
	public void init() {
		GameModel gameModel = new GameModel();
		DiseaseController diseaseController = new DiseaseController();
		this.cityController = new CityController(diseaseController);
		this.playerDeckController = new PlayerDeckCardController(cityController);
		this.infectionDeckController = new InfectionDeckCardController(cityController);

		Set<CityModel> setOfCities = cityController.getCities();
		this.listOfCities = new ArrayList<CityModel>(setOfCities);

		this.cityToCardMap = playerDeckController.getCityToCardMap();
		
	}
	
	@Test
	public void testSpecialShuffle(){
		int numberToUseInShuffle = 3;
		this.infectionDeckController.specialShuffle(numberToUseInShuffle);
		assertEquals(0, this.infectionDeckController.getNumberOfCardsInDiscard());
		CardModel cardToDiscard = this.infectionDeckController.draw();
		this.infectionDeckController.discard(cardToDiscard);
		this.infectionDeckController.specialShuffle(0);
		assertEquals(0, this.infectionDeckController.getDiscardedCards().size());
	}
	
	@Test
	public void testAddToTop(){
		CardModel cardToPutOnTop = this.infectionDeckController.draw();
		assertEquals(47, infectionDeckController.getNumberOfCardsInDeck());
		infectionDeckController.addToTop(cardToPutOnTop);
		assertEquals(48, infectionDeckController.getNumberOfCardsInDeck());		
	}
	
	@Test
	public void testDrawBottomCard(){
		CardModel cardToPutOnTop = this.infectionDeckController.drawBottomCard();
		assertEquals(47, infectionDeckController.getNumberOfCardsInDeck());
		infectionDeckController.addToTop(cardToPutOnTop);
		assertEquals(48, infectionDeckController.getNumberOfCardsInDeck());	
	}
	
	/*@Test
	public void testDrawBottomCardThrowException() {
		for(int i = 0; i < 48; i++){
			this.infectionDeckController.drawBottomCard();
		}
		assertEquals(null, infectionDeckController.drawBottomCard());
	}*/

}
