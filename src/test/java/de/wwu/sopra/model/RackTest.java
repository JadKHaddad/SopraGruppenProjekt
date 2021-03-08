package de.wwu.sopra.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Testet das Rack
 * 
 * @author Gruppe 5
 */
public class RackTest {

    Probe probe1;
    Probe probe2;
    Probe probe3;
    Probe probe4;
    Rack rack;
    Behaeltertyp behaeltertyp1;
    Behaeltertyp behaeltertyp2;
    Schublade schublade1;
    Schublade schublade2;
    Gestell gestell;
    Deckeltyp deckeltyp;
    /**
     * Erstellt fuer die Tests benoetigte Objekte
     */
    @BeforeEach
    public void setProbeGetProbenPlatzTest() {
        behaeltertyp1 = new Behaeltertyp("b1", 10, 200, 10 * 10 * 200 * 3, deckeltyp);
        behaeltertyp2 = new Behaeltertyp("b2", 10, 200, 10 * 10 * 200 * 3, deckeltyp);
        probe1 = new Probe(1, null, null, null, null, null, behaeltertyp1);
        probe2 = new Probe(2, null, null, null, null, null, behaeltertyp1);
        probe3 = new Probe(3, null, null, null, null, null, behaeltertyp2);
        probe4 = new Probe(4, null, null, null, null, null, null);
        gestell = new Gestell(null, 3);
        schublade1 = new Schublade(gestell, 3);
        schublade2 = new Schublade(gestell, 4);
        rack = new Rack(1, 2, 3, behaeltertyp1, schublade1, 0);
        gestell = new Gestell(null, 3);
    }

    /**
     * setBid testen
     */
    @Test
    public void testSetBid() {
        int bid = 12345;
        Rack RackTest = new Rack(1234, 3, 3, null, null, 0);
        RackTest.setBid(bid);
        assertEquals(RackTest.getBid(), bid);
    }

    /**
     * getBid testen
     */
    @Test
    public void testGetBid() {
        Rack RackTest = new Rack(1234, 3, 3, null, null, 0);
        int expResult = 12345;
        RackTest.setBid(12345);
        int result = RackTest.getBid();
        assertEquals(expResult, result);
    }

    /**
     * setZeilen testen
     */
    @Test
    public void testSetZeilen() {
        int zeilen = 5;
        Rack RackTest = new Rack(1234, 3, 3, null, null, 0);
        RackTest.setZeilen(zeilen);
        assertEquals(RackTest.getZeilen(), zeilen);
    }

    /**
     * getZeilen testen
     */
    @Test
    public void testGetZeilen() {
        Rack RackTest = new Rack(1234, 3, 3, null, null, 0);
        int expResult = 5;
        RackTest.setZeilen(5);
        int result = RackTest.getZeilen();
        assertEquals(expResult, result);
    }

    /**
     * setSpalten testen
     */
    @Test
    public void testSetSpalten() {
        int spalten = 5;
        Rack RackTest = new Rack(1234, 3, 3, null, null, 0);
        RackTest.setSpalten(spalten);
        assertEquals(RackTest.getSpalten(), spalten);
    }

    /**
     * getBid testen
     */
    @Test
    public void testGetSpalten() {
        Rack RackTest = new Rack(1234, 3, 3, null, null, 0);
        int expResult = 5;
        RackTest.setSpalten(5);
        int result = RackTest.getSpalten();
        assertEquals(expResult, result);
    }

    /**
     * setBehaeltertyp testen
     */
    @Test
    public void testSetBehaeltertyp() {
        Behaeltertyp behaeltertyp = new Behaeltertyp("b2", 1.2f, 5, 5, null);
        Rack RackTest = new Rack(123, 3, 3, new Behaeltertyp("b2", 1, 1, 1, null), null, 0);
        RackTest.setBehaeltertyp(behaeltertyp);
        assertEquals(RackTest.getBehaeltertyp(), behaeltertyp);
    }

