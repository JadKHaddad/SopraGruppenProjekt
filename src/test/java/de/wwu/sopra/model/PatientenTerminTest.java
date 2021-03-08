package de.wwu.sopra.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

/**
 * Testet den Patienten Termin
 * @author Gruppe 5
 */
public class PatientenTerminTest {

    /**
     * Testet getTemin
     */
    @Test
    public void testGetTermin() {
        LocalDateTime d = LocalDateTime.now();
        PatientenTermin pt = new PatientenTermin(null, d, null);
        assertEquals(pt.getTermin(), d);
    }

    /**
     * testet setTermin
     */
    @Test
    public void testSetTermin() {
        LocalDateTime d = LocalDateTime.now();
        PatientenTermin pt = new PatientenTermin(null, null, null);
        pt.setTermin(d);
        assertEquals(pt.getTermin(), d);
    }

    /**
     * testet isDruchgefuehrt
     */
    @Test
    public void testIsDurchgefuerhrt() {
        PatientenTermin pt = new PatientenTermin(null, null, null);
        assertEquals(pt.isDurchgefuehrt(), false);
    }

    /**
     * testet setDurchgefuehrt
     */
    @Test
    public void testSetDurchgefuerhrt() {
        PatientenTermin pt = new PatientenTermin(null, null, null);
        pt.setDurchgefuehrt(true);
        assertEquals(pt.isDurchgefuehrt(), true);
    }

    /**
     * testet isAbgesagt
     */
    @Test
    public void testIsAbgesagt() {
        PatientenTermin pt = new PatientenTermin(null, null, null);
        assertEquals(pt.isAbgesagt(), false);
    }

    /**
     * testet setAbgesagt
     */
    @Test
    public void testSetAbgesagt() {
        PatientenTermin pt = new PatientenTermin(null, null, null);
        pt.setAbgesagt(true);
        assertEquals(pt.isAbgesagt(), true);
    }

    /**
     * testet getVisite
     */
    @Test
    public void testGetVisite() {
        Visite v = new Visite("Test", 1, null);
        PatientenTermin pt = new PatientenTermin(v, null, null);
        assertEquals(pt.getVisite(), v);
    }

    /**
     * tetet setVisite
     */
    @Test
    public void testSetVisite() {
        Visite v = new Visite("Test", 1, null);
        PatientenTermin pt = new PatientenTermin(null, null, null);
        pt.setVisite(v);
        assertEquals(pt.getVisite(), v);
    }

    /**
     * testet addMessung und hasMessung
     */
    @Test
    public void testAddMessungHasMessung() {
        PatientenTermin pt = new PatientenTermin(null, null, null);
        Messung m = new Messung("0", null, pt);
        pt.addMessung(m);
        assertEquals(pt.hasMessung(m), true);
    }

    /**
     * testet removeMessung
     */
    @Test
    public void testRemoveMessung() {
        PatientenTermin pt = new PatientenTermin(null, null, null);
        Messung m = new Messung("0", null, pt);
        pt.addMessung(m);
        assertEquals(pt.hasMessung(m), true);
        pt.removeMessung(m);
        assertEquals(pt.hasMessung(m), false);

    }

    /**
     * testet addProbe und hasProbe
     */
    @Test
    public void testAddProbeHasProbe() {
        PatientenTermin pt = new PatientenTermin(null, null, null);
        Probe p = new Probe(0, null, null, null, null, pt, null);
        pt.addProbe(p);
        assertEquals(pt.hasProbe(p), true);

    }
     
    /**
     * testet removeProbe
     */
    @Test
    public void testRemoveProbe() {
        PatientenTermin pt = new PatientenTermin(null, null, null);
        Probe p = new Probe(0, null, null, null, null, pt, null);
        pt.addProbe(p);
        assertEquals(pt.hasProbe(p), true);
        pt.removeProbe(p);
        assertEquals(pt.hasProbe(p), false);
    }
    
    /**
     * testet getTeilnehmer und setTeilnehmer
     */
    @Test
    public void testGetSetTeilnehmer() {
        PatientenTermin pt = new PatientenTermin(null, null, null);
        Studienteilnehmer st = new Studienteilnehmer(null, null);
        pt.setStudienTeilnehmer(st);
        pt.setStudienTeilnehmer(st);
        pt.setStudienTeilnehmer(st);
        assertEquals(pt.getStudienTeilnehmer(), st);
    }
    
    /**
     * Testet getMessungen
     */
    @Test
    public void testGetMessungen() {
        PatientenTermin pt = new PatientenTermin(null, null, null);
        Messung m = new Messung("5", null, null);
        pt.addMessung(m);
        assertTrue(pt.getMessungen().contains(m));
    }
}





