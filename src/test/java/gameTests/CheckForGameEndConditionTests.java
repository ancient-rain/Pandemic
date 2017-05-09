package gameTests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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

public class CheckForGameEndConditionTests {
	
	private Game testGame;
	private int numberOfInfectionCardsToDrawToEmptyDeck = 48;
	private int numberOfInfectionsOnOneCityToGuarenteeItOutbreaksEightTimes = 11;
	private int numberOfCardsToDrawToEmptyPlayerDeck = 48;
	
	@Before
	public void init() throws InvocationTargetException, IOException, InterruptedException{
		this.testGame = new Game();
	}
	
	@Test
	public void testCheckLossOnGameStart(){
		assertFalse(this.testGame.checkForLoss());
	}
	
	@Test
	public void testCheckLossOnCubeShortage(){
		this.testGame.resetInfectionDeck();
		this.testGame.drawAndInfect(this.numberOfInfectionCardsToDrawToEmptyDeck);
		this.testGame.infectionDiscardBackOnTop();
		this.testGame.drawAndInfect(this.numberOfInfectionCardsToDrawToEmptyDeck);
		this.testGame.infectionDiscardBackOnTop();
		this.testGame.drawAndInfect(1);
		assertTrue(this.testGame.checkForLoss());
	}
	
	@Test
	public void testCheckLossOnOutBreakCounter(){
		this.testGame.resetInfectionDeck();
		this.testGame.infectionDiscardBackOnTop();
		for(int i = 0; i < this.numberOfInfectionsOnOneCityToGuarenteeItOutbreaksEightTimes; i++){
			this.testGame.drawAndInfect(1);
			this.testGame.infectionDiscardBackOnTop();
			this.testGame.clearOutbrokenCities();
		}
		assertTrue(this.testGame.checkForLoss());
	}
	
	@Test
	public void testCheckLossOnEmptyPlayerDeck(){
		Set<Action> actionSet = new HashSet<Action>();
		PlayerCharacter player = new PlayerCharacter(actionSet, new City("Atlanta", Color.BLUE), (PlayerRoleInterface) EasyMock.createMock(PlayerRoleInterface.class));
		this.testGame.resetPlayerDeck();
		
		this.testGame.drawAndGiveCardToPlayer(player, this.numberOfCardsToDrawToEmptyPlayerDeck);
		this.testGame.drawAndGiveCardToPlayer(player, 1);
		assertTrue(this.testGame.checkForLoss());
	}

}
