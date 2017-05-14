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

public class AbstractDeckCardControllerTests {

	DiseaseController diseaseController;
	DiseaseModel blueDisease;
	String cityName = "Chicago";
	GameController gameController;
	OperationsExpertCharacterController operSpecialist;
	Map<CityModel, CardModel> cityToCardMap;
	AbstractDeckCardController playerDeckController;
	AbstractDeckCardController infectionDeckController;
	CityController cityController;
	List<CityModel> listOfCities;
	CharacterModel characterModel;

	@Before
	public void init() {
		GameModel gameModel = new GameModel();
		DiseaseController diseaseController = new DiseaseController();
		this.cityController = new CityController(diseaseController);
		this.playerDeckController = new PlayerDeckCardController(cityController);
	}
	
	@Test
	public void testDrawZeroCards(){
		int numCardsToDraw = 0;
		assertEquals(0, this.playerDeckController.drawNumberOfCards(numCardsToDraw).size());
	}
	
	@Test
	public void testDrawOneCards(){
		int numCardsToDraw = 1;
		assertEquals(1, this.playerDeckController.drawNumberOfCards(numCardsToDraw).size());
	}
	
	@Test
	public void testDrawTenCards(){
		int numCardsToDraw = 10;
		assertEquals(10, this.playerDeckController.drawNumberOfCards(numCardsToDraw).size());
	}
	
	@Test
	public void testDrawNegativeCards(){
		int numCardsToDraw = -10;
		assertEquals(0, this.playerDeckController.drawNumberOfCards(numCardsToDraw).size());
		assertEquals(0, this.playerDeckController.getNumberOfCardsInDiscard());
	}

}
