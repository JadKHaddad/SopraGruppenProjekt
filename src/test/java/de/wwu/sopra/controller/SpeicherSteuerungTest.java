package de.wwu.sopra.controller;

import de.wwu.sopra.controller.data.BenutzerVerwaltung;
import de.wwu.sopra.controller.data.ProbenKategorieVerwaltung;
import de.wwu.sopra.model.Benutzer;
import de.wwu.sopra.model.ProbenKategorie;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testet die SpeicherSteuerung
 * @author Gruppe 5
 */
public class SpeicherSteuerungTest {
    
    static File file = new File("test.crypt");
    
    /**
     * Leert die Verwaltung
     */
    @BeforeEach
    public void reset() {
        BenutzerVerwaltung.getInstance().setBenutzerSet(new HashSet<>());
        ProbenKategorieVerwaltung.getInstance().setProbenKategorieSet(new HashSet<>());
        
        file.delete();
        SpeicherSteuerung.getInstance().setFile(file);
    }
    
    @AfterAll
    public static void deleteSave() {
        file.delete();
    }
    
    /**
     * Testet, einen Benutzer zu erstellen
     */
    @Test
    public void testBenutzerErstellen() {
        BenutzerVerwaltung.getInstance().setBenutzerSet(new HashSet<>());
        SpeicherSteuerung.getInstance().setPassword("passwort");
        SpeicherSteuerung.getInstance().laden();
        assertEquals(1, BenutzerVerwaltung.getInstance().getBenutzerSet().size());
        BenutzerSteuerung.getInstance().login("admin", "passwort");
        Benutzer b = BenutzerSteuerung.getInstance().getAktiverBenutzer();
        assertNotNull(b);
        assertEquals("admin", b.getName());
    }

    /**
     * Testet das Speichern
     */
    @Test
    public void testSpeichern() {
        BenutzerVerwaltung.getInstance().setBenutzerSet(new HashSet<>());
        SpeicherSteuerung.getInstance().setPassword("passwort");
        SpeicherSteuerung.getInstance().laden();
        ProbenKategorieVerwaltung.getInstance().addProbenKategorie(new ProbenKategorie("Blut", "ml", 7, 0));
        SpeicherSteuerung.getInstance().speichern();
        assertTrue(file.exists());

        BenutzerVerwaltung.getInstance().setBenutzerSet(new HashSet<>());
        ProbenKategorieVerwaltung.getInstance().setProbenKategorieSet(new HashSet<>());

        SpeicherSteuerung.getInstance().laden();
        assertEquals(1, ProbenKategorieVerwaltung.getInstance().getProbenKategorieSet().size());
    }


}
