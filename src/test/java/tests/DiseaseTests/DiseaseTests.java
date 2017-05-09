package tests.DiseaseTests;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

import code.Disease;

public class DiseaseTests {

	@Test
	public void testGetColor() {
		Disease disease = new Disease();
		
		assertEquals(Color.WHITE, disease.getColor());
	}
	
	@Test
	public void testSetColor() {
		Disease disease = new Disease();
		
		disease.setColor(Color.BLUE);
		
		assertEquals(Color.BLUE, disease.getColor());
	}
	
	@Test
	public void testSetColorNull() {
		Disease disease = new Disease();
		
		disease.setColor(null);
		
		assertEquals(Color.WHITE, disease.getColor());
	}
	
	@Test
	public void testGetCured() {
		Disease disease = new Disease();
		
		assertFalse(disease.isCured());
	}
	
	@Test
	public void testSetCured() {
		Disease disease = new Disease();
		
		disease.setCured();
		
		assertTrue(disease.isCured());
	}
	
	@Test
	public void testGetSunsetted() {
		Disease disease = new Disease();
		
		assertFalse(disease.isEradicated());
	}
	
	@Test
	public void testSetSunsetted() {
		Disease disease = new Disease();
		
		disease.setEradicated();
		
		assertTrue(disease.isEradicated());
	}
	
	@Test
	public void testGetNumRemaining() {
		Disease disease = new Disease();
		
		assertEquals(0, disease.getNumRemaining());
	}
	
	@Test
	public void testSetNumRemaining() {
		Disease disease = new Disease();
		
		disease.setNumRemaining(3);
		
		assertEquals(3, disease.getNumRemaining());
	}
	
	@Test
	public void testSetNumRemainingZero() {
		Disease disease = new Disease();
		
		disease.setNumRemaining(0);
		
		assertEquals(0, disease.getNumRemaining());
	}

}
