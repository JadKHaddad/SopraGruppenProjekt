package de.wwu.sopra.controller;

import de.wwu.sopra.controller.data.BenutzerVerwaltung;
import de.wwu.sopra.model.Benutzer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Testet die BenutzerSteuerung
 * @author Gruppe 5
 */
public class BenutzerSteuerungTest {
    
    /**
     * Leert die Verwaltung
     */
    @BeforeEach
    public void reset() {
        if (BenutzerSteuerung.getInstance().getAktiverBenutzer() != null) {
            BenutzerSteuerung.getInstance().logout();
        }
        BenutzerVerwaltung.getInstance().setBenutzerSet(new HashSet<>());
    }

    /**
     * testet den Login
     */
    @Test
    public void testLogin() {
        BenutzerSteuerung benutzerSteuerung = BenutzerSteuerung.getInstance();
        BenutzerVerwaltung benutzerVerwaltung = BenutzerVerwaltung.getInstance();
        Benutzer gehashterBenutzer = benutzerSteuerung.erstelleBenutzer("a", "ichbinbruno3", "Bruno", "Braun");
        benutzerVerwaltung.addBenutzer(gehashterBenutzer);
        benutzerSteuerung.login("a", "ichbinbruno3");
        assertTrue(benutzerSteuerung.login("a", "ichbinbruno3"));
        assertTrue(benutzerVerwaltung.hasBenutzer(gehashterBenutzer));
        assertEquals(gehashterBenutzer, benutzerSteuerung.getAktiverBenutzer());
    }
    
    /**
     * testen, ob eine Benutzername verwendet wird
     */
    @Test
    public void testIstBenutzernameVerwendet() {
        BenutzerSteuerung benutzerSteuerung = BenutzerSteuerung.getInstance();
        BenutzerVerwaltung benutzerVerwaltung = BenutzerVerwaltung.getInstance();
        Benutzer gehashterBenutzer = benutzerSteuerung.erstelleBenutzer("a", "ichbinbruno3", "Bruno", "Braun");
        Benutzer gehashterBenutzer2 = benutzerSteuerung.erstelleBenutzer("b", "ichbinbruno3", "Bruno2", "Braun2");
        benutzerVerwaltung.addBenutzer(gehashterBenutzer);
        assertTrue(benutzerSteuerung.istBenutzernameVerwendet("a", null));
        assertFalse(benutzerSteuerung.istBenutzernameVerwendet("a", gehashterBenutzer));
        assertFalse(benutzerSteuerung.istBenutzernameVerwendet("b", null));
    }

    /**
     * testet die logout Funktion
     */
    @Test
    public void testLogout() {
        BenutzerSteuerung benutzerSteuerung = BenutzerSteuerung.getInstance();
        BenutzerVerwaltung benutzerVerwaltung = BenutzerVerwaltung.getInstance();
        Benutzer gehashterBenutzer = benutzerSteuerung.erstelleBenutzer("a", "ichbinbruno3", "Bruno", "Braun");
        benutzerVerwaltung.addBenutzer(gehashterBenutzer);
        benutzerSteuerung.login("a", "ichbinbruno3");
        assertEquals(gehashterBenutzer, benutzerSteuerung.getAktiverBenutzer());
        benutzerSteuerung.logout();
        assertFalse(gehashterBenutzer.equals(benutzerSteuerung.getAktiverBenutzer()));
    }

    /**
     * testet die PasswortAendern Funktion um sicherzustellen, dass das Passwort korrekt geaendert wird
     */
    @Test
    public void testPasswortAendern() {
        BenutzerVerwaltung benutzerVerwaltung = BenutzerVerwaltung.getInstance();
        BenutzerSteuerung benutzerSteuerung = BenutzerSteuerung.getInstance();
        Benutzer gehashterBenutzer = benutzerSteuerung.erstelleBenutzer("a", "ichbinbruno3", "Bruno", "Braun");
        benutzerVerwaltung.addBenutzer(gehashterBenutzer);
        benutzerSteuerung.login("a", "ichbinbruno3");
        benutzerSteuerung.passwortAendern("ichbinbruno3", "dasisteinsicheresPasswort", "dasisteinsicheresPasswort");
        assertEquals(gehashterBenutzer, benutzerSteuerung.getAktiverBenutzer());
        benutzerSteuerung.logout();
        benutzerSteuerung.login("a", "ichbinbruno3");
        assertFalse(gehashterBenutzer.equals(benutzerSteuerung.getAktiverBenutzer()));
        benutzerSteuerung.login("a", "dasisteinsicheresPasswort");
        assertEquals(gehashterBenutzer, benutzerSteuerung.getAktiverBenutzer());
    }
    
    /**
     *Testet die ueberschriebene Benutzer erstellen Methode 
     */
    @Test
    public void testBenutzerErstellen() {
        BenutzerSteuerung bst = BenutzerSteuerung.getInstance();
        Benutzer b = bst.erstelleBenutzer("","","");
        Benutzer b2  = bst.erstelleBenutzer("3", "2", "1");
        assertNotEquals(b, b2);
    }
    /**
     *Testet die ueberschriebene Benutzer erstellen Methode 
     */
    @Test
    public void testPasswordAendern() {
        BenutzerSteuerung bst = BenutzerSteuerung.getInstance();
        Benutzer b = bst.erstelleBenutzer("","","");
        String s = b.getPassword();
        bst.passwortAendern(b, "");
        assertNotEquals(b.getPassword(), s);
    }

    /**
     * Testet die Methode um ein Passwort zu aendern, wenn das alte Passwort vergessen wurde
     */
    @Test
    public void testPasswortAendern2() {
        BenutzerVerwaltung benutzerVerwaltung = BenutzerVerwaltung.getInstance();
        BenutzerSteuerung benutzerSteuerung = BenutzerSteuerung.getInstance();
        Benutzer gehashterBenutzer = benutzerSteuerung.erstelleBenutzer("a", "ichbinbruno3", "Bruno", "Braun");
        benutzerVerwaltung.addBenutzer(gehashterBenutzer);
        benutzerSteuerung.passwortAendern(gehashterBenutzer, "1234");
        benutzerSteuerung.login("a", "ichbinbruno3");
        assertFalse(gehashterBenutzer.equals(benutzerSteuerung.getAktiverBenutzer()));
        benutzerSteuerung.login("a", "1234");
        assertEquals(gehashterBenutzer, benutzerSteuerung.getAktiverBenutzer());
        
    }
    /**
     * Testet genPasswort()
     */
    @Test
    public void testGenPasswort() {
        BenutzerSteuerung benutzerSteuerung = BenutzerSteuerung.getInstance();
        assertFalse(benutzerSteuerung.genPasswort().isBlank());
    }    
}