    /**
     * getBehaeltertyp testen
     */
    @Test
    public void testGetBehaeltertyp() {
        Rack RackTest = new Rack(123, 3, 3, new Behaeltertyp("b2", 1, 1, 1, null), null, 0);
        Behaeltertyp expResult = new Behaeltertyp("b2", 1.2f, 5, 5, null);
        RackTest.setBehaeltertyp(expResult);
        Behaeltertyp result = RackTest.getBehaeltertyp();
        assertEquals(expResult, result);
    }

    /**
     * testet GetProbe setProbe
     */
    @Test
    public void testGetProbeSetProbe() {
        Rack r = new Rack(0, 3, 3, null, null, 0);
        Probe p = new Probe(0, null, null, null, null, null, null);
        r.setProbe(1, 1, p);
        assertEquals(r.getProbe(1, 1), p);
    }

    /**
     * testet getSchublade und setRack
     */
    @Test
    public void testGetSchublade() {
        Rack r = new Rack(0, 3, 3, null, null, 0);
        Schublade s = new Schublade(null, 0);
        s.setRack(r, 2);
        assertEquals(r.getSchublade(), s);
        assertEquals(r.getStelleInSchublade(), 2);
    }

    /**
     * Testet SetSchublade
     */
    @Test
    public void testSetSchublade() {
        Rack r = new Rack(0, 3, 3, null, null, 0);
        Schublade s = new Schublade(null, 0);
        r.setSchublade(s, 2);
        assertTrue(r.getSchublade().hasRack(r));
        assertEquals(r, s.getRack(2));
    }

    /**
     * Testet die Standartfaelle, wenn man eine Probe in ein Rack stellt und
     * entnimmt
     */
    @Test
    public void testSetGetProbe() {
        rack.setProbe(1, 2, probe2);
        rack.setProbe(0, 2, probe1);
        assertEquals(rack.getProbe(1, 2), probe2);
        assertEquals(rack.getProbe(0, 0), null);
        assertEquals(rack.getBehaeltertyp(), behaeltertyp1);
    }

    /**
     * Testet Sonderfaelle von SetProbe und GetProbe  
     */
    @Test
    public void testSetGetProbeSonderfaelle() {
        rack.setProbe(1, 2, probe1);
        rack.setProbe(1, 2, null);
        assertEquals(rack.getProbe(1, 2), null);
        rack.setProbe(1, 2, probe3);
        assertEquals(rack.getProbe(1, 2), null);
        rack.setProbe(0, 0, probe1);
        assertEquals(rack.getProbe(0, 0), probe1);
    }

    /**
     * Testet getProbenPlatz
     */
    @Test
    public void getProbenPlatzTest() {
        rack.setProbe(1, 2, probe2);
        rack.setProbe(0, 2, probe1);
        assertEquals(rack.getProbenplatz(1, 2).getProbe(), probe2);
        assertEquals(rack.getProbenplatz(1, 1).getProbe(), null);

    }

    /**
     * Testet setSchublade wenn die gleiche Schublade wieder an die gleiche Stelle gestellt werden soll
     */
    @Test
    public void setGleicheSchubladeTest() {
        rack.setSchublade(schublade1, 1);
        assertEquals(rack.getSchublade(), schublade1);
        assertEquals(rack,schublade1.getRack(1));
    }

    /**
     * Testet setSchublade wenn eine Null-Referenz uebergeben wird
     */
    @Test
    public void setNullSchubladeTest() {
        rack.setSchublade(null, 1);
        assertEquals(rack.getSchublade(), null);
        assertEquals(null,schublade1.getRack(1));
        ;
    }

    /**
     * testet den Normalfall von SetSchublade
     */
    @Test
    public void setAndereSchubladeTest() {
        rack.setSchublade(schublade2, 1);
        assertEquals(rack.getSchublade(), schublade2);
        assertEquals(rack,schublade2.getRack(1));
        assertEquals(null,schublade1.getRack(1));
    } 
}
