package gameTests;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import characters.AbstractCharacterController;
import city.CityController;
import city.CityModel;
import game.GameController;
import game.GameModel;

public class GameControllerTests {
	GameController controller;
	GameModel model;
	CityController cityController;
	AbstractCharacterController player;
	
	@Before
	public void init(){
		this.model = new GameModel();
//		this.model.setDifficulty(3);
		this.controller = new GameController(model);
		this.cityController = this.controller.getCityController();
		this.player = this.controller.getCurrentPlayer();
	}

	@Test
	public void testMoveCharacters() {
		CityModel currentCity = this.player.getCharactersCurrentCity();
		Iterator<CityModel> iter = currentCity.getNeighbors().iterator();
		CityModel nextCity = iter.next();
		
		assertTrue(this.controller.moveCharacter(player, nextCity));
	}

}
