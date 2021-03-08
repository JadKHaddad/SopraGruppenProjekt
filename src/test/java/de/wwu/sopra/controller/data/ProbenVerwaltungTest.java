package de.wwu.sopra.controller.data;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

import de.wwu.sopra.controller.data.ProbenVerwaltung;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.wwu.sopra.model.Probe;

/**
 * Testet die Probenverwaltung
 * @author Gruppe 5
 */
class ProbenVerwaltungTest {

    Probe probe1;
    Probe probe2;
    ProbenVerwaltung pv;
     
    /**
     * Erstellt zwei Proben, speichere Instanz der ProbenVerwaltung und 
     * setze probenSet auf new HashSet<Probe> damit sie zu Anfang immer leer ist
     * @throws Exception
     */
    @BeforeEach
    void setUp() throws Exception {
        probe1 = new Probe(444, null, null, null, null, null, null);
        probe2 = new Probe(222, null, null, null, null, null, null);
        pv=ProbenVerwaltung.getInstance();
        pv.setProbenSet(new HashSet<Probe>());
    }

    /**
     * Teste Getter und Setter
     */
    @Test
    void setGetTest() {
        HashSet<Probe> proben= new HashSet<Probe>();
        proben.add(probe1);
        pv.setProbenSet(proben);
        assertTrue(pv.getProbenSet().equals(proben));
    }
    
    /**
     * Testet die add Methode
     */
    @Test
    void addPatient() {
        pv.addProbe(probe2);
        pv.getProbenSet();
        HashSet<Probe> proben = new HashSet<Probe>();
        proben.add(probe2);
        assertTrue((pv.getProbenSet()).equals(proben));
    }
    
    /**
     * Testet die Remove Methode
     */
    @Test
    void removePatient() {
        pv.addProbe(probe1);
        pv.addProbe(probe2);
        pv.removeProbe(probe1);
        pv.getProbenSet();
        HashSet<Probe> proben = new HashSet<Probe>();
        proben.add(probe2);
        assertTrue((pv.getProbenSet()).equals(proben));
    }
    
    /**
     * Testet die has Methode
     */
    @Test
    void hasPatient() {
        pv.addProbe(probe1);
        assertTrue(pv.hasProbe(probe1));
        assertFalse(pv.hasProbe(probe2));
    }
}
