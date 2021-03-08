package de.wwu.sopra.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Testet die Schublade
 * 
 * @author Gruppe 5
 */
public class SchubladeTest {

    /**
     * testet wenn bei has Rack Null uebergeben wird
     */
    @Test
    public void hasRackNull() {
        Gestell gestell = new Gestell(null, 0);
        Schublade schublade = new Schublade(gestell, 0);
        schublade.hasRack(null);
    }

    /**
     * Test getRacks
     */
    @Test
    public void testGetRacks() {
        Gestell gestell = new Gestell(null, 0);
        Schublade schublade = new Schublade(gestell, 0);
        Rack rack = new Rack(0, 0, 0, null, schublade, 0);
        schublade.setRack(rack, 0);
        assertFalse((schublade.getRacks().equals(null)));

    }

    /**
     * testet die getGestell Methode der Schublade Klasse
     */
    @Test
    public void testGetGestell() {
        Gestell g = new Gestell(null, 0);
        Schublade s = new Schublade(g, 1);
        assertEquals(g, s.getGestell());
        assertEquals(1, s.getStelleImGestell());
    }

    /**
     * testet setRack und hasRack
     */
    @Test
    public void testSetRackHasRack() {
        Schublade s = new Schublade(null, 0);
        Rack r = new Rack(0, 0, 0, null, null, 0);
        s.setRack(r, 2);
        assertTrue(s.hasRack(r));
        assertEquals(r, s.getRack(2));
    }

    /**
     * testet das aendern eines Racks
     */
    @Test
    public void testGleichesRackAendern() {
        Schublade s1 = new Schublade(null, 0);
        Rack r1 = new Rack(0, 0, 0, null, s1, 2);

        s1.setRack(r1, 2);
        assertEquals(r1, s1.getRack(2));
    }

    /**
     * testet ob man eine null Referenz als Rack uebergeben kann
     */
    @Test
    public void testNullRackAendern() {
        Schublade s = new Schublade(null, 0);
        Rack r = new Rack(0, 0, 0, null, s, 2);
        assertEquals(r, s.getRack(2));
        s.setRack(null, 2);
        assertNull(s.getRack(2));
    }

    /**
     * testet, ob man eine Rack Referenz aendern kann
     */
    @Test
    public void testAnderesRackAendern() {
        Schublade s1 = new Schublade(null, 0);
        Schublade s2 = new Schublade(null, 0);
        Rack r1 = new Rack(0, 0, 0, null, s1, 2);
        Rack r2 = new Rack(0, 0, 0, null, s2, 2);
        s1.setRack(r2, 2);
        assertEquals(r2, s1.getRack(2));
    }

    /**
     * komplexerer Testfall um die Konsistenz zwischen Schublade und Rack zu pruefen
     */
    @Test
    public void testAnderesRackAendernExtrem() {
        Schublade s1 = new Schublade(null, 0);
        Schublade s2 = new Schublade(null, 0);
        Rack r1 = new Rack(0, 0, 0, null, s1, 2);
        Rack r2 = new Rack(0, 0, 0, null, s2, 2);
        s1.setRack(r2, 2);
        s1.setRack(r1, 1);
        s1.setRack(null, 2);
        s2.setRack(r1, 0);
        s1.setRack(r1, 1);
        s1.setRack(r1, 2);
        s1.setRack(null, 2);
        s1.setRack(r2, 2);
        assertEquals(r1, s2.getRack(0));
        assertEquals(null, s1.getRack(0));
        assertEquals(r1, s1.getRack(1));
        assertEquals(r2, s1.getRack(2));
        assertEquals(null, r1.getSchublade());
        assertEquals(s1, r2.getSchublade());

    }

    /**
     * testet die getRack Methode und den Konstruktor der Schublade Klasse
     */
    @Test
    public void testKonstruktorUndGetRack() {
        Schublade schublade = new Schublade(null, 0);
        for (int i = 0; i < Schublade.RACKS_PRO_SCHUBLADE; i++) {
            assertNull(schublade.getRack(i));
        }
        assertThrows(IndexOutOfBoundsException.class, () -> {
            schublade.getRack(Schublade.RACKS_PRO_SCHUBLADE);
        });
    }

    /**
     * Testet die GetKuehlschrank Methode
     */
    @Test
    public void testGetKuehlschrank() {
        Kuehlschrank kuehlschrank = new Kuehlschrank("asdf", 0.1f, 0.1f, 0.1f, 0.1f, 4, null);
        Segment segment = kuehlschrank.getSegment(2);
        assertEquals(kuehlschrank, segment.getKuehlschrank());
        assertEquals(2, segment.getStelleImKuehlschrank());
    }
}
