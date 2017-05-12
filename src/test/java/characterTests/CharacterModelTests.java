package characterTests;

import static org.junit.Assert.*;

import java.awt.Color;
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
import characters.ScientistCharacterController;
import city.CityController;
import city.CityModel;
import diseases.DiseaseController;
import diseases.DiseaseModel;
import game.GameController;
import game.GameModel;

public class CharacterModelTests {

	DiseaseController diseaseController;
	DiseaseModel blueDisease;
	String cityName = "Chicago";
	GameController gameController;
	ScientistCharacterController scientist;
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
		this.infectionDeckController = new InfectionDeckCardController(cityController);

		Set<CityModel> setOfCities = cityController.getCities();
		this.listOfCities = new ArrayList<CityModel>(setOfCities);
		
		String characterName = "CharacterName";
		DiseaseModel diseaseModel = new DiseaseModel();
		CityModel cityModel = new CityModel(cityName, diseaseModel);
		this.characterModel = new CharacterModel(characterName, "img", Color.BLUE, cityModel);
		this.scientist = new ScientistCharacterController(characterModel);
		this.blueDisease = diseaseController.getBlueDisease();

		this.cityToCardMap = playerDeckController.getCityToCardMap();
	}
	
	@Test
	public void testSetName(){
		this.characterModel.setName("newName");
		assertEquals("newName", this.characterModel.getName());
	}
	
	@Test
	public void testGetImgPath(){
		assertEquals("img", this.characterModel.getImgPath());
	}
	
	@Test
	public void testGetColor(){
		assertEquals(Color.BLUE, this.characterModel.getColor());
	}
	
	@Test
	public void testGetHandSize(){
		assertEquals(0, this.characterModel.getHandSize());
	}
	
	@Test
	public void testGetNameOfCurrentCity(){
		assertEquals(this.cityName, this.characterModel.getNameOfCurrentCity());
	}
	
	@Test
	public void testIsCurrentCityQuarentined(){
		assertFalse(this.characterModel.isCurrentCityQuarentined());
	}

}
