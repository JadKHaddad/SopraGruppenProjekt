package de.wwu.sopra.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.wwu.sopra.model.Messtyp;
import de.wwu.sopra.model.Messung;
import de.wwu.sopra.model.Patient;
import de.wwu.sopra.model.PatientenTermin;
import de.wwu.sopra.model.Probe;
import de.wwu.sopra.model.ProbenInfo;
import de.wwu.sopra.model.ProbenKategorie;
import de.wwu.sopra.model.ProbenStatus;
import de.wwu.sopra.model.Studie;
import de.wwu.sopra.model.Studienteilnehmer;
import de.wwu.sopra.model.Visite;

/**
 * Testet die PatientenSteuerung
 * @author Gruppe 5
 */
public class PatientenSteuerungTest {
    
    /**
     * Testet, ob ein Patient entfernt wird
     */
    @Test
    public void testPatientEntfernen() {
        PatientenSteuerung patientenSteuerung = PatientenSteuerung.getInstance();
        Studie studie = new Studie("Testudie", 100);
        Patient patient = new Patient("Huchen", "Herbert", 200, "asdhfaerf");
        Studienteilnehmer studienTeilnehmer = new Studienteilnehmer(patient, studie);
        Visite visite = new Visite("Testvisite", 3, studie);
        visite.addMesstyp(new Messtyp ("Blutdruck", "Dings"));
        ProbenInfo probenInfo = new ProbenInfo(4f, new ProbenKategorie("test", "ml", 0, 5), null);
        visite.addProbenInfo(probenInfo);
        studie.addVisite(visite);
        PatientenTermin patientenTermin = new PatientenTermin(visite, null, studienTeilnehmer);
        patientenTermin.addMessung(new Messung("2", new Messtyp("Blutdruck", "Dings"), patientenTermin));
        patientenTermin.addProbe(new Probe(5, null, probenInfo, ProbenStatus.EINGELAGERT, null, patientenTermin, null));
        patientenSteuerung.patientEntfernen(patient);
        
        assertEquals(patient.getStudienteilnehmer().isEmpty(), true);
        assertEquals(studie.getTeilnehmer().isEmpty(), true);
    }
}
