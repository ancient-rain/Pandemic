package cardTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
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
	AbstractDeckCardController abstractDeckCardController;
	CityController cityController;
	List<CityModel> listOfCities;
	CharacterModel characterModel;

	@Before
	public void init() {
		GameModel gameModel = new GameModel();
		DiseaseController diseaseController = new DiseaseController();
		this.cityController = new CityController(diseaseController);
		this.abstractDeckCardController = new AbstractDeckCardController(cityController){
			@Override
			public void specialShuffle(int numberToUseInShuffle) {
			}
		};
	}
	
	@Test 
	public void testKillShuffleMutant(){
		Random seed1 = new Random(0l);
		Random seed2 = new Random(System.nanoTime());
		AbstractDeckCardController abstractDeckCardController1 = new AbstractDeckCardController(cityController, seed1){
			@Override
			public void specialShuffle(int numberToUseInShuffle) {
			}
		};
		AbstractDeckCardController abstractDeckCardController2 = new AbstractDeckCardController(cityController, seed2){
			@Override
			public void specialShuffle(int numberToUseInShuffle) {
			}
		};
		assertFalse(abstractDeckCardController1.draw().getName().equals(abstractDeckCardController2.draw().getName()));
	}
	
	@Test
	public void testDrawZeroCards(){
		int numCardsToDraw = 0;
		assertEquals(0, this.abstractDeckCardController.drawNumberOfCards(numCardsToDraw).size());
	}
	
	@Test
	public void testDrawOneCards(){
		int numCardsToDraw = 1;
		assertEquals(1, this.abstractDeckCardController.drawNumberOfCards(numCardsToDraw).size());
	}
	
	@Test
	public void testDrawTenCards(){
		int numCardsToDraw = 10;
		assertEquals(10, this.abstractDeckCardController.drawNumberOfCards(numCardsToDraw).size());
	}
	
	@Test
	public void testDrawNegativeCards(){
		int numCardsToDraw = -10;
		assertEquals(0, this.abstractDeckCardController.drawNumberOfCards(numCardsToDraw).size());
		assertEquals(0, this.abstractDeckCardController.getNumberOfCardsInDiscard());
	}

}
