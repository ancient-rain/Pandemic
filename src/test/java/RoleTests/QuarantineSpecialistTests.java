package RoleTests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import actions.Action;
import code.City;
import code.Game;
import code.PlayerCharacter;
import roles.PlayerRoleInterface;
import roles.QuarantineSpecialist;

public class QuarantineSpecialistTests {
	Game newGame;
	Set<Action> playerActions;
	Set<City> neighbors;
	City city;
	PlayerRoleInterface role;

	@Before
	public void init(){
		newGame = new Game();
		playerActions = new HashSet<Action>();
		neighbors = new HashSet<City>();
		city = new City("Terre Haute", neighbors, Color.RED);
		role = EasyMock.createNiceMock(QuarantineSpecialist.class);
	}
	
	@Test
	public void testCannotInfectCurrentCity() {
		int cubesRemaining = newGame.getCubesRemaining();
		newGame.resetInfectionDeck();
		PlayerCharacter player = new PlayerCharacter(playerActions, city, role);
		newGame.addToPlayerMap("Quarantine Specialist", player);
		EasyMock.expect(role.getName()).andStubReturn("Quarantine Specialist");

		newGame.infect(city, city.getColor());
		int newCubesRemaining = newGame.getCubesRemaining();
		
		assertTrue(newCubesRemaining == cubesRemaining);
	}
	
	@Test
	public void testCannotInfectCurrentCityNeighbor() {
		int cubesRemaining = newGame.getCubesRemaining();
		newGame.resetInfectionDeck();
		PlayerCharacter player = new PlayerCharacter(playerActions, city, role);
		City newCity = new City("Brazil", new HashSet<City>(), Color.RED);
		neighbors.add(newCity);
		city.setNeighbors(neighbors);
		newGame.addToPlayerMap("Quarantine Specialist", player);
		EasyMock.expect(role.getName()).andStubReturn("Quarantine Specialist");

		newGame.infect(newCity, newCity.getColor());
		int newCubesRemaining = newGame.getCubesRemaining();
		
		assertTrue(newCubesRemaining == cubesRemaining);
	}
	
	@Test
	public void testCanInfectOtherCityNotOnOrConnected() {
		int cubesRemaining = newGame.getCubesRemaining();
		newGame.resetInfectionDeck();
		PlayerCharacter player = new PlayerCharacter(playerActions, city, role);
		City newCity = new City("Brazil", new HashSet<City>(), Color.RED);
		newGame.addToPlayerMap("Quarantine Specialist", player);
		EasyMock.expect(role.getName()).andStubReturn("Quarantine Specialist");

		newGame.infect(newCity, newCity.getColor());
		int newCubesRemaining = newGame.getCubesRemaining();
		
		assertTrue(newCubesRemaining == cubesRemaining - 1);
	}

}
