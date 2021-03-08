package de.wwu.sopra.controller;

import de.wwu.sopra.model.Kuehlschrank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.wwu.sopra.controller.data.BenutzerVerwaltung;
import de.wwu.sopra.controller.data.LagerVerwaltung;
import de.wwu.sopra.model.Benutzer;
import de.wwu.sopra.model.Raum;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testet die RaumSteuerung
 * @author Gruppe 5
 */
public class RaumSteuerungTest {

    @BeforeEach
    public void setup() {
        LagerVerwaltung.getInstance().setRaumSet(new HashSet<>());
    }
    
    /**
     * Testet, ob ein Raumname bereits verwendet wird
     */
    @Test
    public void testIstRaumnameVerwendet() {
        RaumSteuerung raumSteuerung = RaumSteuerung.getInstance();
        LagerVerwaltung lagerVerwaltung = LagerVerwaltung.getInstance();
        Raum neuRaum = new Raum("asd", 2, 2);
        Raum neuRaum2 = new Raum("asd2", 2, 2);
        lagerVerwaltung.addRaum(neuRaum);
        assertTrue(raumSteuerung.istRaumnameVerwendet("asd", null));
        assertFalse(raumSteuerung.istRaumnameVerwendet("asd", neuRaum));
        assertFalse(raumSteuerung.istRaumnameVerwendet("asd2", null));
    }

    /**
     * testet, ob die benutzte Flaeche korrekt berechnet wird
     */
    @Test
    public void testBenutzteFlaeche(){
        RaumSteuerung raumSteuerung = RaumSteuerung.getInstance();
        LagerVerwaltung lagerVerwaltung = LagerVerwaltung.getInstance();
        Raum neuRaum = new Raum("asd", 10, 2);
        Kuehlschrank k1 =  new Kuehlschrank("k1",2,2,2,2,2, neuRaum);
        neuRaum.removeKuehlschrank(k1);
        assertEquals(raumSteuerung.getBenutzteFlaeche(neuRaum), 0);
        neuRaum.addKuehlschrank(k1);
        lagerVerwaltung.addRaum(neuRaum);
        assertEquals(raumSteuerung.getBenutzteFlaeche(neuRaum), 4);
        Kuehlschrank k2 =  new Kuehlschrank("k1",2,2,2,2,2,neuRaum);
        neuRaum.addKuehlschrank(k2);
        assertEquals(raumSteuerung.getBenutzteFlaeche(neuRaum), 8);
    }
}
