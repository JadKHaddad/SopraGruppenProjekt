package de.wwu.sopra.controller.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import de.wwu.sopra.controller.data.LagerVerwaltung;
import org.junit.jupiter.api.Test;

import de.wwu.sopra.model.Kuehlschrank;
import de.wwu.sopra.model.Rack;
import de.wwu.sopra.model.Raum;

/**
 * Test der Klasse LagerVerwaltung
 * 
 * @author Gruppe 5
 *
 */

public class LagerVerwaltungTest {

    
    /**
     * testet ob bei getLagerVerwaltung ein Objekt erstellt wird
     */
    @Test
    public void testGetLagerVerwaltung() {
        assertNotNull(LagerVerwaltung.getInstance());
    }

    
    /**
     * testet die addRaum und hasRaum Methode der LagerVerwaltung
     */
    @Test
    public void testAddRaumHasRaum() {
        LagerVerwaltung lagerVerwaltung = LagerVerwaltung.getInstance();
        Raum raum = new Raum("a", 2f, 2f);
        lagerVerwaltung.addRaum(raum);
        assertTrue(lagerVerwaltung.hasRaum(raum));
    }

    
    /**
     * testet die removeRaum Methode der LagerVerwaltung
     */
    @Test
    public void testRemoveRaum() {
        LagerVerwaltung lagerVerwaltung = LagerVerwaltung.getInstance();
        Raum raum = new Raum("a", 2f, 2f);
        lagerVerwaltung.addRaum(raum);
        lagerVerwaltung.removeRaum(raum);
        assertFalse(lagerVerwaltung.hasRaum(raum));
    }

    
    /**
     * testet die getRaumSet und setRaumSet Methode der LagerVerwaltung
     */
    @Test
    public void testGetRaumSetSetRaumSet() {
        LagerVerwaltung lagerVerwaltung = LagerVerwaltung.getInstance();
        Raum raum = new Raum("a", 2f, 2f);
        Set<Raum> raeume = new HashSet<Raum>();
        raeume.add(raum);
        lagerVerwaltung.setRaumSet(raeume);
        assertEquals(raeume, lagerVerwaltung.getRaumSet());
        assertTrue(lagerVerwaltung.hasRaum(raum));
    }
    
    /**
     * testet getAnzProbenplaetze
     */
    @Test
    public void testGetAnzProbenplaetze() {
        LagerVerwaltung lagerVerwaltung = LagerVerwaltung.getInstance();
        Raum raum = new Raum("a", 2f, 2f);
        Kuehlschrank k = new Kuehlschrank("test", 111, 111, 111, 111, 5, raum);
        k.getSegment(0).getGestell(0).getSchublade(0).setRack(new Rack(0, 10, 10, null, null, 0), 0);
        raum.addKuehlschrank(k);
        lagerVerwaltung.addRaum(raum);
      
        assertEquals(lagerVerwaltung.getAnzProbenplaetze(), 100);
    }
}
