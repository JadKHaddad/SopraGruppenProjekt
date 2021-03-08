package de.wwu.sopra.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

/**
 * Testet den Probenplatz
 * @author Gruppe 5
 */
public class ProbenplatzTest {

	/**
	 * setZeilen testen
	 */
	@Test
	public void testSetZeilen() {
		int zeilen = 5;
		ProbenPlatz ProbeTest = new ProbenPlatz(5, 5, null, null);
		ProbeTest.setZeile(5);
		assertEquals(ProbeTest.getZeile(), zeilen);
	}
	
	/**
	 * getZeilen testen
	 */
	@Test
	public void testGetZeilen() {
		ProbenPlatz ProbeTest = new ProbenPlatz(5, 5, null, null);
		int expResult = 5;
		ProbeTest.setZeile(5);
		int result = ProbeTest.getZeile();
		assertEquals(expResult, result);
	}
	 
	/**
	 * setSpalten testen
	 */
	@Test
	public void testSetSpalten() {
		int spalten = 5;
		ProbenPlatz ProbeTest = new ProbenPlatz(5, 5, null, null);
		ProbeTest.setSpalte(5);
		assertEquals(ProbeTest.getSpalte(), spalten);
	}
	
	/**
	 * getSpalten testen
	 */
	@Test
	public void testGetSpalten() {
		ProbenPlatz ProbeTest = new ProbenPlatz(5, 5, null, null);
		int expResult = 5;
		ProbeTest.setSpalte(5);
		int result = ProbeTest.getSpalte();
		assertEquals(expResult, result);
		
	}
	
	/**
	 * setRack testen
	 */
	@Test
	public void testSetRack(){
		Rack rack = new Rack(123, 5, 5, null, null,0);
		ProbenPlatz ProbeTest = new ProbenPlatz(5, 5, null, null);
		ProbeTest.setRack(rack);
		assertEquals(ProbeTest.getRack(), rack);
	} 
	
	/**
	 * getRack testen
	 */
	@Test
	public void testGetRack() {
		ProbenPlatz ProbeTest = new ProbenPlatz(5, 5, null, null);
		Rack expResult = new Rack(123, 5, 5, null, null,0 );
		ProbeTest.setRack(expResult);
		Rack result = ProbeTest.getRack();
		assertEquals(expResult, result);
	}
	
	/**
	 * testet getProbe setProbe
	 */
	@Test
	public void testGetProbeSetProbe() {
	      ProbenPlatz pp = new ProbenPlatz(5, 5, null, null);
	      Probe p = new Probe(0, null, null, null, pp, null, null);
	      pp.setProbe(p);
	      assertEquals(p, pp.getProbe());
	}
}
