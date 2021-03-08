package de.wwu.sopra.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Testet die Studienteilnehmer
 * @author Gruppe 5
 */
class StudienteilnehmerTest {

    /**
     * testet getStudie
     */
    @Test
    public void testGetStudie() {
        Studienteilnehmer st = new Studienteilnehmer(null, null);
        Studie s = new Studie(null, 100);
        s.addTeilnehmer(st);
        assertEquals(s, st.getStudie());
    }

    /**
     * testet setStudie
     */
    @Test
    public void testSetStudie() {
        Studienteilnehmer st = new Studienteilnehmer(null, null);
        Studie s = new Studie(null, 100);
        st.setStudie(s);
        assertEquals(s, st.getStudie());
    }

    /**
     * testet getPatient und setPatient
     */
    @Test
    public void testGetPatientSetPatient() {
        Studienteilnehmer st = new Studienteilnehmer(null, null);
        Patient p = new Patient(null, null, 0, null);
        st.setPatient(p);
        assertEquals(p, st.getPatient());
    }
    

    /**
     * testet addPatientenTermin und hasPatientenTermin
     */
    @Test
    public void testAddPatientenTerminHasPatientenTermin() {
        Studienteilnehmer st = new Studienteilnehmer(null, null);
        PatientenTermin pt = new PatientenTermin(null, null, null);
        st.addPatientenTermin(pt);
        assertEquals(true, st.hasPatientenTermin(pt));
    }

    /**
     * testet removePatientenTermin
     */
    @Test
    public void testRemovePatientenTermin() {
        Studienteilnehmer st = new Studienteilnehmer(null, null);
        PatientenTermin pt = new PatientenTermin(null, null, null);
        st.addPatientenTermin(pt);
        assertEquals(true, st.hasPatientenTermin(pt));
        st.removePatientenTermin(pt);
        assertEquals(false, st.hasPatientenTermin(pt));
    }
}
