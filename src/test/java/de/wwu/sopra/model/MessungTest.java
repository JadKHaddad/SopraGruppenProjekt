package de.wwu.sopra.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Testet die Messung
 * @author Grupe 5
 */
public class MessungTest {
    
    
    Messtyp messtyp;
    
    @BeforeEach
    public void setUp() {
        messtyp = new Messtyp("name", "einheit");
    }
    
	/**
	 * Testet getWert
	 */
	@Test
	public void testGetWert() {
		Messung m = new Messung("5", messtyp, null);
		assertEquals("5", m.getWert());
	}

	/**
	 * Testet setWert
	 */
	@Test
	public void testSetWert() {
		Messung m = new Messung("5", messtyp, null);
		m.setWert("6");
		assertEquals("6", m.getWert());
	}

	/**
	 * testet getTyp
	 */
	@Test
	public void testGetTyp() {
		Messung m = new Messung("5", messtyp, null);
		assertEquals(messtyp, m.getTyp());
	}

	/**
	 * Testet setTyp
	 */
	@Test
	public void testSetTyp() {
		Messung m = new Messung("5", messtyp, null);
		m.setTyp(messtyp);
		assertEquals(messtyp, m.getTyp());
	}

	/**
	 * testet getTermin
	 */
	@Test
	public void testGetTermin() {
		PatientenTermin pt = new PatientenTermin(null, null, null);
		Messung m = new Messung("5", messtyp, pt);
		assertEquals(pt, m.getTermin());
	}
 
	/**
	 * testet setTermin
	 */
	@Test
	public void testSetTermin() {
		PatientenTermin pt = new PatientenTermin(null, null, null);
		Messung m = new Messung("5", messtyp, null);
		m.setTermin(pt);
		assertEquals(pt, m.getTermin());
	}
}
