package de.wwu.sopra.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Testet die Studie
 * @author Gruppe 5
 */
public class StudieTest {

    
    /**
     * testet bearbeitbar
     */
    @Test
    public void testIstBearbeitbar() {
        Studie studie = new Studie("Hallo", 100);
        assertTrue(studie.isBearbeitbar());
        studie.setBearbeitbar(false);
        assertFalse(studie.isBearbeitbar());
    }
    
    
    
    /**
     * testet getName
     */
    @Test
    public void testGetName() {
        Studie s = new Studie("Hallo", 100);
        assertEquals("Hallo", s.getName());
    }

    /**
     * testet setName
     */
    @Test
    public void testSetName() {
        Studie s = new Studie("Hallo", 100);
        s.setName("Hi");
        assertEquals("Hi", s.getName());
    }

    /**
     * testet getAnzTeilnehmer
     */
    @Test
    public void testGetAnzTeilnehmer() {
        Studie s = new Studie("Hallo", 100);
        assertEquals(100, s.getAnzTeilnehmer());
    }

    /**
     * testet setAnzTeilnehmer
     */
    @Test
    public void testSetAnzTeilnehmer() {
        Studie s = new Studie("Hallo", 100);
        s.setAnzTeilnehmer(150);
        assertEquals(150, s.getAnzTeilnehmer());
    }

    /**
     * testet addVisite und hasVisite
     */
    @Test
    public void testAddVisiteHasVisite() {
        Studie s = new Studie("Hallo", 100);
        Visite v = new Visite(null, 1, s);
        s.addVisite(v);
        assertEquals(s.hasVisite(v), true);
        assertTrue(s.getVisiten().contains(v));
    }

    /**
     * testet removeVisite
     */
    @Test
    public void testRemoveVisite() {
        Studie s = new Studie("Hallo", 100);
        Visite v = new Visite(null, 1, s);
        s.addVisite(v);
        assertEquals(s.hasVisite(v), true);
        s.removeVisite(v);
        assertEquals(s.hasVisite(v), false);
    }
    
    /**
     * testet addTeilnehmer und hasTeilnehmer
     */
    @Test
    public void testAddTeilnehmerHasTeilnehmer() {
        Studie s = new Studie("Hallo", 100);
        Studienteilnehmer st = new Studienteilnehmer(null, null);
        s.addTeilnehmer(st);
        assertEquals(s.hasTeilnehmer(st), true);

    }
    
    /**
     * testet removeTeilnehmer
     */
    @Test
    public void testRemoveTeilnehmer() {
        Studie s = new Studie("Hallo", 100);
        Studienteilnehmer st = new Studienteilnehmer(null, null);
        s.addTeilnehmer(st);
        assertEquals(s.hasTeilnehmer(st), true);
        s.removeTeilnehmer(st);
        assertEquals(s.hasTeilnehmer(st), false);

    }
    
    /**
     * testet hasBenutzer und addBenuter
     */
    @Test
    public void testHasBenutzerAddBenutzer() {
        Studie s = new Studie("Hallo", 100);
        Benutzer b = new Benutzer(null, null, null, null);
        s.addBenutzer(b);
        assertEquals(true, s.hasBenutzer(b));
        assertTrue(s.getBenutzer().contains(b));

    }
    
    
    /**
     * testet removeBenutzer
     */
    @Test
    public void testRemoveBenutzer() {
        Studie s = new Studie("Hallo", 100);
        Benutzer b = new Benutzer(null, null, null, null);
        s.addBenutzer(b);
        assertEquals(true, s.hasBenutzer(b));
        s.removeBenutzer(b);
        assertEquals(false, s.hasBenutzer(b));
    }
}
