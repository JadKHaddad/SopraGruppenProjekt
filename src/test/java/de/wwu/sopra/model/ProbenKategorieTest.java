/**
 * 
 */
package de.wwu.sopra.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Testet die Probenkategorie
 * @author Gruppe 5
 */
class ProbenKategorieTest {
 
	/**
	 * setBid testen 
	 */
	@Test
	public void testSetName() {
		
		String name = "Blut";
		ProbenKategorie kategorieTest = new ProbenKategorie ("Speichel", "ml", 20.0f, 5.0f);
		kategorieTest.setName(name);
		assertEquals(kategorieTest.getName(), name);
	
	}
	
	/**
	 * getMenge testen
	 */
	@Test
	public void testGetName(){
		
		ProbenKategorie kategorie = new ProbenKategorie("Blut", "ml", 20.0f, 5.0f);
		String expResult = "Blut";
		String result = kategorie.getName();
		assertEquals(expResult, result); 
	}
	
	/**
	 * setMenge testen 
	 */
	@Test
	public void testSetEinheit() {
		
		String einheit = "ml";
		ProbenKategorie kategorie = new ProbenKategorie("Blut", "l", 20.0f, 5.0f);
		kategorie.setEinheit(einheit);
		assertEquals(kategorie.getEinheit(), einheit);
		
	}
	
	/**
	 * getKategorie testen
	 */
	@Test
	public void testGetEinheit(){
		
		
		ProbenKategorie kategorie = new ProbenKategorie("Blut", "ml", 20.0f, 5.0f);
		String expResult = "ml";
		String result = kategorie.getEinheit();
		assertEquals(expResult, result); 
	}
	
	/**
	 * getMaxLagerTemp testen
	 */
	@Test
	public void testGetMaxLagerTemp() {
		
		ProbenKategorie kategorie = new ProbenKategorie("Blut", "ml", 20.0f, 5.0f);
		float expResult = 20.0f;
		float result = kategorie.getMaxLagerTemp();
		assertEquals(expResult, result);
	}
	
	/**
	 * setMaxLagerTemp testen
	 */
	@Test
	public void testSetMaxLagerTemp() {
		
		float maxLagerTemp = 20.0f;
		ProbenKategorie kategorie = new ProbenKategorie("Blut", "l", 18.0f, 5.0f);
		kategorie.setMaxLagerTemp(maxLagerTemp);
		assertEquals(kategorie.getMaxLagerTemp(), maxLagerTemp);	
	}
	
	/**
	 * getMinLagerTemp testen
	 */
	@Test
	public void testGetMinLagerTemp() {
		
		ProbenKategorie kategorie = new ProbenKategorie("Blut", "ml", 20.0f, 5.0f);
		float expResult = 5.0f;
		float result = kategorie.getMinLagerTemp();
		assertEquals(expResult, result);
		
	}
	
	/**
	 * setMinLagerTemp testen
	 */
	@Test
	public void testSetMinLagerTemp() {
		
		float minLagerTemp = 5.0f;
		ProbenKategorie kategorie = new ProbenKategorie("Blut", "l", 20.0f, 2.0f);
		kategorie.setMinLagerTemp(minLagerTemp);
		assertEquals(kategorie.getMinLagerTemp(), minLagerTemp);		
	}
	/**
	 * Testet toString
	 */
	@Test
	public void testToString() {
	    ProbenKategorie pkg = new ProbenKategorie("", "", 5f, 5f);
	    pkg.setName("LOL");
	    assertEquals(pkg.getName(),pkg.toString());
	}
}
