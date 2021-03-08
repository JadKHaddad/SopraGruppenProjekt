package de.wwu.sopra.controller;

import de.wwu.sopra.controller.data.BidVerwaltung;
import de.wwu.sopra.controller.data.LagerVerwaltung;
import de.wwu.sopra.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * testet die Proben Steuerung
 * @author  Gruppe 5
 */
public class ProbenSteuerungTest {

    @BeforeEach
    public void reset() {
        LagerVerwaltung.getInstance().setRaumSet(new HashSet<>());
    }

    /**
     *
     */
    @Test
    public void testGetInstance(){
        assertNotNull(ProbenSteuerung.getInstance());
    }

    @Test
    public void testGetAnzahlAnBenoetigetenProbenPlaetzen() {
        ProbenSteuerungExtension probenSteuerung = new ProbenSteuerungExtension();
        Studie studie = new Studie("studie", 21);
        ProbenKategorie probenKategorie = new ProbenKategorie("n", "e", 7, 5);
        Behaeltertyp behaelterTyp = new Behaeltertyp("Behaeltertyp", 3, 4, 5, null);
        ProbenInfo probenInfo = new ProbenInfo(2, probenKategorie, behaelterTyp);
        Visite visite = new Visite("visite", 2, studie);
        visite.addProbenInfo(probenInfo);
        Map<ProbenKategorie, Map<Behaeltertyp, Integer>> anzahlAnBenoetigenProbenplaetzen = probenSteuerung.getAnzahlAnBenoetigenProbenplaetzen(studie);
        assertEquals(21, anzahlAnBenoetigenProbenplaetzen.get(probenKategorie).get(behaelterTyp));
    }

    /**
     *
     */
    @Test
    public void testReserviereProben(){
        ProbenSteuerungExtension probenSteuerung = new ProbenSteuerungExtension();
        Raum raum = new Raum("Raum1", 12, 4);
        LagerVerwaltung.getInstance().addRaum(raum);
        Kuehlschrank kuehlschrank = new Kuehlschrank("kühlschrank", 3f, 40f, 67f, 6f, 2, raum);
        Studie studie = new Studie("studie", 21);
        ProbenKategorie probenKategorie = new ProbenKategorie("n", "e", 7f, 5f);
        Behaeltertyp behaelterTyp = new Behaeltertyp("Behaeltertyp", 3, 4, 5, null);
        ProbenInfo probenInfo = new ProbenInfo(2, probenKategorie, behaelterTyp);
        Visite visite = new Visite("visite", 2, studie);
        visite.addProbenInfo(probenInfo);
        assertTrue(probenSteuerung.reserviereProben(studie));
    }

    /**
     *
     */
    @Test
    public void testFuegeProbeEin(){
        ProbenSteuerungExtension probenSteuerung = new ProbenSteuerungExtension();
        Raum raum = new Raum("Raum1", 12, 4);
        LagerVerwaltung.getInstance().addRaum(raum);
        Kuehlschrank kuehlschrank = new Kuehlschrank("kühlschrank", 3f, 40f, 67f, 6f, 2, raum);
        ProbenKategorie probenKategorie = new ProbenKategorie("n", "e", 7f, 5f);
        Behaeltertyp behaelterTyp = new Behaeltertyp("Behaeltertyp", 3, 4, 5, null);
        ProbenInfo probenInfo = new ProbenInfo(2, probenKategorie, behaelterTyp);
        Studie studie = new Studie("studie", 2);
        Patient patient = new Patient("Patient", "V", 10, "An");
        Studienteilnehmer teilnehmer = new Studienteilnehmer(patient, studie);
        Visite visite = new Visite("visite", 2, studie);
        visite.addProbenInfo(probenInfo);
        PatientenTermin termin = new PatientenTermin(visite, null, teilnehmer);
        Probe probe = new Probe(10, null, probenInfo, ProbenStatus.NEU, null, termin, behaelterTyp);
        assertTrue(probenSteuerung.reserviereProben(studie));
        assertTrue(probenSteuerung.fuegeProbeEin(probe));
    }

    private static class ProbenSteuerungExtension extends ProbenSteuerung {

        @Override
        public Map<ProbenKategorie, Map<Behaeltertyp, Integer>> getAnzahlAnBenoetigenProbenplaetzen(Studie studie) {
            return super.getAnzahlAnBenoetigenProbenplaetzen(studie);
        }
    }
}
