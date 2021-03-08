package de.wwu.sopra.controller.data;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

import de.wwu.sopra.controller.data.StudienVerwaltung;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.wwu.sopra.model.Benutzer;
import de.wwu.sopra.model.Studie;

/**
 * Testet die StudienVerwaltung
 * @author Gruppe 5
 */
class StudienVerwaltungTest {

    Studie studie1;
    Studie studie2;
    Studie studie3;
    StudienVerwaltung studienVerwaltung;

    /**
     * Es werden drei Studien erstellt und die Instanz der Studienverwaltung wird
     * gespeichert um einfacher daruaf zugreifen zu koennen Damit das Testen
     * einfacher ist, wird das HashSet aller Listen vor jedem Test komplett geleert
     */
    @BeforeEach
    void setUp() {
        studie1 = new Studie("Studie 1", 4);
        studie2 = new Studie("Studie 2", 5);
        studie3 = new Studie("Studie 3", 6);
        studienVerwaltung = StudienVerwaltung.getInstance();
        studienVerwaltung.setStudienSet(new HashSet<Studie>());
    }

    /**
     * Testet, ob das HashSet wie gewollt am Anfang immer leer ist
     */
    @Test
    void leereListeTest() {
        assertTrue(studienVerwaltung.getStudienSet().isEmpty());
    }

    /**
     * Testet die Add Methode Standart Fall
     */
    @Test
    void addStudie1() {
        studienVerwaltung.addStudie(studie1);
        assertTrue(studienVerwaltung.hasStudie(studie1));
    }

    /**
     * Testet, dass nur die gewollte Studie hinzugefuegt wird
     */
    @Test
    void addStudie2() {
        studienVerwaltung.addStudie(studie1);
        assertFalse(studienVerwaltung.hasStudie(studie2));
        assertFalse(studienVerwaltung.hasStudie(studie3));
    }

    /**
     * Testet den null Fall der has Methode
     */
    @Test
    void hasStudieNull() {
        assertFalse(studienVerwaltung.hasStudie(null));
    }

    /**
     * Testet die addStudie() ohne die hasStudie() zu benutzen
     */
    @Test
    void addStudi3() {
        studienVerwaltung.addStudie(studie3);
        studienVerwaltung.getStudienSet();
        HashSet<Studie> studien = new HashSet<Studie>();
        studien.add(studie3);
        assertTrue((studienVerwaltung.getStudienSet()).equals(studien));
    }

    /**
     * Testet die delete Methode
     */
    @Test
    void removeStudie1() {
        studienVerwaltung.addStudie(studie2);
        studienVerwaltung.addStudie(studie1);
        studienVerwaltung.removeStudie(studie1);
        assertTrue(studienVerwaltung.hasStudie(studie2));
        assertFalse(studienVerwaltung.hasStudie(studie1));
    }

    /**
     * Testet die Delete-Methode ohne die has-Methode zu benutzen
     */
    @Test
    void removeStudie2() {
        studienVerwaltung.addStudie(studie3);
        studienVerwaltung.removeStudie(studie3);
        HashSet<Studie> studien = new HashSet<Studie>();
        assertTrue((studienVerwaltung.getStudienSet()).equals(studien));
    }

    
    
    /**
     * Testet die remove Methode  wenn zu einer Studie noch Benutzer gehören
     */
    @Test
    void removeStudie3() {
        studienVerwaltung.addStudie(studie2);
        Benutzer benutzer = new Benutzer("benu_01", "1234", "Nachname", "Vorname");
        studie1.addBenutzer(benutzer);
        studienVerwaltung.addStudie(studie1);
        studienVerwaltung.removeStudie(studie1);
        assertTrue(studienVerwaltung.hasStudie(studie2));
        assertFalse(studienVerwaltung.hasStudie(studie1));
    }

    
    /**
     * Testet die Delete-Methode spezifischer
     */
    @Test
    void removeStudie4() {
        Benutzer b = new Benutzer("", "", "", "");
        studie3.addBenutzer(b);
        studienVerwaltung.addStudie(studie3);
        studienVerwaltung.removeStudie(studie3);
        assertFalse(studienVerwaltung.hasStudie(studie3));        
    }

    /**
     * Testet getter und setter
     */
    @Test
    void getSetStudie() {
        HashSet<Studie> studien = new HashSet<Studie>();
        studien.add(studie3);
        
        studien.add(studie2);
        studienVerwaltung.setStudienSet(studien);
        assertTrue(studienVerwaltung.getStudienSet().equals(studien));
    }

    /**
     * Testet hasStudie()
     */
    @Test
    void hasStudie() {
        studienVerwaltung.addStudie(studie3);
        HashSet<Studie> studien = new HashSet<Studie>();
        studien.add(studie3);
        assertTrue((studienVerwaltung.getStudienSet()).equals(studien) == studienVerwaltung.hasStudie(studie3));
    }
}
