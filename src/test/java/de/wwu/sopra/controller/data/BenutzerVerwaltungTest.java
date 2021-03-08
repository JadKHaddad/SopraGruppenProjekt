package de.wwu.sopra.controller.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashSet;
import java.util.Set;

import de.wwu.sopra.controller.data.BenutzerVerwaltung;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.wwu.sopra.model.Benutzer;

/**
 * Test der BenutzerVerwaltung Klasse
 *
 * @author Gruppe 5
 */
public class BenutzerVerwaltungTest {

    /**
     * Bereitet die Verwaltung vor
     */
    @BeforeEach
    public void setUp() {
        BenutzerVerwaltung benutzerVerwaltung = BenutzerVerwaltung.getInstance();
        benutzerVerwaltung.setBenutzerSet(new HashSet<>());
    }
    
    
    /**
     * testet, ob die getBenutzerVerwaltung Methode ein Objekt erstellt
     */
    @Test
    public void testGetBenutzerVerwaltung() {
        assertNotNull(BenutzerVerwaltung.getInstance());
    }

    /**
     * testet die addBenutzer und hasBenutzer Methoden der BenutzerVerwaltung
     */
    @Test
    public void testAddBenutzerHasBenutzer() {
        BenutzerVerwaltung benutzerVerwaltung = BenutzerVerwaltung.getInstance();
        Benutzer benutzer = new Benutzer("a", "ichbinbruno3", "Bruno", "Braun");
        benutzerVerwaltung.addBenutzer(benutzer);
        assertTrue(benutzerVerwaltung.hasBenutzer(benutzer));
    }

    
    /**
     * testet die removeBenutzer Methoden der BenutzerVerwaltung
     */
    @Test
    public void testRemoveBenutzer() {
        BenutzerVerwaltung benutzerVerwaltung = BenutzerVerwaltung.getInstance();
        Benutzer benutzer = new Benutzer("a", "ichbinbruno3", "Bruno", "Braun");
        benutzerVerwaltung.addBenutzer(benutzer);
        benutzerVerwaltung.removeBenutzer(benutzer);
        assertFalse(benutzerVerwaltung.hasBenutzer(benutzer));
    }

    
    
    /**
     * testet die getBenutzerSet und setBenutzerSet Methoden der Benutzerverwaltung
     */
    @Test
    public void testGetBenutzerSetSetBenutzerSet() {
        BenutzerVerwaltung benutzerVerwaltung = BenutzerVerwaltung.getInstance();
        Set<Benutzer> benutzerSet = new HashSet<Benutzer>();
        Benutzer benutzer = new Benutzer("a", "ichbinbruno3", "Bruno", "Braun");
        benutzerSet.add(benutzer);
        benutzerVerwaltung.setBenutzerSet(benutzerSet);
        assertEquals(benutzerSet, benutzerVerwaltung.getBenutzerSet());
        assertTrue(benutzerVerwaltung.hasBenutzer(benutzer));
    }
}
