package de.wwu.sopra.controller;

import de.wwu.sopra.controller.data.StudienVerwaltung;
import de.wwu.sopra.model.Benutzer;
import de.wwu.sopra.model.Rolle;
import de.wwu.sopra.model.Studie;
import de.wwu.sopra.model.Visite;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Gruppe 5
 */
public class StudienSteuerungTest {

    @BeforeEach
    public void setUp(){
        StudienVerwaltung.getInstance().setStudienSet(new HashSet<Studie>());
    }
    /**
     * testet die erstelleStudie Methode
     */
    @Test
    public void testErstelleStudie(){
        String studienName = "Herzschlagsforschung";
        int teilNehmerAnzahl = 15;
        Visite visite = new Visite("Visite 1", 0, null);
        BenutzerSteuerung bst = BenutzerSteuerung.getInstance();
        Benutzer b = bst.erstelleBenutzer("","","");
        b.addRolle(Rolle.STUDY_NURSE);
        Collection<Benutzer> benutzers = new LinkedList<>();
        benutzers.add(b);
        Collection<Visite> visites = new LinkedList<>();
        visites.add(visite);

        Studie studie = StudienSteuerung.getInstance().erstelleStudie(studienName, teilNehmerAnzahl, visites, benutzers);
        assertNotNull(studie);
        assertTrue(studie.hasBenutzer(b));
        assertTrue(studie.hasVisite(visite));
    }

    /**
     * testet die removeStudie Methode
     */
    @Test
    public void testRemoveStudie(){
        String studienName = "Herzschlagsforschung";
        int teilNehmerAnzahl = 15;
        Visite visite = new Visite("Visite 1", 0, null);
        BenutzerSteuerung bst = BenutzerSteuerung.getInstance();
        Benutzer b = bst.erstelleBenutzer("","","");
        b.addRolle(Rolle.STUDY_NURSE);
        Collection<Benutzer> benutzers = new LinkedList<>();
        benutzers.add(b);
        Collection<Visite> visites = new LinkedList<>();
        visites.add(visite);

        Studie studie = StudienSteuerung.getInstance().erstelleStudie(studienName, teilNehmerAnzahl, visites, benutzers);
        assertTrue(StudienVerwaltung.getInstance().hasStudie(studie));
        StudienSteuerung.getInstance().entferneStudie(studie);
        assertFalse(StudienVerwaltung.getInstance().hasStudie(studie));
    }

    /**
     * testet die starteStudie Methode
     */
    @Test
    public void testStarteStudie(){
        String studienName = "Herzschlagsforschung";
        int teilNehmerAnzahl = 15;
        Visite visite = new Visite("Visite 1", 0, null);
        BenutzerSteuerung bst = BenutzerSteuerung.getInstance();
        Benutzer b = bst.erstelleBenutzer("","","");
        b.addRolle(Rolle.STUDY_NURSE);
        Collection<Benutzer> benutzers = new LinkedList<>();
        benutzers.add(b);
        Collection<Visite> visites = new LinkedList<>();
        visites.add(visite);

        Studie studie = StudienSteuerung.getInstance().erstelleStudie(studienName, teilNehmerAnzahl, visites, benutzers);
        StudienSteuerung.getInstance().starteStudie(studie);
        assertFalse(studie.isBearbeitbar());
    }
}
