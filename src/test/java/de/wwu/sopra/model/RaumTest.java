package de.wwu.sopra.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Testet den Raum
 * @author Grupe 5
 */
public class RaumTest {
    
    /**
     * testet die setName Methode der Raum Klasse
     */
    @Test
    public void testSetName() {
        Raum r = new Raum("asd", 0.1f, 0.2f);
        r.setName("qwe");
        assertEquals("qwe", r.getName());
    }
 
    /**
     * testet die setQuadratmeter Methode der Raum Klasse
     */
    @Test
    public void testSetQuadratmeter() {
        Raum r = new Raum("asd", 0.1f, 0.2f);
        r.setQuadratmeter(0.3f);
        assertEquals(0.3f, r.getQuadratmeter());
    }
  
    /**
     * testet die setHoehe Methode der Raum Klasse
     */
    @Test
    public void testSetHoehe() {
        Raum r = new Raum("asd", 0.1f, 0.2f);
        r.setHoehe(0.3f);
        assertEquals(0.3f, r.getHoehe());
    }

    /**
     * testet die getName Methode der Raum Klasse
     */
    @Test
    public void testGetName() {
        Raum r = new Raum("asd", 0.1f, 0.2f);
        assertEquals("asd", r.getName());
    }

    /**
     * testet die getQuadratmeter Methode der Raum Klasse
     */
    @Test
    public void testGetQuadratmeter() {
        Raum r = new Raum("asd", 0.1f, 0.2f);
        assertEquals(0.1f, r.getQuadratmeter());
    }

    /**
     * testet die getHoehe Methode der Raum Klasse
     */
    @Test
    public void testGetHoehe() {
        Raum r = new Raum("asd", 0.1f, 0.2f);
        assertEquals(0.2f, r.getHoehe());
    }

    /**
     * testet die getKuehlschrank Methode der Raum Klasse
     */
    @Test
    public void testGetKuehlschrank() {
        Raum r = new Raum("asd", 0.1f, 0.2f);
        assertEquals(r.getKuehlschraenke(), r.getKuehlschraenke());
    }

    /**
     * testet die hasKuehlschrank Methode der Raum Klasse
     */
    @Test
    public void testHasKuehlschrank() {
        Raum r = new Raum("asd", 0.1f, 0.2f);
        Kuehlschrank k = new Kuehlschrank("a",0.1f, 0.1f, 0.1f, 0.1f, 1, r);
        r.addKuehlschrank(k);
        assertEquals(true, r.hasKuehlschrank(k));
        assertEquals(r, k.getRaum());
    }

    /**
     * testet die addKuehlschrank Methode der Raum Klasse
     */
    @Test
    public void testAddKuehlschrank() {
        Raum r = new Raum("asd", 0.1f, 0.2f);
        Kuehlschrank k = new Kuehlschrank("a", 0.1f, 0.1f, 0.1f, 0.1f, 1, r);
        r.addKuehlschrank(k);
        assertTrue(r.hasKuehlschrank(k));
        assertEquals(r, k.getRaum());
    }

    /**
     * testet die removeKuehlschrank Methode der Raum Klasse
     */
    @Test
    public void testRemoveKuehlschrank() {
        Raum r = new Raum("asd", 0.1f, 0.2f);
        Kuehlschrank k = new Kuehlschrank("a",0.1f, 0.1f, 0.1f, 0.1f, 1, r);
        r.addKuehlschrank(k);
        r.removeKuehlschrank(k);
        assertEquals(false, r.hasKuehlschrank(k));
        assertEquals(null, k.getRaum());
    }
}
