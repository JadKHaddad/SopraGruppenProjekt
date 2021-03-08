package de.wwu.sopra.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
/**
 * Testet den Patienten
 * @author Gruppe 5
 */
class PatientTest {

	Patient patient;
	Studienteilnehmer teilnehmer1;
	Studienteilnehmer teilnehmer2;
	Studie studie1; 
	
	/**
	 * Bereitet den Test vor
	 */
	@BeforeEach
	void setup() {
		patient = new Patient("Peters", "Steffi", 19, "Lummerland");
		studie1 = new Studie("studie1", 5);
		teilnehmer1 = new Studienteilnehmer(patient, studie1);
	}
	/**
	 * Testet die getter-Methoden
	 */
	@Test
	void gettest() {
		assertTrue("Peters".contentEquals(patient.getName()));
		assertTrue("Steffi".contentEquals(patient.getVorname()));
		assertTrue(19==patient.getAlter());
		assertTrue("Lummerland".contentEquals(patient.getAnschrift()));
	}
	/**
	 * Testet die setter-Methoden
	 */
	@Test
	void settest() {
		patient.setAlter(20);
		patient.setVorname("Miriam");
		patient.setName("Meyer");
		patient.setAnschrift("Mond");
		assertTrue("Meyer".contentEquals(patient.getName()));
		assertTrue("Miriam".contentEquals(patient.getVorname()));
		assertTrue(20==patient.getAlter());
		assertTrue("Mond".contentEquals(patient.getAnschrift()));
	}
	
	/**
	 * Testet die add, remove und has Methode
	 */
	@Test
	void addRemoveHasStudienteilnehmerTest() {
	    patient.addStudienteilnehmer(teilnehmer1);
	    patient.addStudienteilnehmer(teilnehmer2);
	    patient.removeStudienteilnehmer(teilnehmer2);
	    patient.removeStudienteilnehmer(null);
	    assertTrue(patient.hasStudienteilnehmer(teilnehmer1));
	    assertFalse(patient.hasStudienteilnehmer(teilnehmer2));
	    HashSet<Studienteilnehmer> studienteilnehmer = new HashSet<Studienteilnehmer>();
	    studienteilnehmer.add(teilnehmer1);
	    assertTrue(studienteilnehmer.containsAll((patient.getStudienteilnehmer())));
	    
	}
	@Test
	void removeStudienTest() {
	    patient.addStudienteilnehmer(teilnehmer1);
        patient.addStudienteilnehmer(teilnehmer2);
        patient.removeStudienteilnehmer(teilnehmer2);
        patient.removeStudienteilnehmer(teilnehmer1);
        assertTrue(patient.getStudienteilnehmer().isEmpty());
    
	}
	
	
}
