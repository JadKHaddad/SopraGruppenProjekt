package de.wwu.sopra.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Testet die Behealtertypen
 * @author Gruppe 5
 */
public class BehaeltertypTest {
    
    Deckeltyp deckeltyp = new Deckeltyp("Deckeltyp1");

	/**
	 * setRadius testen 
	 */
	@Test
	public void testSetRadius() {
		float radius = 1.2f;
		Behaeltertyp BehTest = new Behaeltertyp("b1",1.3f, 5, 5, deckeltyp);
		BehTest.setRadius(1.2f);
		assertEquals(BehTest.getRadius(), radius);
	}
	
	/**
	 * getRadius testen
	 */
	@Test
	public void testGetRadius(){
		
		
		Behaeltertyp behTest = new Behaeltertyp("b1",1.3f, 5, 5, deckeltyp);
		float expResult = 1.2f;
		behTest.setRadius(1.2f);
		float result = behTest.getRadius();
		assertEquals(expResult, result); 
	}
	
	/**
	 * setHoehe testen 
	 */
	@Test
	public void testSetHoehe() {
		float Hoehe = 1.2f;
		Behaeltertyp behTest = new Behaeltertyp("b1",1.3f, 5, 5, deckeltyp);
		behTest.setHoehe(1.2f);
		assertEquals(behTest.getHoehe(), Hoehe);
	}
	
	/**
	 * getHoehe testen
	 */
	@Test
	public void testGetHoehe(){
		
		
		Behaeltertyp behTest = new Behaeltertyp("b1",1.3f, 5, 5, deckeltyp);
		float expResult = 1.2f;
		behTest.setHoehe(1.2f);
		float result = behTest.getHoehe();
		assertEquals(expResult, result); 
	}
	
	
	/**
	 * setVolumen testen 
	 */
	@Test
	public void testSetVolumen() {
		float volumen = 1.2f;
		Behaeltertyp behTest = new Behaeltertyp("b1",1.3f, 5, 5, deckeltyp);
		behTest.setVolumen(1.2f);
		assertEquals(behTest.getVolumen(), volumen);
	}
	
	/**
	 * getVolumen testen
	 */
	@Test
	public void testGetVolumen(){
		
		
		Behaeltertyp behTest = new Behaeltertyp("b1",1.3f, 5, 5, deckeltyp);
		float expResult = 1.2f;
		behTest.setVolumen(1.2f);
		float result = behTest.getVolumen();
		assertEquals(expResult, result); 
	}

	/**
	 * setDeckel testen
	 */
	@Test
	public void testSetDeckel() {
		
		Behaeltertyp behTest = new Behaeltertyp("b2",1.2f, 2, 2, null);
		behTest.setDeckel(deckeltyp);
		assertEquals(behTest.getDeckel(), deckeltyp);
	}
	
	/**
	 * getDeckel testen
	 */
	@Test
	public void testGetDeckel() {
		Behaeltertyp behTest = new Behaeltertyp("b2",1.2f, 2, 2, null);
		Deckeltyp expResult = deckeltyp;
		behTest.setDeckel(expResult);
		Deckeltyp result = behTest.getDeckel();
		assertEquals(expResult, result);
	}


    /**
     * setName testen
     */
    @Test
    public void testSetName() {
        
        Behaeltertyp behTest = new Behaeltertyp("b2",1.2f, 2, 2, null);
        behTest.setName("b3");
        assertEquals(behTest.getName(), "b3");
    }
    
    /**
     * getName testen
     */
    @Test
    public void testGetName() {
        Behaeltertyp behTest = new Behaeltertyp("b2",1.2f, 2, 2, null);
        assertEquals(behTest.getName(), "b2");
    }
}
