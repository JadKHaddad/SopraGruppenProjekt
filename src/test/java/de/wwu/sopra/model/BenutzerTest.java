package de.wwu.sopra.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Testet den Benutzer
 * @author Gruppe 5
 */
class BenutzerTest {

    private Benutzer benutzer;
    
    /**
     * Bereitet den Test vor
     */
    @BeforeEach
    void setup() {
        benutzer = new Benutzer("b_braun", "passwortbruno", "Braun", "Bruno");
    }
    
    /**
     * getBenutzerName testen
     */
    @Test
    void testGetBenutzerName() {
        assertTrue("b_braun".contentEquals(benutzer.getBenutzerName()));
    }
    
    /**
     * setBenutzerName testen
     */
    @Test
    public void testSetBenutzerName() {
    	
    	String benutzerName = "bernhard_b";
    	benutzer.setBenutzerName(benutzerName);
		assertEquals(benutzer.getBenutzerName(), benutzerName);
    	

    }
    
    /**
     * getVorname testen
     */
    @Test
    void testGetVorname() {
        assertTrue("Bruno".contentEquals(benutzer.getVorname()));
    }
    
    /**
     * setVorname testen
     */
    @Test
    void testSetVorname() {
    	String vorname = "Bernhard";
    	benutzer.setVorname(vorname);
    	assertEquals(benutzer.getVorname(), vorname);
    }
    
    /**
     * getName testen
     */
    @Test
    void testGetName() {
        assertTrue("Braun".contentEquals(benutzer.getName()));
    }
    
    /**
     * setName testen
     */
    @Test
    void testSetName() {
    	String name = "Blume";
    	benutzer.setName(name);
    	assertEquals(benutzer.getName(), name);
    }
    
    /**
     * getPassword testen
     */

    @Test
    void testGetPassword() {
        assertTrue("passwortbruno".contentEquals(benutzer.getPassword()));
    }
    
    /**
     * setPassword testen
     */
    @Test
    void testSetPassword() {
    	String password = "ichbinbruno3";
    	benutzer.setPassword(password);
    	assertEquals(benutzer.getPassword(), password);
    }
    
    /**
     * Rollenzugehoerigkeit testen
     */
    @Test
    void testRolle() {
        assertFalse(benutzer.hasRolle(Rolle.PERSONALLEITER));
        benutzer.addRolle(Rolle.PERSONALLEITER);
        assertTrue(benutzer.hasRolle(Rolle.PERSONALLEITER));
        benutzer.removeRolle(Rolle.PERSONALLEITER);
        benutzer.removeRolle(Rolle.BIOBANKLEITER);
        assertFalse(benutzer.hasRolle(Rolle.PERSONALLEITER));
    }
    
    /**
     * Rolle ist null testen
     */
    @Test
    void testRolleNull() {
        assertFalse(benutzer.hasRolle(null));
        benutzer.addRolle(null);
        assertFalse(benutzer.hasRolle(null));
    }
    
    /**
     * testet addBenutzer und hasBenutzer
     */
    @Test
    public void testAddBenutzerHasBenutzer() {
        Benutzer b = new Benutzer(null, null, null, null);
        Studie s = new Studie(null, 100);
        b.addStudie(s);
        assertEquals(true, b.hasStudie(s));
        assertEquals(true, s.hasBenutzer(b));

    }
    
    /**
     * testet removeBenutzer
     */
    @Test
    public void testRemoveBenutzer() {
        Benutzer b = new Benutzer(null, null, null, null);
        Studie s = new Studie(null, 100);
        b.addStudie(s);
        assertEquals(true, b.hasStudie(s));
        assertEquals(true, s.hasBenutzer(b));
        b.removeStudie(s);
        assertEquals(false, b.hasStudie(s));
        assertEquals(false, s.hasBenutzer(b));
    }
    
    /**
     * Testet getRollen
     */
    @Test
    public void testGetRollen() {
        Benutzer b = new Benutzer(null, null, null, null);
        b.addRolle(Rolle.BIOBANKLEITER);
        assertTrue(b.getRollen().contains(Rolle.BIOBANKLEITER));
    }
    
    /**
     * Testet getStudien
     */
    @Test
    public void testGetStudien() {
        Benutzer b = new Benutzer(null, null, null, null);
        Studie s = new Studie("", 5);
        b.addStudie(s);
        assertTrue(b.getStudien().contains(s));
    }
}
