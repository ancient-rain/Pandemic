package gameTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import code.Game;

public class EpidemicTests {
	
	private Game testGame;
	int maxCubesOnACity = 3;
	
	@Before
	public void init(){
		this.testGame = new Game();
	}
	
	private void testXInfectionRateIncrease(int x){
		for(int i = 0; i < x; i++){
			this.testGame.handleEpidemic();
		}
	}
	
	@Test
	public void testFirstInfectionRateIncrease(){
		testXInfectionRateIncrease(1);
		assertEquals(2, this.testGame.getInfectionRate());
	}
	
	@Test
	public void testSecondInfectionRateIncrease(){
		testXInfectionRateIncrease(2);
		assertEquals(2, this.testGame.getInfectionRate());
	}

	@Test
	public void testThirdInfectionRateIncrease(){
		testXInfectionRateIncrease(3);
		assertEquals(3, this.testGame.getInfectionRate());
	}
	
	@Test
	public void testFourthInfectionRateIncrease(){
		testXInfectionRateIncrease(4);
		assertEquals(3, this.testGame.getInfectionRate());
	}
	
	@Test
	public void testFifthInfectionRateIncrease(){
		testXInfectionRateIncrease(5);
		assertEquals(4, this.testGame.getInfectionRate());
	}
	
	@Test
	public void testSixthInfectionRateIncrease(){
		testXInfectionRateIncrease(6);
		assertEquals(4, this.testGame.getInfectionRate());
	}
	
	@Test
	public void testOutOfBoundsInfectionRateIncrease(){
		testXInfectionRateIncrease(7);
		assertEquals(4, this.testGame.getInfectionRate());
	}
	
	@Test
	public void testDrawAndInfectBottomCardNoOutbreak(){
		int startingCubes = this.testGame.getCubesRemaining();
		this.testGame.handleEpidemic();
		int remainingCubes = this.testGame.getCubesRemaining();
		
		assertTrue(remainingCubes == startingCubes - 3);
		
	}
	
	@Test
	public void testShuffleAndPushInfectionDiscard(){
		int startingCubes = this.testGame.getCubesRemaining();
		this.testGame.handleEpidemic();
		this.testGame.drawAndInfect(1);
		int remainingCubes = this.testGame.getCubesRemaining();
		
		assertTrue(remainingCubes < startingCubes - 4);
	}
}
