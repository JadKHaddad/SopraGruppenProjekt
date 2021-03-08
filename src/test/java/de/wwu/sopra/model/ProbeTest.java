/**
 *
 */
package de.wwu.sopra.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Gruppe 5
 *
 */
class ProbeTest {

    LocalDateTime entnahmeDatum;
    ProbenStatus status;
    ProbenKategorie kategorie;
    ProbenInfo info;
    Probe probe;
    // ProbenPlatz
    // PatientenTermin
    /*
     *
     * @TODO Alle Konstruktoren muessen um Behaeltertyp und Patiententermin ergänzt
     * werden
     */

    @BeforeEach
    public void setUp() {
        entnahmeDatum = LocalDateTime.now();
        status = ProbenStatus.EINGELAGERT;
        kategorie = new ProbenKategorie("Blut", "ml", 20.0f, 5.0f);
        info = new ProbenInfo(1.2f, kategorie, null);
        probe = new Probe(123, entnahmeDatum, info, status, null, null, null);
    }

    /**
     * getBid testen
     */
    @Test
    public void testGetBid() {

        int expResult = 123;
        int result = probe.getBid();
        assertEquals(expResult, result);

    }

    /**
     * v setBid() testen
     */
    @Test
    public void testSetBid() {

        int bid = 456;
        probe.setBid(bid);
        assertEquals(probe.getBid(), bid);

    }

    /**
     * getEntnahmeDatum testen
     */
    @Test
    public void testGetEntnahmeDatum() {

        LocalDateTime expResult = entnahmeDatum;
        LocalDateTime result = probe.getEntnahmeDatum();
        assertEquals(expResult, result);
    }

    /**
     * setEntnahmeDatum testen
     */
    @Test
    public void testSetEntnahmeDatum() {

        LocalDateTime entnahmeDatum = LocalDateTime.now();
        probe.setEntnahmeDatum(entnahmeDatum);
        assertEquals(probe.getEntnahmeDatum(), entnahmeDatum);
    }

    /**
     * getInfo testen
     */
    @Test
    public void testGetInfo() {

        ProbenInfo result = probe.getInfo();
        assertEquals(info, result);

    }

    /**
     * setInfo testen
     */
    @Test
    public void testSetInfo() {

        ProbenInfo info = new ProbenInfo(1.5f, kategorie, null);
        probe.setInfo(info);
        assertEquals(probe.getInfo(), info);
    }

    /**
     * getStatus testen
     */
    @Test
    public void testGetStatus() {

        ProbenStatus expResult = ProbenStatus.EINGELAGERT;
        ProbenStatus result = probe.getStatus();
        assertEquals(expResult, result);

    }

    /**
     * setStatus testen
     */
    @Test
    public void testSetStatus() {

        ProbenStatus status = ProbenStatus.VERLOREN;
        probe.setStatus(status);
        assertEquals(probe.getStatus(), status);
    }

    /**
     * testet getTermin und setTermin
     */
    @Test
    public void testGetTerminSetTermin() {
        PatientenTermin expResult = new PatientenTermin(null, null, null);
        probe.setTermin(expResult);
        PatientenTermin result = probe.getTermin();
        assertEquals(expResult, result);

    }

    /**
     * testet getProbenPlatz setProbenplatz
     */
    @Test
    public void testGetProbenPlatzSetProbenPlatz() {
        ProbenPlatz expResult = new ProbenPlatz(0, 0, null, null);
        probe.setProbenPlatz(expResult);
        ProbenPlatz result = probe.getProbenplatz();
        assertEquals(expResult, result);
    }

    /**
     * testet getBehaeltertyp setBehaeltertyp
     */
    @Test
    public void testGetBehaeltertypSetBehaeltertyp() {
        Behaeltertyp expResult = new Behaeltertyp("b2",0, 0, 0, null);
        probe.setBehaeltertyp(expResult);
        Behaeltertyp result = probe.getBehaeltertyp();
        assertEquals(expResult, result);

    }
 
}
