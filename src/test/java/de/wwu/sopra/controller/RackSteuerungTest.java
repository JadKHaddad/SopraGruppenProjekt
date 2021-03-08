package de.wwu.sopra.controller;

import de.wwu.sopra.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testet die RackSteuerung
 * 
 * @author Gruppe 5
 */
public class RackSteuerungTest {
    /**
     * Testet, ob ein Rack leer ist
     */
    @Test
    public void istRackleerTest() {
        Kuehlschrank kuehlschrank = new Kuehlschrank("kühlschrank", 3f, 40f, 67f, 89f, 2, new Raum("raum", 45f, 56f));
        Segment segment = new Segment(kuehlschrank, 1);
        Gestell gestell = new Gestell(segment, 1);
        Schublade schublade = new Schublade(gestell, 1);
        Behaeltertyp behaelterTyp = new Behaeltertyp("Behaeltertyp", 3, 4, 5, null);
        Rack rack = new Rack(12, 15, 15, behaelterTyp, schublade, 0);
        ProbenKategorie probenKategorie = new ProbenKategorie("n", "e", 7, 5);
        ProbenInfo probenInfo = new ProbenInfo(2, probenKategorie, null);
        Studie studie = new Studie("studie", 2);
        Patient patient = new Patient("Patient", "V", 10, "An");
        Studienteilnehmer teilnehmer = new Studienteilnehmer(patient, studie);
        Visite visite = new Visite("visite", 2, studie);
        PatientenTermin termin = new PatientenTermin(visite, null, teilnehmer);
        Probe probe = new Probe(10, null, probenInfo, ProbenStatus.DAUERHAFT_ENTNOMMEN, null, termin, behaelterTyp);
        assertTrue(RackSteuerung.getInstance().istRackLeer(rack));
        rack.setProbe(2, 2, probe);
        assertFalse(RackSteuerung.getInstance().istRackLeer(rack));
    }

    /**
     * Testet, ob ein Rack aus einer Schublade entfernnt wurde
     */
    @Test
    public void removeRackTest() {
        Kuehlschrank kuehlschrank = new Kuehlschrank("kühlschrank", 3f, 40f, 67f, 89f, 2, new Raum("raum", 45f, 56f));
        Segment segment = new Segment(kuehlschrank, 1);
        Gestell gestell = new Gestell(segment, 1);
        Schublade schublade = new Schublade(gestell, 1);
        Rack rack = new Rack(12, 15, 15, new Behaeltertyp("Behaeltertyp", 3, 4, 5, null), schublade, 0);
        assertFalse(schublade.getRack(0) == null);
        assertTrue(RackSteuerung.getInstance().removeRack(rack));
        assertTrue(schublade.getRack(0) == null);
    }

    /**
     * testet die erstelle neue Rack Methode
     */
    @Test
    public void testErstelleRack(){
        Behaeltertyp behaeltertyp = new Behaeltertyp("a",1,1,1, new Deckeltyp("b"));
        Schublade schublade = new Schublade(null, 0);
        Rack rack = RackSteuerung.getInstance().erstelleRack(behaeltertyp, schublade, 0);
        assertNotNull(rack);
    }

    /**
     * testet die getLetterString Methode
     */
     @Test
    public void testGetLetterString(){
         int a = 1;
         int ab = 28;
         assertEquals("A", RackSteuerung.getInstance().getLetterString(a));
         assertEquals("AB", RackSteuerung.getInstance().getLetterString(ab));
     }
}
