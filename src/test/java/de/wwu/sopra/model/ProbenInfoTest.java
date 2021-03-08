/**
 * 
 */
package de.wwu.sopra.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Testet die Proben Info
 * @author Gruppe 5
 */
class ProbenInfoTest {
    
    @Test
    public void testGetBehaeltertypSetBehaeltertyp() {
        ProbenInfo infoTest = new ProbenInfo(1.2f, null, null);
        Behaeltertyp behaelterTyp = new Behaeltertyp(null, 0, 0, 0, null);
        infoTest.setBehaeltertyp(behaelterTyp);
        assertEquals(behaelterTyp, infoTest.getBehaeltertyp());
    }

	/**
	 * setKategorie testen 
	 */
	@Test
	public void testSetKategorie() {
		
		ProbenKategorie kategorie = new ProbenKategorie("Blut", "ml", 20.0f, 5.0f);
		ProbenInfo infoTest = new ProbenInfo(1.2f, kategorie, null);
		ProbenKategorie kategorieTest = new ProbenKategorie("Speichel", "ml", 18.f, 0.2f);
		infoTest.setKategorie(kategorieTest);
		assertEquals(infoTest.getKategorie(), kategorieTest);
	}
	
	/**
	 * getMenge testen
	 */
	@Test
	public void testGetMenge(){
		
		ProbenKategorie kategorie = new ProbenKategorie("Blut", "ml", 20.0f, 5.0f);
		ProbenInfo infoTest = new ProbenInfo(1.2f, kategorie, null);
		float expResult = 1.2f;
		float result =infoTest.getMenge();
		assertEquals(expResult, result); 
	}
	
	/**
	 * setMenge testen 
	 */
	@Test
	public void testSetMenge() {
		
		float menge = 1.8f;
		ProbenKategorie kategorie = new ProbenKategorie("Blut", "ml", 20.0f, 5.0f);
		ProbenInfo infoTest = new ProbenInfo(1.2f, kategorie, null);
		infoTest.setMenge(menge);
		assertEquals(infoTest.getMenge(), menge);
		
	}
	
	/**
	 * getKategorie testen
	 */
	@Test
	public void testGetKategorie(){
		
		ProbenKategorie kategorie = new ProbenKategorie("Blut", "ml", 20.0f, 5.0f);
		ProbenInfo infoTest = new ProbenInfo(1.2f, kategorie, null);
		ProbenKategorie result = infoTest.getKategorie();
		assertEquals(kategorie, result);	
	}
}
