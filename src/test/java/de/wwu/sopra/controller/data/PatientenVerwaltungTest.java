package de.wwu.sopra.controller.data;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

import de.wwu.sopra.controller.data.PatientenVerwaltung;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.wwu.sopra.model.Patient;

/**
 * Testet die PatientenVerwaltung
 * @author Gruppe 5
 */
class PatientenVerwaltungTest {

    Patient patient1;
    Patient patient2;
    PatientenVerwaltung pv;
    
    /**
     * Erstellt zwei Patienten, speichere Instanz der PatientenVerwaltung und 
     * setze patientenSet auf new HashSet<Patient> damit sie zu Anfang immer leer ist
     * @throws Exception
     */
    @BeforeEach
    void setUp() throws Exception {
        patient1 = new Patient("Steffi", "Peters",19  , "Lummerland");
        patient2 = new Patient("Miriam", "Adlev", 19, "Mond");
        pv=PatientenVerwaltung.getInstance();
        pv.setPatientenSet(new HashSet<Patient>());
    }

    /**
     * Teste Getter und Setter
     */
    @Test
    void setGetTest() {
        HashSet<Patient> patienten= new HashSet<Patient>();
        patienten.add(patient1);
        pv.setPatientenSet(patienten);
        assertTrue(pv.getPatientenSet().equals(patienten));
    } 
    
    /**
     * Testet die add Methode
     */
    @Test
    void addPatient() {
        pv.addPatient(patient2);
        pv.getPatientenSet();
        HashSet<Patient> patienten = new HashSet<Patient>();
        patienten.add(patient2);
        assertTrue((pv.getPatientenSet()).equals(patienten));
    }
    
    /**
     * Testet die Remove Methode
     */
    @Test
    void removePatient() {
        pv.addPatient(patient1);
        pv.addPatient(patient2);
        pv.removePatient(patient1);
        pv.getPatientenSet();
        HashSet<Patient> patienten = new HashSet<Patient>();
        patienten.add(patient2);
        assertTrue((pv.getPatientenSet()).equals(patienten));
    }
    
    
    /**
     * Testet die has Methode
     */
    @Test
    void hasPatient() {
        pv.addPatient(patient1);
        assertTrue(pv.hasPatient(patient1));
        assertFalse(pv.hasPatient(patient2)); 
    }
}
                                    