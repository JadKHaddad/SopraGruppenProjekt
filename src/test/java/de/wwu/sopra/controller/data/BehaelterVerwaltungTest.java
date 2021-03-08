package de.wwu.sopra.controller.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import de.wwu.sopra.controller.data.BehaelterVerwaltung;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.wwu.sopra.model.Behaeltertyp;
import de.wwu.sopra.model.Deckeltyp;

/**
 * Testklasse zur BehaelterVerwaltung
 * 
 * @author Gruppe 5
 *
 */
public class BehaelterVerwaltungTest {
    
    Deckeltyp deckeltyp = new Deckeltyp("Deckeltyp1");
    /**
     * Stellt sicher, dass zu Anfang der Test keine Behaltertypen im HashSet sind
     */
    @BeforeEach
    public void setup() {
        BehaelterVerwaltung.getInstance().setBehaeltertypSet(new HashSet<Behaeltertyp>());

        
    }

    /**
     * testet, ob die getBehaelterVerwaltung Methode ein Objekt erstellt.
     */
    @Test
    public void testGetBehaelterVerwaltung() {
        assertNotNull(BehaelterVerwaltung.getInstance());
    }

    /**
     * testet die addBehaeltertyp und hasBehaeltertyp Methode
     */
    @Test
    public void testAddBehaeltertypHasBehaeltertyp() {
        BehaelterVerwaltung behaelterVerwaltung = BehaelterVerwaltung.getInstance();
        Behaeltertyp behaeltertyp = new Behaeltertyp("b2",2f, 2f, 2f, deckeltyp);
        behaelterVerwaltung.addBehaeltertyp(behaeltertyp);
        assertTrue(behaelterVerwaltung.hasBehaeltertyp(behaeltertyp));
    }

    /**
     * testet die removeBehaeltertyp Methode
     */
    @Test
    public void testRemoveBehaeltertyp() {
        BehaelterVerwaltung behaelterVerwaltung = BehaelterVerwaltung.getInstance();
        Behaeltertyp behaeltertyp = new Behaeltertyp("b2",2f, 2f, 2f, deckeltyp);
        behaelterVerwaltung.addBehaeltertyp(behaeltertyp);
        behaelterVerwaltung.removeBehaeltertyp(behaeltertyp);
        assertFalse(behaelterVerwaltung.hasBehaeltertyp(behaeltertyp));
    }

    /**
     * testet die getBehaeltertypSet und setBehaeltertypSet Methoden
     */
    @Test
    public void testGetBehaelterTypSetSetBehaelterTypSet() {
        BehaelterVerwaltung behaelterVerwaltung = BehaelterVerwaltung.getInstance();
        Behaeltertyp behaeltertyp = new Behaeltertyp("b2",2f, 2f, 2f, deckeltyp);
        Set<Behaeltertyp> behaeltertypen = new HashSet<Behaeltertyp>();
        behaeltertypen.add(behaeltertyp);
        behaelterVerwaltung.setBehaeltertypSet(behaeltertypen);
        assertEquals(behaeltertypen, behaelterVerwaltung.getBehaeltertypSet());
        assertTrue(behaelterVerwaltung.hasBehaeltertyp(behaeltertyp));
    }
    
    /**
     * Testet NameisUNique
     */
    @Test
    public void testNameIsUnique() {
        assertTrue(BehaelterVerwaltung.getInstance().nameIsUnique("b2"));
        Behaeltertyp behaeltertyp = new Behaeltertyp("b2",2f, 2f, 2f, deckeltyp);
        BehaelterVerwaltung.getInstance().addBehaeltertyp(behaeltertyp);
        assertTrue(!(BehaelterVerwaltung.getInstance().nameIsUnique("b2")));
    }
}



